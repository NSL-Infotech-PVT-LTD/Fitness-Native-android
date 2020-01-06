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
import com.netscape.utrain.response.SlotListResponse;
import com.netscape.utrain.utils.CommonMethods;

import java.util.List;

import static com.netscape.utrain.R.color.colorGreen;


public class HourlySlotListAdapter extends RecyclerView.Adapter<HourlySlotListAdapter.ViewCoachStaffListHolder> {

    Context context;
    List<SlotListResponse.DataBean> list;
    private CommonMethods commonMethods = new CommonMethods();

    public HourlySlotListAdapter(Context context, List<SlotListResponse.DataBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewCoachStaffListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slots_hourly_calander_view, parent, false);
        ViewCoachStaffListHolder holder=new ViewCoachStaffListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewCoachStaffListHolder holder, int position) {
        String hour = commonMethods.convertDate((position + 1));

        if ((Integer.parseInt(hour)) >= 12 && (Integer.parseInt(hour) <= 23)) {
            holder.timeSlotTv.setText(hour + " Pm");
        } else {
            holder.timeSlotTv.setText(hour + " Am");
        }


//        if (list != null && list.size() > 0) {
//            SlotListResponse.DataBean data = list.get(position);
////        Glide.with(context).load(Constants.ORG_COACH_IMAGE_BASE_URL + data.getProfile_image()).thumbnail(Glide.with(context).load(Constants.COACH_IMAGE_BASE_URL + Constants.THUMBNAILS + data.getProfile_image())).into(holder.coachStaffImg);
//            String slotStart = data.getAvailable_slot().get(position).toString();
//            String slotEnd = data.getAvailable_slot().get(position).toString();
//            String slotHour = commonMethods.getSplitedValue(slotStart, ":");
//
//        for (int i=0;i<data.getAvailable_slot().size();i++){
//                String slot=data.getAvailable_slot().get(0).get(i);
//            if ((Integer.parseInt(hour)==(Integer.parseInt(slot)))){
//                holder.layoutSlotIndicator.setBackgroundColor(context.getResources().getColor(colorGreen));
//            }
//
//        }
//        holder.coachStaffProfession.setText(data.getProfession());
//        holder.coachStaffAdpLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        }
    }

    @Override
    public int getItemCount() {
        return 24;
    }

    public class ViewCoachStaffListHolder extends RecyclerView.ViewHolder {

        AppCompatTextView timeSlotTv, slotBookedTv;
        ConstraintLayout layoutSlotIndicator;

        public ViewCoachStaffListHolder(@NonNull View itemView) {
            super(itemView);

            timeSlotTv = itemView.findViewById(R.id.timeSlotTv);
            slotBookedTv = itemView.findViewById(R.id.slotBookedTv);
            layoutSlotIndicator = itemView.findViewById(R.id.layoutSlotIndicator);
        }
    }
}
