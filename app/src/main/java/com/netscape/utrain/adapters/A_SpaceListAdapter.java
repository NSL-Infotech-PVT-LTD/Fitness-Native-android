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
import android.widget.RatingBar;
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
import com.netscape.utrain.model.A_SpaceDataModel;
import com.netscape.utrain.model.A_SpaceListModel;
import com.netscape.utrain.model.AthleteBookListModel;
import com.netscape.utrain.model.AthleteSpaceBookList;
import com.netscape.utrain.model.O_SpaceDataModel;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;
import com.netscape.utrain.utils.RatingInterface;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class A_SpaceListAdapter extends RecyclerView.Adapter<A_SpaceListAdapter.CustomTopCoachesHolder> {

    onSpaceClick onSpaceClick;
    private Context context;
    private RatingInterface onRatingClick;
    private int previusPos = -1;
    private List<AthleteSpaceBookList.DataBeanX.DataBean> supplierData;
    private JSONArray jsonArray;
    private int type;
    private AlertDialog dialogMultiOrder;

    public A_SpaceListAdapter(Context context, List supplierData, onSpaceClick onSpaceClick, int typ, RatingInterface onRateClick) {
        this.context = context;
        this.onSpaceClick = onSpaceClick;
        this.supplierData = supplierData;
        this.type=typ;
        this.onRatingClick=onRateClick;

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

            }
        });

        if (type==1) {
            holder.completedRatingText.setVisibility(View.VISIBLE);
        }
        holder.completedRatingText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleImageSelection(data);

            }
        });
    }
    public void handleImageSelection(AthleteSpaceBookList.DataBeanX.DataBean data) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View content = inflater.inflate(R.layout.rating_design, null);
        builder.setView(content);
        ImageView cancel = (ImageView) content.findViewById(R.id.cancelDialog);
        AppCompatImageView ratingProfileImg = (AppCompatImageView) content.findViewById(R.id.ratingProfileImg);
        MaterialTextView ratingNnameTv = (MaterialTextView) content.findViewById(R.id.ratingNnameTv);
        MaterialTextView servicesTv = (MaterialTextView) content.findViewById(R.id.servicesTv);
        RatingBar ratingBar = (RatingBar) content.findViewById(R.id.ratingBooking);
        dialogMultiOrder = builder.create();
        dialogMultiOrder.setCancelable(false);
        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, context).equalsIgnoreCase(Constants.Coach) || CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, context).equalsIgnoreCase(Constants.Organizer)) {
            ratingNnameTv.setText(data.getTarget_data().getName());
            try {
                if (data.getTarget_data().getImages() != null) {

                    JSONArray jsonArray = new JSONArray(data.getTarget_data().getImages());
                    if (jsonArray != null && jsonArray.length() > 0) {
                        Glide.with(context).load(Constants.IMAGE_BASE_PLACE + jsonArray.get(0)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_EVENT + Constants.THUMBNAILS + jsonArray.get(0))).into(ratingProfileImg);
                    }
                }

            } catch (JSONException e) {

                Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }else {
            ratingNnameTv.setText(data.getSpace().getName());
            try {
                if (data.getSpace().getImages() != null) {

                    JSONArray jsonArray = new JSONArray(data.getSpace().getImages());
                    if (jsonArray != null && jsonArray.length() > 0) {
                        Glide.with(context).load(Constants.IMAGE_BASE_PLACE + jsonArray.get(0)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_EVENT + Constants.THUMBNAILS + jsonArray.get(0))).into(ratingProfileImg);
                    }
                }

            } catch (JSONException e) {

                Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }






        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                dialogMultiOrder.dismiss();
                onRatingClick.ratingVallue(data.getId(),v,data.getType());

//               rateService(data.getId(),v,rateMaterialTv);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogMultiOrder.dismiss();
            }
        });

        dialogMultiOrder.show();
        dialogMultiOrder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
        MaterialTextView eventName, findPlaceDistanceTv, eventVenue, eventDate,bookingTicketTv,completedRatingText;

        public CustomTopCoachesHolder(@NonNull View itemView) {
            super(itemView);
            ti_tickets = itemView.findViewById(R.id.ti_tickets);
            eventImage = itemView.findViewById(R.id.bookingEventImage);
            eventName = itemView.findViewById(R.id.bookingEventName);
            eventVenue = itemView.findViewById(R.id.bookingVenueTv);
            eventDate = itemView.findViewById(R.id.bookingEventDate);
            bookingTicketTv = itemView.findViewById(R.id.bookingTicketTv);
            completedRatingText = itemView.findViewById(R.id.completedRatingText);
//            findPlaceDistanceTv = itemView.findViewById(R.id.findPlaceDistanceTv);
        }
    }


}
