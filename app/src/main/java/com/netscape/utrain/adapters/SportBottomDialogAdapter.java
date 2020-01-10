package com.netscape.utrain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.netscape.utrain.R;
import com.netscape.utrain.model.SportListModel;

import java.util.ArrayList;
import java.util.List;

public class SportBottomDialogAdapter extends RecyclerView.Adapter<SportBottomDialogAdapter.ViewHolder> {
    Context context;
    List<SportListModel.DataBeanX.DataBean> sportsList;
    private sportFilterInter sportFilterInter;

    public SportBottomDialogAdapter(Context context, List sport, sportFilterInter interSport) {
        this.context = context;
        this.sportsList = sport;
        this.sportFilterInter = interSport;
    }

    @NonNull
    @Override
    public SportBottomDialogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sports_bottom_recycler_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SportBottomDialogAdapter.ViewHolder holder, int position) {
        SportListModel.DataBeanX.DataBean data = sportsList.get(position);
        if (data.getName() != null) {
            holder.sportNameTv.setText(data.getName());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sportFilterInter.getSportName(sportsList.get(position).getName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return sportsList.size();
    }

    public interface sportFilterInter {
        void getSportName(String name);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView sportNameTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sportNameTv = itemView.findViewById(R.id.sportBottomRecyclerTv);
        }

    }
}
