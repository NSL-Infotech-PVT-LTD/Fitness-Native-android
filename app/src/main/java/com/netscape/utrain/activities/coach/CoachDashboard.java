package com.netscape.utrain.activities.coach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.CalendarViewWithNotesActivity;
import com.netscape.utrain.activities.SignUpTypeActivity;
import com.netscape.utrain.databinding.ActivityCoachDashboardBinding;
import com.netscape.utrain.fragments.A_ChatsFragment;
import com.netscape.utrain.fragments.A_HomeFragment;
import com.netscape.utrain.fragments.C_HomeFragment;
import com.netscape.utrain.fragments.O_RegistrationProfile;
import com.netscape.utrain.fragments.O_HistoryFragment;
import com.netscape.utrain.fragments.O_NotificationFragment;
import com.netscape.utrain.fragments.O_StardFragment;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.PrefrenceConstant;

import de.hdodenhof.circleimageview.CircleImageView;

public class CoachDashboard extends AppCompatActivity {
    private TextView mTextMessage;
    public DrawerLayout drawer;
    private ActivityCoachDashboardBinding binding;
    private boolean doubleBackToExitPressedOnce = false;
    private AppCompatImageView coachDrawer;
    BottomNavigationView orgNavView;
    private CircleImageView navImageView;
    private MaterialTextView navNameTv;
    
    CoachDashboard activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(CoachDashboard.this, R.layout.activity_coach_dashboard);
       activity = this ;
       navImageView=binding.coachSlider.getHeaderView(0).findViewById(R.id.naviProfileImage);
        navNameTv=binding.coachSlider.getHeaderView(0).findViewById(R.id.navNameTv);

        Glide.with(CoachDashboard.this).load(CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, CoachDashboard.this)).into(navImageView);
        navNameTv.setText(CommonMethods.getPrefData(PrefrenceConstant.USER_NAME, CoachDashboard.this));

        coachDrawer = findViewById(R.id.coachDrawer);
        orgNavView = findViewById(R.id.orgNavView);
        drawer = findViewById(R.id.orgdrawerlayout);
//        coachDrawer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) { binding.orgdrawerlayout.openDrawer(GravityCompat.START);
//
//            }
//        });
        loadFragment(new C_HomeFragment());
//        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);

        binding.orgNavView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        final NavigationView navigationView = findViewById(R.id.coachSlider);
        navigationView.findViewById(R.id.org_logOutTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(activity)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Are you sure you want to Logout")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LoginManager.getInstance().logOut();
                                CommonMethods.clearPrefData(activity);
                                Intent intent = new Intent(activity, SignUpTypeActivity.class);
                                startActivity(intent);
                                finish();

                            }

                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }

                        })
                        .show();
            }
        });
        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                openCloseDrawer();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        });
        coachDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openCloseDrawer();

            }
        });
        binding.coachSlider.getHeaderView(0).findViewById(R.id.coachDashboardTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openCloseDrawer();
                loadFragment(new A_HomeFragment());

            }
        });
        binding.coachSlider.getHeaderView(0).findViewById(R.id.bookingTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCloseDrawer();

                loadFragment(new A_ChatsFragment());


            }
        });
        binding.coachSlider.getHeaderView(0).findViewById(R.id.transactionTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCloseDrawer();

                loadFragment(new A_ChatsFragment());

            }
        });
        binding.coachSlider.getHeaderView(0).findViewById(R.id.calenderTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCloseDrawer();

                startActivity(new Intent(CoachDashboard.this, CalendarViewWithNotesActivity.class));

            }
        });
        binding.coachSlider.getHeaderView(0).findViewById(R.id.aboutUsTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCloseDrawer();

                loadFragment(new A_ChatsFragment());

            }
        });


    }

    @SuppressLint("WrongConstant")
    public void openCloseDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(Gravity.LEFT);
            orgNavView.setVisibility(View.VISIBLE);
            //CLOSE Nav Drawer!
        } else {
            drawer.openDrawer(Gravity.LEFT); //OPEN Nav Drawer!
            orgNavView.setVisibility(View.GONE);

        }


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);

                    fragment = new C_HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_chat:
//                    mTextMessage.setText(R.string.title_dashboard);
                    fragment = new O_StardFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_reqimage:
//                    mTextMessage.setText(R.string.title_notifications);
//                    fragment = new O_RegistrationProfile();
//                    loadFragment(fragment);
                    return true;
                case R.id.navigation_running:

//                    mTextMessage.setText(R.string.title_notifications);
                    fragment = new O_HistoryFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
                    fragment = new O_NotificationFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.coachNavFragment, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(Gravity.LEFT);
        } else {
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
}
