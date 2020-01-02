package com.netscape.utrain.Firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.BookingDetails;
import com.netscape.utrain.activities.SplashActivity;
import com.netscape.utrain.activities.athlete.AthleteHomeScreen;
import com.netscape.utrain.activities.coach.CoachDashboard;
import com.netscape.utrain.activities.organization.OrgHomeScreen;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


public class MyFirebaseService extends FirebaseMessagingService {
//
//    private static final String TAG = "FirebaseMessService";
//    private int NOTIFICATION_ID = 0;
//    private NotificationCompat.Builder mBuilder;
//    private String title = "F17ONE";
//    private String response = "";
//
//
//    @Override
//    public void onNewToken(String token) {
//        Log.d(TAG, "Refreshedtoken: " + token);
//// If you want to send messages to this application instance or
//// manage this apps subscriptions on the server side, send the
//// Instance ID token to your app server.
//    }
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
//
//
//// TODO(developer): Handle FCM messages here.
//// Not getting messages here? See why this may be: https://goo.gl/39bRNJ
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
//
//// Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            Log.d(TAG, "Message data payload: " + remoteMessage.getData().get("data"));
//            String tragetId = "";
//
//            response = remoteMessage.getData().toString();
//            try {
//
//                JSONObject json = new JSONObject(response);
//                JSONObject jsonObject = json.getJSONObject("data");
//                //  tragetId = jsonObject.getString("target_id");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//
//// if (remoteMessage.getNotification() != null) {
//// Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//                if (remoteMessage.getNotification() != null)
//                    Greater_M_version(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
//            } else {
//                if (remoteMessage.getNotification() != null)
//                    setNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
//// }
//            }
//
//
//        }
//
//// Check if message contains a notification payload.
//
//
//    }
//
//    private void setNotification(String title, String data) {
//        Intent intent = null;
//        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getApplicationContext()).equalsIgnoreCase(Constants.Organizer)) {
//            intent = new Intent(this, OrgHomeScreen.class);
//            intent.putExtra("pushnotification", response);
//        } else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getApplicationContext()).equalsIgnoreCase(Constants.Athlete)) {
//            intent = new Intent(this, AthleteHomeScreen.class);
//            intent.putExtra("pushnotification", response);
//        } else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getApplicationContext()).equalsIgnoreCase(Constants.Coach)) {
//            intent = new Intent(this, CoachDashboard.class);
//            intent.putExtra("pushnotification", response);
//        }
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        PendingIntent notifyPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
//        Notification notification;
//        notification = mBuilder.setSmallIcon(R.drawable.u_train).setTicker("New Notification").setWhen(0)
//                .setAutoCancel(true)
//                .setContentIntent(notifyPendingIntent)
//                .setContentTitle(title)
//                .setSmallIcon(R.drawable.u_train)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.u_train))
//                .setContentText(data)
//                .setSound(defaultSoundUri)
//                .setAutoCancel(true)
//                .build();
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(NOTIFICATION_ID, notification);
//        notificationManager.cancelAll();
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void Greater_M_version(String title, String messageData) {
//        Intent intent = null;
//        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getApplicationContext()).equalsIgnoreCase(Constants.Organizer)) {
//            intent = new Intent(this, OrgHomeScreen.class);
//            intent.putExtra("pushnotification", response);
//        }  else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getApplicationContext()).equalsIgnoreCase(Constants.Athlete)) {
//            intent = new Intent(this, AthleteHomeScreen.class);
//            intent.putExtra("pushnotification", response);
//        }  else if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getApplicationContext()).equalsIgnoreCase(Constants.Coach)){
//            intent = new Intent(this, CoachDashboard.class);
//            intent.putExtra("pushnotification", response);
//}
//        Notification notification;
//        String channelId = getApplicationContext().getString(R.string.default_notification_channel_id);
//        NotificationChannel channel = null;
//// channel = new NotificationChannel(getApplicationContext().getPackageName(), getApplicationContext().getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
//        channel = new NotificationChannel(channelId, "a", NotificationManager.IMPORTANCE_HIGH);
//        channel.enableVibration(true);
//        channel.setDescription(messageData);
//        channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null);
//        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        if (manager != null) {
//            manager.createNotificationChannel(channel);
//        }
//
//        mBuilder = new NotificationCompat.Builder(this, channelId);
//        mBuilder.setChannelId(channelId);
//        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.u_train);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        PendingIntent notifyPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
//        notification = mBuilder.setSmallIcon(R.drawable.u_train)
////.setAutoCancel(true)
//                .setContentTitle(title)
//                .setContentIntent(notifyPendingIntent)
//                .setLargeIcon(bitmap)
//                .setContentText(messageData)
//                .setSound(defaultSoundUri)
//                .setAutoCancel(true)
//                .build();
//
//// notification.flags |= Notification.FLAG_AUTO_CANCEL;
//        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            if (notificationManager != null) {
//                notificationManager.createNotificationChannel(channel);
//                notificationManager.notify(1, notification);
//                notificationManager.cancelAll();
//
//            }
//        }
//
////
//    }
}