package com.netscape.utrain.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.EventDetail;
import com.netscape.utrain.model.AthleteEventListModel;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CoachesRecyclerAdapter extends RecyclerView.Adapter<CoachesRecyclerAdapter.ViewHolder> {
    private Context context;
    private int previusPos = -1;
    private List<AthleteEventListModel> supplierData;
    AthleteEventData eventData;
    String value = "", valueEnd = "";


    public CoachesRecyclerAdapter(Context context, List<AthleteEventListModel> supplierData) {
        this.context = context;
        this.supplierData = supplierData;
        this.eventData = eventData;
    }

    @NonNull
    @Override
    public CoachesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.athlete_event_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoachesRecyclerAdapter.ViewHolder holder, final int position) {

        final AthleteEventListModel data = supplierData.get(position);

        String currentString = data.getStart_at();
        String currentStringEnd = data.getEnd_at();
        String[] separated = currentString.split(" ");
        String[] separatedEnd = currentStringEnd.split(" ");

        holder.eventName.setText(data.getName());
        holder.athleteEventAddressTv.setText(data.getLocation());
//        holder.eventEndDateTimeEnterTv.setText(data.getEnd_at());
        Glide.with(context).load(R.drawable.park).into(holder.eventProfileImg);


        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm aa");
        Date dt, dtEnd;


        try {
            dt = sdf.parse(separated[1]);
            dtEnd = sdf.parse(separatedEnd[1]);

            value = parseDateToddMMyyyy(separated[0]) + "  " + sdfs.format(dt);
            valueEnd = parseDateToddMMyyyy(separatedEnd[0]) + "  " + sdfs.format(dtEnd);
            holder.eventStartDateTimeEnterTv.setText(value);
            holder.eventEndDateTimeEnterTv.setText(valueEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventDetail.class);
                intent.putExtra("eventName", data.getName());
                intent.putExtra("eventVenue", data.getLocation());
                intent.putExtra("evenStartDateTime", value);
                intent.putExtra("eventEndDateTime", valueEnd);
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

        private AppCompatTextView eventName, athleteEventAddressTv, eventEndDateTimeEnterTv, eventStartDateTimeEnterTv;
        private ImageView eventProfileImg;
        private ConstraintLayout constraintLayout;

//        public RatingBar ratingBar;
//        public ImageView selectedImage;
//        public ConstraintLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.athleteEventHeaderTv);
            eventProfileImg = itemView.findViewById(R.id.athleteEventProfileImg);
            constraintLayout = itemView.findViewById(R.id.athleteEventLayout);
            athleteEventAddressTv = itemView.findViewById(R.id.athleteEventAddressTv);
            eventStartDateTimeEnterTv = itemView.findViewById(R.id.eventStartDateTimeEnterTv);
            eventEndDateTimeEnterTv = itemView.findViewById(R.id.eventEndDateTimeEnterTv);
//
//            container = itemView.findViewById(R.id.container);
//            ratingBar = itemView.findViewById(R.id.supplierRating);
        }

    }

}
