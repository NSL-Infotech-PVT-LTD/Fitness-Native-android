package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.netscape.utrain.R;
import com.netscape.utrain.adapters.CoachBottomNavAdapter;
import com.netscape.utrain.adapters.OngoingSessionAdapter;
import com.netscape.utrain.model.CoachBottomNavModel;
import com.netscape.utrain.model.OnGoingSessionCoachBottomNavModel;

import java.util.ArrayList;
import java.util.List;

public class CoachBottomNavigation extends AppCompatActivity {

    RecyclerView recyclerView, onGoingSessionRecycler;
    RecyclerView.LayoutManager layoutManager, onGoingSessionLayoutManager;
    CoachBottomNavAdapter adapter;
    List<CoachBottomNavModel> list = new ArrayList<>();

    List<OnGoingSessionCoachBottomNavModel> onGoingList = new ArrayList<>();
    OngoingSessionAdapter ongoingSessionAdapter;
    int image[] = {R.drawable.sophie,R.drawable.athlete,R.drawable.back};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_bottom_navigation);

        recyclerView = findViewById(R.id.coachBottomNavRecentReqRecycler);
        layoutManager = new LinearLayoutManager(this);
        onGoingSessionLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, true);

        list.add(new CoachBottomNavModel(image[0],"Sophie","18 September 2019"));
        list.add(new CoachBottomNavModel(image[1],"Alexa","18 September 2019"));
        list.add(new CoachBottomNavModel(image[2],"Martiana","18 September 2019"));

        adapter = new CoachBottomNavAdapter(CoachBottomNavigation.this,list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        onGoingSessionRecycler = findViewById(R.id.coachBottomNavOngoingRecyclerView);
        onGoingList.add(new OnGoingSessionCoachBottomNavModel("Alex", "Soccor"));
        onGoingList.add(new OnGoingSessionCoachBottomNavModel("Sophia", "Athlete"));
        onGoingList.add(new OnGoingSessionCoachBottomNavModel("Martiana", "Gym"));

        ongoingSessionAdapter = new OngoingSessionAdapter(CoachBottomNavigation.this,onGoingList);
        onGoingSessionRecycler.setLayoutManager(onGoingSessionLayoutManager);
        onGoingSessionRecycler.setAdapter(ongoingSessionAdapter);


    }
}
