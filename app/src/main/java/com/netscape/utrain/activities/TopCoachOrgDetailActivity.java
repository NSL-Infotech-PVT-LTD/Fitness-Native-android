package com.netscape.utrain.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.internal.Utility;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AllEventsMapAct;
import com.netscape.utrain.activities.athlete.EventDetail;
import com.netscape.utrain.adapters.CalendarEventListAdapter;
import com.netscape.utrain.adapters.ServicesBottomSheetAdapter;
import com.netscape.utrain.databinding.ActivityTopCoachOrgDetailBinding;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.model.SportListModel;
import com.netscape.utrain.response.ViewCoachListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.Constants;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopCoachOrgDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ChipGroup chipGroup;

    ActivityTopCoachOrgDetailBinding binding;
    Retrofitinterface retrofitinterface;
    private CoachListModel coachListModel;

    private int type;
    private BottomSheetBehavior sheetBehavior;
    private LinearLayout liearLayout;
    private RecyclerView.LayoutManager layoutManager;
    private ServicesBottomSheetAdapter bottomSheetAdapter;
    private RecyclerView serviceRecycler;
    private ImageView backArrow;
    private ArrayList<SportListModel.DataBeanX.DataBean> sportList = new ArrayList<>();
    private ArrayList<String> portfolioImageList = new ArrayList<>();
    private TextView service, experienceTv, training, eventDateDetailTv, eventTimeDetailTv, title, moreServices;
    private ArrayList<CoachListModel> sList = new ArrayList<>();
    private ViewCoachListResponse viewCoachListResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_top_coach_org_detail);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_top_coach_org_detail);
        binding.detailMapDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(coachListModel.getLatitude()) && ! TextUtils.isEmpty(coachListModel.getLongitude())) {
                    float latitude = Float.parseFloat(coachListModel.getLatitude());
                    float longitude = Float.parseFloat(coachListModel.getLongitude());
//                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 28.7040, 77.1025);
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//               startActivity(intent);

                    String uri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);
                }else {
                    Toast.makeText(TopCoachOrgDetailActivity.this, "No lat long found", Toast.LENGTH_SHORT).show();
                }


//                float latitude = Float.parseFloat();
//                float longitude = Float.parseFloat();
////                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
////                        Uri.parse("http://maps.google.com/maps?saddr="+latitude +","+longitude));
////                startActivity(intent);
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                        Uri.parse("http://maps.google.com/maps?saddr="+latitude+","+longitude+"&daddr="+latitude+","+longitude+""));
//                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(intent);

//                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
//                String geoUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + coachListModel.getName() + ")";
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
//                startActivity(intent);

