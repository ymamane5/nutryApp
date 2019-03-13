package com.example.nadav.nutryapp;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
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

        player = MediaPlayer.create(this, R.raw.backgroundmusic);
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

}
