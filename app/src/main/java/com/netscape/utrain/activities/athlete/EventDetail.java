package com.netscape.utrain.activities.athlete;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.adapters.MyCustomPagerAdapter;
import com.netscape.utrain.adapters.ViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class EventDetail extends AppCompatActivity {

    MaterialTextView venueAddress, eventName, eventInstructionsDetailTv, eventTimeDetailTv, eventDateDetailTv;
    MaterialTextView title;
    MaterialTextView endDateTime;


    AppCompatImageView imgBackArrowImage;


    ViewPager viewPager;
    ViewPagerAdapter adapter;
    List<String> imageList = new ArrayList<>();
    MyCustomPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        venueAddress = findViewById(R.id.eventVanueDetailTv);
        eventName = findViewById(R.id.eventMarathonHeaderTv);
        title = findViewById(R.id.title);
        eventTimeDetailTv = findViewById(R.id.eventTimeDetailTv);
        eventDateDetailTv = findViewById(R.id.eventDateDetailTv);
        eventInstructionsDetailTv = findViewById(R.id.eventInstructionsDetailTv);

        eventName.setText(getIntent().getStringExtra("eventName"));
        venueAddress.setText(getIntent().getStringExtra("eventVenue"));
        eventTimeDetailTv.setText(getIntent().getStringExtra("evenStartDateTime"));
        eventDateDetailTv.setText(getIntent().getStringExtra("eventEndDateTime"));
        eventInstructionsDetailTv.setText(getIntent().getStringExtra("eventDescription"));

        if (getIntent().getStringExtra("from") != null)
            if (getIntent().getStringExtra("from").equalsIgnoreCase("places"))
                title.setText("Places");
        if (getIntent().getStringExtra("from") != null)
            if (getIntent().getStringExtra("from").equalsIgnoreCase("events"))
                title.setText("Events");
        if (getIntent().getStringExtra("from") != null)
            if (getIntent().getStringExtra("from").equalsIgnoreCase("sessions"))
                title.setText("Sessions");


        Bundle b = getIntent().getExtras();
        if (b != null) {
            String Array = b.getString("Array");
            try {
                JSONArray jsonArray = new JSONArray(Array);
                for (int i = 0; i < jsonArray.length(); i++) {
                    imageList.add(String.valueOf(getIntent().getStringExtra("image_url") + jsonArray.get(i)));
                }
            } catch (JSONException e) {
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }


            imgBackArrowImage = findViewById(R.id.eventBackArrowImage);
            imgBackArrowImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(EventDetail.this, AthleteHomeScreen.class);
                    startActivity(intent);
                }
            });
            viewPager = findViewById(R.id.viewPagerImage);
            pagerAdapter = new MyCustomPagerAdapter(this, imageList);
            viewPager.setAdapter(pagerAdapter);
            CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
            if (viewPager.getAdapter().getCount() <=1)

            indicator.setViewPager(null);
            else
                indicator.setViewPager(viewPager);
        }
    }
}
