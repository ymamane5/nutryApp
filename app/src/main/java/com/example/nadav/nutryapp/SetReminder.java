package com.example.nadav.nutryapp;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.nadav.nutryapp.Helpers.SPHelper;

public class SetReminder extends OptionsMenuActivity {

    SPHelper sph;
    private Toolbar mtoolbar;
    private EditText reminder_EditText;
    public String min, hour;

    String time = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);

        Button save_button;
        final Button pick_button;
        final Context context = this;

        mtoolbar = findViewById(R.id.toolbar);  // get toolbar layout
        setSupportActionBar(mtoolbar);
        save_button = findViewById(R.id.button_save_id);
        pick_button = findViewById(R.id.pick_button_id);
        sph = new SPHelper(this);
        reminder_EditText = findViewById(R.id.alert_time_id);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sph.setUserReminder(reminder_EditText.getText().toString());
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();

                Log.w("reminder_tag", "reminder:" + sph.getUserReminder());
                Log.w("reminder_tag", "reminder table:" + sph.SP_GET("reminder"));

            }
        });
        pick_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetReminder.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        reminder_EditText.setText(selectedHour + ":" + selectedMinute);
                        hour = String.valueOf(selectedHour);
                        min = String.valueOf(selectedMinute);
                    }
                }, 0, 0, true);
                mTimePicker.setTitle("choose hour");
                mTimePicker.show();
            }
        });
    }
}
