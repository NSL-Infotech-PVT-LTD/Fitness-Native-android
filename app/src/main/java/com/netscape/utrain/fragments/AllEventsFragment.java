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
import com.netscape.utrain.adapters.AllEventsListAdapter;
import com.netscape.utrain.databinding.AllEventsFragmentBinding;
import com.netscape.utrain.model.C_EventDataListModel;
import com.netscape.utrain.response.C_EventListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllEventsFragment extends Fragment {


    private Retrofitinterface retrofitinterface;
    private AllEventsFragmentBinding binding;
    private Context context;
    private ProgressDialog progressDialog;
    private List<C_EventDataListModel> eventList = new ArrayList<>();
    private RecyclerView allEventListRecycler;
    private RecyclerView.LayoutManager layoutManager;
    private AllEventsListAdapter eventAdapter;

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
        AllEventsCreatedApi();


        return view;
    }

    public void AllEventsCreatedApi() {

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<C_EventListResponse> callEventCoachList =
                retrofitinterface.getCoachEventList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, context), Constants.CONTENT_TYPE, "");

        callEventCoachList.enqueue(new Callback<C_EventListResponse>() {
            @Override
            public void onResponse(Call<C_EventListResponse> call, Response<C_EventListResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().isStatus())
                        if (response.body() != null) {
                            eventList = response.body().getData().getData();
                            eventAdapter = new AllEventsListAdapter(context, eventList);
                            binding.allEventListRecycler.setAdapter(eventAdapter);
                        }
                } else {
                    Toast.makeText(context, "" + response.body().getError().getError_message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<C_EventListResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
