package com.crystalis.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crystalis.interfaces.IMain;
import com.crystalis.menubar.AnimationType;
import com.crystalis.menubar.FragmentSwapManager;

 

public class ReportsFragment extends Fragment {
	
	private View viewMain;
	private Button btn_report1;
	private Button btn_report2;
	private Button btn_report3;
	private Button btn_report4;
	private Fragment bodyFragment;
	private String url;
	private Bundle args;
	

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    	viewMain = inflater.inflate(R.layout.reportmain, container, false);
    	
    	btn_report1 =  (Button) viewMain.findViewById(R.id.reportmain_btn_report1);
    	btn_report1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ReportSalesFragment sales = new ReportSalesFragment();
				sales.setArguments(args);
			    selectFragmentAndSwitch(sales, null, AnimationType.LeftToRight);


			}
		});
    	
    	btn_report2 =  (Button) viewMain.findViewById(R.id.reportmain_btn_report2);
    	btn_report2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ReportInvoicedFragment invoiced = new ReportInvoicedFragment();
			    selectFragmentAndSwitch(invoiced, null, AnimationType.LeftToRight);				
			}
		});
    	btn_report3 =  (Button) viewMain.findViewById(R.id.reportmain_btn_report3);
    	btn_report4 =  (Button) viewMain.findViewById(R.id.reportmain_btn_report4);
    			
    	


        return viewMain;       
    }
 
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	url = getArguments().getString(IMain.URL_KEY);
    	args = new Bundle();
    	args.putString(IMain.URL_KEY, url);
    }



    private void selectFragmentAndSwitch(Fragment fragment, Object necessaryInfo, AnimationType animation) {

	if (bodyFragment == null) {
	    // pop everything
	    bodyFragment = fragment;

	    FragmentTransaction ft = getFragmentManager().beginTransaction();
	    ft.replace(R.id.reports_detail, bodyFragment, "reports");
	    ft.commit();

	} else {
	    FragmentSwapManager.changeFragment(getActivity(), R.id.reports_detail, fragment, null, true, animation);
	    bodyFragment = fragment;
	}
    }
    


}
