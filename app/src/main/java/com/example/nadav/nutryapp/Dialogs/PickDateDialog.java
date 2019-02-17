package com.example.nadav.nutryapp.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.nadav.nutryapp.R;

public class PickDateDialog extends DialogFragment {

    private CalendarView calendarView;
    //private static String ARG1;
    private DateHandler dateHandler;
    public interface DateHandler {
        void datePicked (String year, String month, String day);
    }

    public static PickDateDialog newInstance() {
        //ARG1 = arg1;
        //Log.i("mylog","arg1 from dialog: "+ARG1);
        return new PickDateDialog();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DateHandler) {
            dateHandler = (DateHandler) context;
        }
        else {
            throw new ClassCastException(context.toString() + "must implement DateHandler interface");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_pick_date,container,false);

        calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                dateHandler.datePicked(String.valueOf(i),String.valueOf(i1),String.valueOf(i2));
                dismiss();
            }
        });
        return view;
    }

}
