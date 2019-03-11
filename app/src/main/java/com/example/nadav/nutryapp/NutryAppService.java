package com.example.nadav.nutryapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import androidx.annotation.Nullable;

public class NutryAppService extends Service {

     @Override
    public void onCreate() {
        Log.v("reminder_tag", "inside service onCreate");
        super.onCreate();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //create notification
       // showNotification(intent.getStringExtra("KEY1"));
        showNotification("no key");
        //return super.onStartCommand(intent, flags, startId);
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

     public void showNotification(String str) {
        String notificationTag = "notificationTag2";
        int notificationId = 234;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), "1")
                .setSmallIcon(R.drawable.image)
                .setContentTitle("NutrApp notification")
                .setContentText("str")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat.from(getBaseContext()).notify(notificationTag, notificationId, builder.build());
    }

}
