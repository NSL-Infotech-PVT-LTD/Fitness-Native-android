package com.netscape.utrain.adapters_org;

import android.content.Context;
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
import com.netscape.utrain.model.BookedUserModel;
import com.netscape.utrain.model.O_EventDataModel;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class O_BookedEventListAdapter extends RecyclerView.Adapter<O_BookedEventListAdapter.CustomTopCoachesHolder> {

    private Context context;
    private int previusPos = -1;
    private List<BookedUserModel> supplierData;

    onClick onClick;

    public O_BookedEventListAdapter(Context context, List supplierData, onClick onClick) {
        this.context = context;
        this.supplierData = supplierData;
        this.onClick = onClick;

    }


    @NonNull
    @Override
    public O_BookedEventListAdapter.CustomTopCoachesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_total_booked_user, parent, false);
        return new O_BookedEventListAdapter.CustomTopCoachesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull O_BookedEventListAdapter.CustomTopCoachesHolder holder, int position) {
        final BookedUserModel data = supplierData.get(position);

        holder.userName.setText(data.getUserName());
        Glide.with(context).load(data.getUserImage()).into(holder.circleImageView);

        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onClick.onClick();
            }
        });


    }

    public interface onClick {
        public void onClick();
    }

    @Override
    public int getItemCount() {
        return supplierData.size();
    }

    public class CustomTopCoachesHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        MaterialTextView userName, viewDetails;


        public CustomTopCoachesHolder(@NonNull View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.bookedUserImg);
            userName = itemView.findViewById(R.id.userName);
            viewDetails = itemView.findViewById(R.id.viewDetails);

        }
    }


}
