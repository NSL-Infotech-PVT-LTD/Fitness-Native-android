package com.netscape.utrain.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.netscape.utrain.R;
import com.netscape.utrain.utils.TabLayoutEx;

import java.util.ArrayList;
import java.util.List;

public class CompleteFragment extends Fragment {

    TabLayout tab;
    ViewPagerAdapter adapter;
    ViewPager bookingViewPager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.completed_fragment,container,false);
        tab = view.findViewById(R.id.bookingTab);
        bookingViewPager = view.findViewById(R.id.bookingViewPager);
        tab.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorGreen));
        setUpViewpager(bookingViewPager);
        tab.setupWithViewPager(bookingViewPager);


        return view;
    }

    private void setUpViewpager(ViewPager viewpager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new CompleteFragment(),"Completed");
        adapter.addFragment(new UpcomingFragment(),"Upcoming");
        adapter.addFragment(new CancelledFragment(),"Cancelled");
        viewpager.setAdapter(adapter);

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
