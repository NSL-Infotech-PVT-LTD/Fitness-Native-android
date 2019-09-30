package com.netscape.utrain.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.material.tabs.TabLayout;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.SignUpTypeActivity;
import com.netscape.utrain.adapters.TopCoachesAdapter;
import com.netscape.utrain.adapters.TopOrganizationAdapter;
import com.netscape.utrain.utils.CommonMethods;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A_HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A_HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A_HomeFragment extends Fragment implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView logOut;

    private RecyclerView topCoachesRecycler, topOrgRecycler;
    private RecyclerView.LayoutManager topCoachesLayoutManager, topOrgLayoutManager;
    private TopCoachesAdapter adapter;
    private TopOrganizationAdapter orgAdapter;
    private List<String> data = new ArrayList<>();
    private List<String> orgList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

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
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.athlete_fragment_home, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        logOut = (TextView) view.findViewById(R.id.logOutTv);
        setupViewPager(viewPager);


        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorGreen));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#000000"));
        tabLayout.setupWithViewPager(viewPager);
        logOut.setOnClickListener(this);

        topCoachesRecycler = view.findViewById(R.id.athleteTopCoachesRecycler);
        topCoachesLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);

        topCoachesRecycler.setLayoutManager(topCoachesLayoutManager);
        data.add("Coach1");
        data.add("Coach2");
        data.add("Coach3");
        data.add("Coach4");
        data.add("Coach5");
        data.add("Coach6");
        data.add("Coach7");
        data.add("Coach8");
        data.add("Coach9");
        data.add("Coach10");

        adapter = new TopCoachesAdapter(getContext(), data);
        topCoachesRecycler.setAdapter(adapter);

        topOrgRecycler = view.findViewById(R.id.athleteTopOrganizationRecycler);
        topOrgLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        topOrgRecycler.setLayoutManager(topOrgLayoutManager);

        orgList.add("Athlete1");
        orgList.add("Athlete1");


        orgAdapter = new TopOrganizationAdapter(getContext(),orgList);
        topOrgRecycler.setAdapter(orgAdapter);



        return view;

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new CoachesFragment(), "Events");
        adapter.addFragment(new OrganisationFragment(), "Sessions");
        adapter.addFragment(new PlacesFragment(),"Places");

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
        switch (view.getId()){
            case R.id.logOutTv:
                LoginManager.getInstance().logOut();
                CommonMethods.clearPrefData(getContext());
                Intent intent = new Intent(getActivity(), SignUpTypeActivity.class);
                view.getContext().startActivity(intent);
                getActivity().finish();
                break;
        }
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
