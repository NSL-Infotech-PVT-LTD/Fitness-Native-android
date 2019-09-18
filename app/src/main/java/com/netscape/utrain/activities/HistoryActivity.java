package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.se.omapi.Session;

import com.google.android.material.tabs.TabLayout;
import com.netscape.utrain.R;
import com.netscape.utrain.adapters.SessionFragmentAdapter;
import com.netscape.utrain.adapters.SessionFragmentModel;
import com.netscape.utrain.adapters.ViewPagerAdapter;
import com.netscape.utrain.fragments.PaymentFragment;
import com.netscape.utrain.fragments.ReviewFragment;
import com.netscape.utrain.fragments.SessionFragment;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        tabLayout = findViewById(R.id.historyTabLayout);
        viewPager = findViewById(R.id.historyViewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),HistoryActivity.this);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }
}
