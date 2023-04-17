package com.gsoft.wallet.view.dialog;

import android.content.Context;
import android.icu.util.Calendar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class DatePickerDialog implements View.OnClickListener {
    private android.app.DatePickerDialog datePickerDialog;
    private final Context context;

    public DatePickerDialog(Context context) {
        datePickerDialog = null;
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        EditText editText = (EditText) view;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day =  calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new android.app.DatePickerDialog(context, new android.app.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month += 1;
                String date = dayOfMonth+"-"+month+"-"+year;
                editText.setText(date);
            }
        }, year, month, day);
        datePickerDialog.show();
    }
}
