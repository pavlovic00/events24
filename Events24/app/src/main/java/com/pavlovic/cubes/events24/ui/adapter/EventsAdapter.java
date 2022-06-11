package com.pavlovic.cubes.events24.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlovic.cubes.events24.data.model.Event;
import com.pavlovic.cubes.events24.R;
import com.pavlovic.cubes.events24.databinding.HomeBottomTextBinding;
import com.pavlovic.cubes.events24.databinding.RvItemEventsBigBinding;
import com.pavlovic.cubes.events24.databinding.RvItemEventsSmallBinding;
import com.pavlovic.cubes.events24.databinding.RvItemNewEventsBinding;
import com.pavlovic.cubes.events24.databinding.RvItemSearchRvBinding;
import com.pavlovic.cubes.events24.ui.activity.EventDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    private Context context;
    private ArrayList<Event> list;
    private int resourceId;


    public EventsAdapter(Context context, ArrayList<Event> list, int resourceId) {
        this.context = context;
        this.list = list;
        this.resourceId = resourceId;
    }


    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(resourceId == R.layout.rv_item_events_big) {
            RvItemEventsBigBinding binding =
                    RvItemEventsBigBinding.inflate(LayoutInflater.from(context), parent, false);
            return new EventsViewHolder((binding));
        }
        else if(resourceId == R.layout.rv_item_events_small){
            RvItemEventsSmallBinding binding =
                    RvItemEventsSmallBinding.inflate(LayoutInflater.from(context), parent, false);
            return new EventsViewHolder((binding));
        }
        else if(resourceId == R.layout.rv_item_search_rv){
            RvItemSearchRvBinding binding =
                    RvItemSearchRvBinding.inflate(LayoutInflater.from(context), parent, false);
            return new EventsViewHolder((binding));
        }
        else if(resourceId == R.layout.rv_item_new_events){
            RvItemNewEventsBinding binding =
                    RvItemNewEventsBinding.inflate(LayoutInflater.from(context), parent, false);
            return new EventsViewHolder((binding));
        }
        else {
            HomeBottomTextBinding binding =
                    HomeBottomTextBinding.inflate(LayoutInflater.from(context), parent, false);
            return new EventsViewHolder((binding));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {
        Event event = list.get(position);

        if(holder.bindingBig != null) {
            holder.bindingBig.title.setText(event.title);
            holder.bindingBig.date.setText(event.date);
            Picasso.get().load(event.imageBig).into(holder.bindingBig.imageView);
            if(!(event.priority.equalsIgnoreCase("")))
                holder.bindingBig.imageStar.setVisibility(View.VISIBLE);
        }
        else if(holder.bindingSmall != null){

            holder.bindingSmall.title.setText(event.title);
            holder.bindingSmall.date.setText(event.date);
            Picasso.get().load(event.imageSmall).into(holder.bindingSmall.imageView);

        }

        else if(holder.rvItemNewEventsBinding != null){

            holder.rvItemNewEventsBinding.title.setText(event.title);
            holder.rvItemNewEventsBinding.tvDate.setText(event.date);
            Picasso.get().load(event.imageSmall).into(holder.rvItemNewEventsBinding.imageView);

        }

        else if(holder.bindingSearch != null){

            holder.bindingSearch.title.setText(event.title);
            holder.bindingSearch.date.setText(event.date);
            Picasso.get().load(event.imageBig).into(holder.bindingSearch.imageView);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),EventDetailActivity.class);
                intent.putExtra("id",event.id);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        if(list == null) {
            return 0;
        }

        return list.size();
    }

    public void setData(ArrayList<Event> events){
        this.list = events;
        notifyDataSetChanged();
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder{

        private RvItemEventsBigBinding bindingBig;
        private RvItemEventsSmallBinding bindingSmall;
        public HomeBottomTextBinding homeBottomBinding;
        RvItemNewEventsBinding rvItemNewEventsBinding;
        private RvItemSearchRvBinding bindingSearch;


        public EventsViewHolder(RvItemEventsBigBinding binding) {
            super(binding.getRoot());
            this.bindingBig = binding;
        }

        public EventsViewHolder(RvItemEventsSmallBinding binding) {
            super(binding.getRoot());
            this.bindingSmall = binding;
        }

        public EventsViewHolder(HomeBottomTextBinding binding) {
            super(binding.getRoot());
            this.homeBottomBinding = binding;
        }

        public EventsViewHolder(RvItemNewEventsBinding binding) {
            super(binding.getRoot());
            this.rvItemNewEventsBinding = binding;
        }

        public EventsViewHolder(RvItemSearchRvBinding binding) {
            super(binding.getRoot());
            this.bindingSearch = binding;
        }
    }
}
