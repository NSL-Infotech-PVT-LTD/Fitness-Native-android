package com.netscape.utrain.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.TopCoachOrgDetailActivity;
import com.netscape.utrain.activities.athlete.TopCoachesDetailsActivity;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.utils.Constants;

import java.util.List;

public class TopCoachesAdapter extends RecyclerView.Adapter<TopCoachesAdapter.CustomTopCoachesHolder> {

    private Context context;
    private int previusPos=-1;
    private List<CoachListModel> supplierData;
    public TopCoachesAdapter(Context context, List supplierData){
        this.context = context;
        this.supplierData = supplierData;

    }


    @NonNull
    @Override
    public CustomTopCoachesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_coaches_recycler_design, parent, false);
        return new CustomTopCoachesHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CustomTopCoachesHolder holder, int position) {
                    final CoachListModel data=supplierData.get(position);
        Glide.with(context).load(Constants.COACH_IMAGE_BASE_URL+data.getProfile_image()).thumbnail( Glide.with(context).load(Constants.COACH_IMAGE_BASE_URL+Constants.THUMBNAILS+data.getProfile_image())).into(holder.imageView);
        holder.name.setText(data.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent topCoachesDetails=new Intent(context, TopCoachOrgDetailActivity.class);
                topCoachesDetails.putExtra(Constants.TOP_DATA_INTENT,data);
                topCoachesDetails.putExtra(Constants.TOP_FROM_INTENT,"1");
                context.startActivity(topCoachesDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return supplierData.size();
    }

    public class CustomTopCoachesHolder extends RecyclerView.ViewHolder {

        AppCompatImageView imageView;
        MaterialTextView name;

        public CustomTopCoachesHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.topCoachesRecyclerImg);
            name = itemView.findViewById(R.id.topCoachesRecyclerTv);
        }
    }
}
