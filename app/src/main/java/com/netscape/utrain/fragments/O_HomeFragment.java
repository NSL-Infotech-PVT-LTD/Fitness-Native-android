package com.netscape.utrain.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.CreateEventActivity;
import com.netscape.utrain.activities.CreateTrainingSession;
import com.netscape.utrain.activities.LoginActivity;
import com.netscape.utrain.activities.OfferSpaceActivity;
import com.netscape.utrain.activities.PortfolioActivity;
import com.netscape.utrain.activities.SignUpTypeActivity;
import com.netscape.utrain.activities.athlete.AllEventsMapAct;
import com.netscape.utrain.adapters.Ath_PlaceRecyclerAdapter;
import com.netscape.utrain.databinding.OrgFragmentHomeBinding;
import com.netscape.utrain.model.AthletePlaceModel;
import com.netscape.utrain.response.AthletePlaceResponse;
import com.netscape.utrain.response.AthletePlaceResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.netscape.utrain.activities.PortfolioActivity.clearFromConstants;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link O_HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link O_HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class O_HomeFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private OrgFragmentHomeBinding binding;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    //    private MaterialButton orglogOutTv;
    private View view;
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    private List<AthletePlaceModel> listModels = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private Ath_PlaceRecyclerAdapter adapter;
    private Context context;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public O_HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment A_HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static O_HomeFragment newInstance(String param1, String param2) {
        O_HomeFragment fragment = new O_HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.org_fragment_home, container, false);
        view = binding.getRoot();
        binding.orgWelcomeOrgName.setText("Welcome " + CommonMethods.getPrefData(PrefrenceConstant.USER_NAME, getContext()));

//        orglogOutTv = (MaterialButton) view.findViewById(R.id.orglogOutTv);
        progressDialog = new ProgressDialog(getContext());
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        binding.orgSpaceRecyclerView.setLayoutManager(layoutManager);

        getSpaceList();
        String path = CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, context);
//        Glide.with(context).load(Constants.ORG_IMAGE_BASE_URL + path).into(binding.orgProfileImage); // working code line to display image
//        Glide.with(context).load(path).into(binding.orgProfileImage);
        binding.createEventImg.setOnClickListener(this);
        binding.createSessionImg.setOnClickListener(this);
        binding.createSpaceImg.setOnClickListener(this);
        binding.orgViewAllSpaces.setOnClickListener(this);
//        binding.orglogOutTv.setOnClickListener(this);

//        String experience = (CommonMethods.getPrefData(PrefrenceConstant.USER_EXPERIENCE,context));
//        if (experience != null){
//            binding.expTv.setText(experience);
//        } else {
//            binding.expTv.setVisibility(View.GONE);
//            binding.bioText.setVisibility(View.GONE);
//        }
        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.orglogOutTv:
//                LoginManager.getInstance().logOut();
//                CommonMethods.clearPrefData(getContext());
//                Intent intent = new Intent(getActivity(), SignUpTypeActivity.class);
//                view.getContext().startActivity(intent);
//                getActivity().finish();
//                break;
            case R.id.createEventImg:
                PortfolioActivity.clearFromConstants();
                Intent createEvent = new Intent(getActivity(), CreateEventActivity.class);
                view.getContext().startActivity(createEvent);
                break;
            case R.id.createSessionImg:
                PortfolioActivity.clearFromConstants();
                Intent createSession = new Intent(getActivity(), CreateTrainingSession.class);
                view.getContext().startActivity(createSession);
                break;
            case R.id.createSpaceImg:
                PortfolioActivity.clearFromConstants();
                Intent createSpace = new Intent(getActivity(), OfferSpaceActivity.class);
                view.getContext().startActivity(createSpace);
                break;
            case R.id.orgViewAllSpaces:
                Intent viewAll = new Intent(getContext(), AllEventsMapAct.class);
                viewAll.putExtra("from", "3");
                getContext().startActivity(viewAll);
                break;
        }
    }

    private void getSpaceList() {
        progressDialog.show();
        Call<AthletePlaceResponse> signUpAthlete = retrofitinterface.getAthletePlacesList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, "", "5", "price_low", "");
        signUpAthlete.enqueue(new Callback<AthletePlaceResponse>() {
            @Override
            public void onResponse(Call<AthletePlaceResponse> call, Response<AthletePlaceResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            listModels.clear();
                            listModels.addAll(response.body().getData().getData());
                            if (listModels != null && listModels.size() > 0) {
                                binding.noSpaceOrgImg.setVisibility(View.GONE);
                                binding.orgViewAllSpaces.setVisibility(View.VISIBLE);
                                adapter = new Ath_PlaceRecyclerAdapter(getContext(), listModels);
                                binding.orgSpaceRecyclerView.setAdapter(adapter);
                            } else {
                                binding.noSpaceOrgImg.setVisibility(View.VISIBLE);
                                binding.orgViewAllSpaces.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        Snackbar.make(binding.orgHomeLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                        binding.noSpaceOrgImg.setVisibility(View.VISIBLE);
                        binding.orgViewAllSpaces.setVisibility(View.GONE);

                    }
                } else {
                    binding.noSpaceOrgImg.setVisibility(View.VISIBLE);
                    binding.orgViewAllSpaces.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.orgHomeLayout, errorMessage.toString(), BaseTransientBottomBar.LENGTH_LONG).show();

                    } catch (Exception e) {


                        Snackbar.make(binding.orgHomeLayout, e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<AthletePlaceResponse> call, Throwable t) {
                binding.noSpaceOrgImg.setVisibility(View.VISIBLE);
                binding.orgViewAllSpaces.setVisibility(View.GONE);

                progressDialog.dismiss();
                Snackbar.make(binding.orgHomeLayout, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();

            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }
    }
}
