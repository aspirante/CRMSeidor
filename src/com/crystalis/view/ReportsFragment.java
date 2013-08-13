package com.crystalis.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 

public class ReportsFragment extends Fragment {
	
	private View viewMain;
	

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    	viewMain = inflater.inflate(R.layout.reportmain, container, false);
    	


        return viewMain;       
    }
 
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);



    }





}
