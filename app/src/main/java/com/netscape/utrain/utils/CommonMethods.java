package com.netscape.utrain.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.annotation.RequiresApi;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AthleteHomeScreen;
import com.netscape.utrain.model.ServiceListDataModel;
import com.netscape.utrain.response.NotificationCountResponse;
import com.netscape.utrain.response.NotificationReadResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonMethods {
    private static SharedPreferences data;
    Retrofitinterface retrofitinterface= RetrofitInstance.getClient().create(Retrofitinterface.class);

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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void deleteDirectoryRecursion(Path path) throws IOException {
        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
                for (Path entry : entries) {
                    deleteDirectoryRecursion(entry);
                }
            }
        }
        Files.delete(path);
    }
    public static long getDiffrenceTwoTimes(Date date1,Date date2){
        long difference = date2.getTime() - date1.getTime();
      int days = (int) (difference / (1000*60*60*24));
      int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
      int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
        hours = (hours < 0 ? -hours : hours);
    return hours;
    }

}
