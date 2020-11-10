package com.example.medregister;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder helperChannelNotification = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, helperChannelNotification.build());
    }
}
