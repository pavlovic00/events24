package com.pavlovic.cubes.events24.ui.adapter.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlovic.cubes.events24.data.model.Media;
import com.pavlovic.cubes.events24.databinding.RvItemEventVideoImageBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RvMediaAdapter extends RecyclerView.Adapter<RvMediaAdapter.MediaHolder> {

    private ArrayList<Media> list;


    public RvMediaAdapter(ArrayList<Media> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RvMediaAdapter.MediaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RvItemEventVideoImageBinding binding = RvItemEventVideoImageBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MediaHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RvMediaAdapter.MediaHolder holder, int position) {
       Media media = list.get(position);
        Picasso.get().load(media.image).into(holder.binding.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MediaHolder extends RecyclerView.ViewHolder {

        private RvItemEventVideoImageBinding binding;
        public MediaHolder(RvItemEventVideoImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
