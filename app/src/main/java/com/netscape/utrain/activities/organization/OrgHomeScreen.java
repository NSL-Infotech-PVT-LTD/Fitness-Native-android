package com.netscape.utrain.activities.organization;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.AllCreatedActivity;
import com.netscape.utrain.activities.CalendarViewWithNotesActivity;
import com.netscape.utrain.activities.SettingsActivity;
import com.netscape.utrain.activities.SignUpTypeActivity;
import com.netscape.utrain.activities.TransactionActivity;
import com.netscape.utrain.activities.coach.CoachDashboard;
import com.netscape.utrain.databinding.OActivityBottomNavigationBinding;
import com.netscape.utrain.fragments.A_ChatsFragment;
import com.netscape.utrain.fragments.A_HomeFragment;
import com.netscape.utrain.fragments.A_NotificationFragment;
import com.netscape.utrain.fragments.O_RegistrationProfile;
import com.netscape.utrain.fragments.O_HistoryFragment;
import com.netscape.utrain.fragments.O_HomeFragment;
import com.netscape.utrain.fragments.O_NotificationFragment;
import com.netscape.utrain.fragments.O_StardFragment;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrgHomeScreen extends AppCompatActivity {
    public DrawerLayout drawer;
    BottomNavigationView orgNavView;
    private TextView mTextMessage;
    private OActivityBottomNavigationBinding binding;
    private boolean doubleBackToExitPressedOnce = false;
    private AppCompatImageView orgDrawerImageNew;
    private CircleImageView navImageView;
    private MaterialTextView navNameTv;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    fragment = new O_HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_chat:
//                    mTextMessage.setText(R.string.title_dashboard);
                    fragment = new O_StardFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_reqimage:
//                    mTextMessage.setText(R.string.title_notifications);
                    fragment = new O_RegistrationProfile();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_running:

//                    mTextMessage.setText(R.string.title_notifications);
                    fragment = new O_HistoryFragment();
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
        binding = DataBindingUtil.setContentView(OrgHomeScreen.this, R.layout.o_activity_bottom_navigation);
        orgDrawerImageNew = findViewById(R.id.orgDrawerImageNew);
        orgNavView = findViewById(R.id.orgNavView);
        drawer = findViewById(R.id.orgdrawer_layout);
        navImageView = binding.orgSlider.getHeaderView(0).findViewById(R.id.naviProfileImage);
        navNameTv = binding.orgSlider.getHeaderView(0).findViewById(R.id.navNameTv);
        String path = CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, OrgHomeScreen.this);
//        Glide.with(OrgHomeScreen.this).load(Constants.ORG_IMAGE_BASE_URL+path).into(navImageView);
        Glide.with(OrgHomeScreen.this).load(path).into(navImageView);
        Glide.with(OrgHomeScreen.this).load(path).into(binding.orgProfileImg);
        navNameTv.setText(CommonMethods.getPrefData(PrefrenceConstant.USER_NAME, OrgHomeScreen.this));


        binding.orgProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Fragment fragment = new O_RegistrationProfile();
                loadFragment(fragment);
            }
        });

//        orgDrawerImageNew.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.orgdrawerLayout.openDrawer(GravityCompat.START);
//
//            }
//        });
        loadFragment(new O_HomeFragment());
//        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        binding.orgNavView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        final NavigationView navigationView = findViewById(R.id.orgSlider);
        navigationView.findViewById(R.id.org_logOutTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(OrgHomeScreen.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Are you sure you want to Logout")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LoginManager.getInstance().logOut();
                                CommonMethods.clearPrefData(OrgHomeScreen.this);
                                Intent intent = new Intent(OrgHomeScreen.this, SignUpTypeActivity.class);
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

        orgDrawerImageNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openCloseDrawer();

            }
        });
        binding.orgSlider.getHeaderView(0).findViewById(R.id.coachDashboardTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openCloseDrawer();
                loadFragment(new A_HomeFragment());

            }
        });
        binding.orgSlider.getHeaderView(0).findViewById(R.id.bookingTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCloseDrawer();
                loadFragment(new A_ChatsFragment());
            }
        });
        binding.orgSlider.getHeaderView(0).findViewById(R.id.transactionTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCloseDrawer();
                Intent transaction = new Intent(getApplicationContext(), TransactionActivity.class);
                startActivity(transaction);
            }
        });
        binding.orgSlider.getHeaderView(0).findViewById(R.id.calenderTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCloseDrawer();

                startActivity(new Intent(OrgHomeScreen.this, CalendarViewWithNotesActivity.class));

            }
        });

        binding.orgSlider.getHeaderView(0).findViewById(R.id.allCreatedTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCloseDrawer();
                startActivity(new Intent(OrgHomeScreen.this, AllCreatedActivity.class));
            }
        });

        binding.orgSlider.getHeaderView(0).findViewById(R.id.aboutUsTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCloseDrawer();

                loadFragment(new A_ChatsFragment());

            }
        });
        binding.orgSlider.getHeaderView(0).findViewById(R.id.settingsTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCloseDrawer();
                startActivity(new Intent(OrgHomeScreen.this, SettingsActivity.class));
            }
        });


    }

    @SuppressLint("WrongConstant")
    public void openCloseDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(Gravity.LEFT);
            orgNavView.setVisibility(View.VISIBLE);
            binding.orgProfileImg.setVisibility(View.VISIBLE);
            //CLOSE Nav Drawer!
        } else {
            drawer.openDrawer(Gravity.LEFT); //OPEN Nav Drawer!
            orgNavView.setVisibility(View.GONE);
            binding.orgProfileImg.setVisibility(View.GONE);

        }

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.org_fragment, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (binding.orgdrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.orgdrawerLayout.closeDrawer(Gravity.LEFT);
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
