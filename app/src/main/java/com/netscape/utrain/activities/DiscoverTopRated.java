package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    private String searchText="";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_discover_top_rated);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_discover_top_rated);
        init();
    }

    private void init() {
        binding.discoverSearchEdt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                closeKeyboard();
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.discoverSearchEdt.getRight() - binding.discoverSearchEdt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        if(!binding.discoverSearchEdt.getText().toString().isEmpty()) {
                    if (getIntent().getStringExtra(Constants.TOP_TYPE_INTENT).equalsIgnoreCase(Constants.TOP_COACHES)) {
                        binding.exploreTv.setText(getResources().getString(R.string.explore_the_best_coaches));
                        searchText=binding.discoverSearchEdt.getText().toString();
                        getCoachListApi();
                    }
                    if (getIntent().getStringExtra(Constants.TOP_TYPE_INTENT).equalsIgnoreCase(Constants.TOP_ORG)) {
                        binding.exploreTv.setText(getResources().getString(R.string.explore_the_best_org));
                        searchText=binding.discoverSearchEdt.getText().toString();
                        getTopOrgaNization();
                    }
                    }else {
                        Toast.makeText(DiscoverTopRated.this, "Enter name to search", Toast.LENGTH_SHORT).show();
                    }
                        return true;
                    }
                }
                return false;
            }
        });
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        layoutManager=new LinearLayoutManager(this);
        retrofitinterface=RetrofitInstance.getClient().create(Retrofitinterface.class);
            String img= CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE,DiscoverTopRated.this);
//            if (! TextUtils.isEmpty(img)) {
//                Glide.with(this).load(Constants.IMAGE_BASE_URL + img).into(binding.discoverCircleImg);
//            }
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
//        binding.discoverSearchTv.setOnClickListener(this);
        binding.discoverBackArrowImg.setOnClickListener(this);
        binding.searchedt.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//
//                case R.id.discoverSearchTv:
//                    if(!binding.discoverSearchEdt.getText().toString().isEmpty()) {
//                    if (getIntent().getStringExtra(Constants.TOP_TYPE_INTENT).equalsIgnoreCase(Constants.TOP_COACHES)) {
//                        binding.exploreTv.setText(getResources().getString(R.string.explore_the_best_coaches));
//                        searchText=binding.discoverSearchEdt.getText().toString();
//                        getCoachListApi();
//                    }
//                    if (getIntent().getStringExtra(Constants.TOP_TYPE_INTENT).equalsIgnoreCase(Constants.TOP_ORG)) {
//                        binding.exploreTv.setText(getResources().getString(R.string.explore_the_best_org));
//                        searchText=binding.discoverSearchEdt.getText().toString();
//                        getTopOrgaNization();
//                    }
//                    }else {
//                        Toast.makeText(this, "Enter name to search", Toast.LENGTH_SHORT).show();
//                    }
//                break;
            case R.id.discoverBackArrowImg:
                finish();
                break;
            case R.id.searchedt:
                binding.searchAtuoCompleteEdt.setVisibility(View.VISIBLE);
                binding.searchIcon.setVisibility(View.GONE);
                break;


        }
    }

    private void closeKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    private void getTopOrgaNization() {
        progressDialog.show();
        Call<CoachListResponse> call = retrofitinterface.getTopOrgList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()),searchText,"5","");
        call.enqueue(new Callback<CoachListResponse>() {
            @Override
            public void onResponse(Call<CoachListResponse> call, Response<CoachListResponse> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().getData().size()>0) {
                            binding.topRateRecycler.setVisibility(View.VISIBLE);
                            binding.noDataImageView.setVisibility(View.GONE);
                        orgAdapter = new AthleteTopRatedAdapter(getApplicationContext(), response.body().getData().getData(),2);
                        binding.topRateRecycler.setLayoutManager(layoutManager);
                        binding.topRateRecycler.setAdapter(orgAdapter);
                        }else{
                            binding.topRateRecycler.setVisibility(View.GONE);
                            binding.noDataImageView.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    binding.topRateRecycler.setVisibility(View.GONE);
                    binding.noDataImageView.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
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
                binding.topRateRecycler.setVisibility(View.GONE);
                binding.noDataImageView.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void getCoachListApi() {
        progressDialog.show();
        Call<CoachListResponse> call = retrofitinterface.getCoachList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()),searchText,"5","");
        call.enqueue(new Callback<CoachListResponse>() {
            @Override
            public void onResponse(Call<CoachListResponse> call, Response<CoachListResponse> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().getData().size()>0) {
                            binding.topRateRecycler.setVisibility(View.VISIBLE);
                            binding.noDataImageView.setVisibility(View.GONE);
                            coachAdapter = new AthleteTopRatedAdapter(DiscoverTopRated.this, response.body().getData().getData(), 1);
                            binding.topRateRecycler.setLayoutManager(layoutManager);
                            binding.topRateRecycler.setAdapter(coachAdapter);
                        }else{
                            binding.topRateRecycler.setVisibility(View.GONE);
                            binding.noDataImageView.setVisibility(View.VISIBLE);
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "No Data Found" , Toast.LENGTH_SHORT).show();

                    }
                } else {
                    binding.topRateRecycler.setVisibility(View.GONE);
                    binding.noDataImageView.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
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
                binding.topRateRecycler.setVisibility(View.GONE);
                binding.noDataImageView.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
