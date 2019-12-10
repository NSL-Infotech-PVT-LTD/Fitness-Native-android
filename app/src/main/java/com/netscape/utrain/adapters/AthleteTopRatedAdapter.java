package com.netscape.utrain.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.TopCoachOrgDetailActivity;
import com.netscape.utrain.activities.athlete.TopCoachesDetailsActivity;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class AthleteTopRatedAdapter extends RecyclerView.Adapter<AthleteTopRatedAdapter.CoachListView> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    Context context;
    List<CoachListModel> coachList;
    private int type;
    private String service = "";
    private boolean isLoadingAdded = false;

    public AthleteTopRatedAdapter(Context context, List<CoachListModel> coachList, int type) {

        this.context = context;
        this.coachList = coachList;
        this.type = type;
    }

    public AthleteTopRatedAdapter(Context context,int type) {
        this.context = context;
        coachList = new ArrayList<>();
        this.type = type;
    }

    @NonNull
    @Override
    public AthleteTopRatedAdapter.CoachListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_layout_design_recycler, parent, false);

        AthleteTopRatedAdapter.CoachListView viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);

                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;

//        return new AthleteTopRatedAdapter.CoachListView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AthleteTopRatedAdapter.CoachListView holder, int position) {
        final CoachListModel data = coachList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
        if(data!=null)
        holder.tvName.setText(data.getName());
        if (data.getRoles() != null && data.getRoles().size() > 0) {
            holder.typeUser.setText(data.getRoles().get(0).getName());
        }
        if (data.getService_ids() != null && data.getService_ids().size() > 0) {
//            for (int i=0;i<data.getService_ids().size();i++){
            holder.servicesUser.setText(data.getService_ids().get(0).getName());
//            }
            if (data.getRating() != null) {
                if (!data.getRating().equalsIgnoreCase("0")) {
                }
            }


            if (data.getRating()!=null) {
                if (data.getRating().equalsIgnoreCase("0")) {
                    holder.AthTopRatingView.setVisibility(View.VISIBLE);
                    holder.ratingBar.setVisibility(View.GONE);
                } else {
                    holder.ratingBar.setVisibility(View.VISIBLE);
                    holder.AthTopRatingView.setVisibility(View.GONE);
                    holder.ratingBar.setRating(Float.parseFloat(data.getRating()));
                }
            }


        }

        if (type == 1) {
            Glide.with(context).load(Constants.COACH_IMAGE_BASE_URL + data.getProfile_image()).thumbnail(Glide.with(context).load(Constants.COACH_IMAGE_BASE_URL + Constants.THUMBNAILS + data.getProfile_image())).into(holder.profileImage);
        }
        if (type == 2) {
            Glide.with(context).load(Constants.ORG_IMAGE_BASE_URL + data.getProfile_image()).thumbnail(Glide.with(context).load(Constants.ORG_IMAGE_BASE_URL + Constants.THUMBNAILS + data.getProfile_image())).into(holder.profileImage);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent topCoachesDetails = new Intent(context, TopCoachOrgDetailActivity.class);
                if (type == 1) {
                    topCoachesDetails.putExtra("intentFrom", "coach");
                }
                if (type == 2) {
                    topCoachesDetails.putExtra("intentFrom", "org");
                }
                topCoachesDetails.putExtra(Constants.TOP_DATA_INTENT, data);
                topCoachesDetails.putExtra(Constants.TOP_FROM_INTENT, type + "");
                topCoachesDetails.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(topCoachesDetails);
            }
        });

                break;
            case LOADING:
//                Do nothing
                break;

        }
    }


    @Override
    public int getItemCount() {
        return coachList == null ? 0 : coachList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == coachList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    @NonNull
    private AthleteTopRatedAdapter.CoachListView getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        AthleteTopRatedAdapter.CoachListView viewHolder;
        View v1 = inflater.inflate(R.layout.discover_layout_design_recycler, parent, false);
        viewHolder = new AthleteTopRatedAdapter.CoachListView(v1);
        return viewHolder;
    }

    public void add(CoachListModel r) {
        coachList.add(r);
        notifyItemInserted(coachList.size() - 1);
    }

    public void addAll(List<CoachListModel> moveResults) {
        for (CoachListModel result : moveResults) {
            add(result);
        }
    }

    public void setList(List<CoachListModel> list) {
        this.coachList = list;
        notifyDataSetChanged();
    }

    public void remove(CoachListModel r) {
        int position = coachList.indexOf(r);
        if (position > -1) {
            coachList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
//        add(new C_ProductsSerial.Datum());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = coachList.size() - 1;
        CoachListModel result = getItem(position);

        if (result != null) {
            coachList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public CoachListModel getItem(int position) {
        return coachList.get(position);
    }

    public class CoachListView extends RecyclerView.ViewHolder {
        RatingBar ratingBar;
        AppCompatImageView profileImage;
        MaterialTextView tvName, typeUser, servicesUser,AthTopRatingView;

        public CoachListView(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.discoverUserImg);
            tvName = itemView.findViewById(R.id.discvoverRecyclerNameTv);
            typeUser = itemView.findViewById(R.id.discoverType);
            servicesUser = itemView.findViewById(R.id.discoverServices);
            ratingBar = itemView.findViewById(R.id.discoverRating);
            AthTopRatingView = itemView.findViewById(R.id.AthTopRatingView);

        }
    }

    protected class LoadingVH extends CoachListView {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}
