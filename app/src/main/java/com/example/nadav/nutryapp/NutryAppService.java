package com.example.nadav.nutryapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import androidx.annotation.Nullable;

public class NutryAppService extends Service {

    private  MediaPlayer player;

     @Override
    public void onCreate() {
        Log.v("reminder_tag", "inside service onCreate");
        super.onCreate();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // showNotification(intent.getStringExtra("KEY1"));
        //Log.v("reminder_tag", "inside service onStartCommand");

        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        player.setLooping(true);
        player.start();

        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
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
