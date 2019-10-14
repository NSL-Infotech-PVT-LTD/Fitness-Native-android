package com.netscape.utrain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.model.OnGoingSessionCoachBottomNavModel;

import java.util.List;

public class OngoingSessionAdapter extends RecyclerView.Adapter<OngoingSessionAdapter.OngoingSessionHolder> {

    Context context;
    List<OnGoingSessionCoachBottomNavModel> list;

    public OngoingSessionAdapter(Context context, List<OnGoingSessionCoachBottomNavModel> list){

        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public OngoingSessionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.evet_list_view,parent,false);

        return new OngoingSessionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OngoingSessionHolder holder, int position) {

        OnGoingSessionCoachBottomNavModel data = list.get(position);
        holder.sport.setText(data.getSport());
        holder.name.setText(data.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OngoingSessionHolder extends RecyclerView.ViewHolder {

        MaterialTextView name,sport;

        public OngoingSessionHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.ongoingRecyclerOrganizationNameTv);
            sport =  itemView.findViewById(R.id.ongoingRecyclerSportsName);
        }
    }
}
