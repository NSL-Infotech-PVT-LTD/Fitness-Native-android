package com.netscape.utrain.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AllEventsMapAct;
import com.netscape.utrain.adapters.CoachGridRecyclerAdapter;
import com.netscape.utrain.adapters.ServicesBottomSheetAdapter;
import com.netscape.utrain.databinding.ActivityTopCoachOrgDetailBinding;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.model.ServiceIdModel;
import com.netscape.utrain.model.SportListModel;
import com.netscape.utrain.response.CoachListResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class TopCoachOrgDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ChipGroup chipGroup;
    RelativeLayout add_service;
    ActivityTopCoachOrgDetailBinding binding;
    Retrofitinterface retrofitinterface;
    private CoachListModel coachListModel;

    private int type;
    private BottomSheetBehavior sheetBehavior;
    private LinearLayout liearLayout;
    private RecyclerView.LayoutManager layoutManager;
    private ServicesBottomSheetAdapter bottomSheetAdapter;
    private RecyclerView serviceRecycler;
    private ImageView profImage, backArrow;
    private ArrayList<SportListModel.DataBeanX.DataBean> sportList = new ArrayList<>();
    private ArrayList<String> portfolioImageList = new ArrayList<>();
    private TextView name, typeUser, service, bio, price, experienceTv, training, eventDateDetailTv, eventTimeDetailTv, title, moreServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_top_coach_org_detail);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_top_coach_org_detail);
        binding.detailMapDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = null;
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:19.076,72.8777"));
                startActivity(intent);

            }
        });

        init();

        getSportsIds();


        String saveIntent = getIntent().getStringExtra("intentFrom");
        if (!TextUtils.isEmpty(saveIntent)) {

            if (saveIntent.equalsIgnoreCase("coach")) {
                binding.viewEvents.setVisibility(View.VISIBLE);
                binding.viewSession.setVisibility(View.VISIBLE);
                binding.viewSpaces.setVisibility(View.GONE);
                binding.layoutBtnConstraint.setVisibility(View.GONE);
                binding.view2.setVisibility(View.GONE);
            }
            if (saveIntent.equalsIgnoreCase("org")) {
                binding.viewEvents.setVisibility(View.VISIBLE);
                binding.viewSession.setVisibility(View.VISIBLE);
                binding.viewSpaces.setVisibility(View.VISIBLE);
                getPortfolioImages();
                setPortFolidImages();
            }
        }
        binding.viewEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TopCoachOrgDetailActivity.this, AllEventsMapAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("from", "topEvent");
                intent.putExtra("coach_id", coachListModel.getId() + "");
                startActivity(intent);
            }
        });

        binding.viewSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vSesssion = new Intent(TopCoachOrgDetailActivity.this, AllEventsMapAct.class);
                vSesssion.putExtra("from", "topSession");
                vSesssion.putExtra("coach_id", coachListModel.getId() + "");
                startActivity(vSesssion);
            }
        });

        binding.viewSpaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent vEventOrg = new Intent(TopCoachOrgDetailActivity.this, AllEventsMapAct.class);
                vEventOrg.putExtra("from", "topSpace");
                vEventOrg.putExtra("coach_id", coachListModel.getId() + "");
                startActivity(vEventOrg);
            }
        });


    }

    private void setPortFolidImages() {
        if (portfolioImageList.size() > 0) {
            binding.portfolioText.setVisibility(View.VISIBLE);
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
        name = findViewById(R.id.detailUserName);
        typeUser = findViewById(R.id.detailUserType);
        service = findViewById(R.id.detailUserService);
        bio = findViewById(R.id.detailUserBioTv);
        price = findViewById(R.id.detailPriceTv);
        moreServices = findViewById(R.id.moreServices);
        training = findViewById(R.id.detailNumTraineTv);
        training = findViewById(R.id.detailNumTraineTv);
        title = findViewById(R.id.topDetailsTitleTv);
        backArrow = findViewById(R.id.detailBackArrow);
        profImage = findViewById(R.id.detailImage);
        eventTimeDetailTv = findViewById(R.id.eventTimeDetailTv);
        eventDateDetailTv = findViewById(R.id.eventDateDetailTv);
//        experienceTv = findViewById(R.id.experienceTv);
        add_service = findViewById(R.id.add_service);

        chipGroup = new ChipGroup(this);
        chipGroup.setSingleSelection(false);

        liearLayout = findViewById(R.id.bottomsheet_services);
        serviceRecycler = findViewById(R.id.servicMoreRecycler);
        sheetBehavior = BottomSheetBehavior.from(liearLayout);
        bottomSheetBehavior_sort();
        layoutManager = new LinearLayoutManager(this);
        if (getIntent().getExtras() != null) {
            coachListModel = (CoachListModel) getIntent().getSerializableExtra(Constants.TOP_DATA_INTENT);
            type = Integer.parseInt(getIntent().getStringExtra(Constants.TOP_FROM_INTENT));
        }
        setData();
        backArrow.setOnClickListener(this);
        moreServices.setOnClickListener(this);
        liearLayout.setOnClickListener(this);
        binding.viewBookings.setOnClickListener(this);
    }

    private void setData() {
        if (coachListModel != null) {
            if (type == 1) {
                title.setText("Coach");
                Glide.with(this).load(Constants.COACH_IMAGE_BASE_URL + coachListModel.getProfile_image()).thumbnail(Glide.with(this).load(Constants.COACH_IMAGE_BASE_URL + Constants.THUMBNAILS + coachListModel.getProfile_image())).into(profImage);
//                Glide.with(this).load(Constants.COACH_IMAGE_BASE_URL + coachListModel.getProfile_image()).thumbnail(Glide.with(this).load(Constants.COACH_IMAGE_BASE_URL + Constants.THUMBNAILS + coachListModel.getProfile_image())).into(binding.port1);


                for (int i = 0; i < coachListModel.getService_ids().size(); i++) {
                    final Chip chip = new Chip(this);
                    chip.setEnabled(false);
                    ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Filter);
                    chip.setChipDrawable(chipDrawable);
                    chip.setTextColor(getResources().getColor(R.color.colorWhite));
                    chip.setText(coachListModel.getService_ids().get(i).getName());
                    chip.setTag(coachListModel.getService_ids().get(i).getId());
                    chipGroup.addView(chip);
                }
                chipGroup.setEnabled(false);
                add_service.addView(chipGroup);
            }
        }
        if (type == 2) {
            title.setText("Organization");
            Glide.with(this).load(Constants.ORG_IMAGE_BASE_URL + coachListModel.getProfile_image()).thumbnail(Glide.with(this).load(Constants.ORG_IMAGE_BASE_URL + Constants.THUMBNAILS + coachListModel.getProfile_image())).into(profImage);
//            Glide.with(this).load(Constants.ORG_IMAGE_BASE_URL + coachListModel.getProfile_image()).thumbnail(Glide.with(this).load(Constants.ORG_IMAGE_BASE_URL + Constants.THUMBNAILS + coachListModel.getProfile_image())).into(binding.port1);
            for (int i = 0; i < coachListModel.getService_ids().size(); i++) {
                final Chip chip = new Chip(this);
                chip.setEnabled(false);
                ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Filter);
                chip.setChipDrawable(chipDrawable);
                chip.setTextColor(getResources().getColor(R.color.colorWhite));

                chip.setText(coachListModel.getService_ids().get(i).getName());
                chip.setTag(coachListModel.getService_ids().get(i).getId());


                chipGroup.addView(chip);
            }

            chipGroup.setEnabled(false);


            add_service.addView(chipGroup);
        }
        name.setText(coachListModel.getName());
        binding.location.setText(coachListModel.getLocation());


        if (coachListModel.getRoles() != null && coachListModel.getRoles().size() > 0) {
            typeUser.setText(coachListModel.getRoles().get(0).getName());
        }
        if (coachListModel.getService_ids() != null && coachListModel.getService_ids().size() > 0) {
//            for (int i=0;i<data.getService_ids().size();i++){
            service.setText(coachListModel.getService_ids().get(0).getName());
//            }


        }
