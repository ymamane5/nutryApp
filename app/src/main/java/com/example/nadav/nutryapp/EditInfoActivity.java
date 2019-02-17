package com.example.nadav.nutryapp;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.nadav.nutryapp.Helpers.SPHelper;
import com.example.nadav.nutryapp.Models.User;

import java.util.ArrayList;

public class EditInfoActivity extends OptionsMenuActivity {

    SPHelper sph;
    private Toolbar mtoolbar;

    private EditText et_age, et_weight, et_height;
    private RadioGroup radioGroup;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        sph = new SPHelper(this);

        mtoolbar = findViewById(R.id.toolbar);  // get toolbar layout
        setSupportActionBar(mtoolbar);

        et_age = findViewById(R.id.editInfo_etAge);
        et_weight = findViewById(R.id.editInfo_etWeight);
        et_height = findViewById(R.id.editInfo_etHeight);

        radioGroup = findViewById(R.id.editInfo_readioGroup);
        ((RadioButton)(findViewById(R.id.editInfo_rbMale))).setChecked(true);

        btnSave = findViewById(R.id.editInfo_btnSave);
        btnSave.setOnClickListener(new SaveButtonHnadler());

        // TODO : GET USER INFO AND SHOW ON HINTS

    }

    @Override
    public void onBackPressed() {
    }

    private class SaveButtonHnadler implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            String a = et_age.getText().toString();
            String w = et_weight.getText().toString();
            String h = et_height.getText().toString();

            if (!a.isEmpty() && !w.isEmpty() && !h.isEmpty()) {

                User u = new User(sph.getActiveUser(), "");
                u.setUserAge(Float.valueOf(a));
                u.setUserWeight(Float.valueOf(w));
                u.setUserHeight(Float.valueOf(h));

                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.editInfo_rbMale:
                        u.setUserGender(User.GENDER_MALE);
                        break;
                    case R.id.editInfo_rbFemale:
                        u.setUserGender(User.GENDER_FEMALE);
                        break;
                }

                sph.setUserInfo(u);
                Toast.makeText(getBaseContext(), "user info added successfully", Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(getBaseContext(), "ERROR: empty fields!", Toast.LENGTH_SHORT).show();
            }

        }

    }


}
