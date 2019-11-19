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
import com.netscape.utrain.databinding.PaymentSentFragmentBinding;

public class PaymentSentFragment extends Fragment {
    private PaymentSentFragmentBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.payment_sent_fragment,container,false);
        View view=binding.getRoot();
        return view;
    }
}
