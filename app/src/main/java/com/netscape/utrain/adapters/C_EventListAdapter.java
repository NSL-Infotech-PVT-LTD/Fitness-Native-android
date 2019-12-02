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
import com.netscape.utrain.model.A_EventDataListModel;
import com.netscape.utrain.model.C_EventDataListModel;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class C_EventListAdapter extends RecyclerView.Adapter<C_EventListAdapter.CustomTopCoachesHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private Context context;
    private int previusPos = -1;
    private List<C_EventDataListModel> a_eventList;
    private String status;


    public C_EventListAdapter(Context context, List supplierData, String status) {
        this.context = context;
        this.status = status;
        this.a_eventList = supplierData;
    }
    public C_EventListAdapter(Context context,  String status) {
        this.context = context;
        this.status = status;
       a_eventList = new ArrayList<>();
    }

    @NonNull
    @Override
    public C_EventListAdapter.CustomTopCoachesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_view, parent, false);
//        return new C_EventListAdapter.CustomTopCoachesHolder(view);
        C_EventListAdapter.CustomTopCoachesHolder viewHolder = null;
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
    private C_EventListAdapter.CustomTopCoachesHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        C_EventListAdapter.CustomTopCoachesHolder viewHolder;
        View v1 = inflater.inflate(R.layout.booking_view, parent, false);
        viewHolder = new C_EventListAdapter.CustomTopCoachesHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull C_EventListAdapter.CustomTopCoachesHolder holder, int position) {
        final C_EventDataListModel data = a_eventList.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                if(data!=null)
        try {
            if (data.getImages() != null) {
                JSONArray jsonArray = new JSONArray(data.getImages());
                if (jsonArray !=null && jsonArray.length()>0){
                    Glide.with(context).load(Constants.IMAGE_BASE_EVENT + jsonArray.get(0)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_EVENT + Constants.THUMBNAILS + jsonArray.get(0))).into(holder.eventImage);
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
        holder.eventDate.setText(data.getStart_date());
        holder.bookingTicketTv.setText(data.getGuest_allowed()+" Attandees and Ticket(1 person per ticket)");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent topCoachesDetails = new Intent(context, EventAppliedList.class);
                topCoachesDetails.putExtra(Constants.SELECTED_ID, data.getId() + "");
                topCoachesDetails.putExtra(Constants.SELECTED_TYPE, "Event");
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
        return a_eventList == null ? 0 : a_eventList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return (position == a_eventList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    public void add(C_EventDataListModel r) {
        a_eventList.add(r);
        notifyItemInserted(a_eventList.size() - 1);
    }

    public void addAll(List<C_EventDataListModel> moveResults) {
        for (C_EventDataListModel result : moveResults) {
            add(result);
        }
    }

    public void setList(List<C_EventDataListModel> list) {
        this.a_eventList = list;
        notifyDataSetChanged();
    }

    public void remove(C_EventDataListModel r) {
        int position = a_eventList.indexOf(r);
        if (position > -1) {
            a_eventList.remove(position);
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

        int position = a_eventList.size() - 1;
        C_EventDataListModel result = getItem(position);

        if (result != null) {
            a_eventList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public C_EventDataListModel getItem(int position) {
        return a_eventList.get(position);
    }

    public class CustomTopCoachesHolder extends RecyclerView.ViewHolder {

        AppCompatImageView eventImage, statusImage;
        MaterialTextView eventName, eventVenue, eventDate,bookingTicketTv, completedRatingText;

        public CustomTopCoachesHolder(@NonNull View itemView) {
            super(itemView);

            statusImage = itemView.findViewById(R.id.statusImage);
            eventImage = itemView.findViewById(R.id.bookingEventImage);
            eventName = itemView.findViewById(R.id.bookingEventName);
            eventVenue = itemView.findViewById(R.id.bookingVenueTv);
            bookingTicketTv = itemView.findViewById(R.id.bookingTicketTv);
            eventDate = itemView.findViewById(R.id.bookingEventDate);
        }
    }
    protected class LoadingVH extends CustomTopCoachesHolder{

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}
