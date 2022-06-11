package com.pavlovic.cubes.events24.ui.adapter.detail;

import com.pavlovic.cubes.events24.databinding.RvItemTextViewRvBinding;
import com.pavlovic.cubes.events24.ui.adapter.EventDetailAdapter;

public class RvItemDetailTitle implements RecyclerViewItemDetail{

    private String title;

    public RvItemDetailTitle(String title) {
        this.title = title;
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public void bind(EventDetailAdapter.EventViewHolder holder) {
        RvItemTextViewRvBinding binding = (RvItemTextViewRvBinding) holder.binding;
        binding.textViewTickets.setText(title);
    }
}
