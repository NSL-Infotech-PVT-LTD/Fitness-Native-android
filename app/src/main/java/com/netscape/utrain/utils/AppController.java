package com.netscape.utrain.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class AppController extends Application{
        public static AppController appController = null;
        private static AppController controller;
        private boolean isAppForeground = false;
        private static Context mContext;

//        public ArrayList<ServicesModel> servicesModelList = new ArrayList<>();
//        public ArrayList<AppointmentModel> upcomingAppointmentList = new ArrayList<>();
//        public ArrayList<AppointmentModel> completedAppointmentList = new ArrayList<>();
//        public ArrayList<AppointmentModel> appointmentList = new ArrayList<>();

        @Override
        protected void attachBaseContext(Context base) {
            super.attachBaseContext(base);
        }

        @Override
        public void onCreate() {
            super.onCreate();
//        Stetho.initializeWithDefaults(this);

            /*changes font in complete apps*/
            //TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Montserrat-Regular.ttf");

            controller = this;
            isAppForeground = true;
            mContext = getApplicationContext();
//        handleUncaughtException();
        }

        /*code handle any uncaught exception in the project*/
//        private void handleUncaughtException() {
//            // Setup handler for uncaught exceptions.
//            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//                @Override
//                public void uncaughtException(Thread thread, Throwable e) {
//                    AppDelegate.LogE(e.getMessage() + " " + e.getCause());
////                System.exit(0);
//                }
//            });
//        }


        public boolean isAppForeground() {
            return isAppForeground;
        }

        public void setIsForeground(boolean flag) {
            this.isAppForeground = flag;
        }

        public static AppController getController() {
            return controller;
        }

        public static boolean isPermissionGranted(Activity activity, String permission, int requestCode) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activity.requestPermissions(new String[]{permission},
                            requestCode);
                }
                return false;
            }
            return true;
        }

        public static boolean isPermissionGrantedFragment(Activity activity, String permission, int requestCode) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ActivityCompat.requestPermissions(activity, new String[]{permission},
                            requestCode);
                }
                return false;
            }
            return true;
        }

        public static AppController getInstance() {
            if (appController == null) {
                appController = new AppController();
            }
            return appController;
        }

        public void saveStringPrefs(String key, String value) {

        }

    }

