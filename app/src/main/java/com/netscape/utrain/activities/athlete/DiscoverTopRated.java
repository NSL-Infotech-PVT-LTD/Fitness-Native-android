package com.netscape.utrain.activities.athlete;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.TopCoachOrgDetailActivity;
import com.netscape.utrain.activities.organization.EventAppliedList;
import com.netscape.utrain.adapters.AthleteTopRatedAdapter;
import com.netscape.utrain.adapters.SportsAdapter;
import com.netscape.utrain.databinding.ActivityDiscoverTopRatedBinding;
import com.netscape.utrain.model.SportListModel;
import com.netscape.utrain.response.CoachListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverTopRated extends AppCompatActivity implements View.OnClickListener {
    public static boolean coaches = false;
    Retrofitinterface api;
    private ActivityDiscoverTopRatedBinding binding;
    private AthleteTopRatedAdapter coachAdapter, orgAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Retrofitinterface retrofitinterface;
    private String searchText = "";
    private ProgressDialog progressDialog;
    private ArrayList<SportListModel.DataBeanX.DataBean> sportList = new ArrayList<>();
    private ArrayList<SportListModel.DataBeanX.DataBean> dropDownList = new ArrayList<>();
    private ArrayList<String> sports = new ArrayList<>();
    private SportListModel.DataBeanX.DataBean sport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_discover_top_rated);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_discover_top_rated);
        api = RetrofitInstance.getClient().create(Retrofitinterface.class);

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

                        if (!binding.discoverSearchEdt.getText().toString().isEmpty()) {
                            if (getIntent().getStringExtra(Constants.TOP_TYPE_INTENT).equalsIgnoreCase(Constants.TOP_COACHES)) {
                                binding.dServiceSpinner.setVisibility(View.VISIBLE);
                                binding.exploreTv.setText(getResources().getString(R.string.explore_the_best_coaches));
                                searchText = binding.discoverSearchEdt.getText().toString();
                                getCoachListApi();
                            }
                            if (getIntent().getStringExtra(Constants.TOP_TYPE_INTENT).equalsIgnoreCase(Constants.TOP_ORG)) {

                                binding.exploreTv.setText(getResources().getString(R.string.explore_the_best_org));
                                searchText = binding.discoverSearchEdt.getText().toString();
                                getTopOrgaNization();
                            }
                        } else {
                            Toast.makeText(DiscoverTopRated.this, "Enter name to search", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        layoutManager = new LinearLayoutManager(this);
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        String img = CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, DiscoverTopRated.this);
//            if (! TextUtils.isEmpty(img)) {
//                Glide.with(this).load(Constants.IMAGE_BASE_URL + img).into(binding.discoverCircleImg);
//            }
        if (getIntent().getExtras() != null) {
            if (getIntent().getStringExtra(Constants.TOP_TYPE_INTENT).equalsIgnoreCase(Constants.TOP_COACHES)) {
                binding.exploreTv.setText(getResources().getString(R.string.explore_the_best_coaches));
                getCoachListApi();
                sportsListApi();

            }
            if (getIntent().getStringExtra(Constants.TOP_TYPE_INTENT).equalsIgnoreCase(Constants.TOP_ORG)) {
                binding.exploreTv.setText(getResources().getString(R.string.explore_the_best_org));
                binding.dServiceSpinner.setVisibility(View.GONE);
                binding.spinnerText.setVisibility(View.GONE);
                getTopOrgaNization();
            }
        }
//        binding.discoverSearchTv.setOnClickListener(this);
        binding.discoverBackArrowImg.setOnClickListener(this);
        binding.searchedt.setOnClickListener(this);

//        initializeUI();
    }

    private void initializeUI() {

        getSportsIds();
        for (SportListModel.DataBeanX.DataBean details : dropDownList) {
            sports.add(details.getName());
        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(), R.layout.discover_sports_spinner, sports);
//        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        binding.dServiceSpinner.setAdapter(adapter);
        binding.dServiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String position = i + "";
                binding.dServiceSpinner.setSelection(i);
                binding.spinnerText.setText(dropDownList.get(i).getName());
                if (i == 0) {
                    searchText = "";
                } else {
                    searchText = dropDownList.get(i).getName();
                }
                getCoachListApi();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
//        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        inputManager.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);

    }

    private void getTopOrgaNization() {
        progressDialog.show();
        Call<CoachListResponse> call = retrofitinterface.getTopOrgList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), searchText, "10", "");
        call.enqueue(new Callback<CoachListResponse>() {
            @Override
            public void onResponse(Call<CoachListResponse> call, Response<CoachListResponse> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().getData().size() > 0) {
                            binding.topRateRecycler.setVisibility(View.VISIBLE);
                            binding.noDataImageView.setVisibility(View.GONE);
                            orgAdapter = new AthleteTopRatedAdapter(getApplicationContext(), response.body().getData().getData(), 2);
                            binding.topRateRecycler.setLayoutManager(layoutManager);
                            binding.topRateRecycler.setAdapter(orgAdapter);
                            String[] array = new String[response.body().getData().getData().size()];
                            for (int i = 0; i < response.body().getData().getData().size(); i++) {


                                array[i] = response.body().getData().getData().get(i).getName();
                            }
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(DiscoverTopRated.this, android.R.layout.simple_dropdown_item_1line, array);
                            binding.discoverSearchEdt.setThreshold(1);
                            binding.discoverSearchEdt.setAdapter(adapter);

                            binding.discoverSearchEdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    searchText = adapterView.getItemAtPosition(i).toString();
                                    getTopOrgaNization();
                                }
                            });
                        } else {
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
        Call<CoachListResponse> call = retrofitinterface.getCoachList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), searchText, "5", "", "latest");
        call.enqueue(new Callback<CoachListResponse>() {
            @Override
            public void onResponse(Call<CoachListResponse> call, Response<CoachListResponse> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().getData().size() > 0) {
                            binding.topRateRecycler.setVisibility(View.VISIBLE);
                            binding.noDataImageView.setVisibility(View.GONE);

                            coachAdapter = new AthleteTopRatedAdapter(DiscoverTopRated.this, response.body().getData().getData(), 1);
                            binding.topRateRecycler.setLayoutManager(layoutManager);
                            binding.topRateRecycler.setAdapter(coachAdapter);

                            String[] array = new String[response.body().getData().getData().size()];

                            for (int i = 0; i < response.body().getData().getData().size(); i++) {
                                array[i] = response.body().getData().getData().get(i).getName();
                            }
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(DiscoverTopRated.this, android.R.layout.simple_dropdown_item_1line, array);
                            binding.discoverSearchEdt.setThreshold(1);
                            binding.discoverSearchEdt.setAdapter(adapter);

                            binding.discoverSearchEdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    searchText = adapterView.getItemAtPosition(i).toString();
                                    getCoachListApi();
                                }
                            });
                        } else {
                            binding.topRateRecycler.setVisibility(View.GONE);
                            binding.noDataImageView.setVisibility(View.VISIBLE);
                        }


                    } else {
                        Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

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

    private void getSportsIds() {
        String sportName = CommonMethods.getPrefData(PrefrenceConstant.SPORTS_NAME, getApplicationContext());
        Gson gson = new Gson();

        if (sportName != null) {
            if (sportName.isEmpty()) {
                Toast.makeText(DiscoverTopRated.this, "Service Not Found", Toast.LENGTH_SHORT).show();
            } else {
                Type type = new TypeToken<List<SportListModel.DataBeanX.DataBean>>() {
                }.getType();
                sportList = gson.fromJson(sportName, type);

                StringBuilder builder = new StringBuilder();
                for (SportListModel.DataBeanX.DataBean details : sportList) {
                    builder.append(details.getName() + "\n");

                }


            }
        }
    }

    private void sportsListApi() {
        progressDialog.show();
        Call<SportListModel> call = api.getSportList(Constants.CONTENT_TYPE, "", "");
        call.enqueue(new Callback<SportListModel>() {
            @Override
            public void onResponse(Call<SportListModel> call, Response<SportListModel> response) {
                progressDialog.dismiss();
                if (response.body().isStatus()) {
                    sport = new SportListModel.DataBeanX.DataBean();
                    sport.setName("Select Sports");
                    dropDownList.add(sport);
                    if (response.body() != null) {
                        dropDownList.addAll(response.body().getData().getData());
                        initializeUI();
                    }
                }
            }

            @Override
            public void onFailure(Call<SportListModel> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

}
