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

import com.netscape.utrain.R;
import com.netscape.utrain.activities.EventDetail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CoachesRecyclerAdapter extends RecyclerView.Adapter<CoachesRecyclerAdapter.ViewHolder> {
    private Context context;
    private int previusPos = -1;
    private List<String> supplierData;


    public CoachesRecyclerAdapter(Context context, List supplierData) {
        this.context = context;
        this.supplierData = supplierData;
    }

    @NonNull
    @Override
    public CoachesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.athlete_event_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoachesRecyclerAdapter.ViewHolder holder, final int position) {

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventDetail.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return supplierData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView eventName;
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
//
//            container = itemView.findViewById(R.id.container);
//            ratingBar = itemView.findViewById(R.id.supplierRating);
        }

    }

}
