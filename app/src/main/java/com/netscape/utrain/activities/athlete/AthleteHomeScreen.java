package com.netscape.utrain.activities.athlete;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.AboutUs;
import com.netscape.utrain.activities.CalendarViewWithNotesActivity;
import com.netscape.utrain.activities.MyProfile;
import com.netscape.utrain.activities.SettingsActivity;
import com.netscape.utrain.activities.SignUpTypeActivity;
import com.netscape.utrain.activities.TransactionActivity;
import com.netscape.utrain.activities.coach.CoachDashboard;
import com.netscape.utrain.databinding.AActivityBottomNavigationBinding;
import com.netscape.utrain.fragments.A_ChatsFragment;
import com.netscape.utrain.fragments.A_EditorFragment;
import com.netscape.utrain.fragments.A_HomeFragment;
import com.netscape.utrain.fragments.A_NotificationFragment;
import com.netscape.utrain.fragments.A_StardFragment;
import com.netscape.utrain.fragments.O_HistoryFragment;
import com.netscape.utrain.response.LoginResponse;
import com.netscape.utrain.response.LogoutResponse;
import com.netscape.utrain.response.NotificationCountResponse;
import com.netscape.utrain.response.NotificationReadResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CheckNetwork;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AthleteHomeScreen extends AppCompatActivity {
    public DrawerLayout drawer;
    public BottomNavigationView navView;
    private TextView mTextMessage;
    private AActivityBottomNavigationBinding binding;
    private boolean doubleBackToExitPressedOnce = false;
    private AppBarConfiguration mAppBarConfiguration;
    private AppCompatImageView drawerImage;
    private CircleImageView headerImage;
    private MaterialTextView navNameTv, notificationTv;
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    boolean isInternetPresent = false;
    private AlertDialog dialogMultiOrder;
    private int count =0;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    notificationTv.setText("");
                    fragment = new A_HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_chat:
                    notificationTv.setText("");
                    fragment = new A_StardFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_reqimage:
                    notificationTv.setText("");
                    fragment = new A_EditorFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_running:
                    notificationTv.setText("");
                    fragment = new O_HistoryFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_notifications:
                    SetNotificationRead();
                    notificationTv.setText(R.string.title_notifications);
                    fragment = new A_NotificationFragment();
                    loadFragment(fragment);

                    return true;
            }
            return false;
        }
    };


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

    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent bufferIntent) {
            String status = CheckNetwork.getConnectivityStatusString(context);
            if(status.equals("WIFI") || status.equals("MOBILE")) {
                isInternetPresent = true;
            } else if(status.equals("No Connection")) {
                isInternetPresent = false;
            }

            showNetworkState();
        }
    };

    public void showNetworkState(){
        if(isInternetPresent) {
//            Toast.makeText(this, "Internet Connected", Toast.LENGTH_SHORT).show();
//            networkConnectionImageView.setVisibility(View.VISIBLE);
        } else {
            if (count == 0){
                handleImageSelection();
        }
//            Toast.makeText(this, "Internet Disconnected", Toast.LENGTH_SHORT).show();
//            noNetworkConnectionImageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(AthleteHomeScreen.this, R.layout.a_activity_bottom_navigation);


        init();
    }

    private void init() {
        //        setContentView(R.layout.a_activity_bottom_navigation);
        navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        drawerImage = findViewById(R.id.drawerImageNew);
        notificationTv = findViewById(R.id.notificationHeader);
        navNameTv = binding.slider.getHeaderView(0).findViewById(R.id.navNameTv);
        headerImage = binding.slider.getHeaderView(0).findViewById(R.id.naviProfileImage);
        binding.navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);
        drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.slider);
        View header = navigationView.getHeaderView(0);
        MaterialTextView dashboardTv = header.findViewById(R.id.coachDashboardTv);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        retrofitinterface= RetrofitInstance.getClient().create(Retrofitinterface.class);
        GetNewNotificationCount();

        binding.slider.getHeaderView(0).findViewById(R.id.allCreatedTv).setVisibility(View.GONE);
        binding.slider.getHeaderView(0).findViewById(R.id.allCreatedIcon).setVisibility(View.GONE);
        binding.slider.getHeaderView(0).findViewById(R.id.view7).setVisibility(View.GONE);
        navigationView.findViewById(R.id.logOutTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(AthleteHomeScreen.this)
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
//                notificationTv.setText("");

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
        drawerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openCloseDrawer();

            }
        });
        dashboardTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationTv.setText("");
                navView.getMenu().findItem(R.id.navigation_home).setChecked(true);
                openCloseDrawer();
                loadFragment(new A_HomeFragment());

            }
        });
        binding.slider.getHeaderView(0).findViewById(R.id.bookingTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCloseDrawer();
//                notificationTv.setText("");
                loadFragment(new A_ChatsFragment());


            }
        });
        binding.slider.getHeaderView(0).findViewById(R.id.transactionTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                notificationTv.setText("");
                openCloseDrawer();

                Intent transactionActivity = new Intent(AthleteHomeScreen.this, TransactionActivity.class);
                startActivity(transactionActivity);

            }
        });
        binding.slider.getHeaderView(0).findViewById(R.id.calenderTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                notificationTv.setText("");
                openCloseDrawer();

                startActivity(new Intent(AthleteHomeScreen.this, CalendarViewWithNotesActivity.class));

            }
        });
        binding.slider.getHeaderView(0).findViewById(R.id.aboutUsTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                notificationTv.setText("");
                openCloseDrawer();
                Intent aboutUs = new Intent(AthleteHomeScreen.this, AboutUs.class);
                startActivity(aboutUs);

            }
        });


