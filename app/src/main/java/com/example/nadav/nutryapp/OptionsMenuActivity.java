package com.example.nadav.nutryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.nadav.nutryapp.Helpers.SPHelper;
import com.example.nadav.nutryapp.Models.User;

import java.util.ArrayList;

import static java.lang.System.exit;

public class OptionsMenuActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        MainActivity main = new MainActivity();

        switch (item.getItemId()) {
            case R.id.action_add:
                Intent addRecord = new Intent(this,AddRecordActivity.class);
                startActivity(addRecord);
                return true;
            case R.id.action_history:
                Intent viewHistory = new Intent(this,HistoryActivity.class);
                startActivity(viewHistory);
                return true;
            case R.id.action_stats: {

                // if the user dont have personal info record @ sp, open edit personal info activity instead
                SPHelper sph = new SPHelper(this);
                boolean userInfoExist = false;
                ArrayList<User> userInfo = sph.getUsersInfoTable();

                for (int i=0;i<userInfo.size();i++) {
                    if (userInfo.get(i).getUserName().equals(sph.getActiveUser()))
                        userInfoExist = true;
                }

                Intent viewStatistics = new Intent(this, StatisticsActivity.class);
                Intent viewEditInfo = new Intent(this,EditInfoActivity.class);
                if (userInfoExist)
                    startActivity(viewStatistics);
                else
                    startActivity(viewEditInfo);

                return true;
            }
            case R.id.action_edit_info:
                Intent viewEditInfo = new Intent(this,EditInfoActivity.class);
                startActivity(viewEditInfo);
                return true;
            case R.id.action_set_reminder:
                Intent viewSetReminder = new Intent(this,SetReminder.class);
                startActivity(viewSetReminder);
                return true;
            case R.id.action_logout:
                Intent logOut = new Intent(this,MainActivity.class);
                startActivity(logOut);
                return true;
            case R.id.action_exit:
                //exit(0);
                main.onExitPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void displayMessage (String msg) { Toast.makeText(this,msg,Toast.LENGTH_SHORT).show(); }

}