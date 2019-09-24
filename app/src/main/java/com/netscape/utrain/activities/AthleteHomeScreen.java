package com.netscape.utrain.activities;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.netscape.utrain.R;
import com.netscape.utrain.databinding.AActivityBottomNavigationBinding;
import com.netscape.utrain.fragments.A_ChatsFragment;
import com.netscape.utrain.fragments.A_EditorFragment;
import com.netscape.utrain.fragments.A_HomeFragment;
import com.netscape.utrain.fragments.A_NotificationFragment;
import com.netscape.utrain.fragments.A_StardFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class AthleteHomeScreen extends AppCompatActivity {
    private TextView mTextMessage;
    private AActivityBottomNavigationBinding binding;
    private boolean doubleBackToExitPressedOnce=false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.a_activity_bottom_navigation);
        binding= DataBindingUtil.setContentView(this,R.layout.a_activity_bottom_navigation);
        loadFragment(new A_HomeFragment());
//        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        binding.navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    fragment=new A_HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_stard:
//                    mTextMessage.setText(R.string.title_dashboard);
                    fragment=new A_StardFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_edit:
//                    mTextMessage.setText(R.string.title_notifications);
                    fragment=new A_EditorFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_chat:
//                    mTextMessage.setText(R.string.title_notifications);
                    fragment=new A_ChatsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
                    fragment=new A_NotificationFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction  transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
//        Snackbar.make(binding.container,getResources().getString(R.string.please_click_again_to_exit), BaseTransientBottomBar.LENGTH_LONG).show();

        Toast.makeText(this, getResources().getString(R.string.please_click_again_to_exit), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
