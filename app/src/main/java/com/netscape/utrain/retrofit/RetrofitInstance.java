package com.netscape.utrain.retrofit;

import com.netscape.utrain.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitInstance {
    public static Retrofit retrofit = null;
//    public static final String base_url="https://maps.googleapis.com/maps/api/";
    public static Retrofit retrofit_obj=null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }


//    public static Retrofit getPolyline(){
//        if(retrofit_obj==null){
//            retrofit_obj= new Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create()).build();
//        }//end of if
//        return retrofit_obj;
//    }//end of method


}
