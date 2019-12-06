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
import com.netscape.utrain.adapters.AllSessionCoachistAdapter;
import com.netscape.utrain.adapters.AllSessionOrgListAdapter;
import com.netscape.utrain.adapters.AthleteTopRatedAdapter;
import com.netscape.utrain.databinding.AllSessionsFragmentBinding;
import com.netscape.utrain.model.C_SessionListModel;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.model.O_SessionDataModel;
import com.netscape.utrain.response.C_SessionListResponse;
import com.netscape.utrain.response.CoachListResponse;
import com.netscape.utrain.response.O_SessionListResponse;
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

public class AllSessionsFragment extends Fragment {
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    int page=0;
    //    private int currentPage = PAGE_START;
    private String currentPage="1";
    private int getItemPerPage;
    private AllSessionsFragmentBinding binding;
    private Retrofitinterface retrofitinterface;

    private List<O_SessionDataModel> coachSessionList = new ArrayList<>();
    private AllSessionCoachistAdapter coachSessionAdapter;
    private RecyclerView allSessionCoachListRecycler;
    private LinearLayoutManager layoutManager;

    private List<O_SessionDataModel> orgSessionList = new ArrayList<>();
    private AllSessionOrgListAdapter orgAdapter;

    private Context context;
    private ProgressDialog progressDialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.all_sessions_fragment, container, false);
        View view = binding.getRoot();
        layoutManager = new LinearLayoutManager(context);
        binding.allSessionCoachListRecycler.setLayoutManager(layoutManager);


        recyclerFunc(layoutManager);
        return view;
    }

    private void checkRoleHitApi() {

        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, context).equalsIgnoreCase(Constants.Coach)) {
            AllCoachSessionApi();
        } else {
            AllOrgSessionList();
        }

    }

    private void AllCoachSessionApi() {
        isLastPage=false;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<O_SessionListResponse> coachSession =
                retrofitinterface.getCoachSessions("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, context)
                        , Constants.CONTENT_TYPE, currentPage,"");
        coachSession.enqueue(new Callback<O_SessionListResponse>() {
            @Override
            public void onResponse(Call<O_SessionListResponse> call, Response<O_SessionListResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                    if (response.body().isStatus())
                        if (response.body() != null) {

                            coachSessionList = response.body().getData().getData();
                            if (coachSessionList != null && coachSessionList.size() > 0) {
//                                coachSessionAdapter = new AllSessionCoachistAdapter(context, coachSessionList);
//                                binding.allSessionCoachListRecycler.setAdapter(coachSessionAdapter);
                                binding.allSessionNoImage.setVisibility(View.GONE);

                                binding.allSessionCoachListRecycler.setVisibility(View.VISIBLE);
                                coachSessionAdapter = new AllSessionCoachistAdapter(getContext());
                                binding.allSessionCoachListRecycler.setAdapter(coachSessionAdapter);
                                List<O_SessionDataModel> results = fetchResults(response);

                                TOTAL_PAGES = response.body().getData().getLast_page();
                                getItemPerPage = response.body().getData().getPer_page();

                                coachSessionAdapter.addAll(results);
                                if (! TextUtils.isEmpty(currentPage)) {
                                    page = Integer.parseInt(currentPage);
                                }
                                if (page < TOTAL_PAGES)
                                    coachSessionAdapter.addLoadingFooter();
                                else isLastPage = true;


                            } else {
                                binding.allSessionNoImage.setVisibility(View.VISIBLE);
                            }

                        } else {
                            Toast.makeText(context, "" + response.body().getError().getError_message(), Toast.LENGTH_LONG).show();
                        }
            }

            @Override
            public void onFailure(Call<O_SessionListResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void AllCoachSessionApiNextPage() {
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Loading....");
//        progressDialog.show();

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<O_SessionListResponse> coachSession =
                retrofitinterface.getCoachSessions("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, context)
                        , Constants.CONTENT_TYPE, currentPage,"");
        coachSession.enqueue(new Callback<O_SessionListResponse>() {
            @Override
            public void onResponse(Call<O_SessionListResponse> call, Response<O_SessionListResponse> response) {
//                progressDialog.dismiss();
                if (response.isSuccessful())
                    if (response.body().isStatus())
                        if (response.body() != null) {

                            coachSessionList = response.body().getData().getData();
                            if (coachSessionList != null && coachSessionList.size() > 0) {
//                                coachSessionAdapter = new AllSessionCoachistAdapter(context, coachSessionList);
//                                binding.allSessionCoachListRecycler.setAdapter(coachSessionAdapter);

                                binding.allSessionCoachListRecycler.setVisibility(View.VISIBLE);
                                binding.allSessionNoImage.setVisibility(View.GONE);

                                coachSessionAdapter.removeLoadingFooter();
                                isLoading = false;

                                List<O_SessionDataModel> results = fetchResults(response);

                                TOTAL_PAGES = response.body().getData().getLast_page();
                                getItemPerPage =response.body().getData().getPer_page();

                                coachSessionAdapter.addAll(results);
                                if (! TextUtils.isEmpty(currentPage)) {
                                    page = Integer.parseInt(currentPage);
                                }
                                if (page != TOTAL_PAGES)
                                    coachSessionAdapter.addLoadingFooter();
                                else isLastPage = true;

                            } else {
                                binding.allSessionNoImage.setVisibility(View.VISIBLE);
                            }

                        } else {
                            Toast.makeText(context, "" + response.body().getError().getError_message(), Toast.LENGTH_LONG).show();
                        }
            }

            @Override
            public void onFailure(Call<O_SessionListResponse> call, Throwable t) {
//                progressDialog.dismiss();
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private List<O_SessionDataModel> fetchResults(Response<O_SessionListResponse> response) {
        O_SessionListResponse topRatedMovies = response.body();
        return topRatedMovies.getData().getData();
    }
    private void AllOrgSessionList() {
        isLastPage=false;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<O_SessionListResponse> orgSession = retrofitinterface.getOrgSessionList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, context)
                , Constants.CONTENT_TYPE,currentPage, "");
        orgSession.enqueue(new Callback<O_SessionListResponse>() {
            @Override
            public void onResponse(Call<O_SessionListResponse> call, Response<O_SessionListResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                    if (response.body().isStatus())
                        if (response.body() != null) {

                            orgSessionList = response.body().getData().getData();

                            if (orgSessionList != null && orgSessionList.size() > 0) {
                                binding.allSessionNoImage.setVisibility(View.GONE);
//                                orgAdapter = new AllSessionOrgListAdapter(context, orgSessionList);
//                                binding.allSessionCoachListRecycler.setAdapter(orgAdapter);


                                binding.allSessionCoachListRecycler.setVisibility(View.VISIBLE);
                                orgAdapter = new AllSessionOrgListAdapter(getContext());
                                binding.allSessionCoachListRecycler.setAdapter(orgAdapter);
                                List<O_SessionDataModel> results = fetchResults(response);

                                TOTAL_PAGES = response.body().getData().getLast_page();
                                getItemPerPage = response.body().getData().getPer_page();

                                orgAdapter.addAll(results);
                                if (! TextUtils.isEmpty(currentPage)) {
                                    page = Integer.parseInt(currentPage);
                                }
                                if (page < TOTAL_PAGES)
                                    orgAdapter.addLoadingFooter();
                                else isLastPage = true;



                            } else {
                                binding.allSessionNoImage.setVisibility(View.VISIBLE);
                            }


                        } else {
                            Toast.makeText(context, "" + response.body().getError().getError_message(), Toast.LENGTH_LONG).show();
                        }
            }

            @Override
            public void onFailure(Call<O_SessionListResponse> call, Throwable t) {
                progressDialog.dismiss();

            }
        });


    }
    private void AllOrgSessionListNextPage() {

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<O_SessionListResponse> orgSession = retrofitinterface.getOrgSessionList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, context)
                , Constants.CONTENT_TYPE,currentPage, "");
        orgSession.enqueue(new Callback<O_SessionListResponse>() {
            @Override
            public void onResponse(Call<O_SessionListResponse> call, Response<O_SessionListResponse> response) {
//                progressDialog.dismiss();
                if (response.isSuccessful())
                    if (response.body().isStatus())
                        if (response.body() != null) {

                            orgSessionList = response.body().getData().getData();

                            if (orgSessionList != null && orgSessionList.size() > 0) {
                                binding.allSessionNoImage.setVisibility(View.GONE);
//                                orgAdapter = new AllSessionOrgListAdapter(context, orgSessionList);
//                                binding.allSessionCoachListRecycler.setAdapter(orgAdapter);


                                binding.allSessionCoachListRecycler.setVisibility(View.VISIBLE);
                                binding.allSessionNoImage.setVisibility(View.GONE);

                                orgAdapter.removeLoadingFooter();
                                isLoading = false;

                                List<O_SessionDataModel> results = fetchResults(response);

                                TOTAL_PAGES = response.body().getData().getLast_page();
                                getItemPerPage =response.body().getData().getPer_page();

                                orgAdapter.addAll(results);
                                if (! TextUtils.isEmpty(currentPage)) {
                                    page = Integer.parseInt(currentPage);
                                }
                                if (page != TOTAL_PAGES)
                                    orgAdapter.addLoadingFooter();
                                else isLastPage = true;



                            } else {
                                binding.allSessionNoImage.setVisibility(View.VISIBLE);
                            }


                        } else {
                            Toast.makeText(context, "" + response.body().getError().getError_message(), Toast.LENGTH_LONG).show();
                        }
            }

            @Override
            public void onFailure(Call<O_SessionListResponse> call, Throwable t) {
//                progressDialog.dismiss();

            }
        });


    }
    private void recyclerFunc(LinearLayoutManager layoutManager) {
        binding.allSessionCoachListRecycler.addOnScrollListener(new PaginationScrollListener(layoutManager) {
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
                        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Coach))
                            AllCoachSessionApiNextPage();
                         if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Organizer))
                             AllOrgSessionListNextPage();
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
