package com.netscape.utrain.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.EventDetail;
import com.netscape.utrain.model.AthleteEventListModel;
import com.netscape.utrain.model.AthleteSessionModel;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ath_SessionRecyclerAdapter extends RecyclerView.Adapter<Ath_SessionRecyclerAdapter.ViewHolder> {
    AthleteEventData eventData;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    String value = "", valueEnd = "";
    private Context context;
    private int previusPos = -1;
    private List<AthleteSessionModel> supplierData;
    private boolean isLoadingAdded = false;


    public Ath_SessionRecyclerAdapter(Context context, List<AthleteSessionModel> supplierData) {
        this.context = context;
        this.supplierData = supplierData;
        this.eventData = eventData;
    }
    public Ath_SessionRecyclerAdapter(Context context) {
        this.context = context;
        supplierData = new ArrayList<>();
    }

    @NonNull
    @Override
    public Ath_SessionRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_event_session_design, parent, false);
//        return new ViewHolder(view);

        Ath_SessionRecyclerAdapter.ViewHolder viewHolder = null;
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
    private Ath_SessionRecyclerAdapter.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        Ath_SessionRecyclerAdapter.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.find_event_session_design, parent, false);
        viewHolder = new ViewHolder(v1);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull Ath_SessionRecyclerAdapter.ViewHolder holder, final int position) {

        final AthleteSessionModel data = supplierData.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                holder.trainingSessionVenueDetailTv.setText(data.getLocation());
                holder.eventName.setText(data.getName());
                holder.trainingSessionEndDateEnterTv.setText(data.getEnd_date());
                holder.trainingSessionTimeEnterTv.setText(data.getStart_time() + " To " + data.getEnd_time());

                holder.findPlaceDistanceDetailTv.setText(data.getDistance() + " Miles");
//        holder.athleteEventAddressTv.setText(data.getLocation());
//        holder.eventEndDateTimeEnterTv.setText(data.getBusiness_hour()+" "+data.getBusiness_hour());

                holder.eventStartDateTimeEnterTv.setText(data.getStart_date() + " " + data.getStart_time() + " ");
                holder.findPlaceActualPriceTv.setText("$" + data.getHourly_rate() + "/hr");

                try {
                    JSONArray jsonArray = new JSONArray(data.getImages());
                    if (jsonArray != null && jsonArray.length() > 0) {
                        Glide.with(context).load(Constants.IMAGE_BASE_SESSION + jsonArray.get(0)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_SESSION + Constants.THUMBNAILS + jsonArray.get(0))).into(holder.trainingSessionPlaceImage);
                    }
                } catch (JSONException e) {

                    Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                }


                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm aa");
                Date dt, dtEnd;


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, EventDetail.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("eventName", data.getName());
                        intent.putExtra("guest_allowed", data.getGuest_allowed() + "");
                        intent.putExtra("guest_allowed_left", data.getGuest_allowed_left() + "");
                        intent.putExtra("eventVenue", data.getLocation());
                        intent.putExtra("eventTime", data.getStart_time());
                        intent.putExtra("eventEndTime", data.getEnd_time());
                        intent.putExtra("eventDate", data.getStart_date());
                        intent.putExtra("eventDescription", data.getDescription());
                        intent.putExtra("image_url", Constants.IMAGE_BASE_SESSION);
                        intent.putExtra("event_id", data.getId()+"");
                        intent.putExtra("from", "sessions");
                        intent.putExtra("gmapLat", data.getLatitude()+"");
                        intent.putExtra("gmapLong", data.getLongitude()+"");
                        Bundle b = new Bundle();
                        b.putString("Array", data.getImages());
                        intent.putExtras(b);
                        context.startActivity(intent);
                    }
                });
            case LOADING:
//                Do nothing
                break;

        }

    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd MMMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public int getItemCount() {
        return supplierData == null ? 0 : supplierData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == supplierData.size() - 1 && isLoadingAdded) ? LOADING : ITEM;

    }




        public void add(AthleteSessionModel r) {
            supplierData.add(r);
            notifyItemInserted(supplierData.size() - 1);
        }

        public void addAll(List<AthleteSessionModel> moveResults) {
            for (AthleteSessionModel result : moveResults) {
                add(result);
            }
        }

        public void setList(List<AthleteSessionModel> list) {
            this.supplierData = list;
            notifyDataSetChanged();
        }

        public void remove(AthleteSessionModel r) {
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
            AthleteSessionModel result = getItem(position);

            if (result != null) {
                supplierData.remove(position);
                notifyItemRemoved(position);
            }
        }

        public AthleteSessionModel getItem(int position) {
            return supplierData.get(position);
        }








    public interface AthleteEventData {
        public void getData(Intent intent);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView eventName, findPlaceDistanceDetailTv, findPlaceActualPriceTv,trainingSessionEndDateEnterTv,trainingSessionTimeEnterTv;
        private MaterialTextView eventStartDateTimeEnterTv, trainingSessionVenueDetailTv;
        private AppCompatImageView trainingSessionPlaceImage;
        private MaterialButton viewPlacesBtn;

//        public RatingBar ratingBar;
//        public ImageView selectedImage;
//        public ConstraintLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            trainingSessionVenueDetailTv = itemView.findViewById(R.id.trainingSessionVenueDetailTv);
            findPlaceDistanceDetailTv = itemView.findViewById(R.id.findPlaceDistanceDetailTv);
            eventName = itemView.findViewById(R.id.trainingSessionProfessionDesc);
            trainingSessionPlaceImage = itemView.findViewById(R.id.trainingSessionPlaceImage);
            viewPlacesBtn = itemView.findViewById(R.id.viewPlacesBtn);
            eventStartDateTimeEnterTv = itemView.findViewById(R.id.trainingSessionStrtDateEnterTv);
            findPlaceActualPriceTv = itemView.findViewById(R.id.findPlaceActualPriceTv);
            trainingSessionEndDateEnterTv = itemView.findViewById(R.id.trainingSessionEndDateEnterTv);
            trainingSessionTimeEnterTv = itemView.findViewById(R.id.trainingSessionTimeEnterTv);
//
//            container = itemView.findViewById(R.id.container);
//            ratingBar = itemView.findViewById(R.id.supplierRating);
        }

    }


    protected class LoadingVH extends Ath_SessionRecyclerAdapter.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

}
