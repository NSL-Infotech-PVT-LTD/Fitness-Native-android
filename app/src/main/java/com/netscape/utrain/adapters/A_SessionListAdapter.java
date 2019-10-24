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
import com.netscape.utrain.model.A_SessionDataModel;
import com.netscape.utrain.model.AthleteBookListModel;
import com.netscape.utrain.model.AthleteSessionBookList;
import com.netscape.utrain.model.O_SessionDataModel;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class A_SessionListAdapter extends RecyclerView.Adapter<A_SessionListAdapter.CustomTopCoachesHolder> {

    private Context context;
    private int previusPos = -1;
    private List<AthleteSessionBookList.DataBeanX.DataBean> supplierData;

    public A_SessionListAdapter(Context context, List supplierData) {
        this.context = context;
        this.supplierData = supplierData;

    }


    @NonNull
    @Override
    public A_SessionListAdapter.CustomTopCoachesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_view, parent, false);
        return new A_SessionListAdapter.CustomTopCoachesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull A_SessionListAdapter.CustomTopCoachesHolder holder, int position) {
        final AthleteSessionBookList.DataBeanX.DataBean data = supplierData.get(position);
        try {
            if (data.getSession().getImages() != null) {
                JSONArray jsonArray = new JSONArray(data.getSession().getImages());
                for (int i = 0; i < jsonArray.length(); i++) {
                    Glide.with(context).load(Constants.IMAGE_BASE_SESSION + jsonArray.get(i)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_SESSION+Constants.THUMBNAILS + jsonArray.get(i))).into(holder.eventImage);

                }
            }

        } catch (JSONException e) {

            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        holder.eventName.setText(data.getSession().getName());
//        holder.eventVenue.setText(data.getLocation());
        holder.eventDate.setText(data.getSession().getDate() + " " + data.getSession().getBusiness_hour());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventDetail.class);
                intent.putExtra("eventName", data.getSession().getName());
                intent.putExtra("guest_allowed", data.getSession().getGuest_allowed()+"");
                intent.putExtra("guest_allowed_left", data.getSession().getGuest_allowed_left()+"");
                intent.putExtra("eventVenue", data.getSession().getLocation());
                intent.putExtra("eventTime", data.getSession().getBusiness_hour());
                intent.putExtra("eventDate", data.getSession().getDate());
                intent.putExtra("eventDescription", data.getSession().getDescription());
                intent.putExtra("image_url", Constants.IMAGE_BASE_SESSION);
                intent.putExtra("event_id", data.getSession().getId());
                intent.putExtra("from", "sessions");
                Bundle b = new Bundle();
                b.putString("Array", data.getSession().getImages());
                intent.putExtras(b);
                context.startActivity(intent);
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
