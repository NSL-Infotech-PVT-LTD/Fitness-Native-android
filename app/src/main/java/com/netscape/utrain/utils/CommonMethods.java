package com.netscape.utrain.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AthleteHomeScreen;
import com.netscape.utrain.model.SelectSpaceDaysModel;
import com.netscape.utrain.model.ServiceListDataModel;
import com.netscape.utrain.response.NotificationCountResponse;
import com.netscape.utrain.response.NotificationReadResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private static Dialog loadingDialog;
    private ChipGroup chipSpaceGroup;
    private RelativeLayout layout;
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
    public static List<SelectSpaceDaysModel> getWeekDaysList(){
             List<SelectSpaceDaysModel> startWeekList = new ArrayList<>();
             SelectSpaceDaysModel selectSpaceDaysModel;
            selectSpaceDaysModel=new SelectSpaceDaysModel();
            selectSpaceDaysModel.setChecked(false);
            selectSpaceDaysModel.setDaySeleced("1");
            selectSpaceDaysModel.setDayName("Monday");
            startWeekList.add(selectSpaceDaysModel);
            selectSpaceDaysModel=new SelectSpaceDaysModel();
            selectSpaceDaysModel.setChecked(false);
            selectSpaceDaysModel.setDaySeleced("2");
            selectSpaceDaysModel.setDayName("Tuesday");
            startWeekList.add(selectSpaceDaysModel);
            selectSpaceDaysModel=new SelectSpaceDaysModel();
            selectSpaceDaysModel.setChecked(false);
            selectSpaceDaysModel.setDaySeleced("3");
            selectSpaceDaysModel.setDayName("Wednesday");
            startWeekList.add(selectSpaceDaysModel);
            selectSpaceDaysModel=new SelectSpaceDaysModel();
            selectSpaceDaysModel.setChecked(false);
            selectSpaceDaysModel.setDaySeleced("4");
            selectSpaceDaysModel.setDayName("Thursday");
            startWeekList.add(selectSpaceDaysModel);
            selectSpaceDaysModel=new SelectSpaceDaysModel();
            selectSpaceDaysModel.setChecked(false);
            selectSpaceDaysModel.setDaySeleced("5");
            selectSpaceDaysModel.setDayName("Friday");
            startWeekList.add(selectSpaceDaysModel);
            selectSpaceDaysModel=new SelectSpaceDaysModel();
            selectSpaceDaysModel.setChecked(false);
            selectSpaceDaysModel.setDaySeleced("6");
            selectSpaceDaysModel.setDayName("Saturday");
            startWeekList.add(selectSpaceDaysModel);
            selectSpaceDaysModel=new SelectSpaceDaysModel();
            selectSpaceDaysModel.setChecked(false);
            selectSpaceDaysModel.setDaySeleced("7");
            selectSpaceDaysModel.setDayName("Sunday");
            startWeekList.add(selectSpaceDaysModel);


        return startWeekList;
    }
    public static List<SelectSpaceDaysModel> getDaysFromId(List selectedId,List<SelectSpaceDaysModel> list){
        List<SelectSpaceDaysModel> startWeekList = new ArrayList<>();

        if ((selectedId !=null && list !=null) && (selectedId.size()>0 && list.size()>0)){
            for (int i=0;i<selectedId.size();i++){

                for (int j=0;j<list.size();j++){
                    if (selectedId.get(i).toString().equalsIgnoreCase(list.get(j).getDaySeleced().toString())){
                        startWeekList.add(list.get(i));
                        break;
                    }
                }

            }
        }

        return startWeekList;
    }
    public static String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "EEE, dd MMM";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public static void showLoadingDialog(final Context context,List<SelectSpaceDaysModel> startWeekList) {
        if (context != null) {
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
                loadingDialog = null;
            }
            loadingDialog = new Dialog(context);
            loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            View view = View.inflate(context, R.layout.view_more_layout, null);
            RelativeLayout layout=view.findViewById(R.id.viewMorelayout);
            TextView close=view.findViewById(R.id.closeBtn);
            loadingDialog.setContentView(view);
//            loadingDialog.setCancelable(false);
//            loadingDialog.setCanceledOnTouchOutside(false);
            Window window = loadingDialog.getWindow();
            assert window != null;
            window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            window.setGravity(Gravity.CENTER);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadingDialog.dismiss();
                }
            });
            if (!((Activity) context).isFinishing()) {

                CommonMethods commonMethods=new CommonMethods();
                commonMethods.setChips(context,layout,startWeekList);
                loadingDialog.show();
                loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            } else {
            }
        }
    }
    public  void setChips(Context context,RelativeLayout layout,List<SelectSpaceDaysModel> startWeekList) {

        layout.removeAllViews();
        chipSpaceGroup = new ChipGroup(context);
        for (SelectSpaceDaysModel selectDays : startWeekList) {
            Chip chip = new Chip(context);
            chip.setEnabled(true);
            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(context, null, 0, R.style.Widget_MaterialComponents_Chip_Filter);
            chip.setChipDrawable(chipDrawable);
            chip.setTextColor(context.getResources().getColor(R.color.colorWhite));
//            chip.setMaxWidth(200);
            chip.setText(selectDays.getDayName());
            chip.setTag(selectDays.getDaySeleced());
            chip.setCheckable(false);
            chip.setChipBackgroundColorResource(R.color.gradientDarkColor);
            chipSpaceGroup.addView(chip);
        }
        chipSpaceGroup.setChipSpacingVertical(20);
        layout.addView(chipSpaceGroup);
    }

}
