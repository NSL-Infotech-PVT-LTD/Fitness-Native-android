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
import com.netscape.utrain.model.A_SessionDataModel;
import com.netscape.utrain.model.AthleteBookListModel;
import com.netscape.utrain.model.AthleteSessionBookList;
import com.netscape.utrain.model.O_SessionDataModel;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.RatingInterface;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class A_SessionListAdapter extends RecyclerView.Adapter<A_SessionListAdapter.CustomTopCoachesHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    onSessionClick onSessionClick;
    private Context context;
    private AlertDialog dialogMultiOrder;
    private int previusPos = -1;
    private RatingInterface onclickRate;
    private List<AthleteSessionBookList.DataBeanX.DataBean> supplierData;
    private int type;
    public A_SessionListAdapter(Context context, List supplierData, onSessionClick onSessionClick, int typ, RatingInterface onclickRating) {
        this.context = context;
        this.supplierData = supplierData;
        this.onSessionClick = onSessionClick;
        this.type=typ;
        this.onclickRate=onclickRating;
    }
    public A_SessionListAdapter(Context context, onSessionClick onSessionClick, int typ, RatingInterface onclickRating) {
        this.context = context;
        supplierData = new ArrayList<>();
        this.onSessionClick = onSessionClick;
        this.type=typ;
        this.onclickRate=onclickRating;
    }


    @NonNull
    @Override
    public A_SessionListAdapter.CustomTopCoachesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_view, parent, false);
//        return new A_SessionListAdapter.CustomTopCoachesHolder(view);
        A_SessionListAdapter.CustomTopCoachesHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);

                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private A_SessionListAdapter.CustomTopCoachesHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        A_SessionListAdapter.CustomTopCoachesHolder viewHolder;
        View v1 = inflater.inflate(R.layout.booking_view, parent, false);
        viewHolder = new A_SessionListAdapter.CustomTopCoachesHolder(v1);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull A_SessionListAdapter.CustomTopCoachesHolder holder, int position) {
         final AthleteSessionBookList.DataBeanX.DataBean data = supplierData.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                if (data != null)
        try {
            if (data.getSession().getImages() != null) {
                JSONArray jsonArray = new JSONArray(data.getSession().getImages());
                if (jsonArray !=null && jsonArray.length()>0){
                    Glide.with(context).load(Constants.IMAGE_BASE_SESSION + jsonArray.get(0)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_SESSION + Constants.THUMBNAILS + jsonArray.get(0))).into(holder.eventImage);
                }
            }

        } catch (JSONException e) {

            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        holder.eventName.setText(data.getSession().getName());
        holder.eventVenue.setText(data.getSession().getLocation());
        holder.bookingTv.setText(data.getSession().getGuest_allowed() + " Attandees and Ticket(1 person per ticket)");
        holder.eventDate.setText(data.getSession().getStart_date() + " " + data.getSession().getStart_time());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSessionClick.sessionAmount(data);

            }
        });
            if (type==1) {
                if (data.getStatus().equalsIgnoreCase("pending")){
                    holder.completedRatingText.setVisibility(View.VISIBLE);
                }
            }
           holder.completedRatingText.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   handleImageSelection(data);
               }
           });

                break;
            case LOADING:
//                Do nothing
                break;
        }

    }
    public void handleImageSelection(AthleteSessionBookList.DataBeanX.DataBean data) {
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
        ratingNnameTv.setText(data.getSession().getName());
//        dialogMultiOrder.setCanceledOnTouchOutside(false);
        try {
            if (data.getSession().getImages() != null) {

                JSONArray jsonArray = new JSONArray(data.getSession().getImages());
                if (jsonArray != null && jsonArray.length() > 0) {
                    Glide.with(context).load(Constants.IMAGE_BASE_SESSION + jsonArray.get(0)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_EVENT + Constants.THUMBNAILS + jsonArray.get(0))).into(ratingProfileImg);
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

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                dialogMultiOrder.dismiss();
                onclickRate.ratingVallue(data.getId(),v,data.getType());

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
        return supplierData == null ? 0 : supplierData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == supplierData.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }




    public void add(AthleteSessionBookList.DataBeanX.DataBean r) {
        supplierData.add(r);
        notifyItemInserted(supplierData.size() - 1);
    }

    public void addAll(List<AthleteSessionBookList.DataBeanX.DataBean> moveResults) {
        for (AthleteSessionBookList.DataBeanX.DataBean result : moveResults) {
            add(result);
        }
    }

    public void setList(List<AthleteSessionBookList.DataBeanX.DataBean> list) {
        this.supplierData = list;
        notifyDataSetChanged();
    }

    public void remove(AthleteSessionBookList.DataBeanX.DataBean r) {
        int position = supplierData.indexOf(r);
        if (position > -1) {
            supplierData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
//        add(new C_ProductsSerial.Datum());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = supplierData.size() - 1;
        AthleteSessionBookList.DataBeanX.DataBean result = getItem(position);

        if (result != null) {
            supplierData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public AthleteSessionBookList.DataBeanX.DataBean getItem(int position) {
        return supplierData.get(position);
    }

    public interface onSessionClick {
        void sessionAmount(AthleteSessionBookList.DataBeanX.DataBean dataBean);
    }

    public class CustomTopCoachesHolder extends RecyclerView.ViewHolder {

        AppCompatImageView eventImage;
        MaterialTextView eventName, eventVenue, eventDate, bookingTv,completedRatingText;

        public CustomTopCoachesHolder(@NonNull View itemView) {
            super(itemView);

            eventImage = itemView.findViewById(R.id.bookingEventImage);
            eventName = itemView.findViewById(R.id.bookingEventName);
            eventVenue = itemView.findViewById(R.id.bookingVenueTv);
            eventDate = itemView.findViewById(R.id.bookingEventDate);
            bookingTv = itemView.findViewById(R.id.bookingTicketTv);
            completedRatingText = itemView.findViewById(R.id.completedRatingText);

        }
    }

    protected class LoadingVH extends CustomTopCoachesHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
