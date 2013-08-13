package com.crystalis.dialog;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

@SuppressLint("ValidFragment")
public class TimeDialog extends DialogFragment {

    private OnTimeSetListener ontimeSet;
    private boolean is24hrs;

    public TimeDialog (boolean is24hrs){ this.is24hrs = is24hrs; }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the current time as the default values for the picker
    final Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);
    // Create a new instance of TimePickerDialog and return it
    TimePickerDialog time = new TimePickerDialog(getActivity(), ontimeSet, hour, minute, is24hrs/*DateFormat.is24HourFormat(getActivity())*/);
    time. setTitle("");
    
    return time;
//    return new TimePickerDialog(getActivity(), ontimeSet, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }
    
    
    public void setCallBack(OnTimeSetListener ontimeSet) {
	this.ontimeSet = ontimeSet;
    }
}
