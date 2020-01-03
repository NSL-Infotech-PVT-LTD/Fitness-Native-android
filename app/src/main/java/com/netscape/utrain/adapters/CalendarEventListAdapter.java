package com.netscape.utrain.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.model.O_AllBookingDataListModel;
import com.netscape.utrain.model.O_AllBookingDataModel;
import com.netscape.utrain.utils.CalendarView;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class CalendarEventListAdapter extends RecyclerView.Adapter<CalendarEventListAdapter.CustomTopCoachesHolder> {

private Context context;
private int previusPos=-1;
private JSONArray jsonArray;
    private List<O_AllBookingDataListModel> supplierData;
public CalendarEventListAdapter(Context context, List supplierData){
        this.context = context;
        this.supplierData = supplierData;

        }
@NonNull
@Override
public CustomTopCoachesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewdate) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calander_item_view, parent, false);
        return new CustomTopCoachesHolder(view);
        }
@Override
public void onBindViewHolder(@NonNull final CustomTopCoachesHolder holder, int position) {
final O_AllBookingDataListModel data=supplierData.get(position);
        holder.name.setText(data.getTarget_data().getName());
        holder.location.setText(data.getTarget_data().getLocation());
        holder.date.setText(data.getBooking_date().getStart()+" To "+data.getBooking_date().getEnd());
        holder.mySpaceBookings.setVisibility(View.GONE);

    try {
        if (data.getTarget_data().getImages() != null) {
            jsonArray = new JSONArray(data.getTarget_data().getImages());

        }

    } catch (JSONException e) {

        Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

    }
        if (data.getType().equalsIgnoreCase("event")){
            holder.view.setBackgroundColor(context.getResources().getColor(R.color.eventColor));
            if (jsonArray !=null && jsonArray.length()>0){
                try {
                    Glide.with(context).load(Constants.IMAGE_BASE_EVENT + jsonArray.get(0)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_EVENT +Constants.THUMBNAILS+ jsonArray.get(0))).into(holder.imageView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    if (data.getType().equalsIgnoreCase("session")){
        holder.view.setBackgroundColor(context.getResources().getColor(R.color.sessionColor));
        if (jsonArray !=null && jsonArray.length()>0){
            try {
                Glide.with(context).load(Constants.IMAGE_BASE_SESSION + jsonArray.get(0)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_EVENT +Constants.THUMBNAILS+ jsonArray.get(0))).into(holder.imageView);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    if (data.getType().equalsIgnoreCase("space")){
        holder.view.setBackgroundColor(context.getResources().getColor(R.color.spaceColor));
        if (! data.isIs_booking_my()){
            holder.mySpaceBookings.setVisibility(View.VISIBLE);
        }
        if (jsonArray !=null && jsonArray.length()>0){
            try {
                Glide.with(context).load(Constants.IMAGE_BASE_PLACE + jsonArray.get(0)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_EVENT +Constants.THUMBNAILS+ jsonArray.get(0))).into(holder.imageView);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


        }

@Override
public int getItemCount() {
        return supplierData.size();
        }

public class CustomTopCoachesHolder extends RecyclerView.ViewHolder {

    AppCompatImageView imageView;
    View view;
    TextView location,date,name,mySpaceBookings;



    public CustomTopCoachesHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.calImageView);
        location = itemView.findViewById(R.id.calLocationTV);
        date = itemView.findViewById(R.id.calDateTv);
        name = itemView.findViewById(R.id.calNameTv);
        view = itemView.findViewById(R.id.typeView);
        mySpaceBookings = itemView.findViewById(R.id.mySpaceBookings);

    }
}

}
