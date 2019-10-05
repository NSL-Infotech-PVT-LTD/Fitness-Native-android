package com.netscape.utrain.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.netscape.utrain.R;
import com.netscape.utrain.adapters.ServicesBottomSheetAdapter;
import com.netscape.utrain.model.CoachDataModel;
import com.netscape.utrain.model.CoachListModel;
import com.netscape.utrain.utils.Constants;

public class TopCoachesDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    //    private ActivityTopCoachesDetailsBinding binding;
    private CoachListModel coachListModel;
    private int type;
    private BottomSheetBehavior sheetBehavior;
    private LinearLayout liearLayout;
    private RecyclerView.LayoutManager layoutManager;
    private ServicesBottomSheetAdapter bottomSheetAdapter;
    private RecyclerView serviceRecycler;
    private ImageView profImage, backArrow;
    private TextView name, typeUser, service, bio, price, training, title, moreServices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_coaches_details);
//        binding= DataBindingUtil.setContentView(this,R.layout.activity_top_coaches_details);

        init();
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
    }

    private void setData() {
        if (coachListModel != null) {
            if (type == 1) {
                title.setText("Coach");
                Glide.with(this).load(Constants.COACH_IMAGE_BASE_URL + coachListModel.getProfile_image()).into(profImage);
            }
            if (type == 2) {
                title.setText("Organization");
                Glide.with(this).load(Constants.ORG_IMAGE_BASE_URL + coachListModel.getProfile_image()).into(profImage);
            }
            name.setText(coachListModel.getName());
            if (coachListModel.getRoles()!=null && coachListModel.getRoles().size()>0) {
                typeUser.setText(coachListModel.getRoles().get(0).getName());
            }
            if (coachListModel.getService_ids()!=null && coachListModel.getService_ids().size()>0) {
//            for (int i=0;i<data.getService_ids().size();i++){
                service.setText(coachListModel.getService_ids().get(0).getName());
//            }


            }
//            binding.detailUserType.setText(coachListModel.getName());
//            binding.detailUserService.setText(coachListModel.getName());
//            binding.discoverRating.setText(coachListModel.getName());
            bio.setText(coachListModel.getBio());
//            binding.detailNumTraineTv.setText("");
            price.setText("$ " + coachListModel.getHourly_rate());
        }
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
}
