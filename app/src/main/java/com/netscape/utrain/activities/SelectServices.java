package com.netscape.utrain.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.netscape.utrain.R;
import com.netscape.utrain.adapters.DialogAdapter;
import com.netscape.utrain.adapters.ServicePriceAdapter;
import com.netscape.utrain.databinding.ActivitySelectServicesBinding;
import com.netscape.utrain.model.ServiceListDataModel;
import com.netscape.utrain.response.ServiceListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.Constants;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectServices extends AppCompatActivity implements DialogAdapter.SelectedServicesInterface {
    private ActivitySelectServicesBinding binding;
    private Retrofitinterface retrofitinterface;
    private ProgressDialog progressDialog;
    ArrayList<ServiceListDataModel> mList = new ArrayList<>();
    DialogAdapter dialogAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_services);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_select_services);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        hitServiceListApi();

    }
    private void hitServiceListApi() {
        progressDialog.show();
        Call<ServiceListResponse> signUpAthlete = retrofitinterface.getServiceList(Constants.CONTENT_TYPE);
        signUpAthlete.enqueue(new Callback<ServiceListResponse>() {
            @Override
            public void onResponse(Call<ServiceListResponse> call, Response<ServiceListResponse> response) {
                if (response.isSuccessful()) {

                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            mList.addAll(response.body().getData());
                            binding.serviceRecyclerView.setLayoutManager(new LinearLayoutManager(SelectServices.this));
//                            dialogAdapter = new DialogAdapter();
                            binding.serviceRecyclerView.setAdapter(dialogAdapter);
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
    public void position(int id, boolean ischecked, ServiceListDataModel serviceListDataModel) {

    }

}
