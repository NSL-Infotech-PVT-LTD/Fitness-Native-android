package com.netscape.utrain.StripeConnect;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Presentation;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.netscape.utrain.activities.SettingsActivity;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;

import org.json.JSONObject;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StripeActivity extends Activity {

    private static final String TAG = "StripeActivity";

    private ProgressDialog mSpinner;
    private WebView mWebView;
    private LinearLayout mContent;
    private String mUrl;
    private String mCallBackUrl;
    private String mTokenUrl;
    private String mSecretKey;
    private String mAccountName;
    private Retrofitinterface retrofitinterface;

    static final FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);

        mUrl = getIntent().getStringExtra("url");
        mCallBackUrl = getIntent().getStringExtra("callbackUrl");
        mTokenUrl = getIntent().getStringExtra("tokenUrl");
        mSecretKey = getIntent().getStringExtra("secretKey");
        mAccountName = getIntent().getStringExtra("accountName");

        setUpWebView();

    }

    @SuppressWarnings("deprecation")
    @SuppressLint({"SetJavaScriptEnabled", "NewApi"})
    private void setUpWebView() {

        mSpinner = new ProgressDialog(this);
        mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpinner.setMessage("Loading...");

        mWebView = new WebView(this);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setWebViewClient(new OAuthWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(mUrl);
        mWebView.setLayoutParams(FILL);

        mContent = new LinearLayout(this);
        mContent.setOrientation(LinearLayout.VERTICAL);
        mContent.addView(mWebView);

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.FROYO) {
            addContentView(mContent, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
        } else {
            addContentView(mContent, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.FILL_PARENT));
        }

        StripeUtils.removeAllCookies(this);

    }

    private class OAuthWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            AppLog.d(TAG, "OAuthWebViewClient.shouldOverrideUrlLoading", "Redirecting URL " + url);


//            if (url.startsWith("https://dev.netscapelabs.com")) {
//
//                getconnecteStripe("");
//                return true;
//            }
            if (url.startsWith("https://dev.netscapelabs.com")) {

                String queryString = url.replace(mCallBackUrl + "/?", "");
                AppLog.d(TAG, "OAuthWebViewClient.shouldOverrideUrlLoading", "queryString:" + queryString);
                Map<String, String> parameters = StripeUtils.splitQuery(queryString);
                if (!url.contains("error")) {
                    onComplete(parameters);
                } else {
                    onError(parameters);
                }
                return true;
            }
            return false;
        }


        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            AppLog.e(TAG, "OAuthWebViewClient.onReceivedError", "Page error[errorCode=" + errorCode + "]: " + description);

            super.onReceivedError(view, errorCode, description, failingUrl);
            Map<String, String> error = new LinkedHashMap<String, String>();
            error.put("error", String.valueOf(errorCode));
            error.put("error_description", description);
            onError(error);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            AppLog.d(TAG, "OAuthWebViewClient.onPageStarted", "url: " + url);
            mSpinner.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            AppLog.d(TAG, "OAuthWebViewClient.onPageFinished", "url: " + url);
            mSpinner.dismiss();
        }

    }


    private void onComplete(Map<String, String> parameters) {

        String code = parameters.get("code");


        Intent returnIntent = new Intent(StripeActivity.this, SettingsActivity.class);
        returnIntent.putExtra("stripCode", code);
        setResult(StripeApp.RESULT_CONNECTED, returnIntent);
        startActivity(returnIntent);
        finish();
    }


    private void onError(Map<String, String> parameters) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("error", parameters.get("error"));
        returnIntent.putExtra("error_description", parameters.get("error_description"));
        setResult(StripeApp.RESULT_ERROR, returnIntent);
        finish();
    }

}
