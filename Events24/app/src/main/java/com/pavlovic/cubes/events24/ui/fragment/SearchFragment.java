package com.pavlovic.cubes.events24.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pavlovic.cubes.events24.data.model.Event;
import com.pavlovic.cubes.events24.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pavlovic.cubes.events24.ui.adapter.EventsAdapter;
import com.pavlovic.cubes.events24.databinding.FragmentSearchBinding;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private FirebaseFirestore db;
    private ArrayList<Event> eventList;
    private ArrayList<Event> newList;


    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(inflater,container,false);
        db = FirebaseFirestore.getInstance();
        eventList = new ArrayList<>();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        loadData();
        updateUI();

    }

    private void updateUI() {

//        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        binding.recyclerView.setAdapter(new SearchAdapter(getContext(),newList));


        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(new EventsAdapter(getContext(), eventList, R.layout.rv_item_search_rv));

        binding.textViewCount.setText(" " + eventList.size() + " rezultata");

    }

    private void loadData() {

        db.collection("events")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()) {
                    eventList = new ArrayList<>();

                    for(QueryDocumentSnapshot document : task.getResult()) {
                        eventList.add(new Event(document.getId(), document.getData()));
                    }
                }
                updateUI();
            }
        });



        binding.imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String term = binding.editTextSearch.getText().toString();

                db.collection("events")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()) {
                            eventList = new ArrayList<>();

                            for(QueryDocumentSnapshot document : task.getResult()) {

                                String title = (String) document.get("title");
                                if(title.toLowerCase().contains(term)){
                                    eventList.add(new Event(document.getId(), document.getData()));
                                }
                            }
                        }
                        updateUI();
                    }
                });

            }
        });

    }

}