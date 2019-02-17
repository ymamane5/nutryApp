package com.example.nadav.nutryapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.nadav.nutryapp.Dialogs.RegisterDialog;
import com.example.nadav.nutryapp.Helpers.SPHelper;
import com.example.nadav.nutryapp.Models.User;

import java.util.ArrayList;

public class LogInActivity extends AppCompatActivity implements RegisterDialog.RegisterHandler {

    /*  ACTIVITY VIEWS   */
    private EditText et_userName, et_password;

    /*  private references  */
    User loginUser;

    /*  helpers  */
    SPHelper sphelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        sphelper = new SPHelper(this);  // shared preferences helper
        et_userName = findViewById(R.id.et_userName);
        et_password = findViewById(R.id.et_password);                          // initialize views

    }

    /*  initialize private reference 'loggedInUser'   */
    public void loginClicked(View view) {

        String name, pass;

        name = this.et_userName.getText().toString();
        pass = this.et_password.getText().toString();

        if (name.equals("")|| pass.equals("")) {
            Toast.makeText(this,"ERROR: one or more fields are missing", Toast.LENGTH_SHORT).show();
        }
        else {

            loginUser = new User(name,pass);

            if (sphelper.validateUser(loginUser)) {

                /*  ONLY if user exists, RETURN its details to main activity */
                Intent returnLoginUser = new Intent();
                returnLoginUser.putExtra("USER_NAME",name);
                returnLoginUser.putExtra("USER_PASSWORD",pass);
                setResult(Activity.RESULT_OK,returnLoginUser);
                finish();

            }
            else {
                clearViews();
                Toast.makeText(this, "ERROR: username or password are incorrect", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onBackPressed() {}

    /*  register button press on activity's register button view */
    public void registerNewUser(View view) {

        RegisterDialog regDialog = RegisterDialog.newInstance();
        regDialog.show(getSupportFragmentManager(),"");

    }

    /*  register button press on register dialog*/
    @Override
    public void registerBtnPressed(User u) {

        if (!sphelper.isUserExist(u)) {
            sphelper.addUser(new User(u.getUserName(), u.getUserPassword()));
            Toast.makeText(this, "user added", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "ERROR: user name already exist", Toast.LENGTH_SHORT).show();

    }

    private void clearViews() {
        et_userName.setText("");
        et_userName.setHint("user name");
        et_password.setText("");
        et_password.setHint("password");
    }

}
