package com.netscape.utrain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.model.NotificationDatamodel;
import com.netscape.utrain.model.SlotModel;
import com.netscape.utrain.model.ViewCoachListDataModel;
import com.netscape.utrain.utils.Constants;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddViewRecyclerAdapter extends RecyclerView.Adapter<AddViewRecyclerAdapter.ViewCoachStaffListHolder> {

    Context context;
    List<SlotModel> list;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    public AddViewRecyclerAdapter(Context context, List list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AddViewRecyclerAdapter.ViewCoachStaffListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.space_booking_add_view, parent, false);
        return new AddViewRecyclerAdapter.ViewCoachStaffListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddViewRecyclerAdapter.ViewCoachStaffListHolder holder, int position) {

        final SlotModel data = list.get(position);
//        Glide.with(context).load(Constants.ORG_COACH_IMAGE_BASE_URL + data.getProfile_image()).thumbnail(Glide.with(context).load(Constants.COACH_IMAGE_BASE_URL + Constants.THUMBNAILS + data.getProfile_image())).into(holder.coachStaffImg);
        holder.bookingSlotNum.setText("Booking Slot "+list.size());
        holder.slotType.setText(data.getSlotStartTime()+" To "+data.getSlotEndTime());
        holder.slotDate.setText(data.getSelectedDate());
//        holder.slotTime.setText(data.getSelectedTime());
//        holder.slotTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
        @Override
        public int getItemViewType(int position) {
            return (position == list.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
        }


        public void add(SlotModel r) {
            list.add(r);
            notifyItemInserted(list.size() - 1);
        }

        public void addAll(List<SlotModel> moveResults) {
            for (SlotModel result : moveResults) {
                add(result);
            }
        }

        public void setList(List<SlotModel> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        public void remove(SlotModel r) {
            int position = list.indexOf(r);
            if (position > -1) {
                list.remove(position);
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

            int position = list.size() - 1;
            SlotModel result = getItem(position);

            if (result != null) {
                list.remove(position);
                notifyItemRemoved(position);
            }
        }

        public SlotModel getItem(int position) {
            return list.get(position);
        }




    public class ViewCoachStaffListHolder extends RecyclerView.ViewHolder {


        AppCompatTextView bookingSlotNum,slotType,slotDate,slotTime;


        public ViewCoachStaffListHolder(@NonNull View itemView) {
            super(itemView);

            bookingSlotNum = itemView.findViewById(R.id.bookingSlotNum);
            slotType = itemView.findViewById(R.id.slotType);
            slotDate = itemView.findViewById(R.id.slotDate);
//            slotTime = itemView.findViewById(R.id.slotTime);
        }
    }
}
