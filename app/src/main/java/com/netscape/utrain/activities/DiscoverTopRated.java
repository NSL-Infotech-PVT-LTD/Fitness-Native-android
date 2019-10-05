package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.netscape.utrain.R;
import com.netscape.utrain.adapters.AthleteTopRatedAdapter;
import com.netscape.utrain.adapters.TopCoachesAdapter;
import com.netscape.utrain.adapters.TopOrganizationAdapter;
import com.netscape.utrain.databinding.ActivityDiscoverTopRatedBinding;
import com.netscape.utrain.response.CoachListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverTopRated extends AppCompatActivity implements View.OnClickListener{
    private ActivityDiscoverTopRatedBinding binding;
    private AthleteTopRatedAdapter coachAdapter,orgAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Retrofitinterface retrofitinterface;
    public static boolean coaches=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_discover_top_rated);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_discover_top_rated);
        init();
    }

    private void init() {
        layoutManager=new LinearLayoutManager(this);
        retrofitinterface=RetrofitInstance.getClient().create(Retrofitinterface.class);
            String img= CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE,DiscoverTopRated.this);
            if (! TextUtils.isEmpty(img)) {
                Glide.with(this).load(Constants.IMAGE_BASE_URL + img).into(binding.discoverCircleImg);
            }
        if (getIntent().getExtras()!=null){
            if (getIntent().getStringExtra(Constants.TOP_TYPE_INTENT).equalsIgnoreCase(Constants.TOP_COACHES)){
                binding.exploreTv.setText(getResources().getString(R.string.explore_the_best_coaches));
                getCoachListApi();
            }
            if (getIntent().getStringExtra(Constants.TOP_TYPE_INTENT).equalsIgnoreCase(Constants.TOP_ORG)){
                binding.exploreTv.setText(getResources().getString(R.string.explore_the_best_org));
                getTopOrgaNization();
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }
    private void getTopOrgaNization() {
        Call<CoachListResponse> call = retrofitinterface.getTopOrgList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()),"","5","");
        call.enqueue(new Callback<CoachListResponse>() {
            @Override
            public void onResponse(Call<CoachListResponse> call, Response<CoachListResponse> response) {
                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        orgAdapter = new AthleteTopRatedAdapter(getApplicationContext(), response.body().getData().getData());
                        binding.topRateRecycler.setLayoutManager(layoutManager);
                        binding.topRateRecycler.setAdapter(orgAdapter);
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Toast.makeText(getApplicationContext(), "" + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                    }
                }


            }

            @Override
            public void onFailure(Call<CoachListResponse> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void getCoachListApi() {
        Call<CoachListResponse> call = retrofitinterface.getCoachList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()),"","5","");
        call.enqueue(new Callback<CoachListResponse>() {
            @Override
            public void onResponse(Call<CoachListResponse> call, Response<CoachListResponse> response) {
                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        coachAdapter = new AthleteTopRatedAdapter(DiscoverTopRated.this, response.body().getData().getData());
                        binding.topRateRecycler.setLayoutManager(layoutManager);
                        binding.topRateRecycler.setAdapter(coachAdapter);
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);

                        Toast.makeText(getApplicationContext(), "" + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                    }
                }


            }

            @Override
            public void onFailure(Call<CoachListResponse> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
