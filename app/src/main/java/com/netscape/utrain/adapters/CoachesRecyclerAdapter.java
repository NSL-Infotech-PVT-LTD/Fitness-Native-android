package com.netscape.utrain.adapters;

import android.content.Context;
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

    }

    @Override
    public int getItemCount() {
        return supplierData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView supplierName;
        public ImageView selectionImg;
//        public RatingBar ratingBar;
//        public ImageView selectedImage;
//        public ConstraintLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            supplierName = itemView.findViewById(R.id.athleteVanueTv);
            selectionImg = itemView.findViewById(R.id.athleteEventProfileImg);
//
//            container = itemView.findViewById(R.id.container);
//            ratingBar = itemView.findViewById(R.id.supplierRating);
        }

    }

}
