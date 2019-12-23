package com.netscape.utrain.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.netscape.utrain.activities.athlete.TopCoachesDetailsActivity;
import com.netscape.utrain.activities.organization.EventAppliedList;
import com.netscape.utrain.model.AthleteBookListModel;
import com.netscape.utrain.model.O_EventDataModel;
import com.netscape.utrain.model.O_SessionDataModel;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.RatingInterface;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class O_SessionListAdapter extends RecyclerView.Adapter<O_SessionListAdapter.CustomTopCoachesHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private Context context;
    private int previusPos = -1;
    private List<O_SessionDataModel> supplierData;
    private String status;

    public O_SessionListAdapter(Context context, List supplierData, String status) {
        this.context = context;
        this.status = status;
        this.supplierData = supplierData;

    }
    public O_SessionListAdapter(Context context, String status) {
        this.context = context;
        supplierData = new ArrayList<>();
        this.status = status;

    }


    @NonNull
    @Override
    public O_SessionListAdapter.CustomTopCoachesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_view, parent, false);
//        return new O_SessionListAdapter.CustomTopCoachesHolder(view);
        O_SessionListAdapter.CustomTopCoachesHolder viewHolder = null;
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
    private O_SessionListAdapter.CustomTopCoachesHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        O_SessionListAdapter.CustomTopCoachesHolder viewHolder;
        View v1 = inflater.inflate(R.layout.booking_view, parent, false);
        viewHolder = new O_SessionListAdapter.CustomTopCoachesHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull O_SessionListAdapter.CustomTopCoachesHolder holder, int position) {
        final O_SessionDataModel data = supplierData.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                if (data != null)

        try {
            if (data.getImages() != null) {
                JSONArray jsonArray = new JSONArray(data.getImages());
                if (jsonArray !=null && jsonArray.length()>0){
                    Glide.with(context).load(Constants.IMAGE_BASE_SESSION + jsonArray.get(0)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_SESSION +Constants.THUMBNAILS+ jsonArray.get(0))).into(holder.eventImage);
                }
            }

        } catch (JSONException e) {

            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        if (status.equalsIgnoreCase("completed")) {
            holder.statusImage.setImageResource(R.drawable.ic_ti_confirm);
        } else {
            holder.statusImage.setImageResource(R.drawable.ic_yellow_ticket);

        }

        holder.eventName.setText(data.getName());
        holder.eventVenue.setText(data.getLocation());
        holder.bookingTicketTv.setText(data.getGuest_allowed() + " Attandees and Ticket(1 person per ticket)");
        holder.eventDate.setText(data.getStart_date() +" "+data.getStart_time());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent topCoachesDetails = new Intent(context, EventAppliedList.class);
                topCoachesDetails.putExtra(Constants.SELECTED_ID, data.getId() + "");
                topCoachesDetails.putExtra(Constants.SELECTED_TYPE, Constants.SESSION);
                topCoachesDetails.putExtra(Constants.STATUS, status);
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
        return supplierData == null ? 0 : supplierData.size();
    }



    @Override
    public int getItemViewType(int position) {
        return (position == supplierData.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void add(O_SessionDataModel r) {
        supplierData.add(r);
        notifyItemInserted(supplierData.size() - 1);
    }

    public void addAll(List<O_SessionDataModel> moveResults) {
        for (O_SessionDataModel result : moveResults) {
            add(result);
        }
    }

    public void setList(List<O_SessionDataModel> list) {
        this.supplierData = list;
        notifyDataSetChanged();
    }

    public void remove(O_SessionDataModel r) {
        int position = supplierData.indexOf(r);
        if (position > -1) {
            supplierData.remove(position);
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

        int position = supplierData.size() - 1;
        O_SessionDataModel result = getItem(position);

        if (result != null) {
            supplierData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public O_SessionDataModel getItem(int position) {
        return supplierData.get(position);
    }



    public class CustomTopCoachesHolder extends RecyclerView.ViewHolder {

        AppCompatImageView eventImage, statusImage;
        MaterialTextView eventName, eventVenue, eventDate, bookingTicketTv;

        public CustomTopCoachesHolder(@NonNull View itemView) {
            super(itemView);

            eventImage = itemView.findViewById(R.id.bookingEventImage);
            statusImage = itemView.findViewById(R.id.statusImage);
            eventName = itemView.findViewById(R.id.bookingEventName);
            eventVenue = itemView.findViewById(R.id.bookingVenueTv);
            bookingTicketTv = itemView.findViewById(R.id.bookingTicketTv);
            eventDate = itemView.findViewById(R.id.bookingEventDate);
        }
    }
    protected class LoadingVH extends CustomTopCoachesHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}
