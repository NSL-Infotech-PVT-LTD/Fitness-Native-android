package com.netscape.utrain.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.EventDetail;
import com.netscape.utrain.model.AthleteEventListModel;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CoachesRecyclerAdapter extends RecyclerView.Adapter<CoachesRecyclerAdapter.ViewHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    AthleteEventData eventData;
    String value = "", valueEnd = "";
    String currentStringEnd, currentStringStart;
    String[] separatedEnd = null, separated = null;
    private Context context;
    private int previusPos = -1;
    private List<AthleteEventListModel> supplierData;
    private boolean isLoadingAdded = false;


    public CoachesRecyclerAdapter(Context context, List<AthleteEventListModel> supplierData) {
        this.context = context;
        this.supplierData = supplierData;
        this.eventData = eventData;
    }

    @NonNull
    @Override
    public CoachesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        CoachesRecyclerAdapter.ViewHolder viewHolder = null;
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
    private CoachesRecyclerAdapter.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        CoachesRecyclerAdapter.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.find_event_session_design, parent, false);
        viewHolder = new ViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CoachesRecyclerAdapter.ViewHolder holder, final int position) {
        final AthleteEventListModel data = supplierData.get(position);
        holder.trainingSessionStrtDateEnterTv.setText(data.getStart_date());
        holder.trainingSessionEndDateEnterTv.setText(data.getEnd_date());
        holder.trainingSessionTimeEnterTv.setText(data.getStart_time() + " " + "To" + " " + data.getEnd_time());
        holder.trainingSessionProfessionDesc.setText(data.getName());
        holder.findPlaceDistanceDetailTv.setText(data.getDistance() + " Miles");

        holder.findPlaceActualPriceTv.setText("$" + data.getPrice());
        holder.trainingSessionVenueDetailTv.setText(data.getLocation());
        try {
            if (data.getImages() != null) {
                JSONArray jsonArray = new JSONArray(data.getImages());
                if (jsonArray !=null && jsonArray.length()>0){
                    Glide.with(context).load(Constants.IMAGE_BASE_EVENT + jsonArray.get(0)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_EVENT + Constants.THUMBNAILS + jsonArray.get(0))).into(holder.eventProfileImg);
                }

            }

        } catch (JSONException e) {

            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventDetail.class);
                intent.putExtra("eventName", data.getName());
                intent.putExtra("eventVenue", data.getLocation());
                intent.putExtra("event_id", data.getId());
                intent.putExtra("guest_allowed", data.getGuest_allowed() + "");
                intent.putExtra("guest_allowed_left", data.getGuest_allowed_left() + "");
                intent.putExtra("eventDate", data.getStart_date());
                intent.putExtra("eventTime", data.getStart_time());
                intent.putExtra("eventDescription", data.getDescription());
                intent.putExtra("image_url", Constants.IMAGE_BASE_EVENT);
                intent.putExtra("from", "events");
                intent.putExtra("capacity", data.getGuest_allowed());
                Bundle b = new Bundle();
                b.putString("Array", data.getImages());
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });
        switch (getItemViewType(position)) {
            case ITEM:


//                if (data.getEnd_at()!=null) {
//                    currentStringEnd  = data.getEnd_at();
//                    separatedEnd = currentStringEnd.split(" ");
//
//                }
//
//                if (data.getStart_at()!=null) {
//                currentStringStart  = data.getStart_at();
//
//                separated = currentStringStart.split(" ");
//
//                }
//                holder.eventName.setText(data.getName());
//                holder.findPlaceActualPriceTv.setText("$" + data.getPrice());
//                holder.trainingSessionVenueDetailTv.setText(data.getLocation());
////                holder.itemView.setBackground(context.getDrawable(R.drawable.card_shape_bottom_green));
////        holder.eventEndDateTimeEnterTv.setText(data.getEnd_at());
//                try {
//                    if (data.getImages() != null) {
//                        JSONArray jsonArray = new JSONArray(data.getImages());
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            Glide.with(context).load(Constants.IMAGE_BASE_EVENT + jsonArray.get(i)).into(holder.eventProfileImg);
//
//                        }
//                    }
//
//                } catch (JSONException e) {
//
//                    Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                }
//
//                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
//                final SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm aa");
//                Date dt = null, dtEnd;
//
//
//                try {
//                    if (data.getStart_at()!=null) {
//                        dt = sdf.parse(separated[1]);
//
//                        value = parseDateToddMMyyyy(separated[0]) + " " + sdfs.format(dt);
//                        holder.eventStartDateTimeEnterTv.setText(value);
//                    }
//                    if (data.getEnd_at()!=null) {
//                        dtEnd = sdf.parse(separatedEnd[1]);
//
//
//                        valueEnd = parseDateToddMMyyyy(separatedEnd[0]) + "  " + sdfs.format(dtEnd);
//                    }
////            holder.eventEndDateTimeEnterTv.setText(valueEnd);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
////                holder.viewPlacesBtn.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
//                final Date finalDt = dt;

//                break;
            case LOADING:
//                Do nothing
                break;
        }


    }

    @Override
    public int getItemCount() {
        return supplierData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == supplierData.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public List<AthleteEventListModel> getsupplierData() {
        return supplierData;
    }

    public void setsupplierData(List<AthleteEventListModel> supplierData) {
        this.supplierData = supplierData;
    }

    public void add(AthleteEventListModel mc) {
        supplierData.add(mc);
        notifyItemInserted(supplierData.size() - 1);
    }

    public void addAll(List<AthleteEventListModel> mcList) {
        for (AthleteEventListModel mc : mcList) {
            add(mc);
        }
    }

    public void remove(AthleteEventListModel city) {
        int position = supplierData.indexOf(city);
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
        add(new AthleteEventListModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = supplierData.size() - 1;
        AthleteEventListModel item = getItem(position);

        if (item != null) {
            supplierData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public AthleteEventListModel getItem(int position) {
        return supplierData.get(position);
    }

    public interface AthleteEventData {
        public void getData(Intent intent);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView trainingSessionStrtDateEnterTv, trainingSessionEndDateEnterTv, trainingSessionTimeEnterTv, trainingSessionProfessionDesc;
        private AppCompatTextView trainingSessionVenueDetailTv, findPlaceDistanceDetailTv, findPlaceActualPriceTv, eventStartDateTimeEnterTv;
        private ImageView eventProfileImg;
        private MaterialButton viewPlacesBtn;
        private ConstraintLayout constraint_background;
//        public RatingBar ratingBar;
//        public ImageView selectedImage;
//        public ConstraintLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            trainingSessionProfessionDesc = itemView.findViewById(R.id.trainingSessionProfessionDesc);
            findPlaceDistanceDetailTv = itemView.findViewById(R.id.findPlaceDistanceDetailTv);
            eventProfileImg = itemView.findViewById(R.id.trainingSessionPlaceImage);
            trainingSessionVenueDetailTv = itemView.findViewById(R.id.trainingSessionVenueDetailTv);
//            viewPlacesBtn = itemView.findViewById(R.id.viewPlacesBtn);
//            eventStartDateTimeEnterTv = itemView.findViewById(R.id.trainingSessionDateTimeEnterTv);
            trainingSessionStrtDateEnterTv = itemView.findViewById(R.id.trainingSessionStrtDateEnterTv);
            trainingSessionEndDateEnterTv = itemView.findViewById(R.id.trainingSessionEndDateEnterTv);
            trainingSessionTimeEnterTv = itemView.findViewById(R.id.trainingSessionTimeEnterTv);


            constraint_background = itemView.findViewById(R.id.constraint_background);
            findPlaceActualPriceTv = itemView.findViewById(R.id.findPlaceActualPriceTv);
//
//            container = itemView.findViewById(R.id.container);
//            ratingBar = itemView.findViewById(R.id.supplierRating);
        }

    }

    protected class LoadingVH extends CoachesRecyclerAdapter.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

}
