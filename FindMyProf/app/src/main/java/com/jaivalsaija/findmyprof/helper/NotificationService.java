package com.jaivalsaija.findmyprof.helper;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.jaivalsaija.findmyprof.Professor.NotifyProfessor;
import com.jaivalsaija.findmyprof.Professor.ProfessorNotificationManager;
import com.jaivalsaija.findmyprof.Student.NotifyStudent;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationService extends FirebaseMessagingService {
    private static final String TAG = "MessagingService";
    Intent intent;

    //    @Override
//    public void onNewToken(String s) {
//        super.onNewToken(s);
//        Log.i("newToken", s);
////        getSharedPreferences(ConstantValue.SHARED_PREF, MODE_PRIVATE).edit().putString(ConstantValue.TOKEN_Key, s).apply();
//    }
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void sendPushNotification(JSONObject json) {
        //optionally we can display the json into log

        Log.e(TAG, "Notification JSON " + json.toString());
        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");

            //parsing json data
            String title = data.getString("title");
            String message = data.getString("message");
            //String channel = data.getString("android_channel_id");

            //creating NotificationManager object
            ProfessorNotificationManager notificationManager = new ProfessorNotificationManager(getApplicationContext());

            //creating an intent for the notification
            if (SharedPref.getInstance(getApplicationContext()).getUserType().equals("Professor")) {
                intent = new Intent(getApplicationContext(), NotifyProfessor.class);
            } else if (SharedPref.getInstance(getApplicationContext()).getUserType().equals("Student")) {
                intent = new Intent(getApplicationContext(), NotifyStudent.class);
            }
            //displaying small notification
            notificationManager.showSmallNotification(title, message, intent);
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }
}
