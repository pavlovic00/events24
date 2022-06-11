package com.pavlovic.cubes.events24.ui.adapter.detail;

import android.util.Log;
import android.view.View;

import com.pavlovic.cubes.events24.R;
import com.pavlovic.cubes.events24.data.database.AppDatabase;
import com.pavlovic.cubes.events24.data.model.Event;
import com.pavlovic.cubes.events24.databinding.RvItemConcertBinding;
import com.pavlovic.cubes.events24.ui.adapter.EventDetailAdapter;
import com.pavlovic.cubes.events24.ui.view.EventsMessage;
import com.squareup.picasso.Picasso;

public class RvItemDetailHeader implements RecyclerViewItemDetail{

    private Event event;

    public RvItemDetailHeader(Event event) {
        this.event = event;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public void bind(EventDetailAdapter.EventViewHolder holder) {

        RvItemConcertBinding binding = (RvItemConcertBinding) holder.binding;
        binding.textConcert.setText(event.type);
        binding.textViewDate.setText(event.date);
        binding.textViewTitle.setText(event.title);
        binding.textViewLocation.setText(event.location);
        Picasso.get().load(event.imageBig).into(binding.imageViewBig);

        Event favEvent = AppDatabase.getInstance(binding.getRoot().getContext()).eventDAO().getEvent(event.id);

        if (favEvent != null){
            binding.buttonFavorite.setImageResource(R.drawable.ic_favorite_yes);
        }
        else {
            binding.buttonFavorite.setImageResource(R.drawable.ic_favorite);
        }

        binding.buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favEvent != null){
                    AppDatabase.getInstance(view.getContext()).eventDAO().delete(event);
                    binding.buttonFavorite.setImageResource(R.drawable.ic_favorite);
                    EventsMessage.showMessage(view.getContext(),"Remove event from favourites list.");
                }
                else {
                    AppDatabase.getInstance(view.getContext()).eventDAO().insert(event);
                    binding.buttonFavorite.setImageResource(R.drawable.ic_favorite_yes);
                    EventsMessage.showMessage(view.getContext(),"Add event to favourites list.");
                }
            }
        });


    }
}
