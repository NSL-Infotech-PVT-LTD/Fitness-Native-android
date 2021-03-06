package com.netscape.utrain.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AllEventsMapAct;
import com.netscape.utrain.activities.athlete.DiscoverTopRated;
import com.netscape.utrain.activities.SignUpTypeActivity;
import com.netscape.utrain.adapters.TopCoachesAdapter;
import com.netscape.utrain.adapters.TopOrganizationAdapter;
import com.netscape.utrain.model.AthleteSignUpModel;
import com.netscape.utrain.model.AthleteUserModel;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.model.ServiceIdModel;
import com.netscape.utrain.model.SportListModel;
import com.netscape.utrain.response.CoachListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;
import com.netscape.utrain.utils.TabLayoutEx;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A_HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A_HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A_HomeFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TopCoachesAdapter coachAdapter;
    String sExperience, sAchievement;
    private TabLayoutEx tabLayout;
    private ViewPager viewPager;
    private TextView logOut;
    private RecyclerView topCoachesRecycler, topOrgRecycler;
    private RecyclerView.LayoutManager topCoachesLayoutManager, topOrgLayoutManager;
    private TopCoachesAdapter adapter;
    private TopOrganizationAdapter orgAdapter;
    private List<CoachListModel> data = new ArrayList<>();
    private List<CoachListModel> orgList = new ArrayList<>();
    private MaterialTextView btnTopCoaches, athleteNameTvDBoard, aExpDetailTv, aAchieveDetailTv, aSportsNameTv, aSportsText, aExperienceDBoard, aAchieveDBoard, athleteTopCoachesTv, topCoachesViewAllBtn;
    private MaterialTextView btnTopOrganization, athleteTopOrganizationTv, topOrgViewAllBtn;
    private Retrofitinterface api;
    private List<CoachListModel> coachList = new ArrayList<>();
    private Context context;
    private AppCompatImageView sessionIconImg, eventIconImg, findSpacesIconImg, aProfileImgDBoard, cViewAllDImage, oViewAllDImage,essDImg;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private ArrayList<SportListModel.DataBeanX.DataBean> sportList = new ArrayList<>();

    public A_HomeFragment() {
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
    public static A_HomeFragment newInstance(String param1, String param2) {
        A_HomeFragment fragment = new A_HomeFragment();
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

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        if (tabStrip != null) {
            tabStrip.setAccessible(true);

            LinearLayout llTab = null;
            try {
                llTab = (LinearLayout) tabStrip.get(tabs);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
            int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

            for (int i = 0; i < llTab.getChildCount(); i++) {
                View child = llTab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params.leftMargin = left;
                params.rightMargin = right;
                child.setLayoutParams(params);
                child.invalidate();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.athlete_fragment_home, container, false);

        viewPager = view.findViewById(R.id.viewpager);
        logOut = view.findViewById(R.id.logOutTv);
        setupViewPager(viewPager);
        btnTopCoaches = view.findViewById(R.id.topCoachesViewAllBtn);
        sessionIconImg = view.findViewById(R.id.sessionIconImg);
        btnTopOrganization = view.findViewById(R.id.topOrgViewAllBtn);
        eventIconImg = view.findViewById(R.id.eventIconImg);
        findSpacesIconImg = view.findViewById(R.id.findSpacesIconImg);
        aExpDetailTv = view.findViewById(R.id.aExpDetailTv);
        aAchieveDetailTv = view.findViewById(R.id.aAchieveDetailTv);
        aSportsNameTv = view.findViewById(R.id.aSportsNameTv);
        aProfileImgDBoard = view.findViewById(R.id.aProfileImgDBoard);
        aSportsText = view.findViewById(R.id.aSportsText);
        aExperienceDBoard = view.findViewById(R.id.aExperienceDBoard);
        aAchieveDBoard = view.findViewById(R.id.aAchieveDBoard);
        athleteTopCoachesTv = view.findViewById(R.id.athleteTopCoachesTv);
        topCoachesViewAllBtn = view.findViewById(R.id.topCoachesViewAllBtn);
        cViewAllDImage = view.findViewById(R.id.cViewAllDImage);
        athleteTopOrganizationTv = view.findViewById(R.id.athleteTopOrganizationTv);
        topOrgViewAllBtn = view.findViewById(R.id.topOrgViewAllBtn);
        oViewAllDImage = view.findViewById(R.id.oViewAllDImage);
        essDImg = view.findViewById(R.id.essDImg);

        sExperience = (CommonMethods.getPrefData(PrefrenceConstant.USER_EXPERIENCE, context));
        sAchievement = (CommonMethods.getPrefData(PrefrenceConstant.USER_ACHIEVE, context));


        if (sExperience == null) {
            aExperienceDBoard.setVisibility(View.GONE);
            aExpDetailTv.setVisibility(View.GONE);
        } else {
            aExpDetailTv.setVisibility(View.VISIBLE);
            aExperienceDBoard.setVisibility(View.VISIBLE);
            aExpDetailTv.setText(sExperience);


        }
        if (sAchievement == null) {
            aAchieveDBoard.setVisibility(View.GONE);
            aAchieveDetailTv.setVisibility(View.GONE);
        } else {

            aAchieveDBoard.setVisibility(View.VISIBLE);
            aAchieveDetailTv.setVisibility(View.VISIBLE);
            aAchieveDetailTv.setText(sAchievement);


        }

        getSportsIds();
        Glide.with(context).load(Constants.IMAGE_BASE_URL + CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, context)).into(aProfileImgDBoard);
        athleteNameTvDBoard = view.findViewById(R.id.athleteNameTvDBoard);
        athleteNameTvDBoard.setText(getResources().getString(R.string.welcome) + " " + CommonMethods.getPrefData(PrefrenceConstant.USER_NAME, context));


        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorGreen));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#000000"));
        tabLayout.setupWithViewPager(viewPager);
        topCoachesRecycler = view.findViewById(R.id.athleteTopCoachesRecycler);
        topOrgRecycler = view.findViewById(R.id.athleteTopOrganizationRecycler);
        topCoachesLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        topOrgLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        btnTopCoaches.setOnClickListener(this);
        btnTopOrganization.setOnClickListener(this);
        sessionIconImg.setOnClickListener(this);
        eventIconImg.setOnClickListener(this);
        findSpacesIconImg.setOnClickListener(this);

        getCoachListApi();
        getTopOrgaNization();
        tabLayout.setTabIndicatorFullWidth(false);
//        wrapTabIndicatorToTitle(tabLayout,100,20);
//        topCoachesRecycler.setLayoutManager(topCoachesLayoutManager);
//        data.add("Coach1");
//        data.add("Coach2");
//        data.add("Coach3");
//        data.add("Coach4");
//        data.add("Coach5");
//        data.add("Coach6");
//        data.add("Coach7");
//        data.add("Coach8");
//        data.add("Coach9");
//        data.add("Coach10");
//
//        adapter = new TopCoachesAdapter(getContext(), data);
//        topCoachesRecycler.setAdapter(adapter);
//
//        topOrgRecycler = view.findViewById(R.id.athleteTopOrganizationRecycler);
//        topOrgLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
//        topOrgRecycler.setLayoutManager(topOrgLayoutManager);
//
//        orgList.add("Athlete1");
//        orgList.add("Athlete1");
//
//
//        orgAdapter = new TopOrganizationAdapter(getContext(),orgList);
//        topOrgRecycler.setAdapter(orgAdapter);
//


        return view;

    }

    private void getSportsIds() {
        String sportName = CommonMethods.getPrefData(PrefrenceConstant.SPORTS_NAME, getApplicationContext());
        Gson gson = new Gson();
        if (sportName != null) {
            if (sportName.isEmpty()) {
                Toast.makeText(context, "Service Not Found", Toast.LENGTH_SHORT).show();
            } else {
                Type type = new TypeToken<List<SportListModel.DataBeanX.DataBean>>() {
                }.getType();
                sportList = gson.fromJson(sportName, type);

                StringBuilder builder = new StringBuilder();
                int i=1;
                for (SportListModel.DataBeanX.DataBean details : sportList) {
                    if (i< sportList.size()){
                        builder.append(details.getName() + ",");
                        i=i+1;
                    }
                    else if (i == sportList.size()){
                        builder.append(details.getName());
                        i=i+1;
                    }
                }
                aSportsNameTv.setText(builder.toString());
            }
        } else {
            aSportsText.setVisibility(View.GONE);
            aSportsNameTv.setVisibility(View.GONE);
        }
    }

    private void getCoachListApi() {
        api = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<CoachListResponse> call = api.getCoachList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, context), "", "5", "", "latest");
        call.enqueue(new Callback<CoachListResponse>() {
            @Override
            public void onResponse(Call<CoachListResponse> call, Response<CoachListResponse> response) {
                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        coachAdapter = new TopCoachesAdapter(getActivity(), response.body().getData().getData());
                        topCoachesRecycler.setLayoutManager(topCoachesLayoutManager);
                        topCoachesRecycler.setAdapter(coachAdapter);
                        athleteTopCoachesTv.setVisibility(View.VISIBLE);
                        topCoachesViewAllBtn.setVisibility(View.VISIBLE);
                        cViewAllDImage.setVisibility(View.VISIBLE);
                        essDImg.setVisibility(View.VISIBLE);

                    }
                } else {

                    try {

                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Toast.makeText(context, "" + errorMessage, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {

                    }

                }
            }

            @Override
            public void onFailure(Call<CoachListResponse> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTopOrgaNization() {
        api = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<CoachListResponse> call = api.getTopOrgList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, context), "", "5", "");
        call.enqueue(new Callback<CoachListResponse>() {
            @Override
            public void onResponse(Call<CoachListResponse> call, Response<CoachListResponse> response) {
                if (response.body() != null) {
                    if (response.body().isStatus()) {
                        orgAdapter = new TopOrganizationAdapter(getActivity(), response.body().getData().getData());
                        topOrgRecycler.setLayoutManager(topOrgLayoutManager);
                        topOrgRecycler.setAdapter(orgAdapter);
                        athleteTopOrganizationTv.setVisibility(View.VISIBLE);
                        topOrgViewAllBtn.setVisibility(View.VISIBLE);
                        oViewAllDImage.setVisibility(View.VISIBLE);
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Toast.makeText(context, "" + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<CoachListResponse> call, Throwable t) {

                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Ath_EvntsFragment(), getResources().getString(R.string.events));
        adapter.addFragment(new A_SessionsFragment(), getResources().getString(R.string.sessions));
        adapter.addFragment(new A_SpacesFragment(), getResources().getString(R.string.spaces));

        viewPager.setAdapter(adapter);
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
            case R.id.logOutTv:


                break;
            case R.id.topCoachesViewAllBtn:
                Intent topCoahces = new Intent(getContext(), DiscoverTopRated.class);
                topCoahces.putExtra(Constants.TOP_TYPE_INTENT, Constants.TOP_COACHES);
                startActivity(topCoahces);
                break;
            case R.id.topOrgViewAllBtn:
                Intent topOrg = new Intent(getContext(), DiscoverTopRated.class);
                topOrg.putExtra(Constants.TOP_TYPE_INTENT, Constants.TOP_ORG);
                startActivity(topOrg);
                break;

            case R.id.sessionIconImg:
                Intent intents = new Intent(getContext(), AllEventsMapAct.class);
                intents.putExtra("from", "2");
                getContext().startActivity(intents);
                break;
            case R.id.eventIconImg:
                Intent intentss = new Intent(getContext(), AllEventsMapAct.class);
                intentss.putExtra("from", "1");
                getContext().startActivity(intentss);
                break;
            case R.id.findSpacesIconImg:
                Intent intentsss = new Intent(getContext(), AllEventsMapAct.class);
                intentsss.putExtra("from", "3");
                getContext().startActivity(intentsss);
                break;
        }
    }

    public PopupWindow popupDisplay() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View view = inflater.inflate(R.layout.drawer_layout, null);
        final PopupWindow popupWindow = new PopupWindow(view, (int) context.getResources().getDimension(R.dimen._200sdp), ConstraintLayout.LayoutParams.MATCH_PARENT, true);
// display the popup in the center
        popupWindow.showAtLocation(view, Gravity.START, 0, 0);

        MaterialTextView logout = view.findViewById(R.id.logOutTv);
        MaterialTextView naviNameTv = view.findViewById(R.id.naviNameTv);
//        MaterialButton naviNameTv = view.findViewById(R.id.naviNameTv);
        CircleImageView naviProfileImage = view.findViewById(R.id.naviProfileImage);

//        Glide.with(context).load(Constants.IMAGE_BASE_URL + CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, context)).into(naviProfileImage);
//
//        naviNameTv.setText(CommonMethods.getPrefData(PrefrenceConstant.USER_NAME, context));
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Are you sure you want to Logout")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LoginManager.getInstance().logOut();
                                CommonMethods.clearPrefData(getContext());
                                Intent intent = new Intent(getActivity(), SignUpTypeActivity.class);
                                context.startActivity(intent);
                                getActivity().finish();

                            }

                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }

                        })
                        .show();
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                popupWindow.dismiss();
            }
        });
//
        return popupWindow;
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
