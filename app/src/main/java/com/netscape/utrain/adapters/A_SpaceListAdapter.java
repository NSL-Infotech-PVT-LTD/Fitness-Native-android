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
import com.netscape.utrain.model.A_SpaceDataModel;
import com.netscape.utrain.model.A_SpaceListModel;
import com.netscape.utrain.model.AthleteSpaceBookList;
import com.netscape.utrain.model.O_SpaceDataModel;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class A_SpaceListAdapter extends RecyclerView.Adapter<A_SpaceListAdapter.CustomTopCoachesHolder> {

    onSpaceClick onSpaceClick;
    private Context context;
    private int previusPos = -1;
    private List<AthleteSpaceBookList.DataBeanX.DataBean> supplierData;

    public A_SpaceListAdapter(Context context, List supplierData, onSpaceClick onSpaceClick) {
        this.context = context;
        this.onSpaceClick = onSpaceClick;
        this.supplierData = supplierData;

    }


    @NonNull
    @Override
    public A_SpaceListAdapter.CustomTopCoachesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_view, parent, false);
        return new A_SpaceListAdapter.CustomTopCoachesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull A_SpaceListAdapter.CustomTopCoachesHolder holder, int position) {
        final AthleteSpaceBookList.DataBeanX.DataBean data = supplierData.get(position);

        try {
            if (data.getSpace().getImages() != null) {
                JSONArray jsonArray = new JSONArray(data.getSpace().getImages());
                for (int i = position; i < jsonArray.length(); i++) {
                    Glide.with(context).load(Constants.IMAGE_BASE_PLACE + jsonArray.get(i)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_PLACE + Constants.THUMBNAILS + jsonArray.get(i))).into(holder.eventImage);

                }
            }

        } catch (JSONException e) {

            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        //        Glide.with(context).load(Constants.COACH_IMAGE_BASE_URL+data.getImages().into(holder.imageView);
        holder.eventName.setText(data.getSpace().getName());
        holder.eventVenue.setText(data.getSpace().getLocation());
//        holder.findPlaceDistanceTv.setText("Availability");
        holder.eventDate.setText(data.getSpace().getAvailability_week());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSpaceClick.getSpaceAmount(data);
//                Intent intent = new Intent(context, EventDetail.class);
//                intent.putExtra("eventName", data.getSpace().getName());
////                intent.putExtra("eventVenue", data.getSpace().getLocation());
////                intent.putExtra("evenStartDateTime", data.get);
//                intent.putExtra("eventALLImages", data.getSpace().getImages());
//                intent.putExtra("eventDate", data.getSpace().getAvailability_week());
//                intent.putExtra("image_url", Constants.IMAGE_BASE_PLACE);
//                intent.putExtra("event_id", data.getSpace().getId());
//                intent.putExtra("from", "places");
//                Bundle b = new Bundle();
//                b.putString("Array", data.getSpace().getImages());
//                intent.putExtras(b);
//
//                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return supplierData.size();
    }

    public interface onSpaceClick {
        public void getSpaceAmount(AthleteSpaceBookList.DataBeanX.DataBean dataBean);
    }

    public class CustomTopCoachesHolder extends RecyclerView.ViewHolder {

        AppCompatImageView eventImage;
        MaterialTextView eventName, findPlaceDistanceTv, eventVenue, eventDate;

        public CustomTopCoachesHolder(@NonNull View itemView) {
            super(itemView);

            eventImage = itemView.findViewById(R.id.bookingEventImage);
            eventName = itemView.findViewById(R.id.bookingEventName);
            eventVenue = itemView.findViewById(R.id.bookingVenueTv);
            eventDate = itemView.findViewById(R.id.bookingEventDate);
//            findPlaceDistanceTv = itemView.findViewById(R.id.findPlaceDistanceTv);
        }
    }


}
