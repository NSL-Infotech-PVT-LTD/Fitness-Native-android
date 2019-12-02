package com.netscape.utrain.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.CreateEventActivity;
import com.netscape.utrain.activities.OfferSpaceActivity;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.model.O_SessionDataModel;
import com.netscape.utrain.model.O_SpaceDataModel;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class AllSpaceOrgListAdapter extends RecyclerView.Adapter<AllSpaceOrgListAdapter.AllSpaceOrgHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    private Context context;
    private List<O_SpaceDataModel> list;

    public AllSpaceOrgListAdapter(Context context, List<O_SpaceDataModel> list) {
        this.context = context;
        this.list = list;
    }
    public AllSpaceOrgListAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }


    @NonNull
    @Override
    public AllSpaceOrgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_place_old_layout, parent, false);
//        return new AllSpaceOrgHolder(view);
        AllSpaceOrgHolder viewHolder = null;
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

    }
    @NonNull
    private AllSpaceOrgHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        AllSpaceOrgHolder viewHolder;
        View v1 = inflater.inflate(R.layout.find_place_old_layout, parent, false);
        viewHolder = new AllSpaceOrgHolder(v1);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull AllSpaceOrgHolder holder, int position) {

        O_SpaceDataModel data = list.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                if(data!=null)
        holder.placeNameInfoTv.setText(data.getName());
        holder.placenameTv.setText(data.getLocation());
//        holder.findPlaceDistanceTv.setText("Description");
        holder.findPlaceDistanceDetailTv.setText(data.getDescription());
        holder.findPlaceDistanceDetailTv.setTextColor(Color.LTGRAY);
        holder.findPlaceActualPriceTv.setText("$" + data.getPrice_daily() + "/day");
//        holder.statusImage.setVisibility(View.GONE);
        holder.editImage.setVisibility(View.VISIBLE);

        try {
            if (data.getImages() != null) {
                JSONArray jsonArray = new JSONArray(data.getImages());
                if (jsonArray != null && jsonArray.length() > 0) {
                    Glide.with(context).load(Constants.IMAGE_BASE_PLACE + jsonArray.get(0)).thumbnail(Glide.with(context).
                            load(Constants.IMAGE_BASE_PLACE + Constants.THUMBNAILS + jsonArray.get(0))).into(holder.findPlaceImage);
                }
            }
        } catch (JSONException e) {

            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        holder.editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent spaceEdit=new Intent(context, OfferSpaceActivity.class);
                spaceEdit.putExtra("spaceEdit",data);
                OfferSpaceActivity.spaceEdit=true;
                context.startActivity(spaceEdit);
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
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == list.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }



    public void add(O_SpaceDataModel r) {
        list.add(r);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll(List<O_SpaceDataModel> moveResults) {
        for (O_SpaceDataModel result : moveResults) {
            add(result);
        }
    }

    public void setList(List<O_SpaceDataModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void remove(O_SpaceDataModel r) {
        int position = list.indexOf(r);
        if (position > -1) {
            list.remove(position);
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

        int position = list.size() - 1;
        O_SpaceDataModel result = getItem(position);

        if (result != null) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public O_SpaceDataModel getItem(int position) {
        return list.get(position);
    }

    public class AllSpaceOrgHolder extends RecyclerView.ViewHolder {

        MaterialTextView placeNameInfoTv, placenameTv, findPlaceDistanceDetailTv, bookingTicketTv, findPlaceDistanceTv, findPlaceActualPriceTv;
        AppCompatImageView findPlaceImage, statusImage,editImage;       // Using the booking.view layout which is same for completed....

        public AllSpaceOrgHolder(@NonNull View itemView) {
            super(itemView);

            placeNameInfoTv = itemView.findViewById(R.id.placeNameInfoTv);
            placenameTv = itemView.findViewById(R.id.placenameTv);
            findPlaceDistanceDetailTv = itemView.findViewById(R.id.findPlaceDistanceDetailTv);
            findPlaceActualPriceTv = itemView.findViewById(R.id.findPlaceActualPriceTv);
            findPlaceImage = itemView.findViewById(R.id.findPlaceImage);
            editImage = itemView.findViewById(R.id.editImage);

        }
    }

    protected class LoadingVH extends AllSpaceOrgHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
