package com.netscape.utrain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.netscape.utrain.R;
import com.netscape.utrain.model.O_AllBookingDataListModel;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.CustomTopCoachesHolder> {

private Context context;
private int previusPos=-1;
private JSONArray jsonArray;
    private List<O_AllBookingDataListModel> supplierData;
public TransactionAdapter(Context context, List supplierData){
        this.context = context;
        this.supplierData = supplierData;

        }
@NonNull
@Override
public CustomTopCoachesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewdate) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_receive_design, parent, false);
        return new CustomTopCoachesHolder(view);
        }
@Override
public void onBindViewHolder(@NonNull final CustomTopCoachesHolder holder, int position) {
final O_AllBookingDataListModel data=supplierData.get(position);
        holder.name.setText(data.getTarget_data().getName());
        holder.transactionId.setText("ID: "+data.getPayment_id());
        holder.date.setText(data.getTarget_data().getStart_date()+" | "+data.getTarget_data().getStart_time());
        holder.price.setText("$"+data.getPrice());
    try {
        if (data.getTarget_data().getImages() != null) {
            jsonArray = new JSONArray(data.getTarget_data().getImages());

        }

    } catch (JSONException e) {

        Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

    }
        if (data.getType().equalsIgnoreCase("event")){
            if (jsonArray !=null && jsonArray.length()>0){
                try {
                    Glide.with(context).load(Constants.IMAGE_BASE_EVENT + jsonArray.get(0)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_EVENT +Constants.THUMBNAILS+ jsonArray.get(0))).into(holder.imageView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    if (data.getType().equalsIgnoreCase("session")){
        if (jsonArray !=null && jsonArray.length()>0){
            try {
                Glide.with(context).load(Constants.IMAGE_BASE_SESSION + jsonArray.get(0)).thumbnail(Glide.with(context).load(Constants.IMAGE_BASE_EVENT +Constants.THUMBNAILS+ jsonArray.get(0))).into(holder.imageView);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    if (data.getType().equalsIgnoreCase("space")){
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

    CircleImageView imageView;

    TextView transactionId,date,name,price;



    public CustomTopCoachesHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.coachStaffImg);
        transactionId = itemView.findViewById(R.id.transactionId);
        date = itemView.findViewById(R.id.coachStaffProfession);
        name = itemView.findViewById(R.id.coachStaffName);
        price = itemView.findViewById(R.id.transactionPrice);

    }
}

}
