package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityTransactionBinding;
import com.netscape.utrain.fragments.O_CmpEventFragment;
import com.netscape.utrain.fragments.O_HistoryFragment;
import com.netscape.utrain.fragments.O_UpcEventFragment;
import com.netscape.utrain.fragments.PaymentReceiveFragment;
import com.netscape.utrain.fragments.PaymentSentFragment;

import java.util.ArrayList;
import java.util.List;

public class TransactionActivity extends AppCompatActivity {

    ActivityTransactionBinding binding;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_transaction);
        binding = DataBindingUtil.setContentView(TransactionActivity.this, R.layout.activity_transaction);
        binding.transactionTabLayou.setTabTextColors(Color.parseColor("#D6D6D6"), Color.parseColor("#ffffff"));
        setupViewPager(binding.transactionViewPager);
        binding.transactionTabLayou.setupWithViewPager(binding.transactionViewPager);
        transactionBackImageClick();

    }

    private void transactionBackImageClick() {
        binding.transactionBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setupViewPager(final ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PaymentSentFragment(), "Sent");
        adapter.addFragment(new PaymentReceiveFragment(), "Receive");
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

}
