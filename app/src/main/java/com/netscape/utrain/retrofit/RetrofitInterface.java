package com.netscape.utrain.retrofit;

import com.netscape.utrain.model.LogInApi.SignUpModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitInterface {




    @FormUrlEncoded
    @POST("login")
    Call<SignUpModel> getLoggedIn(@Header("Content-Type") String Content_Type,
                                  @Field("email") String email,
                                  @Field("password") String password,
                                  @Field("device_type") String device_type,
                                  @Field("device_token") String device_token);
}
