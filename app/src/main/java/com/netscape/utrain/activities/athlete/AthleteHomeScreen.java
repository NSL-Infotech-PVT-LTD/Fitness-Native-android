package com.netscape.utrain.activities.athlete;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.netscape.utrain.R;
import com.netscape.utrain.databinding.AActivityBottomNavigationBinding;
import com.netscape.utrain.fragments.A_ChatsFragment;
import com.netscape.utrain.fragments.A_EditorFragment;
import com.netscape.utrain.fragments.A_HomeFragment;
import com.netscape.utrain.fragments.A_NotificationFragment;
import com.netscape.utrain.fragments.A_StardFragment;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AthleteHomeScreen extends AppCompatActivity {
    private TextView mTextMessage;
    private AActivityBottomNavigationBinding binding;
    private boolean doubleBackToExitPressedOnce = false;
    private AppBarConfiguration mAppBarConfiguration;
    public static DrawerLayout drawer;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    fragment = new A_HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_chat:
//                    mTextMessage.setText(R.string.title_dashboard);
                    fragment = new A_StardFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_reqimage:
//                    mTextMessage.setText(R.string.title_notifications);
                    fragment = new A_EditorFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_running:
//                    mTextMessage.setText(R.string.title_notifications);
                    fragment = new A_ChatsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
                    fragment = new A_NotificationFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.a_activity_bottom_navigation);
        binding = DataBindingUtil.setContentView(this, R.layout.a_activity_bottom_navigation);

//        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        binding.navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
         drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.slider);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        setProfileImage();
//        loadFragment(new A_HomeFragment());
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.drawer_athlete_home, menu);
//        return true;
//    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
    @SuppressLint("WrongConstant")
    public  static void openCloseDrawer(){
        drawer.openDrawer(Gravity.START);
    }


    private void setProfileImage() {

        String img = CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, AthleteHomeScreen.this);
        if (!TextUtils.isEmpty(img)) {
            Glide.with(AthleteHomeScreen.this).load(Constants.IMAGE_BASE_URL + img).into(binding.athleteProfileImg);
        }
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
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
