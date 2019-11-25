package com.netscape.utrain.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
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
import com.netscape.utrain.adapters.AllSessionCoachistAdapter;
import com.netscape.utrain.adapters.AllSessionOrgListAdapter;
import com.netscape.utrain.databinding.AllSessionsFragmentBinding;
import com.netscape.utrain.model.C_SessionListModel;
import com.netscape.utrain.model.O_SessionDataModel;
import com.netscape.utrain.response.C_SessionListResponse;
import com.netscape.utrain.response.O_SessionListResponse;
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

public class AllSessionsFragment extends Fragment {

    private AllSessionsFragmentBinding binding;
    private Retrofitinterface retrofitinterface;

    private List<O_SessionDataModel> coachSessionList = new ArrayList<>();
    private AllSessionCoachistAdapter coachSessionAdapter;
    private RecyclerView allSessionCoachListRecycler;
    private RecyclerView.LayoutManager layoutManager;

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
        checkRoleHitApi();


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
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<O_SessionListResponse> coachSession =
                retrofitinterface.getCoachSessions("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, context)
                        , Constants.CONTENT_TYPE, "");
        coachSession.enqueue(new Callback<O_SessionListResponse>() {
            @Override
            public void onResponse(Call<O_SessionListResponse> call, Response<O_SessionListResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                    if (response.body().isStatus())
                        if (response.body() != null) {

                            coachSessionList = response.body().getData().getData();
                            if (coachSessionList != null && coachSessionList.size() > 0) {
                                coachSessionAdapter = new AllSessionCoachistAdapter(context, coachSessionList);
                                binding.allSessionCoachListRecycler.setAdapter(coachSessionAdapter);
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

    private void AllOrgSessionList() {

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<O_SessionListResponse> orgSession = retrofitinterface.getOrgSessionList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, context)
                , Constants.CONTENT_TYPE, "");
        orgSession.enqueue(new Callback<O_SessionListResponse>() {
            @Override
            public void onResponse(Call<O_SessionListResponse> call, Response<O_SessionListResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                    if (response.body().isStatus())
                        if (response.body() != null) {

                            orgSessionList = response.body().getData().getData();

                            if (orgSessionList != null && orgSessionList.size() > 0) {
                                orgAdapter = new AllSessionOrgListAdapter(context, orgSessionList);
                                binding.allSessionCoachListRecycler.setAdapter(orgAdapter);
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


}
