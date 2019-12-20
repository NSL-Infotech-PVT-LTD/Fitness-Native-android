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
import com.netscape.utrain.model.C_SessionListModel;
import com.netscape.utrain.model.O_EventDataModel;
import com.netscape.utrain.model.O_SpaceDataModel;
import com.netscape.utrain.model.SelectSpaceDaysModel;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class O_SpaceListAdapter extends RecyclerView.Adapter<O_SpaceListAdapter.CustomTopCoachesHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private Context context;
    private int previusPos = -1;
    private List<O_SpaceDataModel> supplierData;
    private String status;
    List<SelectSpaceDaysModel> startWeekList = new ArrayList<>();

    public O_SpaceListAdapter(Context context, List supplierD,String status) {
        this.context = context;
        this.supplierData = supplierD;
        this.status = status;

    }
    public O_SpaceListAdapter(Context context, String status) {
        this.context = context;
        supplierData = new ArrayList<>();
        this.status = status;

    }


    @NonNull
    @Override
    public O_SpaceListAdapter.CustomTopCoachesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_view, parent, false);
//        return new O_SpaceListAdapter.CustomTopCoachesHolder(view);
        O_SpaceListAdapter.CustomTopCoachesHolder viewHolder = null;
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
    private   O_SpaceListAdapter.CustomTopCoachesHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        O_SpaceListAdapter.CustomTopCoachesHolder viewHolder;
        View v1 = inflater.inflate(R.layout.booking_view, parent, false);
        viewHolder = new   O_SpaceListAdapter.CustomTopCoachesHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull O_SpaceListAdapter.CustomTopCoachesHolder holder, int position) {
        final O_SpaceDataModel data = supplierData.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                if(data!=null)
        try {
            if (data.getImages() != null) {
                JSONArray jsonArray = new JSONArray(data.getImages());
                if (jsonArray !=null && jsonArray.length()>0){
                    Glide.with(context).load(Constants.IMAGE_BASE_PLACE + jsonArray.get(0)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_PLACE + Constants.THUMBNAILS + jsonArray.get(0))).into(holder.eventImage);
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
        //        Glide.with(context).load(Constants.COACH_IMAGE_BASE_URL+data.getImages().into(holder.imageView);
        holder.eventName.setText(data.getName());
        holder.eventVenue.setText(data.getLocation());
        holder.bookingTicketTv.setVisibility(View.GONE);
        holder.ti_tickets.setVisibility(View.GONE);

                startWeekList= CommonMethods.getDaysFromId(data.getAvailability_week(),CommonMethods.getWeekDaysList());
                if (startWeekList !=null && startWeekList.size()>0){
                    holder.eventDate.setText(startWeekList.get(0).getDayName()+"..");
                }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent topCoachesDetails = new Intent(context, EventAppliedList.class);
                topCoachesDetails.putExtra(Constants.SELECTED_ID, data.getId() + "");
                topCoachesDetails.putExtra(Constants.SELECTED_TYPE, Constants.SPACE);
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


    public void add(O_SpaceDataModel r) {
        supplierData.add(r);
        notifyItemInserted(supplierData.size() - 1);
    }

    public void addAll(List<O_SpaceDataModel> moveResults) {
        for (O_SpaceDataModel result : moveResults) {
            add(result);
        }
    }

    public void setList(List<O_SpaceDataModel> list) {
        this.supplierData = list;
        notifyDataSetChanged();
    }

    public void remove(O_SpaceDataModel r) {
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
        O_SpaceDataModel result = getItem(position);

        if (result != null) {
            supplierData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public O_SpaceDataModel getItem(int position) {
        return supplierData.get(position);
    }

    public class CustomTopCoachesHolder extends RecyclerView.ViewHolder {


        AppCompatImageView eventImage,ti_tickets, statusImage;



        MaterialTextView eventName, eventVenue, bookingTicketTv, eventDate;


        public CustomTopCoachesHolder(@NonNull View itemView) {
            super(itemView);

            eventImage = itemView.findViewById(R.id.bookingEventImage);
            ti_tickets = itemView.findViewById(R.id.ti_tickets);
            statusImage = itemView.findViewById(R.id.statusImage);
            eventName = itemView.findViewById(R.id.bookingEventName);
            eventVenue = itemView.findViewById(R.id.bookingVenueTv);
            eventDate = itemView.findViewById(R.id.bookingEventDate);
            bookingTicketTv = itemView.findViewById(R.id.bookingTicketTv);
        }
    }

    protected class LoadingVH extends CustomTopCoachesHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
