package com.pavlovic.cubes.events24.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlovic.cubes.events24.data.model.Authors;
import com.pavlovic.cubes.events24.databinding.RvItemAuthorsRvBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AuthorsAdapter extends RecyclerView.Adapter<AuthorsAdapter.AuthorsViewHolder> {

    private Context context;
    private ArrayList<Authors> authorList;
    private int resourceId;


    public AuthorsAdapter(Context context, ArrayList<Authors> list, int resourceId) {
        this.context = context;
        this.authorList = list;
        this.resourceId = resourceId;
    }




    @NonNull
    @Override
    public AuthorsAdapter.AuthorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RvItemAuthorsRvBinding binding =
                RvItemAuthorsRvBinding.inflate(LayoutInflater.from(context), parent, false);
        return new AuthorsAdapter.AuthorsViewHolder((binding));
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorsAdapter.AuthorsViewHolder holder, int position) {

        Authors author = authorList.get(position);
        holder.authorsBinding.author.setText(author.name + " " + author.surname);
        Picasso.get().load(author.image).into(holder.authorsBinding.imageView);

    }

    @Override
    public int getItemCount() {

        if(authorList == null) {
            return 0;
        }

        return authorList.size();
    }

    public class AuthorsViewHolder extends RecyclerView.ViewHolder{

        public RvItemAuthorsRvBinding authorsBinding;


        public AuthorsViewHolder(RvItemAuthorsRvBinding binding) {
            super(binding.getRoot());
            this.authorsBinding = binding;
        }
    }
}
