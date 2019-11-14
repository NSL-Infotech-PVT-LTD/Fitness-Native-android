package com.netscape.utrain.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netscape.utrain.model.ServiceListDataModel;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CommonMethods {
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
    public static void clearKeyPrefData(String key,Context context){
        data = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = data.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void setPrefData(String key, String vallue, Context context) {
        data = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = data.edit();
        editor.putString(key, vallue);
        editor.commit();
    }
    public static ArrayList<String> getListPrefData(String key, Context ct) {
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
    public static void setLisstPrefData(String key2, ArrayList<ServiceListDataModel> list, Context context) {
        data = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = data.edit();
        Gson gson = new Gson();
        String listData = gson.toJson(list);
        editor.putString(key2, listData);
        editor.commit();
    }
    public static ArrayList<ServiceListDataModel> getListPrefrence(String key,Context context){
        ArrayList<ServiceListDataModel> callLog = new ArrayList<ServiceListDataModel>();
        data = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = data.getString(key,null);
        if (json!=null) {
            if (json.isEmpty()) {
                callLog = new ArrayList<ServiceListDataModel>();
            } else {
                Type type = new TypeToken<List<ServiceListDataModel>>() {
                }.getType();
                callLog = gson.fromJson(json, type);
            }
        }
        return callLog;
    }
    public static List<Date> getDatesBetweenUsingJava7(Date startDate, Date endDate) {
        List<Date> datesInRange = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return datesInRange;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static long betweenDates(Date firstDate, Date secondDate) throws IOException
    {
        return ChronoUnit.DAYS.between(firstDate.toInstant(), secondDate.toInstant());
    }

    public static void deleteDirectory(File dir){

            if (dir.exists()) {
                String deleteCmd = "rm -r " + dir;
                Runtime runtime = Runtime.getRuntime();
                try {
                    runtime.exec(deleteCmd);
                } catch (IOException e) { }
            }

    }

}
