package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.netscape.utrain.R;
import com.netscape.utrain.adapters.ViewPagerAdapter;
import com.netscape.utrain.databinding.ActivityHistoryBinding;
import com.netscape.utrain.fragments.AllEventsFragment;
import com.netscape.utrain.fragments.AllSessionsFragment;
import com.netscape.utrain.fragments.AllSpacesFragment;
import com.netscape.utrain.fragments.PaymentReceiveFragment;
import com.netscape.utrain.fragments.PaymentSentFragment;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import java.util.ArrayList;
import java.util.List;


public class AllCreatedActivity extends AppCompatActivity {


    private ActivityHistoryBinding binding;
    private AllCreatedActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_history);
        binding = DataBindingUtil.setContentView(AllCreatedActivity.this, R.layout.activity_history);
        backImage();

        binding.allCreatedTab.setTabTextColors(Color.parseColor("#D6D6D6"), Color.parseColor("#ffffff"));
        binding.allCreatedTab.setupWithViewPager(binding.allCreatedViewPager);

        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getApplicationContext()).equalsIgnoreCase(Constants.Athlete)){
            setupAthViewPager(binding.allCreatedViewPager);
            wrapTabIndicatorToTitle(binding.allCreatedTab, 200, 200);

        }else {
            setupViewPager(binding.allCreatedViewPager);
//            wrapTabIndicatorToTitle(binding.allCreatedTab, 100, 50);

        }
    }

    private void backImage() {
        binding.BackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void setupTabIcons() {
        binding.allCreatedTab.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.ic_arrow_left));
        binding.allCreatedTab.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_arrow_left));
    }
    private void setupViewPager(final ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllEventsFragment(), getResources().getString(R.string.events));
        adapter.addFragment(new AllSessionsFragment(), getResources().getString(R.string.sessions));
        adapter.addFragment(new AllSpacesFragment(), getResources().getString(R.string.spaces));
        viewPager.setAdapter(adapter);
    }

    private void setupAthViewPager(final ViewPager viewPager) {
        AllCreatedActivity.ViewPagerAdapter adapter = new AllCreatedActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PaymentSentFragment(), "Sent");
        viewPager.setAdapter(adapter);
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

    public void wrapTabIndicatorToTitle(TabLayout tabLayout, int externalMargin, int internalMargin) {
        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            int childCount = ((ViewGroup) tabStrip).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View tabView = tabStripGroup.getChildAt(i);
                //set minimum width to 0 for instead for small texts, indicator is not wrapped as expected
                tabView.setMinimumWidth(0);
                // set padding to 0 for wrapping indicator as title
                tabView.setPadding(0, tabView.getPaddingTop(), 0, tabView.getPaddingBottom());
                // setting custom margin between tabs
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
                    if (i == 0) {
                        // left
                        settingMargin(layoutParams, externalMargin, internalMargin);
                    } else if (i == childCount - 1) {
                        // right
                        settingMargin(layoutParams, internalMargin, externalMargin);
                    } else {
                        // internal
                        settingMargin(layoutParams, internalMargin, internalMargin);
                    }
                }
            }
            tabLayout.requestLayout();
        }
    }

    private void settingMargin(ViewGroup.MarginLayoutParams layoutParams, int start, int end) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParams.setMarginStart(start);
            layoutParams.setMarginEnd(end);
        } else {
            layoutParams.leftMargin = start;
            layoutParams.rightMargin = end;
        }
    }
}
