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
import com.netscape.utrain.activities.organization.EventAppliedList;
import com.netscape.utrain.model.O_EventDataModel;
import com.netscape.utrain.model.O_SpaceDataModel;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class O_SpaceListAdapter extends RecyclerView.Adapter<O_SpaceListAdapter.CustomTopCoachesHolder> {

    private Context context;
    private int previusPos = -1;
    private List<O_SpaceDataModel> supplierData;
    private String status;

    public O_SpaceListAdapter(Context context, List supplierData, String status) {
        this.context = context;
        this.supplierData = supplierData;
        this.status = status;

    }


    @NonNull
    @Override
    public O_SpaceListAdapter.CustomTopCoachesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_view, parent, false);
        return new O_SpaceListAdapter.CustomTopCoachesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull O_SpaceListAdapter.CustomTopCoachesHolder holder, int position) {
        final O_SpaceDataModel data = supplierData.get(position);

        try {
            if (data.getImages() != null) {
                JSONArray jsonArray = new JSONArray(data.getImages());
                for (int i = 0; i < jsonArray.length(); i++) {
                    Glide.with(context).load(Constants.IMAGE_BASE_PLACE + jsonArray.get(i)).into(holder.eventImage);

                }
            }

        } catch (JSONException e) {

            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        //        Glide.with(context).load(Constants.COACH_IMAGE_BASE_URL+data.getImages().into(holder.imageView);
        holder.eventName.setText(data.getName());
        holder.eventVenue.setText(data.getAvailability_week());
        holder.bookingTicketTv.setText(data.getAvailability_week());
        holder.eventDate.setText(data.getAvailability_week());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent topCoachesDetails = new Intent(context, EventAppliedList.class);
                topCoachesDetails.putExtra(Constants.SELECTED_ID, data.getId() + "");
                topCoachesDetails.putExtra(Constants.SELECTED_TYPE, Constants.SPACE);
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

        AppCompatImageView eventImage;
        MaterialTextView eventName,eventVenue,bookingTicketTv,eventDate;


        public CustomTopCoachesHolder(@NonNull View itemView) {
            super(itemView);

            eventImage = itemView.findViewById(R.id.bookingEventImage);
            eventName = itemView.findViewById(R.id.bookingEventName);
            eventVenue = itemView.findViewById(R.id.bookingVenueTv);
            eventDate = itemView.findViewById(R.id.bookingEventDate);
            bookingTicketTv = itemView.findViewById(R.id.bookingTicketTv);
        }
    }


}
