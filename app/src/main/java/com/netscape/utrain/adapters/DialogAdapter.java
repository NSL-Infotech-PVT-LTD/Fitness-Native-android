package com.netscape.utrain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.model.ServicePriceModel;

import java.util.List;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.CustomRecycleView> {

    Context context;
    List<ServicePriceModel> list;
    int defaultPosition = -1;
    AdapterPos adapterPos;

    public DialogAdapter(Context context, List<ServicePriceModel> list, AdapterPos adapterPos){

        this.context = context;
        this.list = list;
        this.adapterPos = adapterPos;
    }

    @NonNull
    @Override
    public CustomRecycleView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_price_list_design, parent, false);

        return new CustomRecycleView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecycleView holder, int position) {

        final ServicePriceModel data = list.get(position);
        holder.serviceName.setText(data.getServiceName());
        holder.cbSelect.setOnCheckedChangeListener(null);
        holder.cbSelect.setChecked(data.isSelected());
        holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                data.setSelected(isChecked);
            }
        });
        adapterPos.position(position);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class CustomRecycleView extends RecyclerView.ViewHolder {

        MaterialTextView serviceName;
        AppCompatCheckBox cbSelect;

        public CustomRecycleView(@NonNull View itemView) {
            super(itemView);

            serviceName = itemView.findViewById(R.id.service_serviceNameTv);
            cbSelect = itemView.findViewById(R.id.service_checkBox);
        }
    }

    public interface AdapterPos {
        void position(int pos);
    }
}
