package com.netscape.utrain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.netscape.utrain.R;

import java.util.ArrayList;
import java.util.List;

public class CoachesRecyclerAdapter extends RecyclerView.Adapter<CoachesRecyclerAdapter.ViewHolder> {
    private Context context;
    private int previusPos=-1;
    private List<String> supplierData;


    public CoachesRecyclerAdapter(Context context, List supplierData) {
        this.context = context;
        this.supplierData = supplierData;
    }

    @NonNull
    @Override
    public CoachesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coaches_recycler_view, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CoachesRecyclerAdapter.ViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return supplierData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView supplierName;
        public ImageView selectionImg;
        public RatingBar ratingBar;
        public ImageView selectedImage;
        public ConstraintLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            supplierName = itemView.findViewById(R.id.supplierName);
            container = itemView.findViewById(R.id.container);
            selectionImg = itemView.findViewById(R.id.coachImageView);
            ratingBar = itemView.findViewById(R.id.supplierRating);
        }

    }


}
