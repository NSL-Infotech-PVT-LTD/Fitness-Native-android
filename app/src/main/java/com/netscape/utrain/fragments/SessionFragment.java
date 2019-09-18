package com.netscape.utrain.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.HistoryActivity;
import com.netscape.utrain.adapters.SessionFragmentAdapter;
import com.netscape.utrain.adapters.SessionFragmentModel;

import java.util.ArrayList;
import java.util.List;

public class SessionFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    RecyclerView sessionRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<SessionFragmentModel> list = new ArrayList<>();


    public SessionFragment() {
        //Require empty public constructor.
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view =inflater.inflate(R.layout.session_fragment, container, false);
        viewPager = (ViewPager)view.findViewById(R.id.historyViewPager);
//        setUpViewPager(viewPager);

        sessionRecyclerView = (RecyclerView)view.findViewById(R.id.session_recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        sessionRecyclerView.setLayoutManager(layoutManager);
        SessionFragmentAdapter sessionFragmentAdapter = new SessionFragmentAdapter(getActivity(),list);
        sessionRecyclerView.setAdapter(sessionFragmentAdapter);

        tabLayout = (TabLayout)view.findViewById(R.id.historyTabLayout);
//        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorGreen));
//        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
//        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#000000"));
//        tabLayout.setupWithViewPager(viewPager);

        return view;

    }

//    private void setUpViewPager (ViewPager viewPager)
//    {
//        ViewpagerAdapter adapter = new ViewpagerAdapter(getFragmentManager(),getContext());
//        adapter.addFragment(new SessionFragment(),"Session");
//        adapter.addFragment(new PaymentFragment(),"Payment");
//        adapter.addFragment(new ReviewFragment(),"Review");
//        viewPager.setAdapter(adapter);
//
//    }


    class ViewpagerAdapter extends FragmentPagerAdapter {

        Context context;
//        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewpagerAdapter(@NonNull FragmentManager fm, Context context)
        {
            super(fm);
            this.context = context;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            if (position == 0)
                return new SessionFragment();
            else if (position == 1)
                return new PaymentFragment();
            else if (position == 2)
                return new ReviewFragment();
            else
                return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

//        public void addFragment(Fragment fragment, String Title)
//        {
//            mFragmentList.add(fragment);
//            mFragmentTitleList.add(Title);
//
//        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position)
        {
            switch (position) {
                case 0:
                    return context.getString(R.string.session_fragment);
                case 1:
                    return context.getString(R.string.payment_fragment);
                case 2:
                    return context.getString(R.string.review_fragment);
                    default:
                        return null;
            }
        }
    }

}
