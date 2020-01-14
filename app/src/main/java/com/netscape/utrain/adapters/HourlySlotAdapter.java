package com.netscape.utrain.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.netscape.utrain.R;
import com.netscape.utrain.activities.SpaceBookingActivity;
import com.netscape.utrain.model.HourSelectedModel;
import com.netscape.utrain.model.SlotModels;
import com.netscape.utrain.response.SlotListResponse;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;

import java.util.List;

public class HourlySlotAdapter extends RecyclerView.Adapter<HourlySlotAdapter.ViewHolder> {
    Context context;
    List<HourSelectedModel> list;
    private CommonMethods commonMethods = new CommonMethods();
    private String slotStartTime="";
    private String slotEndTime="";
    private String slotsDate="",spaceId="";

    public HourlySlotAdapter(Context context, List<HourSelectedModel> list,String date,String Id) {
        this.context = context;
        this.list = list;
        this.slotsDate=date;
        this.spaceId=Id;
    }

    @NonNull
    @Override
    public HourlySlotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slots_hourly_calander_view, parent, false);
        HourlySlotAdapter.ViewHolder holder=new HourlySlotAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HourlySlotAdapter.ViewHolder holder, int position) {
        HourSelectedModel data=list.get(position);
        holder.timeSlotTv.setText(data.getHour()+":00");
        if (data.isSelected()){
            holder.layoutSlotIndicator.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
            holder.slotBookedTv.setText("Available");

        }else {
            holder.layoutSlotIndicator.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            holder.slotBookedTv.setText("Not Available");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(position).isSelected()) {
                    int toTime=Integer.parseInt(list.get(position).getHour())+1;
                    Intent intent = new Intent(context.getApplicationContext(), SpaceBookingActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Constants.SLOT_DATE, slotsDate);
                    intent.putExtra("event_id",spaceId);
                    intent.putExtra(Constants.SLOT_START_TIME, list.get(position).getHour()+":00");
                    intent.putExtra(Constants.SLOT_END_TIME, commonMethods.convertDate(toTime)+":00");
                    SpaceBookingActivity.intentFrom=true;
                    context.startActivity(intent);
                }else {
                    Toast.makeText(context, "Slot not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView timeSlotTv, slotBookedTv;
        ConstraintLayout layoutSlotIndicator;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeSlotTv = itemView.findViewById(R.id.timeSlotTv);
            slotBookedTv = itemView.findViewById(R.id.slotBookedTv);
            layoutSlotIndicator = itemView.findViewById(R.id.layoutSlotIndicator);
        }
    }
}
