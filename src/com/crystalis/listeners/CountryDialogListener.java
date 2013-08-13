package com.crystalis.listeners;

import android.support.v4.app.DialogFragment;

public interface CountryDialogListener {
    
    public void onDialogItemSelectedClick(String item);
    public void onDialogPositiveClick(DialogFragment dialog);
    public void onDialogNegativeClick(DialogFragment dialog);
    

}
