package com.example.medregister;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class Notification extends ContextWrapper {
    public static final String chId = "id";
    public static final String chName = "name";
    private NotificationManager notificationManager;
    final int id = (int) System.currentTimeMillis();

    public Notification(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(chId, chName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        Intent intent = new Intent(this, SchedulePillsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Builder(getApplicationContext(), chId)
                .setContentTitle("MedRegister")
                .setContentText(getString(R.string.notification_text))
                .setSmallIcon(R.drawable.ic_pill)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
    }
}
