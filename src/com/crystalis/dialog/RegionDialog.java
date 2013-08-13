package com.crystalis.dialog;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.crystalis.listeners.CountryDialogListener;
import com.crystalis.listeners.RegionDialogListener;
import com.crystalis.model.DataManager;
import com.crystalis.view.R;
import com.crystalis.view.R.string;

public class RegionDialog extends DialogFragment {
    
    private DataManager datamanager;
    
    // Use this instance of the interface to deliver action events
    private RegionDialogListener regiondialoglistener;
    
    private CharSequence[] regions;
    
    private List<String> regionList;

    public RegionDialog() { }
    
    public RegionDialog(RegionDialogListener regiondialoglistener, List<String> regionList) {
	this.regiondialoglistener = regiondialoglistener;
	this.regionList = regionList;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        datamanager = DataManager.getInstance();

        regions = regionList.toArray(new CharSequence[regionList.size()]);
    }
    

    public Dialog onCreateDialog(Bundle savedInstanceState) {
	
		
	    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity())
  	    .setTitle(getResources().getString(R.string.txt_estado))
  	    .setCancelable(true) 	    
  	    .setItems( regions, new DialogInterface.OnClickListener() {
	        
	        @Override
	        public void onClick(DialogInterface dialog, int which) {

	            Toast.makeText(getActivity(), getResources().getString(R.string.txt_estado)+" "+regions[which], Toast.LENGTH_SHORT).show();
	            regiondialoglistener.onDialogItemSelectedClick(regions[which].toString());
	        }
	    });
    return alertDialogBuilder.create();
    }

}