//                Uri mapUri = Uri.parse("geo:0,0?q="+latitude+","+longitude+"lng(label)");
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                startActivity(mapIntent);
            }
        });

        chipGroup = new ChipGroup(this);
        chipGroup.setSingleSelection(false);

        if (getIntent().getExtras() != null) {
            coachListModel = (CoachListModel) getIntent().getSerializableExtra(Constants.TOP_DATA_INTENT);
            type = Integer.parseInt(getIntent().getStringExtra(Constants.TOP_FROM_INTENT));
            binding.cYearsOfExpTv.setText(coachListModel.getExpertise_years() + " Year ");
            binding.detailUserBioTv.setText(coachListModel.getBio());
            binding.detailPriceTv.setText("$"+coachListModel.getHourly_rate() + "");
            binding.eventTimeDetailTv.setText(coachListModel.getBusiness_hour_starts());
            binding.toTimeTv.setText(coachListModel.getBusiness_hour_ends());
            binding.eventDateDetailTv.setText(coachListModel.getExpertise_years() + " Year");
            binding.detailUserName.setText(coachListModel.getName());
            binding.discoverRating.setRating(Float.parseFloat(coachListModel.getRating()));
            if (coachListModel.getRating()!=null) {
                if (coachListModel.getRating().equalsIgnoreCase("0")) {
                    binding.noRatingText.setVisibility(View.VISIBLE);
                    binding.discoverRating.setVisibility(View.GONE);
                } else {
                    binding.discoverRating.setVisibility(View.VISIBLE);
                    binding.noRatingText.setVisibility(View.GONE);
                    binding.discoverRating.setRating(Float.parseFloat(coachListModel.getRating()));
                }
            }
            binding.discoverRating.setRating(Float.parseFloat(coachListModel.getRating()));
        }
        init();
        String saveIntent = getIntent().getStringExtra("intentFrom");
        if (!TextUtils.isEmpty(saveIntent)) {

            if (saveIntent.equalsIgnoreCase("coach")) {

                binding.viewOrgBtnLayout.setVisibility(View.GONE);
//                binding.view2.setVisibility(View.GONE);
                binding.btnView.setVisibility(View.GONE);
                Glide.with(TopCoachOrgDetailActivity.this).load(Constants.COACH_IMAGE_BASE_URL + coachListModel.getProfile_image()).thumbnail(Glide.with(this).load(Constants.COACH_IMAGE_BASE_URL + Constants.THUMBNAILS + coachListModel.getProfile_image())).into(binding.detailImage);
            }
            if (saveIntent.equalsIgnoreCase("org")) {

                binding.viewOrgBtnLayout.setVisibility(View.VISIBLE);
                Glide.with(this).load(Constants.ORG_IMAGE_BASE_URL + coachListModel.getProfile_image()).thumbnail(Glide.with(this).load(Constants.ORG_IMAGE_BASE_URL + Constants.THUMBNAILS + coachListModel.getProfile_image())).into(binding.detailImage);
                getPortfolioImages();
                setPortFolioImages();
            }
        }
        binding.viewEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TopCoachOrgDetailActivity.this, AllEventsMapAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("from", "1");
                intent.putExtra("coach_id", coachListModel.getId() + "");
                startActivity(intent);
            }
        });

        binding.bookingCoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vSesssion = new Intent(TopCoachOrgDetailActivity.this, HourlySlotsActivity.class);

                startActivity(vSesssion);
            }
        });

        binding.viewSpacesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent vEventOrg = new Intent(TopCoachOrgDetailActivity.this, AllEventsMapAct.class);
                vEventOrg.putExtra("from", "3");
                vEventOrg.putExtra("coach_id", coachListModel.getId() + "");
                startActivity(vEventOrg);
            }
        });
        binding.viewCoachStaffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Redirecting to ViewCoachListActivity....
                Intent intent = new Intent(TopCoachOrgDetailActivity.this, ViewCoachStaffListActivity.class);
                intent.putExtra("fromTopOrgAct","");
                intent.putExtra("coachList",coachListModel.getId()+"");
                startActivity(intent);

            }
        });


    }

    private void setPortFolioImages() {

        if (portfolioImageList.size() > 0) {
            binding.portfolioText.setVisibility(View.VISIBLE);
            binding.viewPriceBottom.setVisibility(View.VISIBLE);
            binding.portfolioImagesConstraint.setVisibility(View.VISIBLE);

            for (int i = 0; i < portfolioImageList.size(); i++) {
                if (i == 0) {
                    binding.pcard1.setVisibility(View.VISIBLE);
                    Glide.with(TopCoachOrgDetailActivity.this).load(Constants.ORG_PORTFOLIO_IMAGE_BASE_URL + portfolioImageList.get(i)).into(binding.port1);
                }
                if (i == 1) {
                    binding.pcard2.setVisibility(View.VISIBLE);
                    Glide.with(TopCoachOrgDetailActivity.this).load(Constants.ORG_PORTFOLIO_IMAGE_BASE_URL + portfolioImageList.get(i)).into(binding.port2);

                }
                if (i == 2) {
                    binding.pcard3.setVisibility(View.VISIBLE);
                    Glide.with(TopCoachOrgDetailActivity.this).load(Constants.ORG_PORTFOLIO_IMAGE_BASE_URL + portfolioImageList.get(i)).into(binding.port3);

                }
                if (i == 3) {
                    binding.pcard4.setVisibility(View.VISIBLE);
                    Glide.with(TopCoachOrgDetailActivity.this).load(Constants.ORG_PORTFOLIO_IMAGE_BASE_URL + portfolioImageList.get(i)).into(binding.port4);

                }
            }
        }
    }

    private void init() {
        moreServices = findViewById(R.id.moreServices);
        training = findViewById(R.id.detailNumTraineTv);
        training = findViewById(R.id.detailNumTraineTv);
        title = findViewById(R.id.topDetailsTitleTv);
        backArrow = findViewById(R.id.detailBackArrow);
        eventTimeDetailTv = findViewById(R.id.eventTimeDetailTv);
        eventDateDetailTv = findViewById(R.id.eventDateDetailTv);
//        experienceTv = findViewById(R.id.experienceTv);
        liearLayout = findViewById(R.id.bottomsheet_services);
        serviceRecycler = findViewById(R.id.servicMoreRecycler);
        sheetBehavior = BottomSheetBehavior.from(liearLayout);
        bottomSheetBehavior_sort();
        layoutManager = new LinearLayoutManager(this);
        setData();
        backArrow.setOnClickListener(this);
        moreServices.setOnClickListener(this);
        liearLayout.setOnClickListener(this);
//        binding.viewBookings.setOnClickListener(this);
    }

    private void setData() {
        if (coachListModel != null) {
            if (type == 1) {
                title.setText("Coach");
//                Glide.with(TopCoachOrgDetailActivity.this).load(Constants.COACH_IMAGE_BASE_URL + coachListModel.getProfile_image()).thumbnail(Glide.with(this).load(Constants.COACH_IMAGE_BASE_URL + Constants.THUMBNAILS + coachListModel.getProfile_image())).into(binding.detailImage);
//                Glide.with(this).load(Constants.COACH_IMAGE_BASE_URL + coachListModel.getProfile_image()).thumbnail(Glide.with(this).load(Constants.COACH_IMAGE_BASE_URL + Constants.THUMBNAILS + coachListModel.getProfile_image())).into(binding.port1);

                for (int i = 0; i < coachListModel.getService_ids().size(); i++) {
                    final Chip chip = new Chip(this);
                    chip.setEnabled(false);
                    ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Filter);
                    chip.setChipDrawable(chipDrawable);
                    chip.setMaxWidth(200);
                    chip.setTextColor(getResources().getColor(R.color.colorWhite));
                    chip.setText(coachListModel.getService_ids().get(i).getName()+"..");
                    chip.setTag(coachListModel.getService_ids().get(i).getId());
                    chipGroup.addView(chip);
                }
                chipGroup.setEnabled(false);
                chipGroup.setChipSpacingVertical(20);
                binding.constraintChipGroup.addView(chipGroup);
            }
        }
        if (type == 2) {
            title.setText("Organization");
//            Glide.with(this).load(Constants.ORG_IMAGE_BASE_URL + coachListModel.getProfile_image()).thumbnail(Glide.with(this).load(Constants.ORG_IMAGE_BASE_URL + Constants.THUMBNAILS + coachListModel.getProfile_image())).into(binding.detailImage);
//            Glide.with(this).load(Constants.ORG_IMAGE_BASE_URL + coachListModel.getProfile_image()).thumbnail(Glide.with(this).load(Constants.ORG_IMAGE_BASE_URL + Constants.THUMBNAILS + coachListModel.getProfile_image())).into(binding.port1);

            for (int i = 0; i < coachListModel.getService_ids().size(); i++) {
                final Chip chip = new Chip(this);
                chip.setEnabled(false);
                ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Filter);
                chip.setChipDrawable(chipDrawable);
                chip.setTextColor(getResources().getColor(R.color.colorWhite));
                chip.setMaxWidth(200);
                chip.setText(coachListModel.getService_ids().get(i).getName()+"..");
                chip.setTag(coachListModel.getService_ids().get(i).getId());
                chipGroup.addView(chip);
            }
            chipGroup.setEnabled(false);
            chipGroup.setChipSpacingVertical(20);
            binding.constraintChipGroup.addView(chipGroup);
        }


