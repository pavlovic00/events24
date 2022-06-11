package com.pavlovic.cubes.events24.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pavlovic.cubes.events24.data.model.Event;
import com.pavlovic.cubes.events24.databinding.ActivityEventDetailBinding;
import com.pavlovic.cubes.events24.ui.adapter.EventDetailAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EventDetailActivity extends AppCompatActivity {

    private ActivityEventDetailBinding binding;
    private FirebaseFirestore db;
    private ArrayList<Event> firstList;
    private ArrayList<Event> secondList;
    private ArrayList<Event> thirdList;
    private ArrayList<Event> fourList;
    private String eventID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = ActivityEventDetailBinding.inflate(getLayoutInflater());
         setContentView(binding.getRoot());
         //kada se pokrene activity
         eventID = getIntent().getStringExtra("id");

        db = FirebaseFirestore.getInstance();
        db.collection("events").document(eventID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Event event = new Event(documentSnapshot.getId(),documentSnapshot.getData());
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.recyclerView.setAdapter(new EventDetailAdapter(event));
            }
        });

         binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 finish();
             }
         });

         binding.imageViewShare.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent sendIntent = new Intent();
                 sendIntent.setAction(Intent.ACTION_SEND);
                 sendIntent.putExtra(Intent.EXTRA_TEXT,"https://www.youtube.com/watch?v=w7Igm3gFvDA");
                 sendIntent.setType("text/plain");

                 Intent shareIntent = Intent.createChooser(sendIntent, null);
                 startActivity(shareIntent);
             }
         });


        //loadData();
    }




    private void loadData(){
        db.collection("events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    firstList = new ArrayList<>();
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        firstList.add(new Event(document.getId(), document.getData()));
                    }
                }

            }
        });

        db.collection("events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    secondList = new ArrayList<>();
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        secondList.add(new Event(document.getId(),document.getData()));
                    }
                }

            }
        });

        db.collection("events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    thirdList = new ArrayList<>();
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        thirdList.add(new Event(document.getId(),document.getData()));
                    }
                }

            }
        });
        db.collection("events").whereEqualTo("author","Srdjan Dincic").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    fourList= new ArrayList<>();
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        fourList.add(new Event(document.getId(),document.getData()));
                    }
                }

            }
        });

    }

}