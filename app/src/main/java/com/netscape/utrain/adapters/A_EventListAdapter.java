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
import com.netscape.utrain.model.A_EventDataListModel;
import com.netscape.utrain.model.A_EventDataModel;
import com.netscape.utrain.model.O_EventDataModel;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class A_EventListAdapter extends RecyclerView.Adapter<A_EventListAdapter.CustomTopCoachesHolder> {

    private Context context;
    private int previusPos = -1;
    private List<A_EventDataListModel> a_eventList;

    public A_EventListAdapter(Context context, List supplierData) {
        this.context = context;
        this.a_eventList = supplierData;

    }


    @NonNull
    @Override
    public A_EventListAdapter.CustomTopCoachesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_view, parent, false);
        return new A_EventListAdapter.CustomTopCoachesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull A_EventListAdapter.CustomTopCoachesHolder holder, int position) {
        final A_EventDataListModel data = a_eventList.get(position);
        try {
            if (data.getImages() != null) {
                JSONArray jsonArray = new JSONArray(data.getImages());
                for (int i = position; i < jsonArray.length(); i++) {
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


//                Intent topCoachesDetails = new Intent(context, TopCoachesDetailsActivity.class);
////                topCoachesDetails.putExtra(Constants.TOP_DATA_INTENT,data);
//                topCoachesDetails.putExtra(Constants.TOP_FROM_INTENT, "1");
//                context.startActivity(topCoachesDetails);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent topCoachesDetails = new Intent(context, EventAppliedList.class);
                topCoachesDetails.putExtra(Constants.SELECTED_ID,data.getId()+"");
                context.startActivity(topCoachesDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return a_eventList.size();
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
