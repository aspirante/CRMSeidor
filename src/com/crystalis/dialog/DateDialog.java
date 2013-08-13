package com.crystalis.dialog;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DateDialog extends DialogFragment {

	private OnDateSetListener ondateSet;
	
	public DateDialog (){	}

	private int year, month, day;

	 @Override
	 public void setArguments(Bundle args) {
	  super.setArguments(args);
	  year = args.getInt("year");
	  month = args.getInt("month");
	  day = args.getInt("day");
	 }
	 
	public void setCallBack(OnDateSetListener ondate) {
    	  ondateSet = ondate;
    	 }
    

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//    	// Use the current date as the default date in the picker
    	final Calendar c = Calendar.getInstance();
    	int year = c.get(Calendar.YEAR);
    	int month = c.get(Calendar.MONTH);
    	int day = c.get(Calendar.DAY_OF_MONTH);
     return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
    }
    
}