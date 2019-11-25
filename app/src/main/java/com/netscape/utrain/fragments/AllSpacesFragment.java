package com.netscape.utrain.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
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
import com.netscape.utrain.adapters.AllSpaceOrgListAdapter;
import com.netscape.utrain.databinding.AllSessionsFragmentBinding;
import com.netscape.utrain.databinding.AllSpacesFragmentBinding;
import com.netscape.utrain.model.O_SpaceDataModel;
import com.netscape.utrain.response.O_SpaceListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllSpacesFragment extends Fragment {

    private AllSpacesFragmentBinding binding;
    private Retrofitinterface retrofitinterface;
    private List<O_SpaceDataModel> orgSpaceList = new ArrayList<>();
    private AllSpaceOrgListAdapter orgSpaceAdapter;
    private RecyclerView orgSpaceRecycler;
    private RecyclerView.LayoutManager layoutManager;
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
        AllSpacesorgList();


        return view;
    }

    private void AllSpacesorgList() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<O_SpaceListResponse> orgSpace = retrofitinterface.getOrgSpaceList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, context),
                Constants.CONTENT_TYPE, "");

        orgSpace.enqueue(new Callback<O_SpaceListResponse>() {
            @Override
            public void onResponse(Call<O_SpaceListResponse> call, Response<O_SpaceListResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful())
                    if (response.body().isStatus())
                        if (response.body() != null) {

                            orgSpaceList = response.body().getData().getData();
                            if (orgSpaceList != null && orgSpaceList.size() > 0) {
                                orgSpaceAdapter = new AllSpaceOrgListAdapter(context, orgSpaceList);
                                binding.orgSpaceRecycler.setAdapter(orgSpaceAdapter);

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
}