//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
//                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

        setProfileImage();
        loadFragment(new A_HomeFragment());

        // When User Will Click on Athlete Home Screen ImageView, it will open MyProfileActivity....

        binding.athleteProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationTv.setText("");
                Intent myProfileIntent = new Intent(AthleteHomeScreen.this, MyProfile.class);
                myProfileIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(myProfileIntent);

            }
        });

        MaterialTextView settingsTv = header.findViewById(R.id.settingsTv);
        settingsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openCloseDrawer();
                Intent settingsTv = new Intent(AthleteHomeScreen.this, SettingsActivity.class);
                startActivity(settingsTv);
            }
        });

    }


    private void setBadgeToNotification(int num) {
        BadgeDrawable badge = navView.getOrCreateBadge(R.id.navigation_notifications);
        badge.setBadgeGravity(BadgeDrawable.TOP_END);
        badge.setNumber(num);
        badge.setMaxCharacterCount(3);
        badge.setBadgeTextColor(getResources().getColor(R.color.colorWhite));

//        navView.removeBadge(R.id.navigation_notifications);

//        BadgeUtils.attachBadgeDrawable(badge, navView, null);
        // to remove
//        navView.removeBadge(R.id.action_settings);
//
//// to add
//        navView.getOrCreateBadge(R.id.action_settings);
//                .apply {
//            //if you want to change other attributes, like badge color, add a number, maximum number (a plus sign is added, e.g. 99+)
//            number = 100;
//            maxCharactersCount = 3
//            backgroundColor = ContextCompat.getColor(getApplicationContext(), R.color.notificationRedIcon);
//        }
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
    public void openCloseDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(Gravity.LEFT);
            navView.setVisibility(View.VISIBLE);
            binding.athleteProfileImg.setVisibility(View.VISIBLE);
            //CLOSE Nav Drawer!
        } else {
            drawer.openDrawer(Gravity.LEFT); //OPEN Nav Drawer!
            navView.setVisibility(View.GONE);
            binding.athleteProfileImg.setVisibility(View.GONE);
        }

    }

    private void setProfileImage() {

        String img = CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, AthleteHomeScreen.this);
        if (!TextUtils.isEmpty(img)) {
            Glide.with(AthleteHomeScreen.this).load(Constants.IMAGE_BASE_URL + img).thumbnail(Glide.with(AthleteHomeScreen.this).load(Constants.IMAGE_BASE_URL + Constants.THUMBNAILS + img)).into(binding.athleteProfileImg);
            Glide.with(AthleteHomeScreen.this).load(Constants.IMAGE_BASE_URL + img).thumbnail(Glide.with(AthleteHomeScreen.this).load(Constants.IMAGE_BASE_URL + Constants.THUMBNAILS + img)).into(headerImage);
            navNameTv.setText(CommonMethods.getPrefData(PrefrenceConstant.USER_NAME, AthleteHomeScreen.this));
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
        Call<LogoutResponse> signUpAthlete = retrofitinterface.LogoutApi(Constants.CONTENT_TYPE,"Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN,AthleteHomeScreen.this));
        signUpAthlete.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                                Toast.makeText(getApplicationContext(),response.body().getData().getScalar().toString(),Toast.LENGTH_SHORT).show();
                            LoginManager.getInstance().logOut();
                            CommonMethods.clearPrefData(AthleteHomeScreen.this);
                            Intent intent = new Intent(AthleteHomeScreen.this, SignUpTypeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Snackbar.make(binding.athHomeLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.athHomeLayout, errorMessage.toString(), BaseTransientBottomBar.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Snackbar.make(binding.athHomeLayout, e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Snackbar.make(binding.athHomeLayout, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }
    private void GetNewNotificationCount() {
//        progressDialog.show();
        Call<NotificationCountResponse> signUpAthlete = retrofitinterface.getNewNotificationCount(Constants.CONTENT_TYPE,"Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN,AthleteHomeScreen.this));
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
                        Snackbar.make(binding.athHomeLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                } else {
//                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.athHomeLayout, errorMessage.toString(), BaseTransientBottomBar.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Snackbar.make(binding.athHomeLayout, e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<NotificationCountResponse> call, Throwable t) {
                Snackbar.make(binding.athHomeLayout, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();
//                progressDialog.dismiss();
            }
        });
    }
    private void SetNotificationRead() {
//        progressDialog.show();
        Call<NotificationReadResponse> signUpAthlete = retrofitinterface.setNewNotificationRead(Constants.CONTENT_TYPE,"Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN,AthleteHomeScreen.this));
        signUpAthlete.enqueue(new Callback<NotificationReadResponse>() {
            @Override
            public void onResponse(Call<NotificationReadResponse> call, Response<NotificationReadResponse> response) {
                if (response.isSuccessful()) {
//                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            navView.removeBadge(R.id.navigation_notifications);
                        }
                    } else {
                        Snackbar.make(binding.athHomeLayout, response.body().getError().getError_message().getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                } else {
//                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Snackbar.make(binding.athHomeLayout, errorMessage.toString(), BaseTransientBottomBar.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Snackbar.make(binding.athHomeLayout, e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }

            }
            @Override
            public void onFailure(Call<NotificationReadResponse> call, Throwable t) {
                Snackbar.make(binding.athHomeLayout, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();
//                progressDialog.dismiss();
            }
        });
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

                if(isInternetPresent) {
                    count=0;
                    dialogMultiOrder.dismiss();
//                    init();
                } else {
                }

            }
        });

        dialogMultiOrder.show();
        count=1;
        dialogMultiOrder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

}
