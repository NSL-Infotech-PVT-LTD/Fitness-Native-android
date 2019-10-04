package com.netscape.utrain.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.EventDetail;
import com.netscape.utrain.model.AthleteEventListModel;
import com.netscape.utrain.model.AthleteSessionModel;
import com.netscape.utrain.response.AthleteSessionResponse;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Ath_SessionRecyclerAdapter extends RecyclerView.Adapter<Ath_SessionRecyclerAdapter.ViewHolder> {
    private Context context;
    private int previusPos = -1;
    private List<AthleteSessionModel> supplierData;
    AthleteEventData eventData;
    String value = "", valueEnd = "";


    public Ath_SessionRecyclerAdapter(Context context, List<AthleteSessionModel> supplierData) {
        this.context = context;
        this.supplierData = supplierData;
        this.eventData = eventData;
    }

    @NonNull
    @Override
    public Ath_SessionRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_session_layout_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Ath_SessionRecyclerAdapter.ViewHolder holder, final int position) {

        final AthleteSessionModel data = supplierData.get(position);


        holder.eventName.setText(data.getName());
//        holder.athleteEventAddressTv.setText(data.getLocation());
//        holder.eventEndDateTimeEnterTv.setText(data.getBusiness_hour()+" "+data.getBusiness_hour());
        holder.eventStartDateTimeEnterTv.setText(data.getBusiness_hour()+" "+data.getDate());
        holder.findPlaceActualPriceTv.setText("$"+data.getHourly_rate()+"/hr");

        try {
            JSONArray jsonArray = new JSONArray(data.getImages());
            for (int i = 0; i < jsonArray.length(); i++) {
                Glide.with(context).load(Constants.IMAGE_BASE_SESSION + jsonArray.get(i)).into(holder.eventProfileImg);

            }
        } catch (JSONException e) {

            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

        }


        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm aa");
        Date dt, dtEnd;


        holder.viewPlacesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventDetail.class);
                intent.putExtra("eventName", data.getName());
//                intent.putExtra("eventVenue", data.getLocation());
                intent.putExtra("evenStartDateTime", data.getDate());
                intent.putExtra("eventEndDateTime", data.getBusiness_hour());
                intent.putExtra("eventDescription", data.getDescription());

                context.startActivity(intent);
            }
        });

    }

    public interface AthleteEventData {
        public void getData(Intent intent);

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView eventName, findPlaceActualPriceTv,eventStartDateTimeEnterTv;
        private ImageView eventProfileImg;
        private MaterialButton viewPlacesBtn;

//        public RatingBar ratingBar;
//        public ImageView selectedImage;
//        public ConstraintLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.trainingSessionProfessionDesc);
            eventProfileImg = itemView.findViewById(R.id.findPlaceImage);
            viewPlacesBtn = itemView.findViewById(R.id.viewPlacesBtn);
            eventStartDateTimeEnterTv = itemView.findViewById(R.id.trainingSessionDateTimeEnterTv);
            findPlaceActualPriceTv = itemView.findViewById(R.id.findPlaceActualPriceTv);
//
//            container = itemView.findViewById(R.id.container);
//            ratingBar = itemView.findViewById(R.id.supplierRating);
        }

    }

}
