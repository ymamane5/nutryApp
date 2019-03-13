package com.example.nadav.nutryapp;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nadav.nutryapp.Helpers.SPHelper;

import static android.content.Intent.ACTION_TIME_TICK;
import static java.lang.System.exit;

public class MainActivity extends OptionsMenuActivity {

  private Toolbar mtoolbar;
  private TextView userPrompt, welcomeBack;
  final private int LOGIN_CODE = 0;
  BroadcastReceiver br = new BroadcastClockReceiver();
  //Service NutryService = new NutryAppService();

  /*  the currently logged in user */

  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    IntentFilter clockTickIntent = new IntentFilter(ACTION_TIME_TICK);
    registerReceiver(br, clockTickIntent);

    mtoolbar = findViewById(R.id.toolbar);  // get toolbar layout
    setSupportActionBar(mtoolbar);

    userPrompt = findViewById(R.id.userPrompt);
    welcomeBack = findViewById(R.id.welcomeBack);

    Intent logIn = new Intent(this,LogInActivity.class);
    startActivityForResult(logIn,LOGIN_CODE);

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

    if (requestCode == LOGIN_CODE) {
      if (resultCode == Activity.RESULT_OK) {

        SPHelper sph = new SPHelper(this);
        String userLoggedIn = data.getStringExtra("USER_NAME");
        sph.setActiveUser(userLoggedIn);

        userPrompt.setText(userLoggedIn);
        Toast.makeText(this,"LOGGED IN SUCCESSFULY", Toast.LENGTH_SHORT).show();
        welcomeBack.setVisibility(View.VISIBLE);
        userPrompt.setVisibility(View.VISIBLE);

      }
    }

  }

  @Override
  public void onBackPressed() {}

}
