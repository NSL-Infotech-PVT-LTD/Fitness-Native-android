package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivitySelecteTimeSlotBinding;
import com.netscape.utrain.model.ServiceListDataModel;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.PrefrenceConstant;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SelecteTimeSlot extends AppCompatActivity {
    private ActivitySelecteTimeSlotBinding binding;
    private ArrayList<ServiceListDataModel> sList = new ArrayList<>();
    ChipGroup chipGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_selecte_time_slot);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_selecte_time_slot);

        chipGroup = new ChipGroup(this);
        chipGroup.setSingleSelection(true);

        getService();
        setChips();
    }
    private void getService() {
        String serviceName = CommonMethods.getPrefData(PrefrenceConstant.SERVICE_IDS, getApplicationContext());
        Gson gson = new Gson();

        if (serviceName != null) {
            if (serviceName.isEmpty()) {
                Toast.makeText(SelecteTimeSlot.this, "Service Not Found", Toast.LENGTH_SHORT).show();
            } else {
                Type type = new TypeToken<List<ServiceListDataModel>>() {
                }.getType();
                sList = gson.fromJson(serviceName, type);
                sList.addAll(sList);
                sList.addAll(sList);

                StringBuilder builder = new StringBuilder();
                for (ServiceListDataModel details : sList) {
                    builder.append(details.getName() + "\n");

                }

            }
        }
    }
    private void setChips() {
        for (int i = 0; i < sList.size(); i++) {
            final Chip chip = new Chip(this);
            chip.setEnabled(true);
            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Filter);
            chip.setChipDrawable(chipDrawable);
            chip.setTextColor(getResources().getColor(R.color.colorWhite));
            chip.setMaxWidth(200);
            chip.setText(sList.get(i).getName()+"..");
            chip.setTag(sList.get(i).getId());
            chipGroup.addView(chip);
        }
        chipGroup.setEnabled(true);
        chipGroup.setChipSpacingVertical(20);
        binding.constraintChipGroup.addView(chipGroup);
    }

}
