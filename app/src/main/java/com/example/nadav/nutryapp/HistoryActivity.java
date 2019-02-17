package com.example.nadav.nutryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.nadav.nutryapp.Adapters.ListviewHistoryAdapter;
import com.example.nadav.nutryapp.Dialogs.PickDateDialog;
import com.example.nadav.nutryapp.Helpers.SPHelper;
import com.example.nadav.nutryapp.Models.NutritionRecord;
import java.util.ArrayList;

public class HistoryActivity extends OptionsMenuActivity implements PickDateDialog.DateHandler, AdapterView.OnItemLongClickListener {

    private Toolbar mtoolbar;
    SPHelper sph;
    ListView historyListView;
    TextView dateTextView, calTextView, longClickPrompt;
    ListviewHistoryAdapter lvha;
    PickDateDialog pdd;
    ArrayList<NutritionRecord> userNutritionRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mtoolbar = findViewById(R.id.toolbar);  // get toolbar layout
        setSupportActionBar(mtoolbar);

        lvha = null;
        dateTextView = findViewById(R.id.nhActivity_tvDate);
        calTextView = findViewById(R.id.nhActivity_tvCal);
        longClickPrompt = findViewById(R.id.historyActivity_longClickPrompt);

        sph = new SPHelper(this);
        historyListView = findViewById(R.id.lv_nutritionHistory);

        /*   clear previous list view items   */
        //ArrayList<NutritionRecord> userNutritionRecords = null;
        userNutritionRecords = null;
        lvha = new ListviewHistoryAdapter(this, userNutritionRecords);
        lvha.notifyDataSetChanged();

        pdd = PickDateDialog.newInstance();
        pdd.show(getSupportFragmentManager(), "");

    }

    /*  after user has picked a date from dialog  */
    /*  DIALOG WAITS UNTILL THIS METHOD RETURNS AND THEN DISMISS    */
    @Override
    public void datePicked(String year, String month, String day) {

        NutritionRecord justForDate = new NutritionRecord("","","");
        justForDate.setDate(year,month,day);

        /*  get nutrition records for a date     */
        if (sph.getActiveUser() != null)
            userNutritionRecords = sph.getNutritionRecords(sph.getActiveUser(), justForDate.getDate());
        else
            Log.i("mylog","fatal error: HistoryActivity.datePicked()");

        /*  if no records founds update textview    */
        if (userNutritionRecords.isEmpty()) {
            dateTextView.setText("No records are found for: " + justForDate.getDate());
            calTextView.setText("");
            longClickPrompt.setVisibility(View.INVISIBLE);
        }
        else {
            /*  initialize list view adapter for user nutrition record history */
            lvha = new ListviewHistoryAdapter(this, userNutritionRecords);
            historyListView.setAdapter(lvha);

            /*  set long click listener to delete selected records  */
            historyListView.setOnItemLongClickListener(this);

            /*  handlte prompt text views  */
            dateTextView.setText("Listing nutrition records for: " + justForDate.getDate());

            int totalCalories = 0;
            for (int i=0;i<userNutritionRecords.size();i++)
                totalCalories+=Integer.parseInt(userNutritionRecords.get(i).getCalories());
            calTextView.setText("Total calories count: "+totalCalories);
            longClickPrompt.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onBackPressed() {}

    /*  This class is the listener of the Long clicks of the user on the ListView   */
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        // TODO : DELETE SELECTED RECORD

        // REMOVE FROM SP
        String date = userNutritionRecords.get(0).getDate();
        sph.removeUserNutritionRecordsOnDate(sph.getActiveUser(),date);

        // REMOVE FROM LIST
        userNutritionRecords.remove(i);
        lvha.notifyDataSetChanged();

        if (userNutritionRecords.isEmpty()) {
            dateTextView.setText("No records are found for: " + date);
            calTextView.setText("");
            longClickPrompt.setVisibility(View.INVISIBLE);
        }

        return false;

    }

    /*  handle changer date button click    */
    public void refreshListWithNewDate(View view) {

        Intent in = new Intent(this,HistoryActivity.class);
        startActivity(in);

    }


}
