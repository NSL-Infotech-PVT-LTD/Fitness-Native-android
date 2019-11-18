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
import com.netscape.utrain.databinding.PaymentReceiveFragmentBinding;

public class PaymentReceiveFragment extends Fragment {

    private PaymentReceiveFragmentBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.payment_receive_fragment,container,false);
        binding = DataBindingUtil.inflate(inflater,R.layout.payment_receive_fragment,container,false);
        View view = binding.getRoot();


        return view;
    }
}
