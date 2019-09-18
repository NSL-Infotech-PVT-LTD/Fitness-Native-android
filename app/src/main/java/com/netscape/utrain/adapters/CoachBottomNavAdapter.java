package com.netscape.utrain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.model.CoachBottomNavModel;

import java.util.List;

public class CoachBottomNavAdapter extends RecyclerView.Adapter<CoachBottomNavAdapter.CoachBottomnavHolder> {

    Context context;
    List<CoachBottomNavModel> list;
    public CoachBottomNavAdapter(Context context, List<CoachBottomNavModel> list){

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CoachBottomnavHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coach_bottomnav_recyler_design, parent, false);

        return new CoachBottomnavHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoachBottomnavHolder holder, int position) {

        CoachBottomNavModel data = list.get(position);
        holder.image.setImageResource(data.getImage());
        holder.name.setText(data.getName());
        holder.date.setText(data.getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CoachBottomnavHolder extends RecyclerView.ViewHolder {

        AppCompatImageView image;
        MaterialTextView name, date;

        public CoachBottomnavHolder(@NonNull View itemView) {
            super(itemView);

            image = (AppCompatImageView)itemView.findViewById(R.id.coachBtmNavDesignImg);
            name = (MaterialTextView)itemView.findViewById(R.id.coachBtmNavNameTv);
            date = (MaterialTextView)itemView.findViewById(R.id.coachBtmNavDateTv);
        }
    }
}
