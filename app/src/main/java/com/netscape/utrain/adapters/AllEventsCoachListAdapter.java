package com.netscape.utrain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.model.C_EventDataListModel;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class AllEventsCoachListAdapter extends RecyclerView.Adapter<AllEventsCoachListAdapter.AllEventsListHolder> {

    private Context context;
    private List<C_EventDataListModel> list;

    public AllEventsCoachListAdapter(Context context, List<C_EventDataListModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public AllEventsListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_view, parent, false);
        return new AllEventsListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllEventsListHolder holder, int position) {

        C_EventDataListModel data = list.get(position);

        holder.bookingEventName.setText(data.getName());
        holder.bookingEventDate.setText(data.getStart_date() + " " + data.getStart_time());
        holder.bookingVenueTv.setText(data.getLocation());
        holder.bookingTicketTv.setText(data.getGuest_allowed()+" Attandees and Ticket(1 person per ticket)");
        holder.statusImage.setVisibility(View.GONE);

        try {
            if (data.getImages() != null) {
                JSONArray jsonArray = new JSONArray(data.getImages());
                if (jsonArray != null && jsonArray.length() > 0) {
                    Glide.with(context).load(Constants.IMAGE_BASE_EVENT + jsonArray.get(0)).thumbnail(Glide.with(context).
                            load(Constants.IMAGE_BASE_EVENT + Constants.THUMBNAILS + jsonArray.get(0))).into(holder.bookingEventImage);
                }
            }

        } catch (JSONException e) {

            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AllEventsListHolder extends RecyclerView.ViewHolder {

        MaterialTextView bookingEventName, bookingEventDate, bookingVenueTv, bookingTicketTv;
        AppCompatImageView bookingEventImage, statusImage;       // Using the booking.view layout which is same for completed....

        public AllEventsListHolder(@NonNull View itemView) {
            super(itemView);

            bookingEventName = itemView.findViewById(R.id.bookingEventName);
            bookingEventDate = itemView.findViewById(R.id.bookingEventDate);
            bookingVenueTv = itemView.findViewById(R.id.bookingVenueTv);
            bookingTicketTv = itemView.findViewById(R.id.bookingTicketTv);
            bookingEventImage = itemView.findViewById(R.id.bookingEventImage);
            statusImage = itemView.findViewById(R.id.statusImage);

        }
    }
}
