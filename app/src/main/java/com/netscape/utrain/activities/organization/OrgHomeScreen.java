package com.netscape.utrain.activities.organization;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.AboutUs;
import com.netscape.utrain.activities.AllCreatedActivity;
import com.netscape.utrain.activities.CalendarViewWithNotesActivity;
import com.netscape.utrain.activities.SettingsActivity;
import com.netscape.utrain.activities.SignUpTypeActivity;
import com.netscape.utrain.activities.TransactionActivity;
import com.netscape.utrain.activities.coach.CoachDashboard;
import com.netscape.utrain.databinding.OActivityBottomNavigationBinding;
import com.netscape.utrain.fragments.A_ChatsFragment;
import com.netscape.utrain.fragments.A_NotificationFragment;
import com.netscape.utrain.fragments.O_RegistrationProfile;
import com.netscape.utrain.fragments.O_HistoryFragment;
import com.netscape.utrain.fragments.O_HomeFragment;
import com.netscape.utrain.fragments.O_StardFragment;
import com.netscape.utrain.response.LogoutResponse;
import com.netscape.utrain.response.NotificationCountResponse;
import com.netscape.utrain.response.NotificationReadResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrgHomeScreen extends AppCompatActivity {
    public DrawerLayout drawer;
    BottomNavigationView orgNavView;
    private TextView mTextMessage;
    private OActivityBottomNavigationBinding binding;
    private boolean doubleBackToExitPressedOnce = false;
    private AppCompatImageView orgDrawerImageNew;
    private CircleImageView navImageView;
    private MaterialTextView navNameTv;
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
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
                    SetNotificationRead();
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
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        retrofitinterface= RetrofitInstance.getClient().create(Retrofitinterface.class);

//        Glide.with(OrgHomeScreen.this).load(Constants.ORG_IMAGE_BASE_URL+path).into(navImageView);
        Glide.with(OrgHomeScreen.this).load(path).into(navImageView);
        Glide.with(OrgHomeScreen.this).load(path).into(binding.orgProfileImg);
        navNameTv.setText(CommonMethods.getPrefData(PrefrenceConstant.USER_NAME, OrgHomeScreen.this));

        GetNewNotificationCount();
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
                                HitLogoutApi();


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
                orgNavView.getMenu().findItem(R.id.navigation_home).setChecked(true);
                openCloseDrawer();
                loadFragment(new O_HomeFragment());

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
                Intent aboutUs = new Intent(OrgHomeScreen.this, AboutUs.class);
                startActivity(aboutUs);

            }
        });
        binding.orgSlider.getHeaderView(0).findViewById(R.id.settingsTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCloseDrawer();
                startActivity(new Intent(OrgHomeScreen.this, SettingsActivity.class));
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
    private void HitLogoutApi() {
        progressDialog.show();
        Call<LogoutResponse> signUpAthlete = retrofitinterface.LogoutApi(Constants.CONTENT_TYPE,"Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, OrgHomeScreen.this));
        signUpAthlete.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            Toast.makeText(getApplicationContext(),response.body().getData().getScalar().toString(),Toast.LENGTH_SHORT).show();
                            LoginManager.getInstance().logOut();
                            CommonMethods.clearPrefData(OrgHomeScreen.this);
                            Intent intent = new Intent(OrgHomeScreen.this, SignUpTypeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Snackbar.make(binding.orgContainer, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.orgContainer, errorMessage.toString(), BaseTransientBottomBar.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Snackbar.make(binding.orgContainer, e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Snackbar.make(binding.orgContainer, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }


    private void setBadgeToNotification(int num) {
        BadgeDrawable badge = orgNavView.getOrCreateBadge(R.id.navigation_notifications);
        badge.setBadgeGravity(BadgeDrawable.TOP_END);
        badge.setNumber(num);
        badge.setMaxCharacterCount(3);
        badge.setBadgeTextColor(getResources().getColor(R.color.colorWhite));
    }

    private void GetNewNotificationCount() {
//        progressDialog.show();
        Call<NotificationCountResponse> signUpAthlete = retrofitinterface.getNewNotificationCount(Constants.CONTENT_TYPE,"Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN,OrgHomeScreen.this));
        signUpAthlete.enqueue(new Callback<NotificationCountResponse>() {
            @Override
            public void onResponse(Call<NotificationCountResponse> call, Response<NotificationCountResponse> response) {
                if (response.isSuccessful()) {
//                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
//                            Toast.makeText(getApplicationContext(),response.body().getData().getNotification_count(),Toast.LENGTH_SHORT).show();
                            if (response.body().getData().getNotification_count()>0){
                                setBadgeToNotification(response.body().getData().getNotification_count());
                            }

                        }
                    } else {
//                        Snackbar.make(binding.athHomeLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                } else {
//                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
//                        Snackbar.make(binding.athHomeLayout, errorMessage.toString(), BaseTransientBottomBar.LENGTH_LONG).show();

                    } catch (Exception e) {
//                        Snackbar.make(binding.athHomeLayout, e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<NotificationCountResponse> call, Throwable t) {
//                Snackbar.make(binding.athHomeLayout, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();
//                progressDialog.dismiss();
            }
        });
    }
    private void SetNotificationRead() {
//        progressDialog.show();
        Call<NotificationReadResponse> signUpAthlete = retrofitinterface.setNewNotificationRead(Constants.CONTENT_TYPE,"Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN,OrgHomeScreen.this));
        signUpAthlete.enqueue(new Callback<NotificationReadResponse>() {
            @Override
            public void onResponse(Call<NotificationReadResponse> call, Response<NotificationReadResponse> response) {
                if (response.isSuccessful()) {
//                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            orgNavView.removeBadge(R.id.navigation_notifications);
                        }
                    } else {
//                        Snackbar.make(binding.athHomeLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                } else {
//                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
//                        Snackbar.make(binding.athHomeLayout, errorMessage.toString(), BaseTransientBottomBar.LENGTH_LONG).show();

                    } catch (Exception e) {
//                        Snackbar.make(binding.athHomeLayout, e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }

            }
            @Override
            public void onFailure(Call<NotificationReadResponse> call, Throwable t) {
//                Snackbar.make(binding.athHomeLayout, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();
//                progressDialog.dismiss();
            }
        });
    }
}
