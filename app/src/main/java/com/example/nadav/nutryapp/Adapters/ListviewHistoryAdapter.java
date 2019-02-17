package com.example.nadav.nutryapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nadav.nutryapp.Models.NutritionRecord;
import com.example.nadav.nutryapp.R;

import java.util.ArrayList;

public class ListviewHistoryAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<NutritionRecord> nutritionRecords;

    public ListviewHistoryAdapter (Context context, ArrayList<NutritionRecord> nutritionRecords) {

        this.context = context;
        this.nutritionRecords = nutritionRecords;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return nutritionRecords.size();
    }

    @Override
    public Object getItem(int i) {
        return nutritionRecords.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = layoutInflater.inflate(R.layout.listviewdetail_nutrition_history,null);
        
        TextView desc = v.findViewById(R.id.lvDetail_history_description);
        TextView cal = v.findViewById(R.id.lvDetail_history_calories);

        desc.setText(nutritionRecords.get(i).getDescription());
        cal.setText("calories: "+nutritionRecords.get(i).getCalories());

        return v;
    }

}
