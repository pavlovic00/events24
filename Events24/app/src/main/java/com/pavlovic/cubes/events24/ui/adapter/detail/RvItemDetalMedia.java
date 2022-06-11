package com.pavlovic.cubes.events24.ui.adapter.detail;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlovic.cubes.events24.data.model.Media;
import com.pavlovic.cubes.events24.databinding.RvItemHorizontalDetailMediaRvBinding;
import com.pavlovic.cubes.events24.ui.adapter.EventDetailAdapter;

import java.util.ArrayList;

public class RvItemDetalMedia implements RecyclerViewItemDetail{

    public ArrayList<Media> list;

    public RvItemDetalMedia(ArrayList<Media> list) {
        this.list = list;
    }

    @Override
    public int getType() {
        return 3;
    }

    @Override
    public void bind(EventDetailAdapter.EventViewHolder holder) {

        RvItemHorizontalDetailMediaRvBinding binding = (RvItemHorizontalDetailMediaRvBinding) holder.binding;
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),
                RecyclerView.HORIZONTAL,false));
        binding.recyclerView.setAdapter(new RvMediaAdapter(list));
    }
}
