package com.example.omar.outreach.Managers;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.omar.outreach.Activities.PeriodicalFormActivity_1;
import com.example.omar.outreach.App;
import com.example.omar.outreach.R;

public class NotificationReciever extends BroadcastReceiver {

    private static final String NOTIFY_CHANNEL_ID = "1002";
    private static final String NOTIFY_CHANNEL_NAME = "periodicaForm";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("Notif","Got it");
        createNotification(context,intent);

    }

    @TargetApi(26)
    public void createNotification(Context context, Intent intent) {

        // extras
        int notifyID = intent.getIntExtra("notifyID", 0);

        // create notification channel
        NotificationChannel channel = new NotificationChannel(NOTIFY_CHANNEL_ID, NOTIFY_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService (Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);

        // create the NotificationCompat Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFY_CHANNEL_ID);

        // Create the intent that will start the ResultActivity when the user
        // taps the notification or chooses an action button
        Intent targetIntent = new Intent(context, PeriodicalFormActivity_1.class);

        // Store the notification ID so we can cancel it later in the ResultActivity
        intent.putExtra("notifyID", notifyID);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifyID, targetIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Set the three required items all notifications must have
        builder.setSmallIcon(R.drawable.heart);
        builder.setContentTitle("Emotions app");
        builder.setContentText("please enter your current status ❤️");

        // large icon
//        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.heart));

        // Set the notification to cancel when the user taps on it
        builder.setAutoCancel(true);

        // Set the small subtext message
        builder.setSubText("Tap to view");

        // Set the content intent to launch our result activity
        builder.setContentIntent(pendingIntent);

        // Set the lock screen visibility of the notification
        builder.setVisibility(Notification.VISIBILITY_PUBLIC);

        // Build the finished notification and then display it to the user
        Notification notification = builder.build();
        NotificationManager mgr = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mgr.notify(notifyID, notification);
    }
}