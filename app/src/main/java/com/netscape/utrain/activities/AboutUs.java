package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityAboutUsBinding;
import com.netscape.utrain.model.AboutUsModel;
import com.netscape.utrain.response.AboutUsResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUs extends AppCompatActivity {

    private ActivityAboutUsBinding binding;
    private AppCompatImageView aboutUsBackImg;
    private Retrofitinterface retrofitinterface;
    private AboutUsModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_about_us);
        binding = DataBindingUtil.setContentView(AboutUs.this, R.layout.activity_about_us);
        aboutUsBackImg = findViewById(R.id.aboutUsBackImg);

        aboutUsBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        hitAboutUsApi();
    }

    private void hitAboutUsApi() {

        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);

        Call<AboutUsResponse> aboutUsResponseCall = retrofitinterface.aboutUs();
        aboutUsResponseCall.enqueue(new Callback<AboutUsResponse>() {
            @Override
            public void onResponse(Call<AboutUsResponse> call, Response<AboutUsResponse> response) {

                if (response.isSuccessful())

                    if (response.body() != null) {
                        if (response.body().isStatus())
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                binding.htmlViewText.setText(Html.fromHtml(response.body().getData().getAbout_us()+"", Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                binding.htmlViewText.setText(Html.fromHtml(response.body().getData().getAbout_us()+""));
                            }

                    }
            }

            @Override
            public void onFailure(Call<AboutUsResponse> call, Throwable t) {

            }
        });


    }
}
