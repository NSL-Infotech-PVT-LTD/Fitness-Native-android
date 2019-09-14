package com.netscape.utrain.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.databinding.BindingMethod;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.adapters.DialogAdapter;
import com.netscape.utrain.adapters.ServicePriceAdapter;
import com.netscape.utrain.model.ServicePriceModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ServicePriceActivity extends AppCompatActivity {

    MaterialTextView addService;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ServicePriceAdapter serviceAdapter;

    List<ServicePriceModel> model = new ArrayList<>();
    List<ServicePriceModel> mList = new ArrayList<>();
    DialogAdapter dialogAdapter;
    AlertDialog alertDialog;
    MaterialButton btnDialogNext;
    int mPosition = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_price);

        btnDialogNext = findViewById(R.id.dialog_btnNext);
        addService = findViewById(R.id.service_AddServiceTv);

        recyclerView = findViewById(R.id.service_recyclerView);
        layoutManager = new LinearLayoutManager(ServicePriceActivity.this);

        mList = (List<ServicePriceModel>) getIntent().getSerializableExtra("name");
        if (model.get(mPosition).getServiceName().length()>0)
//        if (mList.get(mPosition).getServiceName().length()>0)
        serviceAdapter = new ServicePriceAdapter(ServicePriceActivity.this,model);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(serviceAdapter);



        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                RecyclerView mRecyclerView;
                AlertDialog.Builder builder = new AlertDialog.Builder(ServicePriceActivity.this);
                LayoutInflater inflater = LayoutInflater.from(ServicePriceActivity.this);

                final View content = inflater.inflate(R.layout.dialog_custom_design, null);
                builder.setView(content);
                mRecyclerView = (RecyclerView) content.findViewById(R.id.dialog_RecyclerView);
                btnDialogNext = (MaterialButton) content.findViewById(R.id.dialog_btnNext);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(ServicePriceActivity.this));
                mList.add(new ServicePriceModel("Athlete", "99"));
                mList.add(new ServicePriceModel("Coach", "88"));
                mList.add(new ServicePriceModel("Organization", "77"));

                dialogAdapter = new DialogAdapter(ServicePriceActivity.this, mList, new DialogAdapter.AdapterPos() {
                    @Override
                    public void position(int pos) {
                        mPosition = pos;

                    }
                });
                mRecyclerView.setAdapter(dialogAdapter);
                final AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                btnDialogNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (mList.get(mPosition).getServiceName().length()>0)
                        {
                            mList.get(mPosition).isSelected();

                            Intent intent = new Intent(ServicePriceActivity.this,ServicePriceActivity.class);
                            intent.putExtra("name", mList.get(mPosition).getServiceName());
                            startActivity(intent);

                        }

                    }
                });

            }

        });
    }
}
