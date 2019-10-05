package com.netscape.utrain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.model.CoachListModel;

import java.util.List;

public class AthleteTopRatedAdapter  extends RecyclerView.Adapter<AthleteTopRatedAdapter.CoachListView> {

    Context context;
    List<CoachListModel> coachList;

    public AthleteTopRatedAdapter( Context context, List<CoachListModel> coachList){

        this.context = context;
        this.coachList = coachList;

    }

    @NonNull
    @Override
    public AthleteTopRatedAdapter.CoachListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_layout_design_recycler, parent, false);

        return new AthleteTopRatedAdapter.CoachListView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AthleteTopRatedAdapter.CoachListView holder, int position) {

        CoachListModel data = coachList.get(position);
        holder.tvName.setText(data.getName());
        Glide.with(context).load(data.getProfile_image()).into(holder.profileImage);

    }

    @Override
    public int getItemCount() {
        return coachList.size();
    }

    public class CoachListView extends RecyclerView.ViewHolder {

        RatingBar ratingBar;
        AppCompatImageView profileImage;
        MaterialTextView tvName,type,services;

        public CoachListView(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.discoverUserImg);
            tvName = itemView.findViewById(R.id.discvoverRecyclerNameTv);
            type = itemView.findViewById(R.id.discoverType);
            services = itemView.findViewById(R.id.discoverServices);
            ratingBar = itemView.findViewById(R.id.discoverRating);

        }
    }

}
