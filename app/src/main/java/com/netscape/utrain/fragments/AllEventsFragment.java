package com.netscape.utrain.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.netscape.utrain.R;
import com.netscape.utrain.databinding.AllEventsFragmentBinding;

public class AllEventsFragment extends Fragment {

    private AllEventsFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.all_events_fragment, container, false);
        View view = binding.getRoot();


        return view;
    }

    private void AllEventsCreatedApi() {



    }
}
