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
import com.netscape.utrain.model.ServicePriceModel;

import java.util.ArrayList;
import java.util.List;

public class ServicePriceAdapter extends RecyclerView.Adapter<ServicePriceAdapter.getRecyclerView> {

    private Context context;
    List<ServicePriceModel> list;


    public ServicePriceAdapter(Context context, List<ServicePriceModel> list)
    {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public getRecyclerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_design_service, parent,false);
        return new getRecyclerView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull getRecyclerView holder, int position) {

        ServicePriceModel data = list.get(position);
        holder.serviceName.setText(data.getServiceName());

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
