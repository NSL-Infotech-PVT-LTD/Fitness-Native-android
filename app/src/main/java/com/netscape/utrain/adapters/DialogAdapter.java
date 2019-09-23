package com.netscape.utrain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.ServicePriceActivity;
import com.netscape.utrain.model.ServiceListDataModel;
import com.netscape.utrain.model.ServicePriceModel;
import com.netscape.utrain.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.CustomRecycleView> {

    Context context;
    ArrayList<ServiceListDataModel> list;
    int defaultPosition = -1;
    private SelectedServicesInterface selectedServices;

    public DialogAdapter(Context context, ArrayList<ServiceListDataModel> list,SelectedServicesInterface services ){

        this.context = context;
        this.list = list;
        this.selectedServices=services;
    }

    @NonNull
    @Override
    public CustomRecycleView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_price_list_design, parent, false);
        return new CustomRecycleView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomRecycleView holder, final int position) {

        final ServiceListDataModel data = list.get(position);
        if (data.isSelected()){
            holder.serviceName.setText(data.getName());
            holder.serviceName.setTextColor(context.getResources().getColor(R.color.colorBlack));
            holder.cbSelect.setChecked(true);
        }else{
            holder.serviceName.setText(data.getName());
            holder.cbSelect.setChecked(false);
        }


        holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Constants.CHECKBOX_IS_CHECKED=+1;
                    holder.serviceName.setTextColor(context.getResources().getColor(R.color.colorBlack));
                }else {
                    holder.serviceName.setTextColor(context.getResources().getColor(R.color.lightGrayFont));
                    Constants.CHECKBOX_IS_CHECKED=+1;
                }
                selectedServices.position(position,isChecked,data);
//                Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
            }
        });
//        adapterPos.position(position);


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

    public interface SelectedServicesInterface {
        void position(int pos,boolean ischecked,ServiceListDataModel serviceListDataModel);
    }
}
