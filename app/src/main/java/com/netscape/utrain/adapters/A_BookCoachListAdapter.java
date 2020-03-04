package com.netscape.utrain.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
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
import com.netscape.utrain.activities.BookingDetails;
import com.netscape.utrain.activities.TopCoachOrgDetailActivity;
import com.netscape.utrain.model.A_CoachBookingList;
import com.netscape.utrain.model.AthleteSpaceBookList;
import com.netscape.utrain.model.SelectSpaceDaysModel;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;
import com.netscape.utrain.utils.RatingInterface;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class A_BookCoachListAdapter extends RecyclerView.Adapter<A_BookCoachListAdapter.CustomTopCoachesHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    onSpaceClick onSpaceClick;
    private Context context;
    private RatingInterface onRatingClick;
    private int previusPos = -1;
    private List<A_CoachBookingList.DataBean> supplierData;
    private JSONArray jsonArray;
    private int type;
    private AlertDialog dialogMultiOrder;
    private Date endDate=null;
    List<SelectSpaceDaysModel> startWeekList = new ArrayList<>();

    public A_BookCoachListAdapter(Context context,  List<A_CoachBookingList.DataBean> supplierData, onSpaceClick onSpaceClick, int typ, RatingInterface onRateClick) {
        this.context = context;
        this.onSpaceClick = onSpaceClick;
        this.supplierData = supplierData;
        this.type=typ;
        this.onRatingClick=onRateClick;

    }
    public A_BookCoachListAdapter(Context context, onSpaceClick onSpaceClick,List<A_CoachBookingList.DataBean> list, RatingInterface onRateClick) {
        this.context = context;
        this.onSpaceClick = onSpaceClick;
        this.supplierData= list;
        this.onRatingClick=onRateClick;

    }



    @NonNull
    @Override
    public A_BookCoachListAdapter.CustomTopCoachesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_view, parent, false);
//        return new A_SpaceListAdapter.CustomTopCoachesHolder(view);
        A_BookCoachListAdapter.CustomTopCoachesHolder viewHolder = null;
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
    private A_BookCoachListAdapter.CustomTopCoachesHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        A_BookCoachListAdapter.CustomTopCoachesHolder viewHolder;
        View v1 = inflater.inflate(R.layout.booking_view, parent, false);
        viewHolder = new A_BookCoachListAdapter.CustomTopCoachesHolder(v1);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull A_BookCoachListAdapter.CustomTopCoachesHolder holder, int position) {
                final A_CoachBookingList.DataBean data = supplierData.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                if (data != null)
        try {


                        holder.eventName.setText(data.getCoach_details().getName());
                        holder.eventVenue.setText(data.getCoach_details().getLocation());
                        holder.bookingTicketTv.setVisibility(View.GONE);
                        holder.ti_tickets.setVisibility(View.GONE);



                    Glide.with(context).load(Constants.COACH_IMAGE_BASE_URL

                            +data.getCoach_details().getProfile_image()).into(holder.eventImage);

            holder.eventDate.setText(data.getCoach_details().getHourly_rate()+"");


        } catch (Exception e) {

            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        //        Glide.with(context).load(Constants.COACH_IMAGE_BASE_URL+data.getImages().into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent topCoachesDetails = new Intent(context, BookingDetails.class);
                topCoachesDetails.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                topCoachesDetails.putExtra(Constants.SELECTED_TYPE, "coach");
                topCoachesDetails.putExtra(Constants.TOP_DATA_INTENT, data);
                topCoachesDetails.putExtra(Constants.TOP_FROM_INTENT, "2");
                context.startActivity(topCoachesDetails);
//                onSpaceClick.getSpaceAmount(data);
//                Intent topCoachesDetails = new Intent(context, BookingDetails.class);
//                topCoachesDetails.putExtra(Constants.SELECTED_ID, data.getId() + "");
//                topCoachesDetails.putExtra(Constants.SELECTED_TYPE, Constants.SPACE);
////                topCoachesDetails.putExtra(Constants.STATUS, status);
//                context.startActivity(topCoachesDetails);

            }
        });

        if (type==1) {
            if (!TextUtils.isEmpty(data.getCoach_details().getBusiness_hour_ends())){
                CommonMethods commonMethods=new CommonMethods();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    endDate=sdf.parse(data.getCoach_details().getBusiness_hour_ends());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

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
    public void handleImageSelection(A_CoachBookingList.DataBean data) {


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
            ratingNnameTv.setText(data.getCoach_details().getName());
            try {
                if (data.getCoach_details().getPortfolio_image() != null) {

                    JSONArray jsonArray = new JSONArray(data.getCoach_details().getPortfolio_image());
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
            //    onRatingClick.ratingVallue(data.getId(),v,data.getType());

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




    public void add(A_CoachBookingList.DataBean r) {
        supplierData.add(r);
        notifyItemInserted(supplierData.size() - 1);
    }

    public void addAll(List<A_CoachBookingList.DataBean> moveResults) {
        for (A_CoachBookingList.DataBean result : moveResults) {
            add(result);
        }
    }

    public void setList(List<A_CoachBookingList.DataBean> list) {
        this.supplierData = list;
        notifyDataSetChanged();
    }

    public void remove(A_CoachBookingList.DataBean r) {
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
        A_CoachBookingList.DataBean result = getItem(position);

        if (result != null) {
            supplierData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public A_CoachBookingList.DataBean getItem(int position) {
        return supplierData.get(position);
    }



    public interface onSpaceClick {
        public void getSpaceAmount(AthleteSpaceBookList.DataBeanX.DataBean dataBean);
    }

    public class CustomTopCoachesHolder extends RecyclerView.ViewHolder {

        AppCompatImageView eventImage,ti_tickets;
        RatingBar bookingRating;
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
            bookingRating = itemView.findViewById(R.id.bookingRating);
        }
    }
    protected class LoadingVH extends CustomTopCoachesHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

}
