package com.netscape.utrain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.netscape.utrain.R;
import com.netscape.utrain.model.HourSelectedModel;
import com.netscape.utrain.model.SlotModels;
import com.netscape.utrain.response.SlotListResponse;
import com.netscape.utrain.utils.CommonMethods;

import java.util.List;

public class HourlySlotAdapter extends RecyclerView.Adapter<HourlySlotAdapter.ViewHolder> {
    Context context;
    List<HourSelectedModel> list;
    private CommonMethods commonMethods = new CommonMethods();
    private String slotStartTime="";
    private String slotEndTime="";

    public HourlySlotAdapter(Context context, List<HourSelectedModel> list) {
        this.context = context;
        this.list = list;
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
        holder.timeSlotTv.setText(data.getHour());
        if (data.isSelected()){
            holder.layoutSlotIndicator.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
            holder.slotBookedTv.setText("Available");

        }else {
            holder.layoutSlotIndicator.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            holder.slotBookedTv.setText("Not Available");
        }


//        String hour = commonMethods.convertDate((position + 1));
//
//        if ((Integer.parseInt(hour)) >= 12 && (Integer.parseInt(hour) <= 23)) {
//            holder.timeSlotTv.setText(hour + " Pm");
//        } else {
//            holder.timeSlotTv.setText(hour + " Am");
//        }
//        if (list !=null && list.size()>0) {
//            for (int i = 0; i < list.size(); i++) {
//
//                String slotHour = commonMethods.getSplitedValue(list.get(i).getSlotStartTime().toString(),":");
//                if (Integer.parseInt(hour)==Integer.parseInt(slotHour)){
//                    holder.layoutSlotIndicator.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
//                    holder.slotBookedTv.setText("Available");
//                }
//
//
//            }
//        }
//        if (list != null && list.size() > 0) {
//            SlotListResponse.DataBean data = list.get(position);
//            String slotStart = data.getAvailable_slot().get(position).toString();
//            String slotEnd = data.getAvailable_slot().get(position).toString();
//        }
//
//            for (int i = 0; i < data.getAvailable_slot().size(); i++) {
//                String slot = data.getAvailable_slot().get(0).get(i);
//                String slotHour = commonMethods.getSplitedValue(slot, ":");
//                if ((Integer.parseInt(hour) == (Integer.parseInt(slotHour)))) {
//                    holder.layoutSlotIndicator.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
//                }
//
//            }
//        }
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
