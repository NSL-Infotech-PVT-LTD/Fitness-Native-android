package com.netscape.utrain.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.netscape.utrain.activities.athlete.TopCoachesDetailsActivity;
import com.netscape.utrain.model.A_SpaceListModel;
import com.netscape.utrain.model.C_SessionListModel;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class C_SpaceListAdapter extends RecyclerView.Adapter<C_SpaceListAdapter.CustomTopCoachesHolder> {

    private Context context;
    private int previusPos = -1;
    private List<C_SessionListModel> supplierData;
    private String status;

    public C_SpaceListAdapter(Context context, List supplierData, String status) {
        this.context = context;
        this.status = status;
        this.supplierData = supplierData;

    }


    @NonNull
    @Override
    public C_SpaceListAdapter.CustomTopCoachesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_view, parent, false);
        return new C_SpaceListAdapter.CustomTopCoachesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull C_SpaceListAdapter.CustomTopCoachesHolder holder, int position) {
        final C_SessionListModel data = supplierData.get(position);

        try {
            if (data.getImages() != null) {
                JSONArray jsonArray = new JSONArray(data.getImages());
                for (int i = position; i < jsonArray.length(); i++) {
                    Glide.with(context).load(Constants.IMAGE_BASE_PLACE + jsonArray.get(i)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_PLACE +Constants.THUMBNAILS+ jsonArray.get(i))).into(holder.eventImage);

                }
            }

        } catch (JSONException e) {

            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        if (status.equalsIgnoreCase("completed")) {
            holder.statusImage.setImageResource(R.drawable.ic_ti_confirm);
        } else {
            holder.statusImage.setImageResource(R.drawable.ic_yellow_ticket);

        }

        //        Glide.with(context).load(Constants.COACH_IMAGE_BASE_URL+data.getImages().into(holder.imageView);
        holder.eventName.setText(data.getName());
        holder.ti_tickets.setVisibility(View.GONE);
        holder.bookingTicketTv.setVisibility(View.GONE);
//        holder.eventVenue.setText(data.getLocation());
//        holder.eventDate.setText(data.getStart_date());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent topCoachesDetails = new Intent(context, TopCoachesDetailsActivity.class);
//                topCoachesDetails.putExtra(Constants.TOP_DATA_INTENT,data);
                topCoachesDetails.putExtra(Constants.TOP_FROM_INTENT, "1");
                topCoachesDetails.putExtra(Constants.STATUS, status);
                context.startActivity(topCoachesDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return supplierData.size();
    }

    public class CustomTopCoachesHolder extends RecyclerView.ViewHolder {

        AppCompatImageView eventImage, ti_tickets, statusImage;
        MaterialTextView eventName, eventVenue, eventDate, bookingTicketTv;

        public CustomTopCoachesHolder(@NonNull View itemView) {
            super(itemView);

            statusImage = itemView.findViewById(R.id.statusImage);
            ti_tickets = itemView.findViewById(R.id.ti_tickets);
            eventImage = itemView.findViewById(R.id.bookingEventImage);
            eventName = itemView.findViewById(R.id.bookingEventName);
            eventVenue = itemView.findViewById(R.id.bookingVenueTv);
            bookingTicketTv = itemView.findViewById(R.id.bookingTicketTv);
            eventDate = itemView.findViewById(R.id.bookingEventDate);
        }
    }


}
