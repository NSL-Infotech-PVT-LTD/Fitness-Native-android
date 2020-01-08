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
import com.netscape.utrain.activities.CreateEventActivity;
import com.netscape.utrain.model.C_EventDataListModel;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.model.O_EventDataModel;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AllEventsOrgListAdapter extends RecyclerView.Adapter<AllEventsOrgListAdapter.AllOrgEventsListHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private Context context;
    private List<O_EventDataModel> list;
    private CommonMethods commonMethods = new CommonMethods();
    private Date date = null;

    public AllEventsOrgListAdapter(Context context, List<O_EventDataModel> list) {
        this.context = context;
        this.list = list;
    }

    public AllEventsOrgListAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }


    @NonNull
    @Override
    public AllOrgEventsListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_view, parent, false);
//        return new AllOrgEventsListHolder(view);
        AllOrgEventsListHolder viewHolder = null;
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
    private AllOrgEventsListHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        AllOrgEventsListHolder viewHolder;
        View v1 = inflater.inflate(R.layout.booking_view, parent, false);
        viewHolder = new AllOrgEventsListHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllOrgEventsListHolder holder, int position) {

        O_EventDataModel data = list.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                if (data != null)
                    holder.bookingEventName.setText(data.getName());
                holder.bookingEventDate.setText(data.getStart_date());
                holder.bookingVenueTv.setText(data.getLocation());
                holder.bookingTicketTv.setText(data.getGuest_allowed() + " Attandees and Ticket(1 person per ticket)");
                holder.statusImage.setVisibility(View.GONE);
                if (data.getStart_date() != null && !data.getStart_date().isEmpty()) {
                    date = commonMethods.formatDate(data.getStart_date(), "yyyy-MM-dd");
                }

                if (data.isBooked()) {
                    holder.editImage.setVisibility(View.GONE);
                } else if (System.currentTimeMillis() > date.getTime()) {
                    holder.editImage.setVisibility(View.GONE);
                } else {
                    holder.editImage.setVisibility(View.VISIBLE);

                }

                try {
                    if (data.getImages() != null) {
                        JSONArray jsonArray = new JSONArray(data.getImages());
                        if (jsonArray != null && jsonArray.length() > 0) {
                            Glide.with(context).load(Constants.IMAGE_BASE_EVENT + jsonArray.get(0)).thumbnail(Glide.with(context).
                                    load(Constants.IMAGE_BASE_EVENT + Constants.THUMBNAILS + jsonArray.get(0))).into(holder.bookingEventImage);
                        }
                    }

                } catch (JSONException e) {

                    Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                }
                holder.editImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent eventEdit = new Intent(context, CreateEventActivity.class);
                        eventEdit.putExtra("eventEdit", data);
                        CreateEventActivity.editEvent = true;
                        context.startActivity(eventEdit);
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


    public void add(O_EventDataModel r) {
        list.add(r);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll(List<O_EventDataModel> moveResults) {
        for (O_EventDataModel result : moveResults) {
            add(result);
        }
    }

    public void setList(List<O_EventDataModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void remove(O_EventDataModel r) {
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
        O_EventDataModel result = getItem(position);

        if (result != null) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public O_EventDataModel getItem(int position) {
        return list.get(position);
    }


    public class AllOrgEventsListHolder extends RecyclerView.ViewHolder {

        MaterialTextView bookingEventName, bookingEventDate, bookingVenueTv, bookingTicketTv;
        AppCompatImageView bookingEventImage, statusImage, editImage;       // Using the booking.view layout which is same for completed....

        public AllOrgEventsListHolder(@NonNull View itemView) {
            super(itemView);

            bookingEventName = itemView.findViewById(R.id.bookingEventName);
            bookingEventDate = itemView.findViewById(R.id.bookingEventDate);
            bookingVenueTv = itemView.findViewById(R.id.bookingVenueTv);
            bookingTicketTv = itemView.findViewById(R.id.bookingTicketTv);
            bookingEventImage = itemView.findViewById(R.id.bookingEventImage);
            statusImage = itemView.findViewById(R.id.statusImage);
            editImage = itemView.findViewById(R.id.editImage);

        }
    }

    protected class LoadingVH extends AllOrgEventsListHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
