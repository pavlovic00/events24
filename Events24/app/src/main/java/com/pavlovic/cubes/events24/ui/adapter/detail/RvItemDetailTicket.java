package com.pavlovic.cubes.events24.ui.adapter.detail;

import com.pavlovic.cubes.events24.data.model.Ticket;
import com.pavlovic.cubes.events24.databinding.RvItemTicketDetalBinding;
import com.pavlovic.cubes.events24.ui.adapter.EventDetailAdapter;

public class RvItemDetailTicket implements RecyclerViewItemDetail{
    private Ticket ticket;

    public RvItemDetailTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public void bind(EventDetailAdapter.EventViewHolder holder) {
        RvItemTicketDetalBinding binding = (RvItemTicketDetalBinding) holder.binding;
        binding.textViewNameTickets.setText(ticket.title);
        binding.textViewPrice.setText(ticket.price);
    }
}
