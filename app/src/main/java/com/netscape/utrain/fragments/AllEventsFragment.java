package com.netscape.utrain.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.DiscoverTopRated;
import com.netscape.utrain.adapters.AllEventsCoachListAdapter;
import com.netscape.utrain.adapters.AllEventsOrgListAdapter;
import com.netscape.utrain.adapters.AthleteTopRatedAdapter;
import com.netscape.utrain.databinding.AllEventsFragmentBinding;
import com.netscape.utrain.model.C_EventDataListModel;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.model.O_EventDataModel;
import com.netscape.utrain.response.C_EventListResponse;
import com.netscape.utrain.response.CoachListResponse;
import com.netscape.utrain.response.O_EventListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PaginationScrollListener;
import com.netscape.utrain.utils.PrefrenceConstant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllEventsFragment extends Fragment {

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    int page=0;
    //    private int currentPage = PAGE_START;
    private String currentPage="1";
    private int getItemPerPage;
    private Retrofitinterface retrofitinterface;
    private AllEventsFragmentBinding binding;
    private Context context;
    private ProgressDialog progressDialog;

    // below code for CoachEventlist....
    private List<O_EventDataModel> eventList = new ArrayList<>();
    private RecyclerView allEventListRecycler;
    private LinearLayoutManager layoutManager;
    private AllEventsCoachListAdapter eventAdapter;
    private AllEventsOrgListAdapter orgListAdapter;

    // below code for OrgEventList....
    private List<O_EventDataModel> orgEventList = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.all_events_fragment, container, false);
        View view = binding.getRoot();

        layoutManager = new LinearLayoutManager(context);
        binding.allEventListRecycler.setLayoutManager(layoutManager);
        recyclerFunc(layoutManager);


        return view;
    }

    private void checkRoleHitApi() {

        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, context).equalsIgnoreCase(Constants.Coach)) {
            AllCoachEventsCreatedApi();
        } else {
            AllOrgEventsCreatedApi();
        }

    }

    public void AllCoachEventsCreatedApi() {
        isLastPage=false;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<O_EventListResponse> callEventCoachList = retrofitinterface.getCoachEvents("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, context)
                        , Constants.CONTENT_TYPE, "",currentPage);
        callEventCoachList.enqueue(new Callback<O_EventListResponse>() {
            @Override
            public void onResponse(Call<O_EventListResponse> call, Response<O_EventListResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().isStatus())
                        if (response.body() != null) {
                            eventList = response.body().getData().getData();
                            if (eventList != null && eventList.size() > 0) {


                                binding.allEventNoImage.setVisibility(View.GONE);
//                                eventAdapter = new AllEventsCoachListAdapter(context, eventList);
//                                binding.allEventListRecycler.setAdapter(eventAdapter);
                                binding.allEventListRecycler.setVisibility(View.VISIBLE);
                                eventAdapter = new AllEventsCoachListAdapter(getContext());
                                binding.allEventListRecycler.setAdapter(eventAdapter);
                                List<O_EventDataModel> results = fetchResults(response);

                                TOTAL_PAGES = response.body().getData().getLast_page();
                                getItemPerPage = Integer.parseInt(response.body().getData().getPer_page());
                                if (! TextUtils.isEmpty(currentPage)) {
                                    page = Integer.parseInt(currentPage);
                                }
                                eventAdapter.addAll(results);
                                if (page < TOTAL_PAGES)
                                    eventAdapter.addLoadingFooter();
                                else isLastPage = true;



                            } else {
                                binding.allEventNoImage.setVisibility(View.VISIBLE);
                            }
                        }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(context, "" + response.body().getError().getError_message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<O_EventListResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
    public void AllCoachEventsCreatedNextPage() {

//        progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Loading....");
//        progressDialog.show();

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<O_EventListResponse> callEventCoachList = retrofitinterface.getCoachEvents("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, context)
                , Constants.CONTENT_TYPE, "",currentPage);
        callEventCoachList.enqueue(new Callback<O_EventListResponse>() {
            @Override
            public void onResponse(Call<O_EventListResponse> call, Response<O_EventListResponse> response) {
//                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().isStatus())
                        if (response.body() != null) {
                            eventList = response.body().getData().getData();
                            if (eventList != null && eventList.size() > 0) {


                                binding.allEventNoImage.setVisibility(View.GONE);
                                binding.allEventListRecycler.setVisibility(View.VISIBLE);
                                eventAdapter.removeLoadingFooter();
                                isLoading = false;

                                List<O_EventDataModel> results = fetchResults(response);

                                //TOTAL_PAGES = response.body().getData().getLast_page();
                                getItemPerPage = Integer.parseInt(response.body().getData().getPer_page());

                                eventAdapter.addAll(results);
                                if (! TextUtils.isEmpty(currentPage)) {
                                    page = Integer.parseInt(currentPage);
                                }
                                if (page != TOTAL_PAGES)
                                    eventAdapter.addLoadingFooter();
                                else isLastPage = true;


                            } else {
                                binding.allEventNoImage.setVisibility(View.VISIBLE);
                            }
                        }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(context, "" + response.body().getError().getError_message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<O_EventListResponse> call, Throwable t) {
//                progressDialog.dismiss();
            }
        });
    }

    private List<O_EventDataModel> fetchResults(Response<O_EventListResponse> response) {
        O_EventListResponse topRatedMovies = response.body();
        return topRatedMovies.getData().getData();
    }
    public void AllOrgEventsCreatedApi() {
        isLastPage=false;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<O_EventListResponse> callEventOrgList = retrofitinterface.getOrgEentList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, context)
                , Constants.CONTENT_TYPE, "",currentPage);

        callEventOrgList.enqueue(new Callback<O_EventListResponse>() {
            @Override
            public void onResponse(Call<O_EventListResponse> call, Response<O_EventListResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful())
                    if (response.body().isStatus())
                        if (response.body() != null) {
                            orgEventList = response.body().getData().getData();
                            if (orgEventList !=null && orgEventList.size() > 0) {
                                orgListAdapter = new AllEventsOrgListAdapter(context, orgEventList);
                                binding.allEventListRecycler.setAdapter(orgListAdapter);

                                binding.allEventNoImage.setVisibility(View.GONE);
                                binding.allEventListRecycler.setVisibility(View.VISIBLE);
                                orgListAdapter = new AllEventsOrgListAdapter(getContext());
                                binding.allEventListRecycler.setAdapter(orgListAdapter);
                                List<O_EventDataModel> results = fetchResults(response);

                                TOTAL_PAGES = response.body().getData().getLast_page();
                                getItemPerPage = Integer.parseInt(response.body().getData().getPer_page());
                                if (! TextUtils.isEmpty(currentPage)) {
                                    page = Integer.parseInt(currentPage);
                                }
                                orgListAdapter.addAll(results);
                                if (page < TOTAL_PAGES)
                                    orgListAdapter.addLoadingFooter();
                                else isLastPage = true;


                            } else {
                                binding.allEventNoImage.setVisibility(View.VISIBLE);
                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(context, "" + response.body().getError().getError_message(), Toast.LENGTH_LONG).show();
                        }
            }

            @Override
            public void onFailure(Call<O_EventListResponse> call, Throwable t) {

                progressDialog.dismiss();

            }
        });
    }
    public void AllOrgEventsCreatedNextPage() {

//        progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Loading....");
//        progressDialog.show();

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<O_EventListResponse> callEventOrgList = retrofitinterface.getOrgEentList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, context)
                , Constants.CONTENT_TYPE, "",currentPage);

        callEventOrgList.enqueue(new Callback<O_EventListResponse>() {
            @Override
            public void onResponse(Call<O_EventListResponse> call, Response<O_EventListResponse> response) {
//                progressDialog.dismiss();

                if (response.isSuccessful())
                    if (response.body().isStatus())
                        if (response.body() != null) {
                            orgEventList = response.body().getData().getData();
                            if (orgEventList !=null && orgEventList.size() > 0) {
//                                orgListAdapter = new AllEventsOrgListAdapter(context, orgEventList);
//                                binding.allEventListRecycler.setAdapter(orgListAdapter);

                                binding.allEventNoImage.setVisibility(View.GONE);
                                binding.allEventListRecycler.setVisibility(View.VISIBLE);
                                orgListAdapter.removeLoadingFooter();
                                isLoading = false;

                                List<O_EventDataModel> results = fetchResults(response);

                                //TOTAL_PAGES = response.body().getData().getLast_page();
                                getItemPerPage = Integer.parseInt(response.body().getData().getPer_page());

                                orgListAdapter.addAll(results);
                                if (! TextUtils.isEmpty(currentPage)) {
                                    page = Integer.parseInt(currentPage);
                                }
                                if (page != TOTAL_PAGES)
                                    orgListAdapter.addLoadingFooter();
                                else isLastPage = true;


                            } else {
                                binding.allEventNoImage.setVisibility(View.VISIBLE);
                            }

                        } else {
//                            progressDialog.dismiss();
                            Toast.makeText(context, "" + response.body().getError().getError_message(), Toast.LENGTH_LONG).show();
                        }
            }

            @Override
            public void onFailure(Call<O_EventListResponse> call, Throwable t) {

//                progressDialog.dismiss();

            }
        });
    }

    private void recyclerFunc(LinearLayoutManager layoutManager) {
        binding.allEventListRecycler.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                if (! TextUtils.isEmpty(currentPage)) {
                    page = Integer.parseInt(currentPage);
                }
                page += 1;
                currentPage=page+"";

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Organizer))
                            AllOrgEventsCreatedNextPage();
                        else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Coach))
                            AllCoachEventsCreatedNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    @Override
    public void onStart() {
        checkRoleHitApi();
        super.onStart();
    }

}
