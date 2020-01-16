package com.netscape.utrain.activities.organization;

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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import com.netscape.utrain.activities.BookingDetails;
import com.netscape.utrain.activities.CalendarViewWithNotesActivity;
import com.netscape.utrain.activities.SettingsActivity;
import com.netscape.utrain.activities.SignUpTypeActivity;
import com.netscape.utrain.activities.TransactionActivity;
import com.netscape.utrain.activities.athlete.AthleteHomeScreen;
import com.netscape.utrain.activities.athlete.EventDetail;
import com.netscape.utrain.activities.coach.CoachDashboard;
import com.netscape.utrain.databinding.OActivityBottomNavigationBinding;
import com.netscape.utrain.fragments.A_ChatsFragment;
import com.netscape.utrain.fragments.A_NotificationFragment;
import com.netscape.utrain.fragments.O_RegistrationProfile;
import com.netscape.utrain.fragments.O_HistoryFragment;
import com.netscape.utrain.fragments.O_HomeFragment;
import com.netscape.utrain.fragments.O_StardFragment;
import com.netscape.utrain.fragments.TransactionFragment;
import com.netscape.utrain.model.EventBookingModel;
import com.netscape.utrain.response.LogoutResponse;
import com.netscape.utrain.response.NotificationCountResponse;
import com.netscape.utrain.response.NotificationReadResponse;
import com.netscape.utrain.response.SessionDetailResponse;
import com.netscape.utrain.response.SpaceDetailResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CheckNetwork;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrgHomeScreen extends AppCompatActivity {
    public DrawerLayout drawer;
    public BottomNavigationView orgNavView;
    boolean isInternetPresent = false;
    private TextView mTextMessage;
    private OActivityBottomNavigationBinding binding;
    private boolean doubleBackToExitPressedOnce = false;
    private AppCompatImageView orgDrawerImageNew;
    private CircleImageView navImageView;
    private MaterialTextView navNameTv, orgNotification;
    private ProgressDialog progressDialog;
    private Retrofitinterface retrofitinterface;
    private AlertDialog dialogMultiOrder;
    private int count = 0;
    private ArrayList<String> data;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    orgNotification.setText("");
                    fragment = new O_HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_chat:
                    orgNotification.setText("");
                    fragment = new TransactionFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_reqimage:
                    orgNotification.setText("");
                    fragment = new O_RegistrationProfile();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_running:

                    orgNotification.setText("");
                    fragment = new O_HistoryFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_notifications:
                    SetNotificationRead();
                    orgNotification.setText(R.string.title_notifications);
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
//        setContentView(R.layout.a_activity_bottom_navigation);
        binding = DataBindingUtil.setContentView(OrgHomeScreen.this, R.layout.o_activity_bottom_navigation);
        orgDrawerImageNew = findViewById(R.id.orgDrawerImageNew);
        orgNavView = findViewById(R.id.orgNavView);
        drawer = findViewById(R.id.orgdrawer_layout);
        orgNotification = findViewById(R.id.orgNotification);
        navImageView = binding.orgSlider.getHeaderView(0).findViewById(R.id.naviProfileImage);
        navNameTv = binding.orgSlider.getHeaderView(0).findViewById(R.id.navNameTv);
        String path = CommonMethods.getPrefData(PrefrenceConstant.PROFILE_IMAGE, OrgHomeScreen.this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);

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
        onNewIntent(getIntent());

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
        Call<LogoutResponse> signUpAthlete = retrofitinterface.LogoutApi(Constants.CONTENT_TYPE, "Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, OrgHomeScreen.this));
        signUpAthlete.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            Toast.makeText(getApplicationContext(), response.body().getData().getScalar(), Toast.LENGTH_SHORT).show();
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
                        Snackbar.make(binding.orgContainer, errorMessage, BaseTransientBottomBar.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Snackbar.make(binding.orgContainer, e.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
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
        Call<NotificationCountResponse> signUpAthlete = retrofitinterface.getNewNotificationCount(Constants.CONTENT_TYPE, "Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, OrgHomeScreen.this));
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
        Call<NotificationReadResponse> signUpAthlete = retrofitinterface.setNewNotificationRead(Constants.CONTENT_TYPE, "Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, OrgHomeScreen.this));
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

    @Override
    public void onNewIntent(Intent intentData) {

        super.onNewIntent(intentData);
//        Bundle extras = intentData.getExtras();
//
//        if (extras !=null) {
//            Log.d("SystemTray", extras.toString());
//        }
        String data = intentData.getStringExtra("pushnotification");
//        Log.d("StringNoti",data);
        if (data != null) {
            String typeData = "";
            String id = "";
            String targetType = "";
            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                typeData = jsonObject1.getString("target_model");
                id = jsonObject1.getString("target_id");
                if (jsonObject1.getString("target_type") != null)
                    targetType = jsonObject1.getString("target_type");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (typeData.equalsIgnoreCase("space")) {
                hitSpaceDetailAPI(id);
            }
            if (typeData.equalsIgnoreCase("booking")) {
                if (targetType.equalsIgnoreCase("event")) {
//                hitEventDetailAPI(id);
                    Intent topCoachesDetails = new Intent(getApplicationContext(), BookingDetails.class);
                    topCoachesDetails.putExtra(Constants.SELECTED_ID, id);
                    topCoachesDetails.putExtra(Constants.SELECTED_TYPE, Constants.EVENT);
//                        topCoachesDetails.putExtra(Constants.STATUS, status);
                    startActivity(topCoachesDetails);
                }
                if (targetType.equalsIgnoreCase("session")) {
//                getSessionDetails(id);
                    Intent topCoachesDetails = new Intent(getApplicationContext(), BookingDetails.class);
                    topCoachesDetails.putExtra(Constants.SELECTED_ID, id);
                    topCoachesDetails.putExtra(Constants.SELECTED_TYPE, Constants.SESSION);
//                topCoachesDetails.putExtra(Constants.STATUS, status);
                    startActivity(topCoachesDetails);
                }
                if (targetType.equalsIgnoreCase("space")) {
                    Intent topCoachesDetails = new Intent(getApplicationContext(), BookingDetails.class);
                    topCoachesDetails.putExtra(Constants.SELECTED_ID, id);
                    topCoachesDetails.putExtra(Constants.SELECTED_TYPE, Constants.SPACE);
//                topCoachesDetails.putExtra(Constants.STATUS, status);
                    startActivity(topCoachesDetails);
                }
            }
            Log.d("pushnotification", "onNewIntent: " + data);
        }
    }

    public void hitSpaceDetailAPI(String id) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading.........");
        progressDialog.show();
        Call<SpaceDetailResponse> signUpAthlete = retrofitinterface.spaceDetail("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, OrgHomeScreen.this), Constants.CONTENT_TYPE, id);
        signUpAthlete.enqueue(new Callback<SpaceDetailResponse>() {
            @Override
            public void onResponse(Call<SpaceDetailResponse> call, Response<SpaceDetailResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            Intent intent = new Intent(OrgHomeScreen.this, EventDetail.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("eventName", response.body().getData().getName());
                            intent.putExtra("eventVenue", response.body().getData().getLocation());
                            intent.putExtra("eventTime", response.body().getData().getOpen_hours_from());
                            intent.putExtra("eventALLImages", response.body().getData().getImages());
                            intent.putExtra("eventEndTime", response.body().getData().getOpen_hours_to());
//                            intent.putExtra("eventDate", response.body().getData().getAvailability_week());
                            intent.putExtra("image_url", Constants.IMAGE_BASE_PLACE);
                            intent.putExtra("event_id", response.body().getData().getId() + "");
                            intent.putExtra("from", "places");
                            intent.putExtra("desc", response.body().getData().getDescription());
                            intent.putExtra("gmapLat", response.body().getData().getLatitude() + "");
                            intent.putExtra("gmapLong", response.body().getData().getLongitude() + "");
                            Bundle b = new Bundle();
                            b.putString("Array", response.body().getData().getImages());
                            intent.putExtras(b);
                            data = new ArrayList<>();
                            data = (ArrayList<String>) response.body().getData().getAvailability_week();
                            intent.putStringArrayListExtra(Constants.SPACE_DATA, data);
                            startActivity(intent);

                        }
                    }
                } else {
                    progressDialog.dismiss();

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
//                        Snackbar.make(binding.maineventBooking, errorMessage.toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                        Toast.makeText(OrgHomeScreen.this, "" + errorMessage, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Toast.makeText(OrgHomeScreen.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

//                        Snackbar.make(binding.athleteLoginLayout,e.getMessage().toString(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SpaceDetailResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OrgHomeScreen.this, "" + getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();

//                Snackbar.make(binding.maineventBooking, getResources().getString(R.string.something_went_wrong), BaseTransientBottomBar.LENGTH_LONG).show();

            }
        });
    }

}
