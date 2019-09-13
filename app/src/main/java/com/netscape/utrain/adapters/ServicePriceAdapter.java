package com.netscape.utrain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;

import java.util.List;

public class ServicePriceAdapter extends RecyclerView.Adapter<ServicePriceAdapter.getRecyclerView> {

    private Context context;


    public ServicePriceAdapter(Context context)
    {
        this.context = context;

    }

    @NonNull
    @Override
    public getRecyclerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_price_list_design, parent,false);

        return new getRecyclerView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull getRecyclerView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class getRecyclerView extends RecyclerView.ViewHolder {

        MaterialTextView serviceName;
        TextInputEditText servicePrice;

        public getRecyclerView(@NonNull View itemView) {
            super(itemView);

            serviceName = (MaterialTextView) itemView.findViewById(R.id.service_serviceNameTv);
            servicePrice = (TextInputEditText) itemView.findViewById(R.id.service_servicePriceEdt);

        }
    }
}
