package com.netscape.utrain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.model.AthleteSignUpModel;
import com.netscape.utrain.model.AthleteUserModel;
import com.netscape.utrain.model.SportListModel;
import com.netscape.utrain.model.SportsIdModel;
import com.netscape.utrain.response.AthleteSignUpResponse;

import java.util.List;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.SportsViewHolder> {

    List<SportListModel.DataBeanX.DataBean> list;
    Context context;
    RecyclePosition recyclePosition;

    public SportsAdapter (List<SportListModel.DataBeanX.DataBean> list, Context context, RecyclePosition recyclePosition){

        this.list = list;
        this.context = context;
        this.recyclePosition = recyclePosition;
    }

    @NonNull
    @Override
    public SportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_design_sports, parent,false);
        return new SportsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SportsViewHolder holder,final int position) {

        final SportListModel.DataBeanX.DataBean data = list.get(position);
        holder.sportsName.setText(data.getName());
        holder.sportsNameCBox.setChecked(data.isCheckekd());
        holder.sportsNameCBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    recyclePosition.sportPosition(position,b,data);


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SportsViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView sportsName;
        MaterialCheckBox sportsNameCBox;

        public SportsViewHolder(@NonNull View itemView) {
            super(itemView);

            sportsName = itemView.findViewById(R.id.recycler_sportsNameTv);
            sportsNameCBox = itemView.findViewById(R.id.sportsNameCBox);
        }
    }

    public interface RecyclePosition{

        void sportPosition(int position, boolean isChecked, SportListModel.DataBeanX.DataBean list);

    }
}
