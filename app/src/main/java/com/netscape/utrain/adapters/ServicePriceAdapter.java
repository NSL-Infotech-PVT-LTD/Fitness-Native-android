package com.netscape.utrain.adapters;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.model.ServiceListDataModel;
import com.netscape.utrain.model.ServicePriceModel;

import java.util.ArrayList;
import java.util.List;

public class ServicePriceAdapter extends RecyclerView.Adapter<ServicePriceAdapter.getRecyclerView> {

    List<ServiceListDataModel> list;
    private Context context;


    public ServicePriceAdapter(Context context, List<ServiceListDataModel> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public getRecyclerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_design_service, parent, false);
        getRecyclerView recyclerView=new getRecyclerView(view);
        return recyclerView;
    }

    @Override
    public void onBindViewHolder(@NonNull getRecyclerView holder, int position) {

        ServiceListDataModel data = list.get(position);
        if (data.isSelected()) {
            holder.serviceName.setText(data.getName());
        }else {
            return;
        }

    }

    @Override
    public int getItemCount() {

        return list.size();
    }


    public class getRecyclerView extends RecyclerView.ViewHolder {

        MaterialTextView serviceName;


        public getRecyclerView(@NonNull View itemView) {
            super(itemView);

            serviceName = (MaterialTextView) itemView.findViewById(R.id.recycler_serviceNameTv);

        }
    }
}
