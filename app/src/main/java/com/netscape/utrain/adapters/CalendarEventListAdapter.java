package com.netscape.utrain.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.utils.CalendarView;
import com.netscape.utrain.utils.Constants;

import java.util.List;

public class CalendarEventListAdapter extends RecyclerView.Adapter<CalendarEventListAdapter.CustomTopCoachesHolder> {

private Context context;
private int previusPos=-1;
private List<CalendarView.CalendarObject> supplierData;
public CalendarEventListAdapter(Context context, List supplierData){
        this.context = context;
        this.supplierData = supplierData;

        }
@NonNull
@Override
public CustomTopCoachesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calander_item_view, parent, false);
        return new CustomTopCoachesHolder(view);
        }
@Override
public void onBindViewHolder(@NonNull final CustomTopCoachesHolder holder, int position) {
final CalendarView.CalendarObject data=supplierData.get(position);
        holder.name.setText(data.getmTitle());
        holder.type.setText(data.getMtype());

        }

@Override
public int getItemCount() {
        return supplierData.size();
        }

public class CustomTopCoachesHolder extends RecyclerView.ViewHolder {

//    AppCompatImageView imageView;
    TextView name,type;



    public CustomTopCoachesHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.eventName);
        type = itemView.findViewById(R.id.type);

    }
}

}
