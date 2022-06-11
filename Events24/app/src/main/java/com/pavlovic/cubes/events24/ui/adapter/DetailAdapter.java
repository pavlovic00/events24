package com.pavlovic.cubes.events24.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlovic.cubes.events24.R;
import com.pavlovic.cubes.events24.data.model.Event;
import com.pavlovic.cubes.events24.databinding.RvItemConcertBinding;
import com.pavlovic.cubes.events24.databinding.RvItemEventVideoImageBinding;
import com.pavlovic.cubes.events24.databinding.RvItemTextViewRvBinding;
import com.pavlovic.cubes.events24.databinding.RvItemTicketDetalBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder>{
    private Context context;
    private ArrayList<Event> list;
    private int resourceId;


    public DetailAdapter(Context context, ArrayList<Event> list, int resourceId) {
        this.context = context;
        this.list = list;
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (resourceId == R.layout.rv_item_concert) {
            RvItemConcertBinding binding =
                    RvItemConcertBinding.inflate(LayoutInflater.from(context), parent, false);
            return new DetailAdapter.DetailViewHolder((binding));
        }
        else if(resourceId == R.layout.rv_item_text_view_rv){
            RvItemTextViewRvBinding binding = RvItemTextViewRvBinding.inflate(LayoutInflater.from(context),parent,false);
            return new DetailAdapter.DetailViewHolder((binding));
        }
        else if(resourceId == R.layout.rv_item_ticket_detal){
            RvItemTicketDetalBinding binding = RvItemTicketDetalBinding.inflate(LayoutInflater.from(context),parent,false);
            return new DetailAdapter.DetailViewHolder((binding));
        }
        else if(resourceId == R.layout.rv_item_event_video_image){
            RvItemEventVideoImageBinding binding = RvItemEventVideoImageBinding.inflate(LayoutInflater.from(context),parent,false);
            return new DetailAdapter.DetailViewHolder((binding));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        if(holder.bindingBig!=null){
            Event event = list.get(position);
            Picasso.get().load(event.imageBig).into(holder.bindingBig.imageViewBig);
            holder.bindingBig.textConcert.setText(event.type);
            holder.bindingBig.textViewTitle.setText(event.title);
            holder.bindingBig.textViewDate.setText(event.date+", "+event.time);
            holder.bindingBig.textViewLocation.setText(event.location);
        }
        else if(holder.bindingText!=null){
            Event event = list.get(position);
                holder.bindingText.textViewTickets.setText("Ulaznice");


        }

        else if(holder.bindingTicket!=null){
            Event event = list.get(position);
            holder.bindingTicket.textViewNameTickets.getText();
//            holder.bindingTicket.textViewNameTickets2.getText();
//            holder.bindingTicket.textViewNameTickets3.getText();
//            holder.bindingTicket.textViewNameTickets4.getText();

            holder.bindingTicket.textViewPrice.getText();
//            holder.bindingTicket.textViewPrice2.getText();
//            holder.bindingTicket.textViewPrice3.getText();
//            holder.bindingTicket.textViewPrice4.getText();
        }
        else if(holder.bindingVideo!=null){
            Event event = list.get(position);
            Picasso.get().load(event.imageBig).into(holder.bindingVideo.imageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse("https://www.youtube.com/watch?v=KzUV2G3IOm4&t=294s&ab_channel=Standuprs");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(list == null) {
            return 0;
        }

        return list.size();
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder{

        private RvItemConcertBinding bindingBig;
        private RvItemTextViewRvBinding bindingText;
        private RvItemTicketDetalBinding bindingTicket;
        private RvItemEventVideoImageBinding bindingVideo;

        public DetailViewHolder(RvItemConcertBinding binding) {
            super(binding.getRoot());
            this.bindingBig = binding;
        }

        public DetailViewHolder(RvItemTextViewRvBinding binding) {
            super(binding.getRoot());
            this.bindingText = binding;
        }
        public DetailViewHolder(RvItemTicketDetalBinding binding) {
            super(binding.getRoot());
            this.bindingTicket = binding;
        }
        public DetailViewHolder(RvItemEventVideoImageBinding binding) {
            super(binding.getRoot());
            this.bindingVideo = binding;
        }

    }
}
