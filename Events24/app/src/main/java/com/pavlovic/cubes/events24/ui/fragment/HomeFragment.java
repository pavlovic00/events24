package com.pavlovic.cubes.events24.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pavlovic.cubes.events24.data.model.Authors;
import com.pavlovic.cubes.events24.data.model.Event;
import com.pavlovic.cubes.events24.ui.adapter.HomePageAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pavlovic.cubes.events24.databinding.FragmentHomeBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private FirebaseFirestore db;
    private ArrayList<Event> eventList;
    private ArrayList<Event> topList;
    private ArrayList<Event> orderedByDateList;
    private ArrayList<Authors> authorList;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater,container,false);
        db = FirebaseFirestore.getInstance();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadData();
        updateUI();
    }

    private void updateUI() {

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(new HomePageAdapter(getContext(),eventList, authorList, orderedByDateList, topList));

    }

    private void loadData() {

        db.collection("events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()) {
                    eventList = new ArrayList<>();

                    for(QueryDocumentSnapshot document : task.getResult()) {
                        eventList.add(new Event(document.getId(),document.getData()));
                    }

                    orderedByDateList = eventList;
                    Collections.sort(orderedByDateList, new Comparator<Event>(){
                        @Override
                        public int compare(Event t1, Event event) {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            try {
                                String dateStr1 = event.date;
                                String dateStr2 = t1.date;
                                //Parsing the given String to Date object
                                Date date1 = sdf.parse(dateStr1);
                                Date date2 = sdf.parse(dateStr2);
                                Boolean bool1 = date1.after(date2);
                                Boolean bool2 = date1.before(date2);
                                if(bool1){
                                    return -1;
                                }else if(bool2){
                                    return 1;
                                }else
                                    return 0;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return  0;
                        }
                    });
                }



                updateUI();
            }
        });

        db.collection("events").orderBy("viewCount").limit(3).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    topList = new ArrayList<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        topList.add(new Event(document.getId(),document.getData()));

                    }
                    updateUI();
                }
            }
        });

        db.collection("authors").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()) {
                    authorList = new ArrayList<>();

                    for(QueryDocumentSnapshot document : task.getResult()) {
                        authorList.add(new Authors(document.getData()));
                    }
                }

                updateUI();
            }
        });


    }
}