//        if (coachListModel.getRoles() != null && coachListModel.getRoles().size() > 0) {
//            typeUser.setText(coachListModel.getRoles().get(0).getName());
//        }
//        if (coachListModel.getService_ids() != null && coachListModel.getService_ids().size() > 0) {
//            for (int i=0;i<data.getService_ids().size();i++){
//            binding.addService.addView(coachListModel.getService_ids().get(0).getName());


    }
//            binding.detailUserType.setText(coachListModel.getName());
//            binding.detailUserService.setText(coachListModel.getName());
//            binding.discoverRating.setText(coachListModel.getName());
//        bio.setText(coachListModel.getBio());
////            binding.detailNumTraineTv.setText("");
//        price.setText("$ " + coachListModel.getHourly_rate());
//        eventTimeDetailTv.setText(coachListModel.getBusiness_hour_starts());
////        experienceTv.setText(coachListModel.getExpertise_years()+"+ Years");
//        eventDateDetailTv.setText(coachListModel.getExpertise_years() + "+ Years");


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detailBackArrow:
                finish();
                break;
            case R.id.detailJoinNowBtn:
                break;
            case R.id.moreServices:
                bottomSheetUpDown_address();
                break;
            case R.id.bottomsheet_services:
                bottomSheetUpDown_address();
                break;
