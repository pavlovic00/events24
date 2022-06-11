package com.pavlovic.cubes.events24.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pavlovic.cubes.events24.R;
import com.pavlovic.cubes.events24.data.database.AppDatabase;
import com.pavlovic.cubes.events24.data.model.Event;
import com.pavlovic.cubes.events24.databinding.FragmentFavouritesBinding;
import com.pavlovic.cubes.events24.ui.adapter.EventsAdapter;

import java.util.ArrayList;

public class FavouritesFragment extends Fragment {

    private ArrayList<Event> favouritesEvents;
    private FragmentFavouritesBinding binding;
    private String selectedType = "All";
    private EventsAdapter adapter;

    public FavouritesFragment() {
        // Required empty public constructor
    }

    public static FavouritesFragment newInstance() {
        FavouritesFragment fragment = new FavouritesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavouritesBinding.inflate(inflater, container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favouritesEvents = new ArrayList<>();
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter = new EventsAdapter(getContext(),favouritesEvents,R.layout.rv_item_search_rv);
        binding.recyclerView.setAdapter(adapter);

        binding.buttonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedType = "All";
                updateUI();
            }
        });

        binding.buttonKoncerti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedType = "Koncert";
                updateUI();
            }
        });

        binding.buttonPozorista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedType = "Pozoriste";
                updateUI();
            }
        });

        binding.buttonSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedType = "Sport";
                updateUI();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }

    private void updateUI(){
        if (selectedType.equalsIgnoreCase("All")){
            favouritesEvents = (ArrayList<Event>) AppDatabase.getInstance(getContext()).eventDAO().getAll();
        }
        else if(selectedType.equalsIgnoreCase("Koncert")){
            favouritesEvents = (ArrayList<Event>) AppDatabase.getInstance(getContext()).eventDAO().getAllByType("Koncert");
        }
        else if(selectedType.equalsIgnoreCase("Pozoriste")){
            favouritesEvents = (ArrayList<Event>) AppDatabase.getInstance(getContext()).eventDAO().getAllByType("Pozoriste");
        }
        else if(selectedType.equalsIgnoreCase("Sport")){
            favouritesEvents = (ArrayList<Event>) AppDatabase.getInstance(getContext()).eventDAO().getAllByType("Sport");
        }

        adapter.setData(favouritesEvents);
    }
}