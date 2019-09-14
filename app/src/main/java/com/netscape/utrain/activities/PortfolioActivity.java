package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityPortfolioBinding;

public class PortfolioActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityPortfolioBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_portfolio);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addImageOne:
                break;
            case R.id.addImageTwo:
                break;
            case R.id.addImageThree:
                break;
            case R.id.addImageFour:
                break;
            case R.id.addImageSubmitBtn:
                break;
            case R.id.addImageBack:

                break;
        }
    }
}
