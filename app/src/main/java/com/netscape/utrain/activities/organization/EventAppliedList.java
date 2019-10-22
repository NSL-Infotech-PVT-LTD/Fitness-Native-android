package com.netscape.utrain.activities.organization;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.netscape.utrain.R;
import com.netscape.utrain.adapters.O_EventListAdapter;
import com.netscape.utrain.adapters_org.O_BookedEventListAdapter;
import com.netscape.utrain.model.BookedUserModel;
import com.netscape.utrain.model.O_BookedEventDataModel;
import com.netscape.utrain.model.O_BookedSessionDataModel;
import com.netscape.utrain.model.O_SessionDataModel;
import com.netscape.utrain.model.O_SpaceDataModel;
import com.netscape.utrain.model.O_SpaceListDataModel;
import com.netscape.utrain.response.O_BookedSpaceListResponse;
import com.netscape.utrain.response.O_EventBookedListResponse;
import com.netscape.utrain.response.O_EventListResponse;
import com.netscape.utrain.response.O_SessionBookedListResponse;
import com.netscape.utrain.response.O_SessionListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventAppliedList extends AppCompatActivity implements O_BookedEventListAdapter.onClick {
    private RecyclerView.LayoutManager layoutManager;
    private O_BookedEventListAdapter adapter;
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    private ConstraintLayout userBottomSheeet;
    private List<O_BookedEventDataModel> list ;
    private List<O_BookedSessionDataModel> sessionData;
    private List<O_SpaceListDataModel> spaceData;
    BookedUserModel model;
    private String id="";
    private String type="";

    private BottomSheetBehavior sheetBehavior;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_applied_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.appliedListRecycler);


        userBottomSheeet = findViewById(R.id.userBottomSheeet);
        sheetBehavior = BottomSheetBehavior.from(userBottomSheeet);

        bottomSheetBehavior_sort();
        init();


    }

    private void bottomSheetUpDown_address() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }


    }

    private void bottomSheetBehavior_sort() {

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:

                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void init() {
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        if (getIntent().getExtras() != null) {
            id = getIntent().getStringExtra(Constants.SELECTED_ID);
             type= getIntent().getStringExtra(Constants.SELECTED_TYPE);
        }
        if (type.equalsIgnoreCase(Constants.EVENT)){
            getNumOfBookedList();
        }
        if (type.equalsIgnoreCase(Constants.SPACE)){
            getNumOfBookedList();
        }
        if (type.equalsIgnoreCase(Constants.SESSION)){
            getNumSessionList();
        }

        }
//        adapter = new O_BookedEventListAdapter(EventAppliedList.this, list, new O_BookedEventListAdapter.onClick() {
//            @Override
//            public void onClick() {
//
//                bottomSheetUpDown_address();
//
//            }
//        });
//
//        recyclerView.setAdapter(adapter);


    public void getNumOfBookedList() {
        progressDialog.show();
        Call<O_EventBookedListResponse> call = retrofitinterface.getOrganiserBookedList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), Constants.CONTENT_TYPE, id,"event");
        call.enqueue(new Callback<O_EventBookedListResponse>() {
            @Override
            public void onResponse(Call<O_EventBookedListResponse> call, Response<O_EventBookedListResponse> response) {
                if (response.body() != null) {
                    list = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().size() > 0) {
//                            binding.topRateRecycler.setVisibility(View.VISIBLE);
//                            binding.noDataImageView.setVisibility(View.GONE);
//                            data.addAll(response.body().getData());
                            list.addAll(response.body().getData());
                            adapter = new O_BookedEventListAdapter(EventAppliedList.this, list,1,EventAppliedList.this);
//                                    new O_BookedEventListAdapter.onClick() {
//                                @Override
//                                public void onClick() {
//
//                                    bottomSheetUpDown_address();
//
//                                }
//                            });
                            recyclerView.setAdapter(adapter);
                        } else {
//                            binding.topRateRecycler.setVisibility(View.GONE);
//                            binding.noDataImageView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                    }
                } else {
//                    binding.topRateRecycler.setVisibility(View.GONE);
//                    binding.noDataImageView.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<O_EventBookedListResponse> call, Throwable t) {
//                binding.topRateRecycler.setVisibility(View.GONE);
//                binding.noDataImageView.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getNumSessionList() {
        progressDialog.show();
        Call<O_SessionBookedListResponse> callSession = retrofitinterface.getOrganiserBookedSessionList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), Constants.CONTENT_TYPE, id,"session");
        callSession.enqueue(new Callback<O_SessionBookedListResponse>() {
            @Override
            public void onResponse(Call<O_SessionBookedListResponse> call, Response<O_SessionBookedListResponse> response) {
                if (response.body() != null) {
                    sessionData = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().size() > 0) {
//                            binding.topRateRecycler.setVisibility(View.VISIBLE);
//                            binding.noDataImageView.setVisibility(View.GONE);
//                            data.addAll(response.body().getData());
                            sessionData.addAll(response.body().getData());
                            adapter = new O_BookedEventListAdapter(EventAppliedList.this, sessionData,3,EventAppliedList.this);
//                                    new O_BookedEventListAdapter.onClick() {
//                                @Override
//                                public void onClick() {
//
//                                    bottomSheetUpDown_address();
//
//                                }
//                            });
                            recyclerView.setAdapter(adapter);
                        } else {
//                            binding.topRateRecycler.setVisibility(View.GONE);
//                            binding.noDataImageView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                    }
                } else {
//                    binding.topRateRecycler.setVisibility(View.GONE);
//                    binding.noDataImageView.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<O_SessionBookedListResponse> call, Throwable t) {
//                binding.topRateRecycler.setVisibility(View.GONE);
//                binding.noDataImageView.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getNumSpaceList() {
        progressDialog.show();
        Call<O_BookedSpaceListResponse> callSession = retrofitinterface.getOrganiserBookedSpaceList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getApplicationContext()), Constants.CONTENT_TYPE, id,"space");
        callSession.enqueue(new Callback<O_BookedSpaceListResponse>() {
            @Override
            public void onResponse(Call<O_BookedSpaceListResponse> call, Response<O_BookedSpaceListResponse> response) {
                if (response.body() != null) {
                    spaceData = new ArrayList<>();
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData().size() > 0) {
//                            binding.topRateRecycler.setVisibility(View.VISIBLE);
//                            binding.noDataImageView.setVisibility(View.GONE);
//                            data.addAll(response.body().getData());
                            spaceData.addAll(response.body().getData());
                            adapter = new O_BookedEventListAdapter(EventAppliedList.this, spaceData,2,EventAppliedList.this);
//                                    new O_BookedEventListAdapter.onClick() {
//                                @Override
//                                public void onClick() {
//
//                                    bottomSheetUpDown_address();
//
//                                }
//                            });
                            recyclerView.setAdapter(adapter);
                        } else {
//                            binding.topRateRecycler.setVisibility(View.GONE);
//                            binding.noDataImageView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

                    }
                } else {
//                    binding.topRateRecycler.setVisibility(View.GONE);
//                    binding.noDataImageView.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<O_BookedSpaceListResponse> call, Throwable t) {
//                binding.topRateRecycler.setVisibility(View.GONE);
//                binding.noDataImageView.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick() {
        bottomSheetUpDown_address();
    }
}
