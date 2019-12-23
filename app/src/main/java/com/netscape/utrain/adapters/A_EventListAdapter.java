package com.netscape.utrain.adapters;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.BookingDetails;
import com.netscape.utrain.activities.athlete.EventDetail;
import com.netscape.utrain.activities.athlete.TopCoachesDetailsActivity;
import com.netscape.utrain.activities.organization.EventAppliedList;
import com.netscape.utrain.model.A_BookedEventDataModel;
import com.netscape.utrain.model.A_EventDataListModel;
import com.netscape.utrain.model.A_EventDataModel;
import com.netscape.utrain.model.AthleteBookListModel;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.model.O_EventDataModel;
import com.netscape.utrain.response.RatingResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.RatingInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class A_EventListAdapter extends RecyclerView.Adapter<A_EventListAdapter.CustomTopCoachesHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private onEventClick onEventClick;
    private RatingInterface onRatingClick;
    private ProgressDialog progressDialog;
    private Context context;
    private Retrofitinterface retrofitinterface;
    private int previusPos = -1;
    private List<AthleteBookListModel.DataBeanX.DataBean> a_eventList;
        private AlertDialog dialogMultiOrder;
    private int type;
    public A_EventListAdapter(Context context, List supplierData, onEventClick onEventClick, int typ, RatingInterface rating) {
        this.context = context;
        this.a_eventList = supplierData;
        this.onEventClick = onEventClick;
        this.type=typ;
        this.onRatingClick=rating;

    }
    public A_EventListAdapter(Context context,onEventClick onEventClick,int type,RatingInterface rating) {
        this.context = context;
        a_eventList = new ArrayList<>();
        this.type = type;
        this.onRatingClick=rating;
        this.onEventClick = onEventClick;
    }


    @NonNull
    @Override
    public A_EventListAdapter.CustomTopCoachesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_view, parent, false);
//        return new A_EventListAdapter.CustomTopCoachesHolder(view);
        A_EventListAdapter.CustomTopCoachesHolder viewHolder = null;
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
    private A_EventListAdapter.CustomTopCoachesHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        A_EventListAdapter.CustomTopCoachesHolder viewHolder;
        View v1 = inflater.inflate(R.layout.booking_view, parent, false);
        viewHolder = new A_EventListAdapter.CustomTopCoachesHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull A_EventListAdapter.CustomTopCoachesHolder holder, int position) {
        AthleteBookListModel.DataBeanX.DataBean data = a_eventList.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                if (data != null)
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
//                        onEventClick.eventAmount(data);
                        Intent topCoachesDetails = new Intent(context, BookingDetails.class);
                        topCoachesDetails.putExtra(Constants.SELECTED_ID, data.getId() + "");
                        topCoachesDetails.putExtra(Constants.SELECTED_TYPE, Constants.EVENT);
//                        topCoachesDetails.putExtra(Constants.STATUS, status);
                        context.startActivity(topCoachesDetails);

                    }
                });
                if (type == 1) {
                    if (data.getStatus().equalsIgnoreCase("pending")) {
                        holder.completedRatingText.setVisibility(View.VISIBLE);
                    }else {
                        holder.bookingRating.setVisibility(View.VISIBLE);
                        holder.completedRatingText.setVisibility(View.GONE);
                        holder.bookingRating.setRating(Float.parseFloat(data.getRating()));
                    }

                }
                holder.completedRatingText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleImageSelection(data, holder.completedRatingText);

                    }
                });

                break;
            case LOADING:
//                Do nothing
                break;
        }
        }

    @Override
    public int getItemCount() {
        return a_eventList == null ? 0 : a_eventList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == a_eventList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }




    public void add(AthleteBookListModel.DataBeanX.DataBean r) {
        a_eventList.add(r);
        notifyItemInserted(a_eventList.size() - 1);
    }

    public void addAll(List<AthleteBookListModel.DataBeanX.DataBean> moveResults) {
        for (AthleteBookListModel.DataBeanX.DataBean result : moveResults) {
            add(result);
        }
    }

    public void setList(List<AthleteBookListModel.DataBeanX.DataBean> list) {
        this.a_eventList = list;
        notifyDataSetChanged();
    }

    public void remove(AthleteBookListModel.DataBeanX.DataBean r) {
        int position = a_eventList.indexOf(r);
        if (position > -1) {
            a_eventList.remove(position);
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

        int position = a_eventList.size() - 1;
        AthleteBookListModel.DataBeanX.DataBean result = getItem(position);

        if (result != null) {
            a_eventList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public AthleteBookListModel.DataBeanX.DataBean getItem(int position) {
        return a_eventList.get(position);
    }





    public void handleImageSelection(AthleteBookListModel.DataBeanX.DataBean data,MaterialTextView rateMaterialTv) {


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
        ratingNnameTv.setText(data.getEvent().getName());
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

    public interface onEventClick {
        public void eventAmount(AthleteBookListModel.DataBeanX.DataBean dataBean);
    }


    public class CustomTopCoachesHolder extends RecyclerView.ViewHolder {

        AppCompatImageView eventImage;
        RatingBar bookingRating;
        MaterialTextView eventName, eventVenue, eventDate, numTicket, completedRatingText;

        public CustomTopCoachesHolder(@NonNull View itemView) {
            super(itemView);

            eventImage = itemView.findViewById(R.id.bookingEventImage);
            eventName = itemView.findViewById(R.id.bookingEventName);
            eventVenue = itemView.findViewById(R.id.bookingVenueTv);
            eventDate = itemView.findViewById(R.id.bookingEventDate);
            numTicket = itemView.findViewById(R.id.bookingTicketTv);
            completedRatingText = itemView.findViewById(R.id.completedRatingText);
            bookingRating = itemView.findViewById(R.id.bookingRating);


        }
    }
//    public void rateService(int id,float rating,MaterialTextView rateTv) {
//        progressDialog=new ProgressDialog(context);
//        progressDialog.setMessage("Loading..");
//        progressDialog.show();
//        retrofitinterface= RetrofitInstance.getClient().create(Retrofitinterface.class);
//        Call<RatingResponse> call = retrofitinterface.setbookingRating(Constants.CONTENT_TYPE,"Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, context),  id+"", rating+"");
//        call.enqueue(new Callback<RatingResponse>() {
//            @Override
//            public void onResponse(Call<RatingResponse> call, Response<RatingResponse> response) {
//                if (response.body() != null) {
//                    progressDialog.dismiss();
//                    if (response.body().isStatus()) {
//                        rateTv.setVisibility(View.GONE);
//                        Toast.makeText(context, ""+response.body().getData().getMessage().toString(), Toast.LENGTH_SHORT).show();
//                    }else {
//                        Toast.makeText(context, ""+response.body().getData().getMessage().toString(), Toast.LENGTH_SHORT).show();
//                    }
//
//                } else {
//
//                    progressDialog.dismiss();
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
//
//                        Toast.makeText(context, "" + errorMessage, Toast.LENGTH_SHORT).show();
//                    } catch (Exception e) {
//
//                    }
//
//                }
//            }
//
//
//            @Override
//            public void onFailure(Call<RatingResponse> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    protected class LoadingVH extends CustomTopCoachesHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
