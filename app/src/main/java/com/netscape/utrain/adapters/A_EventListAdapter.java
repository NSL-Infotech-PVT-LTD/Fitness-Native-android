package com.netscape.utrain.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.netscape.utrain.activities.athlete.EventDetail;
import com.netscape.utrain.activities.athlete.TopCoachesDetailsActivity;
import com.netscape.utrain.activities.organization.EventAppliedList;
import com.netscape.utrain.model.A_BookedEventDataModel;
import com.netscape.utrain.model.A_EventDataListModel;
import com.netscape.utrain.model.A_EventDataModel;
import com.netscape.utrain.model.AthleteBookListModel;
import com.netscape.utrain.model.O_EventDataModel;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class A_EventListAdapter extends RecyclerView.Adapter<A_EventListAdapter.CustomTopCoachesHolder> {

    onEventClick onEventClick;
    private Context context;
    private int previusPos = -1;
    private List<AthleteBookListModel.DataBeanX.DataBean> a_eventList;

    public A_EventListAdapter(Context context, List supplierData, onEventClick onEventClick) {
        this.context = context;
        this.a_eventList = supplierData;
        this.onEventClick = onEventClick;

    }


    @NonNull
    @Override
    public A_EventListAdapter.CustomTopCoachesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_view, parent, false);
        return new A_EventListAdapter.CustomTopCoachesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull A_EventListAdapter.CustomTopCoachesHolder holder, int position) {
        final AthleteBookListModel.DataBeanX.DataBean data = a_eventList.get(position);
        try {
            if (data.getEvent().getImages() != null) {
                JSONArray jsonArray = new JSONArray(data.getEvent().getImages());
                for (int i = position; i < jsonArray.length(); i++) {
                    Glide.with(context).load(Constants.IMAGE_BASE_EVENT + jsonArray.get(i)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_EVENT + Constants.THUMBNAILS + jsonArray.get(i))).into(holder.eventImage);

                }
            }

        } catch (JSONException e) {

            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        holder.eventName.setText(data.getEvent().getName());
        holder.eventVenue.setText(data.getEvent().getLocation());
        holder.numTicket.setText(data.getEvent().getGuest_allowed() + " Attandees and Ticket(1 person per ticket)");
        holder.eventDate.setText(data.getEvent().getStart_date() + " " + data.getEvent().getStart_time());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, EventDetail.class);
//                intent.putExtra("eventName", data.getEvent().getName());
//                intent.putExtra("guest_allowed", data.getEvent().getGuest_allowed()+"");
//                intent.putExtra("guest_allowed_left", data.getEvent().getGuest_allowed_left()+"");
//                intent.putExtra("eventVenue", data.getEvent().getLocation());
//                intent.putExtra("event_id", data.getEvent().getId());
//
//                intent.putExtra("eventDate", data.getEvent().getStart_date());
//                intent.putExtra("eventTime", data.getEvent().getStart_time());
//                intent.putExtra("eventDescription", data.getEvent().getDescription());
//                intent.putExtra("image_url", Constants.IMAGE_BASE_EVENT);
//                intent.putExtra("from", "events");
//                intent.putExtra("capacity", data.getEvent().getGuest_allowed());
//                Bundle b = new Bundle();
//                b.putString("Array", data.getEvent().getImages());
//                intent.putExtras(b);
//                context.startActivity(intent);

                onEventClick.eventAmount(data);
            }
        });

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                Intent topCoachesDetails = new Intent(context, EventAppliedList.class);
//                topCoachesDetails.putExtra(Constants.SELECTED_ID,data.getId()+"");
//                context.startActivity(topCoachesDetails);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return a_eventList.size();
    }

    public interface onEventClick {
        public void eventAmount(AthleteBookListModel.DataBeanX.DataBean dataBean);
    }

    public class CustomTopCoachesHolder extends RecyclerView.ViewHolder {

        AppCompatImageView eventImage;
        MaterialTextView eventName, eventVenue, eventDate,numTicket;

        public CustomTopCoachesHolder(@NonNull View itemView) {
            super(itemView);

            eventImage = itemView.findViewById(R.id.bookingEventImage);
            eventName = itemView.findViewById(R.id.bookingEventName);
            eventVenue = itemView.findViewById(R.id.bookingVenueTv);
            eventDate = itemView.findViewById(R.id.bookingEventDate);
            numTicket = itemView.findViewById(R.id.bookingTicketTv);
        }
    }


}
