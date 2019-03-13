package com.example.nadav.nutryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nadav.nutryapp.Dialogs.PickDateDialog;
import com.example.nadav.nutryapp.Helpers.SPHelper;
import com.example.nadav.nutryapp.Models.NutritionRecord;

public class AddRecordActivity extends OptionsMenuActivity implements PickDateDialog.DateHandler {

    private Toolbar mtoolbar;
    private EditText et_description, et_calories;
    private String desc, cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        /*MENU*/
        mtoolbar = findViewById(R.id.toolbar);  // get toolbar layout
        setSupportActionBar(mtoolbar);

        /*INIT VIEWS*/
        et_description = findViewById(R.id.addRecord_description);
        et_calories = findViewById(R.id.addRecord_calories);

    }


    /*  "+add" button pressed on the .xml of this activity */
    public void saveButtonPress(View view) {

        SPHelper sph = new SPHelper(this);

        desc = et_description.getText().toString();
        cal = et_calories.getText().toString();

        if (desc.equals("") || cal.equals(""))
            Toast.makeText(this,"please fill all fields",Toast.LENGTH_SHORT).show();
        else {
            PickDateDialog pdd = PickDateDialog.newInstance();
            pdd.show(getSupportFragmentManager(),"");
        }

    }

    /*  HERE THE DATE FORMAT IS BEING DETERMINED */

    @Override
    public void datePicked(String year, String month, String day) {

        String date = day+"."+(Integer.parseInt(month)+1)+"."+year;
        SPHelper sph = new SPHelper(this);
        NutritionRecord nr = new NutritionRecord(sph.getActiveUser(),desc,cal);
        nr.setDate(year,month,day);
        sph.addNutritionRecord(nr);
        Toast.makeText(this,"saved for " + date,Toast.LENGTH_LONG).show();
        et_description.setText("");
        et_calories.setText("");
    }

    @Override
    public void onBackPressed() {}


}
