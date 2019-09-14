package com.netscape.utrain.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.databinding.BindingMethod;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AthleteLoginActivity;
import com.netscape.utrain.adapters.DialogAdapter;
import com.netscape.utrain.adapters.ServicePriceAdapter;
import com.netscape.utrain.databinding.ActivityServicePriceBinding;
import com.netscape.utrain.model.ServiceListDataModel;
import com.netscape.utrain.model.ServicePriceModel;
import com.netscape.utrain.response.ServiceListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicePriceActivity extends AppCompatActivity implements View.OnClickListener, DialogAdapter.SelectedServicesInterface {
    MaterialTextView addService;
    RecyclerView.LayoutManager layoutManager;
    ServicePriceAdapter serviceAdapter;
    List<ServiceListDataModel> selectedService;
    List<ServiceListDataModel> mList;
    ServiceListDataModel serviceModel;
    DialogAdapter dialogAdapter;
    AlertDialog alertDialog;
    MaterialButton btnDialogNext;
    int mPosition = 0;
    private ActivityServicePriceBinding binding;
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_price);

        layoutManager = new LinearLayoutManager(this);
        progressDialog = new ProgressDialog(this);
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        init();
        hitServiceListApi();


    }

    private void setSelectedServiceList() {
        serviceAdapter = new ServicePriceAdapter(getApplicationContext(), mList);
        binding.serviceRecyclerView.setLayoutManager(layoutManager);
        binding.serviceRecyclerView.setAdapter(serviceAdapter);

    }

    private void init() {
        binding.addServiceBtn.setOnClickListener(this);
        binding.servicePriceNextBtn.setOnClickListener(this);
    }

    private void hitServiceListApi() {
        progressDialog.show();
        Call<ServiceListResponse> signUpAthlete = retrofitinterface.getServiceList(Constants.CONTENT_TYPE);
        signUpAthlete.enqueue(new Callback<ServiceListResponse>() {
            @Override
            public void onResponse(Call<ServiceListResponse> call, Response<ServiceListResponse> response) {
                if (response.isSuccessful()) {
                    mList = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            mList.addAll(response.body().getData());

                        }
                    } else {
                        Snackbar.make(binding.serviceLayout, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.serviceLayout, errorMessage.toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Snackbar.make(binding.serviceLayout, e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServiceListResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.serviceLayout, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addServiceBtn:
                displayServiceDialog();
                break;
            case R.id.servicePriceNextBtn:
                Intent portfolio = new Intent(ServicePriceActivity.this, PortfolioActivity.class);
                startActivity(portfolio);
                break;
        }

    }

    private void displayServiceDialog() {

        RecyclerView mRecyclerView;
        AlertDialog.Builder builder = new AlertDialog.Builder(ServicePriceActivity.this);
        final LayoutInflater inflater = LayoutInflater.from(ServicePriceActivity.this);

        final View content = inflater.inflate(R.layout.dialog_custom_design, null);
        builder.setView(content);
        mRecyclerView = (RecyclerView) content.findViewById(R.id.dialog_RecyclerView);
        btnDialogNext = (MaterialButton) content.findViewById(R.id.dialog_btnNext);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ServicePriceActivity.this));
        dialogAdapter = new DialogAdapter(getApplicationContext(), mList, this);
        mRecyclerView.setAdapter(dialogAdapter);
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnDialogNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedService = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).isSelected()) {
                        serviceModel = new ServiceListDataModel();
                        serviceModel.setSelected(mList.get(i).isSelected());
                        serviceModel.setName(mList.get(i).getName());
                        serviceModel.setId(mList.get(i).getId());
                        selectedService.add(serviceModel);
                    }
                    serviceAdapter = new ServicePriceAdapter(getApplicationContext(), selectedService);
                    binding.serviceRecyclerView.setLayoutManager(layoutManager);
                    binding.serviceRecyclerView.setAdapter(serviceAdapter);

                }
                dialog.dismiss();
//                    if (mList.get(mPosition).getName().length()>0)
//                    {
//                        mList.get(mPosition).isSelected();
////                            Intent intent = new Intent(ServicePriceActivity.this,ServicePriceActivity.class);
//////                            intent.putExtra("name", mList.get(mPosition).getName());
////                            startActivity(intent);
//
//                    }

            }
        });

    }

    @Override
    public void position(int pos, Boolean isChecked) {
        mList.get(pos).setSelected(isChecked);
//        serviceAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mList != null) {
//            setSelectedServiceList();
        }
    }
}
