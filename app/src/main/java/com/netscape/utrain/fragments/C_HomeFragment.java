package com.netscape.utrain.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.CreateEventActivity;
import com.netscape.utrain.activities.CreateTrainingSession;
import com.netscape.utrain.activities.OfferSpaceActivity;
import com.netscape.utrain.activities.athlete.AllEventsMapAct;
import com.netscape.utrain.adapters.Ath_PlaceRecyclerAdapter;
import com.netscape.utrain.adapters.TopCoachesAdapter;
import com.netscape.utrain.adapters.TopOrganizationAdapter;
import com.netscape.utrain.databinding.CFragmentHomeBinding;
import com.netscape.utrain.model.AthletePlaceModel;
import com.netscape.utrain.response.AthletePlaceResponse;
import com.netscape.utrain.response.CoachListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;
import com.netscape.utrain.utils.TabLayoutEx;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class C_HomeFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private View view;
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    private List<AthletePlaceModel> listModels = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private Ath_PlaceRecyclerAdapter adapter;
    private CFragmentHomeBinding binding;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public C_HomeFragment() {


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.c_fragment_home, container, false);
        View view = binding.getRoot();
        progressDialog=new ProgressDialog(getContext());
        retrofitinterface=RetrofitInstance.getClient().create(Retrofitinterface.class);
        layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        binding.coachSpaceRecyclerView.setLayoutManager(layoutManager);
        getSpaceList();
        Glide.with(context).load(CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE,context)).into(binding.cDashProImage);
        binding.orgWelcomeOrgName.setText("Welcome "+CommonMethods.getPrefData(PrefrenceConstant.USER_NAME,context));
        binding.createEventImg.setOnClickListener(this);
        binding.createSessionImg.setOnClickListener(this);
        binding.createSpaceImg.setOnClickListener(this);
        binding.orgViewAllSpaces.setOnClickListener(this);
//        binding.orglogOutTv.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.orglogOutTv:
//                LoginManager.getInstance().logOut();
//                CommonMethods.clearPrefData(getContext());
//                Intent intent = new Intent(getActivity(), SignUpTypeActivity.class);
//                view.getContext().startActivity(intent);
//                getActivity().finish();
//                break;
            case R.id.createEventImg:
                Intent createEvent = new Intent(getActivity(), CreateEventActivity.class);
                view.getContext().startActivity(createEvent);
                break;
            case R.id.createSessionImg:
                Intent createSession = new Intent(getActivity(), CreateTrainingSession.class);
                view.getContext().startActivity(createSession);
                break;
            case R.id.createSpaceImg:
                Intent createSpace = new Intent(getActivity(), OfferSpaceActivity.class);
                view.getContext().startActivity(createSpace);
                break;
            case R.id.orgViewAllSpaces:
                Intent viewAll = new Intent(getContext(), AllEventsMapAct.class);
                viewAll.putExtra("from","3");
                getContext().startActivity(viewAll);
                break;
        }
    }
    private void getSpaceList() {
        progressDialog.show();
        Call<AthletePlaceResponse> signUpAthlete = retrofitinterface.getAthletePlacesList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, "","5","price_low");
        signUpAthlete.enqueue(new Callback<AthletePlaceResponse>() {
            @Override
            public void onResponse(Call<AthletePlaceResponse> call, Response<AthletePlaceResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            listModels.clear();
                            listModels.addAll(response.body().getData().getData());
                            if (listModels!=null && listModels.size()>0) {
                                binding.noDataFoundImg.setVisibility(View.GONE);
                                adapter = new Ath_PlaceRecyclerAdapter(getContext(), listModels);
                                binding.coachSpaceRecyclerView.setAdapter(adapter);
                            }else {
                                binding.noDataFoundImg.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        Snackbar.make(binding.orgHomeLayout,response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.orgHomeLayout,errorMessage.toString(), BaseTransientBottomBar.LENGTH_LONG).show();

                    } catch (Exception e) {
                        binding.noDataFoundImg.setVisibility(View.VISIBLE);
                        Snackbar.make(binding.orgHomeLayout,e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<AthletePlaceResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(binding.orgHomeLayout,getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();

            }
        });
    }



}
