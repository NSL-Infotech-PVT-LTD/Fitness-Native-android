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
import com.netscape.utrain.activities.athlete.TopCoachesDetailsActivity;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.utils.Constants;

import java.util.List;

public class AthleteTopRatedAdapter extends RecyclerView.Adapter<AthleteTopRatedAdapter.CoachListView> {

    Context context;
    List<CoachListModel> coachList;
    private int type;
    private String service = "";

    public AthleteTopRatedAdapter(Context context, List<CoachListModel> coachList, int type) {

        this.context = context;
        this.coachList = coachList;
        this.type = type;
    }

    @NonNull
    @Override
    public AthleteTopRatedAdapter.CoachListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_layout_design_recycler, parent, false);
        return new AthleteTopRatedAdapter.CoachListView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AthleteTopRatedAdapter.CoachListView holder, int position) {
        final CoachListModel data = coachList.get(position);
        holder.tvName.setText(data.getName());
        if (data.getRoles() != null && data.getRoles().size() > 0) {
            holder.typeUser.setText(data.getRoles().get(0).getName());
        }
        if (data.getService_ids() != null && data.getService_ids().size() > 0) {
//            for (int i=0;i<data.getService_ids().size();i++){
            holder.servicesUser.setText(data.getService_ids().get(0).getName());
//            }


        }

        if (type == 1) {
            Glide.with(context).load(Constants.COACH_IMAGE_BASE_URL + data.getProfile_image()).into(holder.profileImage);
        }
        if (type == 2) {
            Glide.with(context).load(Constants.ORG_IMAGE_BASE_URL + data.getProfile_image()).into(holder.profileImage);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent topCoachesDetails = new Intent(context, TopCoachesDetailsActivity.class);
                topCoachesDetails.putExtra(Constants.TOP_DATA_INTENT, data);
                topCoachesDetails.putExtra(Constants.TOP_FROM_INTENT, type + "");
                topCoachesDetails.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(topCoachesDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return coachList.size();
    }

    public class CoachListView extends RecyclerView.ViewHolder {
        RatingBar ratingBar;
        AppCompatImageView profileImage;
        MaterialTextView tvName, typeUser, servicesUser;

        public CoachListView(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.discoverUserImg);
            tvName = itemView.findViewById(R.id.discvoverRecyclerNameTv);
            typeUser = itemView.findViewById(R.id.discoverType);
            servicesUser = itemView.findViewById(R.id.discoverServices);
            ratingBar = itemView.findViewById(R.id.discoverRating);

        }
    }

}
