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
import com.netscape.utrain.activities.CreateTrainingSession;
import com.netscape.utrain.model.C_SessionListModel;
import com.netscape.utrain.model.O_SessionDataModel;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class AllSessionOrgListAdapter extends RecyclerView.Adapter<AllSessionOrgListAdapter.AllSessionsOrgHolder> {

    private Context context;
    private List<O_SessionDataModel> list;

    public AllSessionOrgListAdapter(Context context, List<O_SessionDataModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public AllSessionsOrgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_view, parent, false);
        return new AllSessionsOrgHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllSessionsOrgHolder holder, int position) {

        O_SessionDataModel data = list.get(position);

        holder.bookingEventName.setText(data.getName());
        holder.bookingEventDate.setText(data.getStart_date() + " " + data.getStart_time());
        holder.bookingVenueTv.setText(data.getLocation());
        holder.bookingTicketTv.setText(data.getGuest_allowed() + " Attandees and Ticket(1 person per ticket)");
        holder.statusImage.setVisibility(View.GONE);
        holder.editImage.setVisibility(View.VISIBLE);

        try {
            if (data.getImages() != null) {
                JSONArray jsonArray = new JSONArray(data.getImages());
                if (jsonArray != null && jsonArray.length() > 0) {
                    Glide.with(context).load(Constants.IMAGE_BASE_SESSION + jsonArray.get(0)).thumbnail(Glide.with(context).
                            load(Constants.IMAGE_BASE_SESSION + Constants.THUMBNAILS + jsonArray.get(0))).into(holder.bookingEventImage);
                }
            }

        } catch (JSONException e) {

            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        holder.editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sessionEdit=new Intent(context, CreateTrainingSession.class);
                sessionEdit.putExtra("sessionEdit",data);
                CreateTrainingSession.editSession=true;
                context.startActivity(sessionEdit);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AllSessionsOrgHolder extends RecyclerView.ViewHolder {

        MaterialTextView bookingEventName, bookingEventDate, bookingVenueTv, bookingTicketTv;
        AppCompatImageView bookingEventImage, statusImage,editImage;       // Using the booking.view layout which is same for completed....

        public AllSessionsOrgHolder(@NonNull View itemView) {
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
}
