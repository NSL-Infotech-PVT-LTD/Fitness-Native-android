package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.netscape.utrain.R;
import com.netscape.utrain.adapters.ViewCoachStaffListAdapter;
import com.netscape.utrain.databinding.ActivityViewCoachStaffListBinding;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.model.ViewCoachListDataModel;
import com.netscape.utrain.response.ViewCoachListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;

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
        hitViewCoachStaffListApi();
    }

    private void hitViewCoachStaffListApi() {

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<ViewCoachListResponse> coachStaffList = retrofitinterface.getViewCoachList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, ViewCoachStaffListActivity.this), "", "latest", "", getIntent().getStringExtra("coachList"));
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
