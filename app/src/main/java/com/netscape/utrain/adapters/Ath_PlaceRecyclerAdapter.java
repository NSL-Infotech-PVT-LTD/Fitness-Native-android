package com.netscape.utrain.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.EventDetail;
import com.netscape.utrain.model.AthleteEventListModel;
import com.netscape.utrain.model.AthletePlaceModel;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ath_PlaceRecyclerAdapter extends RecyclerView.Adapter<Ath_PlaceRecyclerAdapter.ViewHolder> {
    AthleteEventData eventData;
    private Context context;
    private int previusPos = -1;
    private List<AthletePlaceModel> supplierData;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;



    public Ath_PlaceRecyclerAdapter(Context context, List<AthletePlaceModel> supplierData) {
        this.context = context;
        this.supplierData = supplierData;
        this.eventData = eventData;
    }
    public Ath_PlaceRecyclerAdapter(Context context) {
        this.context = context;
        supplierData = new ArrayList<>();
    }

    @NonNull
    @Override
    public Ath_PlaceRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_place_old_layout, parent, false);
//        return new ViewHolder(view);
        Ath_PlaceRecyclerAdapter.ViewHolder viewHolder = null;
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
    private Ath_PlaceRecyclerAdapter.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        Ath_PlaceRecyclerAdapter.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.find_place_old_layout, parent, false);
        viewHolder = new ViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Ath_PlaceRecyclerAdapter.ViewHolder holder, final int position) {
        AthletePlaceModel data = supplierData.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                if(data!=null)
        holder.eventName.setText(data.getName());
        holder.placenameTv.setText(data.getLocation());
        holder.findPlaceDistanceDetailTv.setText(data.getDistance() + " miles");
        holder.findPlaceActualPriceTv.setText("$" + data.getPrice_daily() + "/day");

        try {
            JSONArray jsonArray = new JSONArray(data.getImages());
            if (jsonArray != null && jsonArray.length() > 0) {
                Glide.with(context).load(Constants.IMAGE_BASE_PLACE + jsonArray.get(0)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_PLACE + Constants.THUMBNAILS + jsonArray.get(0))).into(holder.eventProfileImg);
            }

        } catch (JSONException e) {

            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventDetail.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("eventName", data.getName());
                intent.putExtra("eventVenue", data.getLocation());
                intent.putExtra("eventTime", data.getOpen_hours_from());
                intent.putExtra("eventALLImages", data.getImages());
                intent.putExtra("eventDate", data.getAvailability_week());
                intent.putExtra("image_url", Constants.IMAGE_BASE_PLACE);
                intent.putExtra("event_id", data.getId() + "");
                intent.putExtra("from", "places");
                intent.putExtra("desc", data.getDescription());
                intent.putExtra("gmapLat", data.getLatitude()+"");
                intent.putExtra("gmapLong", data.getLongitude()+"");
                Bundle b = new Bundle();
                b.putString("Array", data.getImages());
                intent.putExtras(b);
                intent.putExtra(Constants.SPACE_DATA, data);
                context.startActivity(intent);
            }
        });
            case LOADING:
//                Do nothing
                break;
        }

    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd MMMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public int getItemCount() {
        return supplierData == null ? 0 : supplierData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == supplierData.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }



    public void add(AthletePlaceModel r) {
        supplierData.add(r);
        notifyItemInserted(supplierData.size() - 1);
    }

    public void addAll(List<AthletePlaceModel> moveResults) {
        for (AthletePlaceModel result : moveResults) {
            add(result);
        }
    }

    public void setList(List<AthletePlaceModel> list) {
        this.supplierData = list;
        notifyDataSetChanged();
    }

    public void remove(AthletePlaceModel r) {
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
        AthletePlaceModel result = getItem(position);

        if (result != null) {
            supplierData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public AthletePlaceModel getItem(int position) {
        return supplierData.get(position);


    }



    public interface AthleteEventData {
        public void getData(Intent intent);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView eventName, placenameTv, findPlaceDistanceDetailTv, findPlaceActualPriceTv;
        private AppCompatImageView eventProfileImg;
        private MaterialButton viewPlacesBtn;

//        public RatingBar ratingBar;
//        public ImageView selectedImage;
//        public ConstraintLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.placeNameInfoTv);
            findPlaceDistanceDetailTv = itemView.findViewById(R.id.findPlaceDistanceDetailTv);
            eventProfileImg = itemView.findViewById(R.id.findPlaceImage);
            viewPlacesBtn = itemView.findViewById(R.id.viewPlacesBtn);
            placenameTv = itemView.findViewById(R.id.placenameTv);
            findPlaceActualPriceTv = itemView.findViewById(R.id.findPlaceActualPriceTv);

//
//            container = itemView.findViewById(R.id.container);
//            ratingBar = itemView.findViewById(R.id.supplierRating);
        }

    }
    protected class LoadingVH extends Ath_PlaceRecyclerAdapter.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

}
