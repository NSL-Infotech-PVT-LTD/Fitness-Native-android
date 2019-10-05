package com.netscape.utrain.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.netscape.utrain.activities.TopCoachesDetailsActivity;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.model.ServiceIdModel;
import com.netscape.utrain.utils.Constants;

import java.util.List;

public class ServicesBottomSheetAdapter  extends RecyclerView.Adapter<ServicesBottomSheetAdapter.CoachListView> {

    Context context;
    List<ServiceIdModel> coachList;
    private int type;

    public ServicesBottomSheetAdapter(Context context, List<ServiceIdModel> coachList) {

        this.context = context;
        this.coachList = coachList;
    }

    @NonNull
    @Override
    public ServicesBottomSheetAdapter.CoachListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_name_view, parent, false);

        return new ServicesBottomSheetAdapter.CoachListView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesBottomSheetAdapter.CoachListView holder, int position) {
       ServiceIdModel data = coachList.get(position);
        holder.servicesName.setText(data.getName());
        holder.serviecPrice.setText(data.getPrice());

    }

    @Override
    public int getItemCount() {
        return coachList.size();
    }

    public class CoachListView extends RecyclerView.ViewHolder {
        MaterialTextView servicesName,serviecPrice;

        public CoachListView(@NonNull View itemView) {
            super(itemView);

            servicesName = itemView.findViewById(R.id.serviceNameTv);
            serviecPrice = itemView.findViewById(R.id.priceTv);


        }
    }


}
