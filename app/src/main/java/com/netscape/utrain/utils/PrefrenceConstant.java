package com.netscape.utrain.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrefrenceConstant {
    private static SharedPreferences data;

    public static String getPrefData(String ke, Context ct) {
        data = PreferenceManager.getDefaultSharedPreferences(ct);
//            list=PreferenceManager.getDefaultSharedPreferences(ct);
        return data.getString(ke, "");
    }//end of set Defaults methods

    public static void clearPrefData(Context context) {
        data = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = data.edit();
        editor.clear();
        editor.apply();
    }

    public static void setPrefData(String key, String vallue, Context context) {
        data = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = data.edit();
        editor.putString(key, vallue);
        editor.commit();
    }
    public ArrayList<String> getPrefListData(String key, Context ct) {
        data = PreferenceManager.getDefaultSharedPreferences(ct);
        Gson gson = new Gson();
        String json = data.getString(key, null);
        String[] text = gson.fromJson(json, String[].class);
        List<String> ulist;
        ulist = Arrays.asList(text);
        ulist = new ArrayList<String>(ulist);
//        ulist = Arrays.asList(json);
        return (ArrayList<String>) ulist;
    }//end of set Defaults methods
}
