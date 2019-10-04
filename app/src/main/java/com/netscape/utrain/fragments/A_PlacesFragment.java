package com.netscape.utrain.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.netscape.utrain.R;
import com.netscape.utrain.adapters.CoachesRecyclerAdapter;
import com.netscape.utrain.adapters.TopCoachesAdapter;

import java.util.ArrayList;
import java.util.List;

public class A_PlacesFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CoachesRecyclerAdapter adapter;
    private List<String> data=new ArrayList<>();

    public A_PlacesFragment(){
        // required empty constructor....
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.athlete_place_fragment_view, container, false);
        recyclerView =view.findViewById(R.id.athletePlaceRecycler);
        data.add("chet");
        data.add("chet");
        data.add("chet");
        data.add("chet");
        data.add("chet");
        data.add("chet");
        data.add("chet");
        data.add("chet");
        data.add("chet");
        data.add("chet");
        data.add("chet");
        data.add("chet");
        data.add("chet");
        data.add("chet");
        adapter=new CoachesRecyclerAdapter(getContext(),data);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
