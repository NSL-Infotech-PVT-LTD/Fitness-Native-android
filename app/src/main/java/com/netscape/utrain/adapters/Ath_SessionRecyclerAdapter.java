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
import com.netscape.utrain.model.AthleteSessionModel;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Ath_SessionRecyclerAdapter extends RecyclerView.Adapter<Ath_SessionRecyclerAdapter.ViewHolder> {
    AthleteEventData eventData;
    String value = "", valueEnd = "";
    private Context context;
    private int previusPos = -1;
    private List<AthleteSessionModel> supplierData;


    public Ath_SessionRecyclerAdapter(Context context, List<AthleteSessionModel> supplierData) {
        this.context = context;
        this.supplierData = supplierData;
        this.eventData = eventData;
    }

    @NonNull
    @Override
    public Ath_SessionRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_event_session_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Ath_SessionRecyclerAdapter.ViewHolder holder, final int position) {

        final AthleteSessionModel data = supplierData.get(position);

        holder.trainingSessionVenueDetailTv.setText(data.getLocation());
        holder.eventName.setText(data.getName());
        holder.trainingSessionEndDateEnterTv.setText(data.getEnd_date());
        holder.trainingSessionTimeEnterTv.setText(data.getStart_time()+" To "+data.getEnd_time());

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
                intent.putExtra("eventName", data.getName());
                intent.putExtra("guest_allowed", data.getGuest_allowed() + "");
                intent.putExtra("guest_allowed_left", data.getGuest_allowed_left() + "");
                intent.putExtra("eventVenue", data.getLocation());
                intent.putExtra("eventTime", data.getStart_time());
                intent.putExtra("eventDate", data.getStart_date());
                intent.putExtra("eventDescription", data.getDescription());
                intent.putExtra("image_url", Constants.IMAGE_BASE_SESSION);
                intent.putExtra("event_id", data.getId());
                intent.putExtra("from", "sessions");
                Bundle b = new Bundle();
                b.putString("Array", data.getImages());
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });

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
        return supplierData.size();
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

}