//            case R.id.viewBookings:
//                Intent intent=new Intent(TopCoachOrgDetailActivity.this,CalendarViewWithNotesActivity.class);
//                intent.putExtra("fromCalendar",title.getText().toString());
//                startActivity(intent);
//                break;
        }

    }


    private void bottomSheetUpDown_address() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        getDataToDisplay();

    }

    private void getDataToDisplay() {
        bottomSheetAdapter = new ServicesBottomSheetAdapter(getApplicationContext(), coachListModel.getService_ids());
        serviceRecycler.setLayoutManager(layoutManager);
        serviceRecycler.setAdapter(bottomSheetAdapter);
    }

    private void bottomSheetBehavior_sort() {
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

//    private void getSportsIds() {
//        String sportName = coachListModel.getSport_id();
//        Gson gson = new Gson();
//
//        if (sportName != null) {
//            if (sportName.isEmpty()) {
//                Toast.makeText(TopCoachOrgDetailActivity.this, "Service Not Found", Toast.LENGTH_SHORT).show();
//            } else {
//                Type type = new TypeToken<List<SportListModel.DataBeanX.DataBean>>() {
//                }.getType();
//                sportList = gson.fromJson(sportName, type);
//
//                StringBuilder builder = new StringBuilder();
//                for (SportListModel.DataBeanX.DataBean details : sportList) {
//                    builder.append(details.getName() + "\n");
//                }
////                binding.sportsDetail.setText(builder.toString());
//            }
////        } else {
////            binding.sportsDetail.setVisibility(View.GONE);
////            binding.sportsName.setVisibility(View.GONE);
////
//        }
//    }

    private void getPortfolioImages() {
        Gson gson = new Gson();

        if (coachListModel.getPortfolio_image() != null) {
            if (coachListModel.getPortfolio_image().isEmpty()) {
                Toast.makeText(TopCoachOrgDetailActivity.this, "No portfolio images", Toast.LENGTH_SHORT).show();
            } else {
                Type type = new TypeToken<List<String>>() {
                }.getType();
                portfolioImageList = gson.fromJson(coachListModel.getPortfolio_image(), type);
            }
        }
    }

//    private void getService() {
//        String serviceName = CommonMethods.getPrefData(PrefrenceConstant.SERVICE_IDS, getApplicationContext());
//        Gson gson = new Gson();
//
//        if (serviceName != null) {
//            if (serviceName.isEmpty()) {
//                Toast.makeText(TopCoachOrgDetailActivity.this, "Service Not Found", Toast.LENGTH_SHORT).show();
//            } else {
//                Type type = new TypeToken<List<CoachListModel>>() {
//                }.getType();
//                sList = gson.fromJson(serviceName, type);
//
//                StringBuilder builder = new StringBuilder();
//                for (CoachListModel details : sList) {
//                    builder.append(details.getName() + "\n");
//
//                }
//
//            }
//        }
//    }
}



