package com.netscape.utrain.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.netscape.utrain.activities.athlete.AllEventsMapAct;
import com.netscape.utrain.adapters.Ath_PlaceRecyclerAdapter;
import com.netscape.utrain.databinding.AthletePlaceFragmentViewBinding;
import com.netscape.utrain.model.AthletePlaceModel;
import com.netscape.utrain.response.AthletePlaceResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class A_SpacesFragment extends Fragment {
    private View view;
    private AthletePlaceFragmentViewBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private Ath_PlaceRecyclerAdapter adapter;
    //    private List<String> data=new ArrayList<>();
    private Retrofitinterface api;
    private List<AthletePlaceModel> listModels = new ArrayList<>();

    public A_SpacesFragment() {
        // required empty constructor....
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.athlete_place_fragment_view, container, false);
        view = binding.getRoot();

//        adapter=new CoachesRecyclerAdapter(getContext(),data);
        layoutManager = new LinearLayoutManager(getContext());
        binding.athletePlaceRecycler.setLayoutManager(layoutManager);
        binding.placeViewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AllEventsMapAct.class);
                intent.putExtra("from","3");
                getContext().startActivity(intent);

            }
        });
        getAthleteEventApi();
        return view;
    }

    private void getAthleteEventApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        api = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<AthletePlaceResponse> call = api.getAthletePlacesList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, "","10","latest");
        call.enqueue(new Callback<AthletePlaceResponse>() {
            @Override
            public void onResponse(Call<AthletePlaceResponse> call, Response<AthletePlaceResponse> response) {

                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        listModels.clear();
                        listModels.addAll(response.body().getData().getData());
                        adapter = new Ath_PlaceRecyclerAdapter(getContext(), listModels);
                        binding.athletePlaceRecycler.setAdapter(adapter);

                    }
                }


            }

            @Override
            public void onFailure(Call<AthletePlaceResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
