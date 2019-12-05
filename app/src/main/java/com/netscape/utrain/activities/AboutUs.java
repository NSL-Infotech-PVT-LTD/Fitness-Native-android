package com.netscape.utrain.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.netscape.utrain.R;
import com.netscape.utrain.databinding.ActivityAboutUsBinding;
import com.netscape.utrain.model.AboutUsModel;
import com.netscape.utrain.response.AboutUsResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUs extends AppCompatActivity {

    private ActivityAboutUsBinding binding;
    private AppCompatImageView aboutUsBackImg;
    private Retrofitinterface retrofitinterface;
    private AboutUsModel model;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_about_us);
        binding = DataBindingUtil.setContentView(AboutUs.this, R.layout.activity_about_us);
        aboutUsBackImg = findViewById(R.id.aboutUsBackImg);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");

        aboutUsBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        hitAboutUsApi();
    }

    private void hitAboutUsApi() {
        progressDialog.show();
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);

        Call<AboutUsResponse> aboutUsResponseCall = retrofitinterface.aboutUs();
        aboutUsResponseCall.enqueue(new Callback<AboutUsResponse>() {
            @Override
            public void onResponse(Call<AboutUsResponse> call, Response<AboutUsResponse> response) {

                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body() != null) {
                        if (response.body().isStatus())
                            if (response.body().getData()!=null && response.body().getData().size()>0) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    binding.htmlViewText.setText(Html.fromHtml(response.body().getData().get(0).getAbout_us() + "", Html.FROM_HTML_MODE_COMPACT));
                                } else {
                                    binding.htmlViewText.setText(Html.fromHtml(response.body().getData().get(0).getAbout_us() + ""));
                                }
                            }

                    }else {
                        Toast.makeText(AboutUs.this, "Something Went wrong", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONArray errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message");
                        String errorMsg = errorMessage.getJSONObject(0).getString("message");
                        Snackbar.make(binding.aboutUs, errorMsg, BaseTransientBottomBar.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Snackbar.make(binding.aboutUs, e.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }                }
            }

            @Override
            public void onFailure(Call<AboutUsResponse> call, Throwable t) {
            progressDialog.dismiss();
            }
        });


    }
}
