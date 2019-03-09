package com.example.nadav.nutryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nadav.nutryapp.Helpers.SPHelper;

public class SetReminder extends OptionsMenuActivity {

    SPHelper sph;
    private Toolbar mtoolbar;

    private EditText reminder_EditText;
    private Button save_button;
    String time = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);

        mtoolbar = findViewById(R.id.toolbar);  // get toolbar layout
        setSupportActionBar(mtoolbar);
    }

    private void saveButtonPressed() {

        sph = new SPHelper(this);
        reminder_EditText = findViewById(R.id.alert_time_id);
        sph.setUserReminder(reminder_EditText.getText().toString());
        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }
}
