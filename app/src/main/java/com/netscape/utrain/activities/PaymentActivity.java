package com.netscape.utrain.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AthleteHomeScreen;
import com.netscape.utrain.activities.coach.CoachDashboard;
import com.netscape.utrain.activities.organization.OrgHomeScreen;
import com.netscape.utrain.databinding.ActivityPaymentBinding;
import com.netscape.utrain.model.BookingConfirmModel;
import com.netscape.utrain.response.SpaceBookingResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    public static final String PUBLISHABLE_KEY = "pk_test_StB3j4S26BTUGHm7RfbMyzUn00q1Q55MWn";
    protected Card cardToSave;
    EditText cardNumberEditText, cardDate, cardCVV, cardHolder;
    TextView validateCard;
    Stripe stripe;
    String backString = "";
    private ActivityPaymentBinding binding;
    private int month, year;
    private ProgressDialog progressDoalog;
    private PaymentActivity activity;
    private TextView textView, productName, productQuantity, productAddress;
    private AlertDialog dialogMultiOrder;
    private String datePattern = "\\d{2}/\\d{4}", currentString;
    private Bundle extras;
    private Retrofitinterface retrofitinterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_payment);
        binding = DataBindingUtil.setContentView(PaymentActivity.this, R.layout.activity_payment);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        binding.paymentBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        init();


    }

    private void init() {
        activity = this;
        backString = "yes";
        retrofitinterface = RetrofitInstance.getClient().create(Retrofitinterface.class);
        cardNumberEditText = findViewById(R.id.cardNumberEditText);
        cardDate = findViewById(R.id.carDate);
        cardCVV = findViewById(R.id.cardCVV);
        cardHolder = findViewById(R.id.cardHolder);
        textView = findViewById(R.id.textView);
        validateCard = findViewById(R.id.validateCard);
        stripe = new Stripe(this);


        validateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePattern = "\\d{2}/\\d{4}";
                currentString = cardDate.getText().toString();
                if (cardNumberEditText.getText().toString().trim().isEmpty()) {
                    cardNumberEditText.setError(getString(R.string.invalidField));
                } else if (cardDate.getText().toString().trim().isEmpty()) {
                    cardDate.setError(getString(R.string.invalidField));
                } else if (!currentString.matches(datePattern)) {
                    cardDate.setError(getString(R.string.invalidDate));

                } else if (cardCVV.getText().toString().trim().isEmpty()) {
                    cardCVV.setError(getString(R.string.invalidField));
                } else {

                    String[] separated = currentString.split("/");

                    if ((!separated[0].isEmpty()) && (!separated[1].isEmpty())) {
                        month = Integer.parseInt(separated[0].trim());

                        year = Integer.parseInt(separated[1].trim());
                    }


                    new AlertDialog.Builder(activity)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setMessage("Are you sure you want confirm this booking")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    buy();

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

            }

        });
        cardNumberEditText.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (cardNumberEditText.getText().length() == 19)
                    cardDate.requestFocus();
                return false;
            }
        });

        cardDate.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (cardDate.getText().toString().matches(datePattern))
                    cardCVV.requestFocus();
                return false;

            }
        });

        cardCVV.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (cardCVV.getText().length() == 3)
                    cardHolder.requestFocus();
                return false;
            }
        });


        checkCard(cardNumberEditText);

        checkDate(cardDate);
    }

    private void buy() {
        progressDoalog = new ProgressDialog(activity);
        progressDoalog.setMessage("Validate your card....");
        progressDoalog.setCancelable(false);
        progressDoalog.show();


        cardToSave = new Card(
                cardNumberEditText.getText().toString(), //card number
                month, //expMonth
                year,//expYear
                cardCVV.getText().toString());//cvc

        boolean validation = cardToSave.validateCard();
        if (validation) {
            new Stripe(this).createToken(
                    cardToSave,
                    PUBLISHABLE_KEY,
                    new TokenCallback() {
                        @Override
                        public void onError(Exception error) {
                            progressDoalog.dismiss();
                            Log.d("Stripe", error.toString());
                        }

                        @Override
                        public void onSuccess(Token token) {

                            progressDoalog.dismiss();

                            if (getIntent().getExtras() != null) {
                                String type = getIntent().getStringExtra("type");
                                if (type.equalsIgnoreCase("space")) {
                                    hitSpaceBookingApi(getIntent().getStringExtra("totalPrice"), getIntent().getStringExtra("type"), token.getId());
                                }
                                if (type.equalsIgnoreCase("session")) {
                                    hitConfirmBookingAPI(getIntent().getIntExtra("tickets", 0) + "", getIntent().getIntExtra("totalPrice", 0) + "", getIntent().getStringExtra("type"), token.getId());
                                }
                                if (type.equalsIgnoreCase("event")) {
                                    hitConfirmBookingAPI(getIntent().getIntExtra("tickets", 0) + "", getIntent().getIntExtra("totalPrice", 0) + "", getIntent().getStringExtra("type"), token.getId());
                                }
                            }

//                            bookingConfirm(getIntent().getIntExtra("appo_id", 0), getIntent().getStringExtra("dateTime"), token.getId());
                        }
                    });
        } else if (!cardToSave.validateNumber()) {
            progressDoalog.dismiss();

            Toast.makeText(activity, "" + getString(R.string.invalidCardDetail), Toast.LENGTH_SHORT).show();
        } else if (!cardToSave.validateExpiryDate()) {
            progressDoalog.dismiss();
            Toast.makeText(activity, "" + getString(R.string.invalidCardDetail), Toast.LENGTH_SHORT).show();
            Log.d("Stripe", "The expiration date that you entered is invalid");
        } else if (!cardToSave.validateCVC()) {
            progressDoalog.dismiss();
            Toast.makeText(activity, "" + getString(R.string.invalidCardDetail), Toast.LENGTH_SHORT).show();
            Log.d("Stripe", "The CVC code that you entered is invalid");
        } else {
            Log.d("Stripe", "The card details that you entered are invalid");
            Toast.makeText(activity, "" + getString(R.string.invalidCardDetail), Toast.LENGTH_SHORT).show();
            progressDoalog.dismiss();
        }
    }

    private void checkCard(EditText cardNumberEditText) {
        cardNumberEditText.addTextChangedListener(new TextWatcher() {

            private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
            private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
            private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
            private static final char DIVIDER = ' ';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
// noop
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));

                }
                if (s.length() == 19) {
                    cardDate.requestFocus();
                }
            }

            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
                boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
                for (int i = 0; i < s.length(); i++) { // check that every element is right
                    if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect &= divider == s.charAt(i);
                    } else {
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
                final StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < digits.length; i++) {
                    if (digits[i] != 0) {
                        formatted.append(digits[i]);
                        if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                            formatted.append(divider);
                        }
                    }
                }

                return formatted.toString();
            }

            private char[] getDigitArray(final Editable s, final int size) {
                char[] digits = new char[size];
                int index = 0;
                for (int i = 0; i < s.length() && index < size; i++) {
                    char current = s.charAt(i);
                    if (Character.isDigit(current)) {
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        });
    }

    private void checkDate(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "MMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int j, int i1, int i2) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
//Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 6) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
//This part makes sure that when we finish entering numbers
//the date is correct, fixing it otherwise
                        int mon = Integer.parseInt(clean.substring(0, 2));
                        int year = Integer.parseInt(clean.substring(2, 6));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);
