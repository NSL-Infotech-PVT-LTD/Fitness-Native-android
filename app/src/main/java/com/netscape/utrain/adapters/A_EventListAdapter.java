package com.netscape.utrain.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    private AlertDialog dialogMultiOrder;

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
        AthleteBookListModel.DataBeanX.DataBean data = a_eventList.get(position);
        try {
            if (data.getEvent().getImages() != null) {
                JSONArray jsonArray = new JSONArray(data.getEvent().getImages());
                if (jsonArray != null && jsonArray.length() > 0) {
                    Glide.with(context).load(Constants.IMAGE_BASE_EVENT + jsonArray.get(0)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_EVENT + Constants.THUMBNAILS + jsonArray.get(0))).into(holder.eventImage);
                }


//                for (int i = position; i < jsonArray.length(); i++) {
////                    Glide.with(context).load(Constants.IMAGE_BASE_EVENT + jsonArray.get(i)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_EVENT + Constants.THUMBNAILS + jsonArray.get(i))).into(holder.eventImage);
//                    String image=Constants.IMAGE_BASE_EVENT+jsonArray.get(0);
//                    Glide.with(context).load(image).into(holder.eventImage);
//                        break;
//                }
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

                onEventClick.eventAmount(data);
            }
        });

        holder.completedRatingText.setVisibility(View.VISIBLE);
        holder.completedRatingText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleImageSelection(data);

            }
        });
    }

    @Override
    public int getItemCount() {
        return a_eventList.size();
    }

    public void handleImageSelection(AthleteBookListModel.DataBeanX.DataBean data) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View content = inflater.inflate(R.layout.rating_design, null);
        builder.setView(content);
        ImageView cancel = (ImageView) content.findViewById(R.id.cancelDialog);
        AppCompatImageView ratingProfileImg = (AppCompatImageView) content.findViewById(R.id.ratingProfileImg);
        dialogMultiOrder = builder.create();
        dialogMultiOrder.setCancelable(false);
//        dialogMultiOrder.setCanceledOnTouchOutside(false);
        try {
            if (data.getEvent().getImages() != null) {

                JSONArray jsonArray = new JSONArray(data.getEvent().getImages());
                if (jsonArray != null && jsonArray.length() > 0) {
                    Glide.with(context).load(Constants.IMAGE_BASE_EVENT + jsonArray.get(0)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_EVENT + Constants.THUMBNAILS + jsonArray.get(0))).into(ratingProfileImg);
                }


//                for (int i = position; i < jsonArray.length(); i++) {
////                    Glide.with(context).load(Constants.IMAGE_BASE_EVENT + jsonArray.get(i)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_EVENT + Constants.THUMBNAILS + jsonArray.get(i))).into(holder.eventImage);
//                    String image=Constants.IMAGE_BASE_EVENT+jsonArray.get(0);
//                    Glide.with(context).load(image).into(holder.eventImage);
//                        break;
//                }
            }

        } catch (JSONException e) {

            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogMultiOrder.dismiss();
            }
        });

        dialogMultiOrder.show();
        dialogMultiOrder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public interface onEventClick {
        public void eventAmount(AthleteBookListModel.DataBeanX.DataBean dataBean);
    }

    public class CustomTopCoachesHolder extends RecyclerView.ViewHolder {

        AppCompatImageView eventImage;
        MaterialTextView eventName, eventVenue, eventDate, numTicket, completedRatingText;

        public CustomTopCoachesHolder(@NonNull View itemView) {
            super(itemView);

            eventImage = itemView.findViewById(R.id.bookingEventImage);
            eventName = itemView.findViewById(R.id.bookingEventName);
            eventVenue = itemView.findViewById(R.id.bookingVenueTv);
            eventDate = itemView.findViewById(R.id.bookingEventDate);
            numTicket = itemView.findViewById(R.id.bookingTicketTv);
            completedRatingText = itemView.findViewById(R.id.completedRatingText);


        }
    }


}
