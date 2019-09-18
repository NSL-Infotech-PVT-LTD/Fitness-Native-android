package com.netscape.utrain.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.netscape.utrain.R;
import com.netscape.utrain.fragments.PaymentFragment;
import com.netscape.utrain.fragments.ReviewFragment;
import com.netscape.utrain.fragments.SessionFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    Context context;
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentListTitles = new ArrayList<>();


    public ViewPagerAdapter(@NonNull FragmentManager fm, Context context) {

        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new SessionFragment();
        } else if (position == 1) {
            return new PaymentFragment();
        } else if (position == 2) {
            return new ReviewFragment();
        }else
            return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){

            case 0:
                return context.getString(R.string.session_fragment);
            case 1:
                return context.getString(R.string.payment_fragment);
            case 2:
                return context.getString(R.string.review_fragment);
                default:
                    return null;
        }

    }

}
