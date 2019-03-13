package com.example.nadav.nutryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.nadav.nutryapp.Dialogs.PickDateDialog;
import com.example.nadav.nutryapp.Helpers.SPHelper;
import com.example.nadav.nutryapp.Models.NutritionRecord;
import com.example.nadav.nutryapp.Models.User;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

public class StatisticsActivity extends OptionsMenuActivity implements PickDateDialog.DateHandler {

    private Toolbar mtoolbar;
    private BarChart barChart;
    private SPHelper sph;
    private ArrayList<NutritionRecord> allRecordsOfUser;
    ArrayList<BarEntry> barEntries;
    private String dateForActivity;
    private int caloriesForDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        sph = new SPHelper(this);
        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        barChart = findViewById(R.id.statsActivity_barChart);
        barEntries = new ArrayList<>();
        caloriesForDate = -1;

        // get date
        PickDateDialog pdd = PickDateDialog.newInstance();
        pdd.show(getSupportFragmentManager(), "");

        //barChart.setDrawBarShadow();
/*
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        allRecordsOfUser = sph.getNutritionRecords(sph.getActiveUser());
        ArrayList<String> datesGroup = getDatesGroup();
        ArrayList<Integer> caloriesCount = getCaloriesCountForDates(datesGroup);

        Log.i("mylog","nutrition records: ");
        for (int i=0;i<allRecordsOfUser.size();i++) {
            Log.i("mylog","date "+i+"): " + allRecordsOfUser.get(i).getDate());
        }
        Log.i("mylog","--------------------");
        Log.i("mylog","--------------------");
        Log.i("mylog","calories per date: ");
        for (int i=0;i<datesGroup.size();i++) {
            Log.i("mylog","calories for "+datesGroup.get(i)+": "+caloriesCount.get(i));
        }

        barEntries.add(new BarEntry(0,-1));
        barEntries.add(new BarEntry(1,-1));
        barEntries.add(new BarEntry(2,30));
        barEntries.add(new BarEntry(3,40));
        barEntries.add(new BarEntry(4,50));
        barEntries.add(new BarEntry(5,60));
        barEntries.add(new BarEntry(6,2000));

        BarDataSet barDataSet = new BarDataSet(barEntries,"bar-data-set label");
        BarData barData = new BarData(barDataSet);

        // set A Axis lables
        XAxis xAxis = barChart.getXAxis();
        String[] labels = new String[7];

        labels[0]="sunday";
        labels[1]="monday";
        labels[2]="tuesday";
        labels[3]="wednesday";
        labels[4]="thursday";
        labels[5]="friday";
        labels[6]="saturday";

        xAxis.setValueFormatter(new AxisValueFormatter(labels));

        barChart.setData(barData);

*/

/*
        // get data
        allRecordsOfUser = sph.getNutritionRecords(sph.getActiveUser());
        ArrayList<String> dates = getAllDifferentDates();
        ArrayList<Integer> calories = getCaloriesCountForDates(dates);

        // get data into bar entries
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < dates.size() && i < 10; i++) {
            barEntries.add(new BarEntry(i, calories.get(i)));
        }

        // put entries in the chart
        BarDataSet barDataSet = new BarDataSet(barEntries,"calorie/ncount");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);


        // set A Axis lables as dates
        //String[] forFormatter = dates.toArray(new String[0]);
        String[] xLabels = new String[5];
        xLabels[0]="one";
        xLabels[1]="two";
        xLabels[2]="thee";
        xLabels[3]="four";
        xLabels[4]="five";
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new AxisValueFormatter(xLabels));

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(35);
        xAxis.setTextSize(18);
        //xAxis.setLabelCount(0,true);
        xAxis.setCenterAxisLabels(true);*/

    }

    @Override
    public void onBackPressed() {}

    @Override
    public void datePicked(String year, String month, String day) {

        NutritionRecord nr = new NutritionRecord("", "", "");
        nr.setDate(year, month, day);
        this.dateForActivity = nr.getDate();

        makeCaloriesBar();
        makeBmrBar();

        // set A Axis lables: calories and bmr
        XAxis xAxis = barChart.getXAxis();
        String[] labels = new String[2];
        xAxis.setLabelCount(2);
        labels[0]="calories";
        labels[1]="bmr";
        xAxis.setValueFormatter(new AxisValueFormatter(labels));

        BarDataSet barDataSet = new BarDataSet(barEntries,"calorie consumption");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.invalidate();


    }

    private void makeBmrBar() {

        ArrayList<User> usersInfo = sph.getUsersInfoTable();

        User forBmrBar = new User("","");

        for (int i=0;i<usersInfo.size();i++) {

            String userName = usersInfo.get(i).getUserName();

            if (userName.equals(sph.getActiveUser())) {
                forBmrBar.setUserAge(usersInfo.get(i).getUserAge());
                forBmrBar.setUserWeight(usersInfo.get(i).getUserWeight());
                forBmrBar.setUserHeight(usersInfo.get(i).getUserHeight());
                forBmrBar.setUserGender(usersInfo.get(i).getUserGender());
            }
        }

        barEntries.add(new BarEntry(1,forBmrBar.getUserBMR()));



    }

    private void makeCaloriesBar() {

        // get ENTRY 1 DATA: calorie intake for date
        ArrayList<NutritionRecord> allRecordsForDate = sph.getNutritionRecords(sph.getActiveUser(), this.dateForActivity);
        if (allRecordsForDate.size() > 0) {


            this.caloriesForDate = 0;
            for (int i = 0; i <allRecordsForDate.size();i++)
                this.caloriesForDate += Integer.parseInt(allRecordsForDate.get(i).getCalories());

        }

        // add calorie entry
        barEntries.add(new BarEntry(0,this.caloriesForDate));   // calories bar entry



    }

    public class AxisValueFormatter implements IAxisValueFormatter {

        private String[] values;

        public AxisValueFormatter(String[] values) {
            this.values = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return values[(int) value];
        }
    }
/*
    private ArrayList<String> getDatesGroup() {

        ArrayList<String> dates = new ArrayList<>();

        boolean accur = false;
        for (int i = 0; i < allRecordsOfUser.size(); i++) {

            String date = allRecordsOfUser.get(i).getDate();

            for (int j = 0; j < dates.size(); j++) {
                // check if exist
                if (date.equals(dates.get(j))) {
                    accur = true;
                    break;
                }
            }
            if (!accur)
                dates.add(date);
            accur = false;
        }

        return dates;
    }

    private ArrayList<Integer> getCaloriesCountForDates(ArrayList<String> dates) {

        ArrayList<Integer> calories = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            String date = dates.get(i);
            int calInCurrDate = 0;

            for (int j = 0; j < allRecordsOfUser.size(); j++) {
                if (date.equals(allRecordsOfUser.get(j).getDate())) {
                    calInCurrDate += Integer.parseInt(allRecordsOfUser.get(j).getCalories());
                }
            }
            calories.add(calInCurrDate);
        }

        return calories;

    }*/

}
