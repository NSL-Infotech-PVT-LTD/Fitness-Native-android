package com.netscape.utrain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.response.CoachListResponse;

import java.util.List;

public class CoachGridRecyclerAdapter extends RecyclerView.Adapter<CoachGridRecyclerAdapter.CoachGridView> {

    Context context;
    List<CoachListModel> list;

    public CoachGridRecyclerAdapter(Context context, List<CoachListModel> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CoachGridView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_coach_service_grid, parent, false);
        return new CoachGridView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoachGridView holder, int position) {

        CoachListModel data = list.get(position);

        holder.priceDispText.setText(data.getHourly_rate()+"");
        holder.durationDispText.setText(data.getBusiness_hour_starts());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CoachGridView extends RecyclerView.ViewHolder {

        MaterialTextView priceDispText, durationDispText;


        public CoachGridView(@NonNull View itemView) {
            super(itemView);

            priceDispText = itemView.findViewById(R.id.priceDispText);
            durationDispText = itemView.findViewById(R.id.durationDispText);

        }
    }
}
