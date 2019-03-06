package com.example.nadav.nutryapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BroadcastClockReceiver extends BroadcastReceiver {

    Context gContext;

    @Override
    public void onReceive(Context context, Intent intent) {

        gContext = context;
       // Toast.makeText(context, "minute passed!", Toast.LENGTH_SHORT).show();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        //if(sdf.format(cal.getTime()).equals("22:38"))
           showNotification();

    }

    public void showNotification() {
        String notificationTag = "notificationTag";
        int notificationId = 123;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(gContext, "1")
                .setSmallIcon(R.drawable.image)
                .setContentTitle("Workout Reminder")
                .setContentText("time for workout!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat.from(gContext).notify(notificationTag, notificationId, builder.build());
    }
}
