package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.netscape.utrain.R;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewCoachStaffListActivity extends AppCompatActivity {

    Retrofitinterface retrofitinterface;
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
        layoutManager = new LinearLayoutManager(this);

        if (getIntent().hasExtra("fromTopOrgAct"))
            binding.createCoach.setVisibility(View.GONE);

        hitViewCoachStaffListApi();
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
        Call<ViewCoachListResponse> coachStaffList = retrofitinterface.getViewCoachList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, ViewCoachStaffListActivity.this), "", "latest", "", CommonMethods.getPrefData(PrefrenceConstant.USER_ID, ViewCoachStaffListActivity.this));
        coachStaffList.enqueue(new Callback<ViewCoachListResponse>() {
            @Override
            public void onResponse(Call<ViewCoachListResponse> call, Response<ViewCoachListResponse> response) {

                if (response.body() != null) {
                    viewCoachList = response.body().getData().getData();
                    adapter = new ViewCoachStaffListAdapter(ViewCoachStaffListActivity.this, viewCoachList);
                    binding.coachListRecycler.setLayoutManager(layoutManager);
                    binding.coachListRecycler.setAdapter(adapter);
                }


            }

            @Override
            public void onFailure(Call<ViewCoachListResponse> call, Throwable t) {

            }
        });
    }
}
