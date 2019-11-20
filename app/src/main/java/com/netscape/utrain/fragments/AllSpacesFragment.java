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
import com.netscape.utrain.databinding.AllSessionsFragmentBinding;
import com.netscape.utrain.databinding.AllSpacesFragmentBinding;

public class AllSpacesFragment extends Fragment {

    private AllSpacesFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.all_spaces_fragment, container,false);
        View view = binding.getRoot();



        return view;
    }
}
