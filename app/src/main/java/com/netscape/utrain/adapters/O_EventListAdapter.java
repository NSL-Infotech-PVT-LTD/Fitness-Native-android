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
import com.netscape.utrain.model.A_EventDataModel;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.model.O_EventDataModel;
import com.netscape.utrain.response.BookingListResponse;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class O_EventListAdapter extends RecyclerView.Adapter<O_EventListAdapter.CustomTopCoachesHolder> {

    private Context context;
    private int previusPos = -1;
    private List<O_EventDataModel> supplierData;

    public O_EventListAdapter(Context context, List supplierData) {
        this.context = context;
        this.supplierData = supplierData;

    }


    @NonNull
    @Override
    public O_EventListAdapter.CustomTopCoachesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_view, parent, false);
        return new O_EventListAdapter.CustomTopCoachesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull O_EventListAdapter.CustomTopCoachesHolder holder, int position) {
        final O_EventDataModel data = supplierData.get(position);
        try {
            if (data.getImages() != null) {
                JSONArray jsonArray = new JSONArray(data.getImages());
                for (int i = 0; i < jsonArray.length(); i++) {
                    Glide.with(context).load(Constants.IMAGE_BASE_EVENT + jsonArray.get(i)).into(holder.eventImage);

                }
            }

        } catch (JSONException e) {

            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        holder.eventName.setText(data.getName());
        holder.eventVenue.setText(data.getLocation());
        holder.eventDate.setText(data.getStart_date());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent topCoachesDetails = new Intent(context, EventAppliedList.class);
                topCoachesDetails.putExtra(Constants.SELECTED_ID,data.getId()+"");
                topCoachesDetails.putExtra(Constants.SELECTED_TYPE,Constants.EVENT);

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
        MaterialTextView eventName, eventVenue, eventDate;

        public CustomTopCoachesHolder(@NonNull View itemView) {
            super(itemView);

            eventImage = itemView.findViewById(R.id.bookingEventImage);
            eventName = itemView.findViewById(R.id.bookingEventName);
            eventVenue = itemView.findViewById(R.id.bookingVenueTv);
            eventDate = itemView.findViewById(R.id.bookingEventDate);
        }
    }


}
