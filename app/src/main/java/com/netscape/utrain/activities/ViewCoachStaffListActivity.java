package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.DiscoverTopRated;
import com.netscape.utrain.activities.organization.OrganizationSignUpActivity;
import com.netscape.utrain.adapters.ViewCoachStaffListAdapter;
import com.netscape.utrain.databinding.ActivityViewCoachStaffListBinding;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.model.ViewCoachListDataModel;
import com.netscape.utrain.response.ViewCoachListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewCoachStaffListActivity extends AppCompatActivity {

    Retrofitinterface retrofitinterface;
    ProgressDialog progressDialog;
    List<ViewCoachListDataModel> viewCoachList = new ArrayList<>();
    CoachListModel coachListModel;
    String coachId = "";
    RecyclerView coachListRecycler;
    RecyclerView.LayoutManager layoutManager;
    ViewCoachStaffListAdapter adapter;
    private ActivityViewCoachStaffListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_coach_staff_list);
        binding = DataBindingUtil.setContentView(ViewCoachStaffListActivity.this, R.layout.activity_view_coach_staff_list);
        progressDialog = new ProgressDialog(this);
        layoutManager = new LinearLayoutManager(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        if (getIntent().hasExtra("fromTopOrgAct"))
            binding.createCoach.setVisibility(View.GONE);
        hitViewCoachStaffListApi();

        binding.vCoachBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // back arrow image code....
                finish();
            }
        });
        binding.topResetFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitViewCoachStaffListApi();
            }
        });
        binding.createCoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // createViewCoachStaff Activity....

                Intent orgCoachSignUp = new Intent(ViewCoachStaffListActivity.this, OrganizationSignUpActivity.class);
                orgCoachSignUp.putExtra(Constants.ActiveUserType, Constants.TypeOrgCoach);
                startActivity(orgCoachSignUp);


            }
        });
    }

    private void hitViewCoachStaffListApi() {

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<ViewCoachListResponse> coachStaffList = null;

        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, ViewCoachStaffListActivity.this).equals(Constants.Organizer))
            coachStaffList = retrofitinterface.getViewCoachList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, ViewCoachStaffListActivity.this), "", "latest", "", CommonMethods.getPrefData(PrefrenceConstant.USER_ID, ViewCoachStaffListActivity.this));
        else
            coachStaffList = retrofitinterface.getViewCoachList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, ViewCoachStaffListActivity.this), "", "latest", "", getIntent().getStringExtra("coachList"));
        coachStaffList.enqueue(new Callback<ViewCoachListResponse>() {
            @Override
            public void onResponse(Call<ViewCoachListResponse> call, Response<ViewCoachListResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().isStatus()) {
                            if (response.body().getData().getData().size() > 0) {
                                binding.noDataImageView.setVisibility(View.GONE);
                                binding.topResetFilter.setVisibility(View.GONE);
                                viewCoachList = response.body().getData().getData();
                                adapter = new ViewCoachStaffListAdapter(ViewCoachStaffListActivity.this, viewCoachList);
                                binding.coachListRecycler.setLayoutManager(layoutManager);
                                binding.coachListRecycler.setAdapter(adapter);
                            } else {
                                binding.noDataImageView.setVisibility(View.VISIBLE);
                                binding.topResetFilter.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        binding.noDataImageView.setVisibility(View.VISIBLE);
                        binding.topResetFilter.setVisibility(View.VISIBLE);
                    }

                } else {
//                    binding.coachListRecycler.setVisibility(View.GONE);
                    binding.noDataImageView.setVisibility(View.VISIBLE);
                    binding.topResetFilter.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject jsonObject = jObjError.getJSONObject("error");
                        String code = jsonObject.getString("code");
                        if (!TextUtils.isEmpty(code)) {
                            if (Integer.parseInt(code) == 401) {
                                CommonMethods.invalidAuthToken(ViewCoachStaffListActivity.this, ViewCoachStaffListActivity.this);
                            }
                        }
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Toast.makeText(getApplicationContext(), "" + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                    }

                }
            }

            @Override
            public void onFailure(Call<ViewCoachListResponse> call, Throwable t) {
                binding.noDataImageView.setVisibility(View.VISIBLE);
                binding.topResetFilter.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
            }
        });
    }
}
