package com.netscape.utrain.adapters;

import android.app.Service;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
    private ServicePriceInterface servicePriceInterface;


    public ServicePriceAdapter(Context context, List<ServiceListDataModel> list,ServicePriceInterface priceInterface) {
        this.context = context;
        this.list = list;
        this.servicePriceInterface=priceInterface;

    }

    @NonNull
    @Override
    public getRecyclerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_design_service, parent, false);
        getRecyclerView recyclerView = new getRecyclerView(view);
        return recyclerView;
    }

    @Override
    public void onBindViewHolder(@NonNull getRecyclerView holder, final int position) {

        ServiceListDataModel data = list.get(position);
        holder.serviceName.setText(data.getName());
        holder.priceEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            servicePriceInterface.getServicePrice(position,charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @Override
    public int getItemCount() {

        return list.size();
    }


    public class getRecyclerView extends RecyclerView.ViewHolder {

        MaterialTextView serviceName;
        EditText priceEdt;


        public getRecyclerView(@NonNull View itemView) {
            super(itemView);

            serviceName = (MaterialTextView) itemView.findViewById(R.id.recycler_serviceNameTv);
            priceEdt=(EditText)itemView.findViewById(R.id.servicePriceEdt);
        }
    }
    public interface ServicePriceInterface{
        void getServicePrice(int position,String servicePrice);
    }
}
