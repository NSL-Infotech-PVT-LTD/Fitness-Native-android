package com.netscape.utrain.fragments;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.DiscoverTopRated;
import com.netscape.utrain.adapters.AthleteTopRatedAdapter;
import com.netscape.utrain.adapters.TransactionAdapter;
import com.netscape.utrain.databinding.PaymentReceiveFragmentBinding;
import com.netscape.utrain.databinding.PaymentSentFragmentBinding;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.model.O_AllBookingDataListModel;
import com.netscape.utrain.response.CoachListResponse;
import com.netscape.utrain.response.O_AllBookingResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PaginationScrollListener;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentReceiveFragment extends Fragment {
    private boolean isLoading = false;
    private boolean isLastPage = false;
    int page = 0;
    private int TOTAL_PAGES;
    private String currentPage = "1";
    private int getItemPerPage;

    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    private TransactionAdapter adapter;
    private LinearLayoutManager layoutManager;
    List<O_AllBookingDataListModel> orgEventList = new ArrayList<>();

    private PaymentReceiveFragmentBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.payment_receive_fragment,container,false);
        binding = DataBindingUtil.inflate(inflater,R.layout.payment_receive_fragment,container,false);
        View view = binding.getRoot();
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        retrofitinterface= RetrofitInstance.getClient().create(Retrofitinterface.class);
        layoutManager=new LinearLayoutManager(getContext());
        binding.payReceiveRecyclerView.setLayoutManager(layoutManager);
        recyclerFunc(layoutManager);
        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Organizer))
            getPaymentReceivedListOrg();
        else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Coach))
            getPaymentReceivedListCoach();
        return view;
    }
    private void getPaymentReceivedListOrg() {
        isLastPage=false;
        progressDialog.show();
        Call<O_AllBookingResponse> call = retrofitinterface.getAllBookingOrg("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, currentPage,getItemPerPage+"");
        call.enqueue(new Callback<O_AllBookingResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<O_AllBookingResponse> call, Response<O_AllBookingResponse> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        orgEventList = response.body().getData().getData();
                        if (orgEventList !=null && orgEventList.size()>0){
                            binding.receveNoPaymentHistory.setVisibility(View.GONE);
//                            adapter=new TransactionAdapter(getContext(),orgEventList);
//                            binding.payReceiveRecyclerView.setAdapter(adapter);

                            adapter = new TransactionAdapter(getContext());
                            binding.payReceiveRecyclerView.setAdapter(adapter);
                            List<O_AllBookingDataListModel> results = fetchResults(response);

                            TOTAL_PAGES = response.body().getData().getLast_page();
                            getItemPerPage = response.body().getData().getPer_page();

                            adapter.addAll(results);
                            if (! TextUtils.isEmpty(currentPage)) {
                                page = Integer.parseInt(currentPage);
                            }
                            if (page < TOTAL_PAGES)
                                adapter.addLoadingFooter();
                            else isLastPage = true;

                        }else {
                            binding.receveNoPaymentHistory.setVisibility(View.VISIBLE);
                            }
                        }else {
                        binding.receveNoPaymentHistory.setVisibility(View.VISIBLE);
                    }
                } else {
                    binding.receveNoPaymentHistory.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Toast.makeText(getContext(), "" + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<O_AllBookingResponse> call, Throwable t) {
                binding.receveNoPaymentHistory.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void getNextPagePaymentReceivedListOrg() {
//        progressDialog.show();
        Call<O_AllBookingResponse> call = retrofitinterface.getAllBookingOrg("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, currentPage,getItemPerPage+"");
        call.enqueue(new Callback<O_AllBookingResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<O_AllBookingResponse> call, Response<O_AllBookingResponse> response) {
                if (response.body() != null) {
//                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        orgEventList = response.body().getData().getData();
                        if (orgEventList !=null && orgEventList.size()>0){
                            binding.receveNoPaymentHistory.setVisibility(View.GONE);
//                            adapter=new TransactionAdapter(getContext(),orgEventList);
//                            binding.payReceiveRecyclerView.setAdapter(adapter);

                            adapter.removeLoadingFooter();
                            isLoading = false;

                            List<O_AllBookingDataListModel> results = fetchResults(response);

                            //TOTAL_PAGES = response.body().getData().getLast_page();
                            getItemPerPage = response.body().getData().getPer_page();

                            adapter.addAll(results);
                            if (! TextUtils.isEmpty(currentPage)) {
                                page = Integer.parseInt(currentPage);
                            }
                            if (page != TOTAL_PAGES)
                                adapter.addLoadingFooter();
                            else isLastPage = true;


                        }else {
                            binding.receveNoPaymentHistory.setVisibility(View.VISIBLE);
                        }
                    }else {
                        binding.receveNoPaymentHistory.setVisibility(View.VISIBLE);
                    }
                } else {
                    binding.receveNoPaymentHistory.setVisibility(View.VISIBLE);
//                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Toast.makeText(getContext(), "" + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<O_AllBookingResponse> call, Throwable t) {
                binding.receveNoPaymentHistory.setVisibility(View.VISIBLE);
//                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<O_AllBookingDataListModel> fetchResults(Response<O_AllBookingResponse> response) {
        O_AllBookingResponse topRatedMovies = response.body();
        return topRatedMovies.getData().getData();
    }
    private void getPaymentReceivedListCoach() {
        isLastPage=false;
        progressDialog.show();
        Call<O_AllBookingResponse> call = retrofitinterface.getAllBookingCoach("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, currentPage,getItemPerPage+"");
        call.enqueue(new Callback<O_AllBookingResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<O_AllBookingResponse> call, Response<O_AllBookingResponse> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        orgEventList = response.body().getData().getData();
                        if (orgEventList !=null && orgEventList.size()>0){
                            binding.receveNoPaymentHistory.setVisibility(View.GONE);
//                            adapter=new TransactionAdapter(getContext(),orgEventList);
//                            binding.payReceiveRecyclerView.setAdapter(adapter);

                            adapter = new TransactionAdapter(getContext());
                            binding.payReceiveRecyclerView.setAdapter(adapter);
                            List<O_AllBookingDataListModel> results = fetchResults(response);

                            TOTAL_PAGES = response.body().getData().getLast_page();
                            getItemPerPage = response.body().getData().getPer_page();

                            adapter.addAll(results);
                            if (! TextUtils.isEmpty(currentPage)) {
                                page = Integer.parseInt(currentPage);
                            }
                            if (page < TOTAL_PAGES)
                                adapter.addLoadingFooter();
                            else isLastPage = true;

                        }else {
                            binding.receveNoPaymentHistory.setVisibility(View.VISIBLE);
                        }
                    }else {
                        binding.receveNoPaymentHistory.setVisibility(View.VISIBLE);
                    }
                } else {
                    binding.receveNoPaymentHistory.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Toast.makeText(getContext(), "" + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<O_AllBookingResponse> call, Throwable t) {
                progressDialog.dismiss();
                binding.receveNoPaymentHistory.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void getNextPagePaymentReceivedListCoach() {
//        progressDialog.show();
        Call<O_AllBookingResponse> call = retrofitinterface.getAllBookingCoach("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, currentPage,getItemPerPage+"");
        call.enqueue(new Callback<O_AllBookingResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<O_AllBookingResponse> call, Response<O_AllBookingResponse> response) {
                if (response.body() != null) {
//                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        orgEventList = response.body().getData().getData();
                        if (orgEventList !=null && orgEventList.size()>0){
                            binding.receveNoPaymentHistory.setVisibility(View.GONE);
//                            adapter=new TransactionAdapter(getContext(),orgEventList);
//                            binding.payReceiveRecyclerView.setAdapter(adapter);


                            adapter.removeLoadingFooter();
                            isLoading = false;

                            List<O_AllBookingDataListModel> results = fetchResults(response);

                            //TOTAL_PAGES = response.body().getData().getLast_page();
                            getItemPerPage = response.body().getData().getPer_page();

                            adapter.addAll(results);
                            if (! TextUtils.isEmpty(currentPage)) {
                                page = Integer.parseInt(currentPage);
                            }
                            if (page != TOTAL_PAGES)
                                adapter.addLoadingFooter();
                            else isLastPage = true;

                        }else {
                            binding.receveNoPaymentHistory.setVisibility(View.VISIBLE);
                        }
                    }else {
                        binding.receveNoPaymentHistory.setVisibility(View.VISIBLE);
                    }
                } else {
                    binding.receveNoPaymentHistory.setVisibility(View.VISIBLE);
//                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Toast.makeText(getContext(), "" + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<O_AllBookingResponse> call, Throwable t) {
//                progressDialog.dismiss();
                binding.receveNoPaymentHistory.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void recyclerFunc(LinearLayoutManager layoutManager) {
        binding.payReceiveRecyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                if (!TextUtils.isEmpty(currentPage)) {
                    page = Integer.parseInt(currentPage);
                }
                page += 1;
                currentPage = page + "";

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Organizer))
                            getNextPagePaymentReceivedListOrg();
                        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Athlete))
                            getNextPagePaymentReceivedListCoach();
//                        if (getIntent().getStringExtra(Constants.TOP_TYPE_INTENT).equalsIgnoreCase(Constants.TOP_COACHES))
//                            nextCoachListApi();
//                        if (getIntent().getStringExtra(Constants.TOP_TYPE_INTENT).equalsIgnoreCase(Constants.TOP_ORG))
//                            nextGetTopOrgaNization();
//                        nextProductsAPI(orderbyValue, searchBy);
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
}
