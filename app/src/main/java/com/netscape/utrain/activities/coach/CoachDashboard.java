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
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import com.netscape.utrain.activities.MyProfile;
import com.netscape.utrain.activities.SettingsActivity;
import com.netscape.utrain.activities.SignUpTypeActivity;
import com.netscape.utrain.activities.TransactionActivity;
import com.netscape.utrain.activities.ViewProfileActivity;
import com.netscape.utrain.activities.athlete.AthleteHomeScreen;
import com.netscape.utrain.databinding.ActivityCoachDashboardBinding;
import com.netscape.utrain.fragments.A_ChatsFragment;
import com.netscape.utrain.fragments.A_NotificationFragment;
import com.netscape.utrain.fragments.C_HomeFragment;
import com.netscape.utrain.fragments.O_HistoryFragment;
import com.netscape.utrain.fragments.O_NotificationFragment;
import com.netscape.utrain.fragments.O_StardFragment;
import com.netscape.utrain.response.LogoutResponse;
import com.netscape.utrain.response.NotificationCountResponse;
import com.netscape.utrain.response.NotificationReadResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CheckNetwork;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoachDashboard extends AppCompatActivity {
    public DrawerLayout drawer;
    public BottomNavigationView orgNavView;
    CoachDashboard activity;
    boolean isInternetPresent = false;
    private TextView mTextMessage;
    private ActivityCoachDashboardBinding binding;
    private boolean doubleBackToExitPressedOnce = false;
    private AppCompatImageView coachDrawer;
    private CircleImageView navImageView;
    private MaterialTextView navNameTv;
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    private AlertDialog dialogMultiOrder;
    private int count = 0;
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
                    fragment = new O_NotificationFragment();
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
    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent bufferIntent) {
            String status = CheckNetwork.getConnectivityStatusString(context);
            if (status.equals("WIFI") || status.equals("MOBILE")) {
                isInternetPresent = true;
            } else if (status.equals("No Connection")) {
                isInternetPresent = false;
            }

            showNetworkState();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(CoachDashboard.this, R.layout.activity_coach_dashboard);
        activity = this;
        Intent intent = getIntent();
//        if (intent != null) {
//            if (!intent.getStringExtra("pushnotification").isEmpty()) {
//                Toast.makeText(this, "Notification area", Toast.LENGTH_SHORT).show();
//            }
//        }
        navImageView = binding.coachSlider.getHeaderView(0).findViewById(R.id.naviProfileImage);
        navNameTv = binding.coachSlider.getHeaderView(0).findViewById(R.id.navNameTv);

        Glide.with(CoachDashboard.this).load(CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, CoachDashboard.this)).into(navImageView);
        Glide.with(CoachDashboard.this).load(CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, CoachDashboard.this)).into(binding.coachProfileImg);
        navNameTv.setText(CommonMethods.getPrefData(PrefrenceConstant.USER_NAME, CoachDashboard.this));
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);

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
        GetNewNotificationCount();

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
        coachDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openCloseDrawer();

            }
        });
        binding.coachSlider.getHeaderView(0).findViewById(R.id.coachDashboardTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orgNavView.getMenu().findItem(R.id.navigation_home).setChecked(true);
                openCloseDrawer();
                loadFragment(new C_HomeFragment());

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
                Intent transaction = new Intent(getApplicationContext(), TransactionActivity.class);
                startActivity(transaction);
            }
        });

        binding.coachSlider.getHeaderView(0).findViewById(R.id.allCreatedTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCloseDrawer();
                startActivity(new Intent(CoachDashboard.this, AllCreatedActivity.class));
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
                Intent aboutUs = new Intent(CoachDashboard.this, AboutUs.class);
                startActivity(aboutUs);

            }
        });

        binding.coachSlider.getHeaderView(0).findViewById(R.id.settingsTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCloseDrawer();
                startActivity(new Intent(CoachDashboard.this, SettingsActivity.class));
            }
        });
        binding.coachProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                notificationTv.setText("");
                Intent myProfileIntent = new Intent(CoachDashboard.this, ViewProfileActivity.class);
                startActivity(myProfileIntent);

            }
        });


    }

    @SuppressLint("WrongConstant")
    public void openCloseDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(Gravity.LEFT);
            orgNavView.setVisibility(View.VISIBLE);
            binding.coachProfileImg.setVisibility(View.VISIBLE);
            //CLOSE Nav Drawer!
        } else {
            drawer.openDrawer(Gravity.LEFT); //OPEN Nav Drawer!
            orgNavView.setVisibility(View.GONE);
            binding.coachProfileImg.setVisibility(View.GONE);

        }


    }

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

    private void HitLogoutApi() {
        progressDialog.show();
        Call<LogoutResponse> signUpAthlete = retrofitinterface.LogoutApi(Constants.CONTENT_TYPE, "Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, CoachDashboard.this));
        signUpAthlete.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            Toast.makeText(getApplicationContext(), response.body().getData().getScalar(), Toast.LENGTH_SHORT).show();
                            LoginManager.getInstance().logOut();
                            CommonMethods.clearPrefData(activity);
                            Intent intent = new Intent(activity, SignUpTypeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Snackbar.make(binding.coachContainer, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.coachContainer, errorMessage, BaseTransientBottomBar.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Snackbar.make(binding.coachContainer, e.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Snackbar.make(binding.coachContainer, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();
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
        Call<NotificationCountResponse> signUpAthlete = retrofitinterface.getNewNotificationCount(Constants.CONTENT_TYPE, "Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, CoachDashboard.this));
        signUpAthlete.enqueue(new Callback<NotificationCountResponse>() {
            @Override
            public void onResponse(Call<NotificationCountResponse> call, Response<NotificationCountResponse> response) {
                if (response.isSuccessful()) {
                    //                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            //                            Toast.makeText(getApplicationContext(),response.body().getData().getNotification_count(),Toast.LENGTH_SHORT).show();
                            if (response.body().getData().getNotification_count() > 0) {
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
        Call<NotificationReadResponse> signUpAthlete = retrofitinterface.setNewNotificationRead(Constants.CONTENT_TYPE, "Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, CoachDashboard.this));
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

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkReceiver);
    }

    public void showNetworkState() {
        if (isInternetPresent) {
//            Toast.makeText(this, "Internet Connected", Toast.LENGTH_SHORT).show();
//            networkConnectionImageView.setVisibility(View.VISIBLE);
        } else {
            if (count == 0) {
                handleImageSelection();
            }
//            Toast.makeText(this, "Internet Disconnected", Toast.LENGTH_SHORT).show();
//            noNetworkConnectionImageView.setVisibility(View.VISIBLE);
        }
    }

    public void handleImageSelection() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View content = inflater.inflate(R.layout.internet_check, null);
        builder.setView(content);
        TextView retry = content.findViewById(R.id.retryInternet);
//        TextView camera = content.findViewById(R.id.cameraSelectionBtn);
//        ImageView cancel = content.findViewById(R.id.closeDialogImg);
        dialogMultiOrder = builder.create();
        dialogMultiOrder.setCancelable(false);
//        dialogMultiOrder.setCanceledOnTouchOutside(false);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetPresent) {
                    count = 0;
                    dialogMultiOrder.dismiss();
                } else {
                }

            }
        });
        dialogMultiOrder.show();
        count = 1;
        dialogMultiOrder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
