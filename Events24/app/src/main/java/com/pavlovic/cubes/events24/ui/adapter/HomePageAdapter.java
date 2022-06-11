package com.pavlovic.cubes.events24.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlovic.cubes.events24.R;
import com.pavlovic.cubes.events24.data.model.Authors;
import com.pavlovic.cubes.events24.data.model.Event;
import com.pavlovic.cubes.events24.databinding.RvItemHorizontalRvBinding;

import java.util.ArrayList;

public class HomePageAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Event> firstList;
    private ArrayList<Event> secondList;
    private ArrayList<Event> thirdList;
    private ArrayList<Event> topList;
    private ArrayList<Authors> authorList;
    private ArrayList<Event> orderedByDateList;
    private ArrayList<Event> bottomList = new ArrayList<>();


    public HomePageAdapter(Context context, ArrayList<Event> list,
                           ArrayList<Authors> authorList, ArrayList<Event> orderedByDateList,
                           ArrayList<Event> topList) {
        this.context = context;
        this.firstList = new ArrayList<>();
        this.secondList = new ArrayList<>();
        this.thirdList = new ArrayList<>();
        this.topList = new ArrayList<>();
        this.authorList = authorList;
        this.orderedByDateList = orderedByDateList;
        this.topList = topList;


        if(list!=null) {
            for (Event event : list) {
                if (event.type.equalsIgnoreCase("Koncert")) {
                    firstList.add(event);
                } else if (event.type.equalsIgnoreCase("Sport")) {
                    secondList.add(event);
                } else if (event.type.equalsIgnoreCase("Pozoriste")){
                    thirdList.add(event);
                }
            }
            this.bottomList.add(firstList.get(0));
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RvItemHorizontalRvBinding binding =
                RvItemHorizontalRvBinding.inflate(LayoutInflater.from(context),parent,false);

        return new FirstViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(position == 0) {
            FirstViewHolder firstHolder = (FirstViewHolder)holder;

            firstHolder.binding.type.setText("Top dogadjaji");
            firstHolder.binding.indicator.setColorFilter(Color.parseColor("#FB0079"));
            firstHolder.binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            firstHolder.binding.recyclerView.setAdapter(new EventsAdapter(context, topList, R.layout.rv_item_events_big));
        }

        if(position == 1) {
            FirstViewHolder firstHolder = (FirstViewHolder)holder;

            firstHolder.binding.type.setText("Koncerti");
            firstHolder.binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            firstHolder.binding.recyclerView.setAdapter(new EventsAdapter(context, firstList, R.layout.rv_item_events_big));
        }

        else if(position == 3){
            FirstViewHolder firstHolder = (FirstViewHolder)holder;

            firstHolder.binding.type.setText("Sport");
            firstHolder.binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            firstHolder.binding.recyclerView.setAdapter(new EventsAdapter(context, secondList, R.layout.rv_item_events_big));
        }
        else if(position == 2){
            FirstViewHolder firstHolder = (FirstViewHolder)holder;

            firstHolder.binding.type.setText("Pozoriste");
            firstHolder.binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            firstHolder.binding.recyclerView.setAdapter(new EventsAdapter(context, thirdList, R.layout.rv_item_events_small));
        }

        else if(position == 5){
            FirstViewHolder firstHolder = (FirstViewHolder)holder;

            firstHolder.binding.type.setText("Izvodjaci");
            firstHolder.binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            firstHolder.binding.recyclerView.setAdapter(new AuthorsAdapter(context, authorList, R.layout.rv_item_authors_rv));
        }

        else if(position == 4){

            FirstViewHolder firstHolder = (FirstViewHolder)holder;

            firstHolder.binding.type.setText("Najnoviji dogadjaji");
            firstHolder.binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            firstHolder.binding.recyclerView.setAdapter(new EventsAdapter(context, orderedByDateList, R.layout.rv_item_new_events));
        }

        else if(position == 6){

            FirstViewHolder firstHolder = (FirstViewHolder)holder;

            firstHolder.binding.type.setVisibility(View.GONE);
            firstHolder.binding.indicator.setVisibility(View.GONE);
            firstHolder.binding.showMore.setVisibility(View.GONE);
            firstHolder.binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            firstHolder.binding.recyclerView.setAdapter(new EventsAdapter(context, bottomList, R.layout.home_bottom_text));
        }


    }

    @Override
    public int getItemCount() {
        return 7;
    }

    private class FirstViewHolder extends RecyclerView.ViewHolder{

        private RvItemHorizontalRvBinding binding;

        public FirstViewHolder(RvItemHorizontalRvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
