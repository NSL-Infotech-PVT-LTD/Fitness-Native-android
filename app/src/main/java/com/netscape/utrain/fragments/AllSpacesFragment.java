package com.netscape.utrain.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.DiscoverTopRated;
import com.netscape.utrain.adapters.AllSpaceOrgListAdapter;
import com.netscape.utrain.adapters.AthleteTopRatedAdapter;
import com.netscape.utrain.databinding.AllSessionsFragmentBinding;
import com.netscape.utrain.databinding.AllSpacesFragmentBinding;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.model.O_SpaceDataModel;
import com.netscape.utrain.response.CoachListResponse;
import com.netscape.utrain.response.O_SpaceListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllSpacesFragment extends Fragment {
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    int page=0;
    //    private int currentPage = PAGE_START;
    private String currentPage="1";
    private int getItemPerPage;
    private AllSpacesFragmentBinding binding;
    private Retrofitinterface retrofitinterface;
    private List<O_SpaceDataModel> orgSpaceList = new ArrayList<>();
    private AllSpaceOrgListAdapter orgSpaceAdapter;
    private RecyclerView orgSpaceRecycler;
    private LinearLayoutManager layoutManager;
    private ProgressDialog progressDialog;

    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.all_spaces_fragment, container, false);
        View view = binding.getRoot();
        layoutManager = new LinearLayoutManager(context);
        binding.orgSpaceRecycler.setLayoutManager(layoutManager);
        recyclerFunc(layoutManager);



        return view;
    }

    private void AllSpacesorgList() {
        isLastPage=false;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<O_SpaceListResponse> orgSpace = retrofitinterface.getOrgSpaceList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, context),
                Constants.CONTENT_TYPE, currentPage,"");

        orgSpace.enqueue(new Callback<O_SpaceListResponse>() {
            @Override
            public void onResponse(Call<O_SpaceListResponse> call, Response<O_SpaceListResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful())
                    if (response.body().isStatus())
                        if (response.body() != null) {

                            orgSpaceList = response.body().getData().getData();
                            if (orgSpaceList != null && orgSpaceList.size() > 0) {
                                binding.allSpaceNoImage.setVisibility(View.GONE);

//                                orgSpaceAdapter = new AllSpaceOrgListAdapter(context, orgSpaceList);
//                                binding.orgSpaceRecycler.setAdapter(orgSpaceAdapter);

                                binding.orgSpaceRecycler.setVisibility(View.VISIBLE);
                                orgSpaceAdapter = new AllSpaceOrgListAdapter(getContext());
                                binding.orgSpaceRecycler.setLayoutManager(layoutManager);
                                binding.orgSpaceRecycler.setAdapter(orgSpaceAdapter);
                                List<O_SpaceDataModel> results = fetchResults(response);

                                TOTAL_PAGES = response.body().getData().getLast_page();
                                getItemPerPage = response.body().getData().getPer_page();

                                orgSpaceAdapter.addAll(results);
                                if (! TextUtils.isEmpty(currentPage)) {
                                    page = Integer.parseInt(currentPage);
                                }
                                if (page < TOTAL_PAGES)
                                    orgSpaceAdapter.addLoadingFooter();
                                else isLastPage = true;

                            } else {
                                binding.allSpaceNoImage.setVisibility(View.VISIBLE);
                            }

                        }
            }

            @Override
            public void onFailure(Call<O_SpaceListResponse> call, Throwable t) {
                progressDialog.dismiss();

            }
        });


    }
    private void AllSpacesorgListNextPage() {
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Loading....");
//        progressDialog.show();

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<O_SpaceListResponse> orgSpace = retrofitinterface.getOrgSpaceList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, context),
                Constants.CONTENT_TYPE, currentPage,"");

        orgSpace.enqueue(new Callback<O_SpaceListResponse>() {
            @Override
            public void onResponse(Call<O_SpaceListResponse> call, Response<O_SpaceListResponse> response) {
//                progressDialog.dismiss();

                if (response.isSuccessful())
                    if (response.body().isStatus())
                        if (response.body() != null) {

                            orgSpaceList = response.body().getData().getData();
                            if (orgSpaceList != null && orgSpaceList.size() > 0) {
                                binding.allSpaceNoImage.setVisibility(View.GONE);

//                                orgSpaceAdapter = new AllSpaceOrgListAdapter(context, orgSpaceList);
//                                binding.orgSpaceRecycler.setAdapter(orgSpaceAdapter);
                                binding.orgSpaceRecycler.setVisibility(View.VISIBLE);
                                orgSpaceAdapter.removeLoadingFooter();
                                isLoading = false;

                                List<O_SpaceDataModel> results = fetchResults(response);

                                TOTAL_PAGES = response.body().getData().getLast_page();
                                getItemPerPage = response.body().getData().getPer_page();

                                orgSpaceAdapter.addAll(results);
                                if (! TextUtils.isEmpty(currentPage)) {
                                    page = Integer.parseInt(currentPage);
                                }
                                if (page != TOTAL_PAGES)
                                    orgSpaceAdapter.addLoadingFooter();
                                else isLastPage = true;


                            } else {
                                binding.allSpaceNoImage.setVisibility(View.VISIBLE);
                            }

                        }
            }

            @Override
            public void onFailure(Call<O_SpaceListResponse> call, Throwable t) {
//                progressDialog.dismiss();

            }
        });


    }
    private List<O_SpaceDataModel> fetchResults(Response<O_SpaceListResponse> response) {
        O_SpaceListResponse topRatedMovies = response.body();
        return topRatedMovies.getData().getData();
    }
    private void recyclerFunc(LinearLayoutManager layoutManager) {
        binding.orgSpaceRecycler.addOnScrollListener(new PaginationScrollListener(layoutManager) {
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
                        AllSpacesorgListNextPage();

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
        AllSpacesorgList();
        super.onStart();
    }
}