// ^ first set year for the line below to work correctly
//with leap years - otherwise, date e.g. 29/02/2012
//would be automatically corrected to 28/02/2012

                        clean = String.format("%02d%02d", mon, year);
                    }

                    clean = String.format("%s/%s",
                            clean.substring(0, 2),
                            clean.substring(2, 6));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    editText.setText(current);
                    editText.setSelection(sel < current.length() ? sel : current.length());

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }

    private void hitConfirmBookingAPI(String tickets, String price, String type, String token) {

        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading.........");
        progressDialog.show();
        Call<BookingConfirmModel> signUpAthlete = retrofitinterface.bookingConfrim("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, activity), Constants.CONTENT_TYPE, type, getIntent().getIntExtra("event_id", 0) + "", tickets, price, "pending", token);
        signUpAthlete.enqueue(new Callback<BookingConfirmModel>() {
            @Override
            public void onResponse(Call<BookingConfirmModel> call, Response<BookingConfirmModel> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            Intent intent = null;
                            if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, activity).equalsIgnoreCase(Constants.Athlete)) {
                                intent = new Intent(activity, AthleteHomeScreen.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, activity).equalsIgnoreCase("orgnizer")) {
                                intent = new Intent(activity, OrgHomeScreen.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                            Toast.makeText(activity, "" + response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                } else {
//                    progressDialog.dismiss();

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message").getString(0);
                        Toast.makeText(activity, "" + errorMessage, Toast.LENGTH_SHORT).show();


                    } catch (Exception e) {
                        Toast.makeText(activity, "" + e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }

            }

            @Override
            public void onFailure(Call<BookingConfirmModel> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

    private void hitSpaceBookingApi(String price, String type, String token) {

        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading.........");
        progressDialog.show();
        Call<SpaceBookingResponse> signUpAthlete = retrofitinterface.spaceBooking("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, activity), Constants.CONTENT_TYPE, type, getIntent().getStringExtra("event_id") + "", price, "pending", token, getIntent().getStringExtra("startDate"), getIntent().getStringExtra("endDate"));
        signUpAthlete.enqueue(new Callback<SpaceBookingResponse>() {
            @Override
            public void onResponse(Call<SpaceBookingResponse> call, Response<SpaceBookingResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            Intent intent = null;
                            if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, activity).equalsIgnoreCase(Constants.Athlete)) {
                                intent = new Intent(activity, AthleteHomeScreen.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, activity).equalsIgnoreCase(Constants.Organizer)) {
                                intent = new Intent(activity, OrgHomeScreen.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                            else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, activity).equalsIgnoreCase(Constants.Coach)) {
                                intent = new Intent(activity, CoachDashboard.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                            Toast.makeText(activity, "" + response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                } else {
//                    progressDialog.dismiss();

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONArray errorMessage = jObjError.getJSONObject("error").getJSONObject("error_message").getJSONArray("message");
                        String errorMsg=errorMessage.getJSONObject(0).getString("message");
                        Toast.makeText(activity, "" + errorMsg, Toast.LENGTH_SHORT).show();


                    } catch (Exception e) {
                        Toast.makeText(activity, "" + e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }

            }

            @Override
            public void onFailure(Call<SpaceBookingResponse> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

}