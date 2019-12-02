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
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.model.O_AllBookingDataListModel;
import com.netscape.utrain.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.CustomTopCoachesHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private Context context;
    private int previusPos=-1;
    private JSONArray jsonArray;
    private List<O_AllBookingDataListModel> supplierData;

    public TransactionAdapter(Context context, List supplierData){
        this.context = context;
        this.supplierData = supplierData;

        }

    public TransactionAdapter(Context context){
        this.context = context;
        supplierData = new ArrayList<>();

    }

@NonNull
@Override
public CustomTopCoachesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewdate) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_receive_design, parent, false);
//        return new CustomTopCoachesHolder(view);

    CustomTopCoachesHolder viewHolder = null;
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());

    switch (viewdate) {
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
    private CustomTopCoachesHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        CustomTopCoachesHolder viewHolder;
        View v1 = inflater.inflate(R.layout.payment_receive_design, parent, false);
        viewHolder = new CustomTopCoachesHolder(v1);
        return viewHolder;
    }


    @Override
public void onBindViewHolder(@NonNull final CustomTopCoachesHolder holder, int position) {
final O_AllBookingDataListModel data=supplierData.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                if(data!=null)
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


    public void add(O_AllBookingDataListModel r) {
        supplierData.add(r);
        notifyItemInserted(supplierData.size() - 1);
    }

    public void addAll(List<O_AllBookingDataListModel> moveResults) {
        for (O_AllBookingDataListModel result : moveResults) {
            add(result);
        }
    }

    public void setList(List<O_AllBookingDataListModel> list) {
        this.supplierData = list;
        notifyDataSetChanged();
    }

    public void remove(O_AllBookingDataListModel r) {
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
        O_AllBookingDataListModel result = getItem(position);

        if (result != null) {
            supplierData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public O_AllBookingDataListModel getItem(int position) {
        return supplierData.get(position);
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
    protected class LoadingVH extends CustomTopCoachesHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