//            binding.detailUserType.setText(coachListModel.getName());
//            binding.detailUserService.setText(coachListModel.getName());
//            binding.discoverRating.setText(coachListModel.getName());TopDetailActivity
        bio.setText(coachListModel.getBio());
//            binding.detailNumTraineTv.setText("");
        price.setText("$ " + coachListModel.getHourly_rate());
        eventTimeDetailTv.setText(coachListModel.getBusiness_hour_starts());
//        experienceTv.setText(coachListModel.getExpertise_years()+"+ Years");
        eventDateDetailTv.setText(coachListModel.getExpertise_years() + "+ Years");
    }


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
            case R.id.viewBookings:
                Intent intent=new Intent(TopCoachOrgDetailActivity.this,CalendarViewWithNotesActivity.class);
                intent.putExtra("fromCalendar",title.getText().toString());
                startActivity(intent);
                break;
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

    private void getSportsIds() {
        String sportName = coachListModel.getSport_id();
        Gson gson = new Gson();

        if (sportName != null) {
            if (sportName.isEmpty()) {
                Toast.makeText(TopCoachOrgDetailActivity.this, "Service Not Found", Toast.LENGTH_SHORT).show();
            } else {
                Type type = new TypeToken<List<SportListModel.DataBeanX.DataBean>>() {
                }.getType();
                sportList = gson.fromJson(sportName, type);

                StringBuilder builder = new StringBuilder();
                for (SportListModel.DataBeanX.DataBean details : sportList) {
                    builder.append(details.getName() + "\n");
                }
                binding.sportsDetail.setText(builder.toString());
            }
        } else {
            binding.sportsDetail.setVisibility(View.GONE);
            binding.sportsName.setVisibility(View.GONE);

        }
    }

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
}



