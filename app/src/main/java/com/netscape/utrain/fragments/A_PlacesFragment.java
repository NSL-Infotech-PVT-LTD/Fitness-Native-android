package com.netscape.utrain.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.netscape.utrain.R;
import com.netscape.utrain.adapters.CoachesRecyclerAdapter;
import com.netscape.utrain.adapters.TopCoachesAdapter;
import com.netscape.utrain.databinding.AthletePlaceFragmentViewBinding;

import java.util.ArrayList;
import java.util.List;

public class A_PlacesFragment extends Fragment {
    private View view;
    private AthletePlaceFragmentViewBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private CoachesRecyclerAdapter adapter;
    private List<String> data=new ArrayList<>();

    public A_PlacesFragment(){
        // required empty constructor....
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.athlete_place_fragment_view, container, false);
        view=binding.getRoot();
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
//        adapter=new CoachesRecyclerAdapter(getContext(),data);
        layoutManager = new LinearLayoutManager(getContext());
        binding.athletePlaceRecycler.setLayoutManager(layoutManager);
        binding.athletePlaceRecycler.setAdapter(adapter);
        return view;
    }

}
