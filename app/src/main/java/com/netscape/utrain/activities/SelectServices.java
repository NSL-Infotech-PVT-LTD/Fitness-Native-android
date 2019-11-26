package com.netscape.utrain.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.netscape.utrain.R;
import com.netscape.utrain.adapters.DialogAdapter;
import com.netscape.utrain.adapters.ServicePriceAdapter;
import com.netscape.utrain.databinding.ActivitySelectServicesBinding;
import com.netscape.utrain.model.OrgUserDataModel;
import com.netscape.utrain.model.ServiceListDataModel;
import com.netscape.utrain.response.ServiceListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectServices extends AppCompatActivity implements View.OnClickListener, DialogAdapter.SelectedServicesInterface {
    ArrayList<ServiceListDataModel> mList = new ArrayList<>();
    ArrayList<ServiceListDataModel> selectedService = new ArrayList<>();
    private ArrayList<ServiceListDataModel> sList = new ArrayList<>();

    DialogAdapter dialogAdapter;
    RecyclerView.LayoutManager layoutManager;
    private ActivitySelectServicesBinding binding;
    private Retrofitinterface retrofitinterface;
    private ProgressDialog progressDialog;
    private OrgUserDataModel orgDataModel;
    private sendToMain send;
    public static boolean updateService=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_services);
        mList.clear();
        init();
        if (mList != null && mList.size() > 0) {
        } else {
            hitServiceListApi();
        }

    }

    private void init() {
        if (getIntent().getExtras() != null) {
            orgDataModel = (OrgUserDataModel) getIntent().getSerializableExtra(Constants.OrgSignUpIntent);
        }
        mList = CommonMethods.getListPrefrence(Constants.SERVICE_LIST, SelectServices.this);
        if (mList != null && mList.size() > 0) {
            binding.serviceRecyclerView.setLayoutManager(new LinearLayoutManager(SelectServices.this));
            dialogAdapter = new DialogAdapter(SelectServices.this, mList, SelectServices.this);
            binding.serviceRecyclerView.setAdapter(dialogAdapter);
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        binding.addServiceBtn.setOnClickListener(this);
        binding.selectServiceBackArrowImg.setOnClickListener(this);
        setBtnColour();
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
                            mList.addAll(response.body().getData().getData());
                            if (updateService){
                                if (getIntent().getExtras()!=null){
                                    Bundle args = getIntent().getExtras();
                                    sList= (ArrayList<ServiceListDataModel>) args.getSerializable("SelectedService");
                                    Constants.CHECKBOX_IS_CHECKED=sList.size();
                                if (sList !=null && sList.size()>0){
                                    binding.addServiceBtn.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                                    binding.addServiceBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                                    for (int i=0;i<sList.size();i++){
                                        for (int j=0;j<mList.size();j++){
                                            if (sList.get(i).getId()==mList.get(j).getId()){
                                                mList.get(j).setSelected(sList.get(i).isSelected());
                                            }
                                        }
                                    }
                                }
                                }
                            }

                            binding.serviceRecyclerView.setLayoutManager(new LinearLayoutManager(SelectServices.this));
                            dialogAdapter = new DialogAdapter(SelectServices.this, mList, SelectServices.this);
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
    public void position(int pos, boolean ischecked, ServiceListDataModel serviceListDataModel) {
        if (ischecked) {
            serviceListDataModel.setPrice(orgDataModel.getHourly_rate());
            SelectedServiceList.getInstance().getList().add(serviceListDataModel);
        } else {
            for (int i = 0; i < SelectedServiceList.getInstance().getList().size(); i++) {
                if (serviceListDataModel.getId() == SelectedServiceList.getInstance().getList().get(i).getId()) {
                    SelectedServiceList.getInstance().getList().remove(i);
                }
            }
        }
        mList.get(pos).setSelected(ischecked);
        setBtnColour();

    }

    public void setBtnColour() {
        if (Constants.CHECKBOX_IS_CHECKED > 0) {
            binding.addServiceBtn.setBackgroundColor(getResources().getColor(R.color.colorGreen));
            binding.addServiceBtn.setTextColor(getResources().getColor(R.color.colorBlack));
            binding.addServiceBtn.setClickable(true);
        }
        if (Constants.CHECKBOX_IS_CHECKED == 0) {
            binding.addServiceBtn.setBackgroundColor(getResources().getColor(R.color.lightGrayBtn));
            binding.addServiceBtn.setTextColor(getResources().getColor(R.color.lightGrayFont));
            binding.addServiceBtn.setClickable(false);
            Toast.makeText(this, "Select at least one service", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addServiceBtn:
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", mList);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                break;
            case  R.id.selectServiceBackArrowImg:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        updateService=false;
        CommonMethods.setLisstPrefData(Constants.SERVICE_LIST, mList, SelectServices.this);
        super.onDestroy();
    }

    public void getAdapterData(sendToMain send) {
        this.send = send;

    }

    public interface sendToMain {
        void getPosition(int position, boolean ischecked, ServiceListDataModel serviceListDataModel);
    }
}
