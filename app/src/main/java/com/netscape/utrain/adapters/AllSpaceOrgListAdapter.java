package com.netscape.utrain.adapters;

import android.content.Context;
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
import com.netscape.utrain.model.O_SessionDataModel;
import com.netscape.utrain.model.O_SpaceDataModel;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class AllSpaceOrgListAdapter extends RecyclerView.Adapter<AllSpaceOrgListAdapter.AllSpaceOrgHolder> {

    private Context context;
    private List<O_SpaceDataModel> list;

    public AllSpaceOrgListAdapter(Context context, List<O_SpaceDataModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public AllSpaceOrgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_place_old_layout, parent, false);
        return new AllSpaceOrgHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllSpaceOrgHolder holder, int position) {

        O_SpaceDataModel data = list.get(position);

        holder.placeNameInfoTv.setText(data.getName());
        holder.placenameTv.setText(data.getLocation());
//        holder.findPlaceDistanceTv.setText("Description");
        holder.findPlaceDistanceDetailTv.setText(data.getDescription());
        holder.findPlaceDistanceDetailTv.setTextColor(Color.LTGRAY);
        holder.findPlaceActualPriceTv.setText("$" + data.getPrice_daily() + "/day");
//        holder.statusImage.setVisibility(View.GONE);

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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AllSpaceOrgHolder extends RecyclerView.ViewHolder {

        MaterialTextView placeNameInfoTv, placenameTv, findPlaceDistanceDetailTv, bookingTicketTv, findPlaceDistanceTv, findPlaceActualPriceTv;
        AppCompatImageView findPlaceImage, statusImage;       // Using the booking.view layout which is same for completed....

        public AllSpaceOrgHolder(@NonNull View itemView) {
            super(itemView);

            placeNameInfoTv = itemView.findViewById(R.id.placeNameInfoTv);
            placenameTv = itemView.findViewById(R.id.placenameTv);
            findPlaceDistanceDetailTv = itemView.findViewById(R.id.findPlaceDistanceDetailTv);
            findPlaceActualPriceTv = itemView.findViewById(R.id.findPlaceActualPriceTv);
            findPlaceImage = itemView.findViewById(R.id.findPlaceImage);

        }
    }
}
