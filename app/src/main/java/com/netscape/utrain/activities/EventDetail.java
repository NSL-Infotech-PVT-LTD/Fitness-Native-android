package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.netscape.utrain.R;
import com.netscape.utrain.adapters.MyCustomPagerAdapter;
import com.netscape.utrain.adapters.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class EventDetail extends AppCompatActivity {

    AppCompatImageView imgBackArrowImage;


    ViewPager viewPager;
    ViewPagerAdapter adapter;
    List<Integer> imageList = new ArrayList<>();
    MyCustomPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);


        for (int i = 0; i < 5; i++) {
            imageList.add(R.drawable.park);

        }


        imgBackArrowImage = findViewById(R.id.eventBackArrowImage);
        imgBackArrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventDetail.this,AthleteHomeScreen.class);
                startActivity(intent);
            }
        });
        viewPager = findViewById(R.id.viewPagerImage);
        pagerAdapter = new MyCustomPagerAdapter(this, imageList);
        viewPager.setAdapter(pagerAdapter);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
    }
}
