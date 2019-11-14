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
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class A_SpaceListAdapter extends RecyclerView.Adapter<A_SpaceListAdapter.CustomTopCoachesHolder> {

    onSpaceClick onSpaceClick;
    private Context context;
    private int previusPos = -1;
    private List<AthleteSpaceBookList.DataBeanX.DataBean> supplierData;
    private JSONArray jsonArray;

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
                if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, context).equalsIgnoreCase(Constants.Coach) || CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, context).equalsIgnoreCase(Constants.Organizer))
                {
                    if (data.getTarget_data().getImages() != null) {
                        jsonArray = new JSONArray(data.getTarget_data().getImages());
                        holder.eventName.setText(data.getTarget_data().getName());
                        holder.eventVenue.setText(data.getTarget_data().getLocation());
                        holder.bookingTicketTv.setVisibility(View.GONE);
                        holder.ti_tickets.setVisibility(View.GONE);
                        holder.eventDate.setText(data.getTarget_data().getAvailability_week());
                    }
                }else {
                    if (data.getSpace().getImages() != null) {
                        jsonArray = new JSONArray(data.getSpace().getImages());
                        holder.eventName.setText(data.getSpace().getName());
                        holder.eventVenue.setText(data.getSpace().getLocation());
                        holder.bookingTicketTv.setVisibility(View.GONE);
                        holder.ti_tickets.setVisibility(View.GONE);
                        holder.eventDate.setText(data.getSpace().getAvailability_week());
                    }
                }
                if (jsonArray !=null && jsonArray.length()>0){
                    Glide.with(context).load(Constants.IMAGE_BASE_PLACE + jsonArray.get(0)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_PLACE + Constants.THUMBNAILS + jsonArray.get(0))).into(holder.eventImage);
                }


        } catch (JSONException e) {

            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        //        Glide.with(context).load(Constants.COACH_IMAGE_BASE_URL+data.getImages().into(holder.imageView);

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

        AppCompatImageView eventImage,ti_tickets;
        MaterialTextView eventName, findPlaceDistanceTv, eventVenue, eventDate,bookingTicketTv;

        public CustomTopCoachesHolder(@NonNull View itemView) {
            super(itemView);
            ti_tickets = itemView.findViewById(R.id.ti_tickets);
            eventImage = itemView.findViewById(R.id.bookingEventImage);
            eventName = itemView.findViewById(R.id.bookingEventName);
            eventVenue = itemView.findViewById(R.id.bookingVenueTv);
            eventDate = itemView.findViewById(R.id.bookingEventDate);
            bookingTicketTv = itemView.findViewById(R.id.bookingTicketTv);
//            findPlaceDistanceTv = itemView.findViewById(R.id.findPlaceDistanceTv);
        }
    }


}
