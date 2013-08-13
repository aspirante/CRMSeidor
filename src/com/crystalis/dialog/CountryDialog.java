package com.crystalis.dialog;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.crystalis.listeners.CountryDialogListener;
import com.crystalis.model.DataManager;
import com.crystalis.view.R;
import com.crystalis.view.R.string;

public class CountryDialog extends DialogFragment {
    
    private DataManager datamanager;
    
    // Use this instance of the interface to deliver action events
    private CountryDialogListener countrydialoglistener;
    
    private CharSequence[] countries;
    
    private List<String> countryList;

    public CountryDialog() { }
    
    public CountryDialog(CountryDialogListener countrydialoglistener, List<String> countryList) {
	this.countrydialoglistener = countrydialoglistener;
	this.countryList = countryList;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        datamanager = DataManager.getInstance();

        countries = countryList.toArray(new CharSequence[countryList.size()]);
    }
    

    public Dialog onCreateDialog(Bundle savedInstanceState) {
	
		
	    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity())
  	    .setTitle(getResources().getString(R.string.txt_pais))
  	    .setCancelable(true) 	    
  	    .setItems( countries, new DialogInterface.OnClickListener() {
	        
	        @Override
	        public void onClick(DialogInterface dialog, int which) {

	            Toast.makeText(getActivity(), getResources().getString(R.string.txt_pais)+" "+countries[which], Toast.LENGTH_SHORT).show();
	            countrydialoglistener.onDialogItemSelectedClick(countries[which].toString());
	        }
	    });
    return alertDialogBuilder.create();
    }

}
