package com.netscape.utrain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.model.CoachListModel;

import java.util.List;

public class CoachListAdapter extends RecyclerView.Adapter<CoachListAdapter.CoachListView> {

    Context context;
    List<CoachListModel> coachList;

    public CoachListAdapter( Context context, List<CoachListModel> coachList){

        this.context = context;
        this.coachList = coachList;

    }

    @NonNull
    @Override
    public CoachListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_coaches_recycler_design, parent, false);

        return new CoachListView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoachListView holder, int position) {

        CoachListModel data = coachList.get(position);
        holder.tvName.setText(data.getName());
        Glide.with(context).load(data.getProfile_image()).into(holder.profileImage);

    }

    @Override
    public int getItemCount() {
        return coachList.size();
    }

    public class CoachListView extends RecyclerView.ViewHolder {


        AppCompatImageView profileImage;
        MaterialTextView tvName;

        public CoachListView(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.topCoachesRecyclerImg);
            tvName = itemView.findViewById(R.id.topCoachesRecyclerTv);

        }
    }
}
