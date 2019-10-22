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
import com.netscape.utrain.model.O_BookedEventDataModel;
import com.netscape.utrain.model.O_BookedSessionDataModel;
import com.netscape.utrain.model.O_EventDataModel;
import com.netscape.utrain.model.O_SpaceListDataModel;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class O_BookedEventListAdapter extends RecyclerView.Adapter<O_BookedEventListAdapter.CustomTopCoachesHolder> {

    private Context context;
    private int previusPos = -1;
    private List<O_BookedEventDataModel> supplierData;
    private List<O_BookedSessionDataModel> sessionData;
    private List<O_SpaceListDataModel> spaceData;
    private int type;
    private onClick onClick;



    public O_BookedEventListAdapter(Context context, List supplierData ,int type,onClick onClick ) {
        this.context = context;
        this.type=type;
        this.onClick=onClick;
        if (type==1){
            this.supplierData = supplierData;
        }
        if (type==2){
            this.spaceData = supplierData;
        }
        if (type==3){
            this.sessionData = supplierData;
        }


    }


    @NonNull
    @Override
    public O_BookedEventListAdapter.CustomTopCoachesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_total_booked_user, parent, false);
        return new O_BookedEventListAdapter.CustomTopCoachesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull O_BookedEventListAdapter.CustomTopCoachesHolder holder, int position) {

        if (type==1){
             O_BookedEventDataModel data = supplierData.get(position);
            holder.userName.setText(data.getUser_details().getName());
            Glide.with(context).load(Constants.IMAGE_BASE_URL+data.getUser_details().getProfile_image()).into(holder.circleImageView);

        }
        if (type==2){
             O_SpaceListDataModel data = spaceData.get(position);
            holder.userName.setText(data.getUser_details().getName());
            Glide.with(context).load(Constants.IMAGE_BASE_URL+data.getUser_details().getProfile_image()).into(holder.circleImageView);

        }
        if (type==3){
             O_BookedSessionDataModel data = sessionData.get(position);
            holder.userName.setText(data.getUser_details().getName());
            Glide.with(context).load(Constants.IMAGE_BASE_URL+data.getUser_details().getProfile_image()).into(holder.circleImageView);
        }

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
        if (type==1){
            return supplierData.size();

        }
        if (type==2){
            return spaceData.size();

        }
        if (type==3){
            return sessionData.size();

        }
        return 0;
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
