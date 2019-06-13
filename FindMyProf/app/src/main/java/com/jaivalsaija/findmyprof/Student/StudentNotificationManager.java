package com.jaivalsaija.findmyprof.Student;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.jaivalsaija.findmyprof.R;

public class StudentNotificationManager {

    private static final String Channel_ID = "Student";
    private static final String Channel_Name = "Name";
    private static final String Channel_Desc = "Desc";

    private static final int ID_SMALL_NOTIFICATION = 235;
    private Context mCtx;

    public StudentNotificationManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    //the method will show a small notification
    //parameters are title for message title, message for message text and an intent that will open
    //when you will tap on the notification
    public void showSmallNotification(String title, String message, Intent intent) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(Channel_ID, Channel_Name, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(Channel_Desc);
            NotificationManager manager = mCtx.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mCtx,
                        ID_SMALL_NOTIFICATION,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx, Channel_ID);
        Notification notification;
        notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.ic_launcher))
                .setContentText(message)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        android.app.NotificationManager notificationManager = (android.app.NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID_SMALL_NOTIFICATION, notification);
    }
}
