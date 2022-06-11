package com.pavlovic.cubes.events24.ui.adapter.detail;

import com.pavlovic.cubes.events24.ui.adapter.EventDetailAdapter;

public interface RecyclerViewItemDetail {

    int getType();
    void bind(EventDetailAdapter.EventViewHolder holder);

}
