package com.crystalis.view;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crystalis.interfaces.IMain;
import com.crystalis.listeners.ChangeFragmenListener;
import com.crystalis.menubar.AnimationType;
import com.crystalis.menubar.FragmentActive;
import com.crystalis.menubar.FragmentSwapManager;
import com.crystalis.model.DataManager;

public class DemoActivity extends FragmentActivity implements ChangeFragmenListener  {

    public static Context appContext;
    
    private ImageView main_btn_exit;
    private FragmentActive currentTab;
    private Fragment bodyFragment;

    private SharedPreferences SP;
    private String url;
    private Bundle args;
    private DataManager datamanager;
    
    private boolean scan = false;
    private String clienteId=null;

    private TextView user;

    private ImageView main_btn_home;

    // Unchanged
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	url = SP.getString("url", "NA");
	args = new Bundle();
	args.putString(IMain.URL_KEY, url);
	
	getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

	datamanager = DataManager.getInstance();
	
	 System.out.println("onCreate () ");

	if (savedInstanceState != null) {
	    
	    currentTab = datamanager.getCurrentTab();
	    
	    if(IMain.DEBUG)
		System.out.println("onCreate: if: savedInstanceState: currentTab: Saved: "+currentTab);

	} else {
	    
	    if( currentTab == null ){
		currentTab = FragmentActive.TAB_HOME;
		BannerFragment banner = new BannerFragment();
		selectFragmentAndSwitch(banner, null, AnimationType.RightToLeft);

	    }
	    else 
		currentTab = datamanager.getCurrentTab();
	    
	    if(IMain.DEBUG)	    
		System.out.println("onCreate: else: currentTab: "+currentTab);
	}

	user	= (TextView) findViewById(R.id.main_user);
	user.setText(datamanager.getUseralias());
	
	main_btn_exit = (ImageView) findViewById(R.id.main_btn_exit);
	main_btn_exit.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		finish();
	    }
	});
	
	main_btn_home = (ImageView) findViewById(R.id.main_btn_home);
	main_btn_home.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		currentTab = FragmentActive.TAB_HOME;
		BannerFragment banner = new BannerFragment();
		selectFragmentAndSwitch(banner, null, AnimationType.RightToLeft);
	    }
	});
	
    }
   
    public boolean isScan() {
        return scan;
    }



    public void setScan(boolean scan) {
        this.scan = scan;
    }



    public String getClienteId() {
        return clienteId;
    }



    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }



    @Override
    protected void onStart() {
	super.onStart();
	 System.out.println("onStart () "+ " currentTab: "+currentTab);

    }
    
    @Override
    protected void onResume() {

        super.onResume();
        System.out.println("onResume () ");
   	onChangeFragmentActionClick(currentTab);
    }

    public void onChangeFragmentActionClick(FragmentActive currentTab) {
	this.currentTab = currentTab;

	switch (currentTab) {
	
	case TAB_HOME:
	    
	    	BannerFragment banner = new BannerFragment();
		selectFragmentAndSwitch(banner, null, AnimationType.RightToLeft);

		    if (IMain.DEBUG)
			System.out.println("Home fragment - Active");

	    break;
	case TAB_CITAS:
	    
		 DatesFragment citas = new DatesFragment();
		    citas.setArguments(args);
		    selectFragmentAndSwitch(citas, null, AnimationType.LeftToRight);

		    if (IMain.DEBUG)
			System.out.println("citas fragment - Active");

	    break;
	case TAB_CLIENTES:

	    CustomerFragment customer = new CustomerFragment();
	    args.putBoolean("scan", isScan() );
	    args.putString("clienteId", getClienteId());
	    customer.setArguments(args);
	    selectFragmentAndSwitch(customer, null, AnimationType.LeftToRight);
	    
	    setScan(false);

	    if (IMain.DEBUG)
		System.out.println("clientes fragmentActive");
	    
	    break;
	case TAB_PRODUCTOS:
	    ProductsFragment products = new ProductsFragment();
	    products.setArguments(args);
	    selectFragmentAndSwitch(products, null, AnimationType.LeftToRight);

	    if (IMain.DEBUG)
		System.out.println("producto fragment - Active");
	    

	    break;
	case TAB_REPORTES:

//	    Reporte1Fragment reportes = new Reporte1Fragment();
//	    selectFragmentAndSwitch(reportes, null, AnimationType.LeftToRight);
//
	    if (IMain.DEBUG)
		System.out.println("reportes fragmentActive");

		ReportsFragment reports = new ReportsFragment();
		reports.setArguments(args);
		selectFragmentAndSwitch(reports, null, AnimationType.LeftToRight);

	    break;
	case TAB_VENTAS:

	    SalesFragment sales = new SalesFragment();
	    sales.setArguments(args);
	    selectFragmentAndSwitch(sales, null, AnimationType.LeftToRight);

	    if (IMain.DEBUG)
		System.out.println("ventas fragment - Active");

	    break;
	default:
	    break;
	}
	datamanager.setCurrentTab(currentTab);
    }
    
    private void selectFragmentAndSwitch(Fragment fragment, Object necessaryInfo, AnimationType animation) {

	if (bodyFragment == null) {
	    // pop everything
	    bodyFragment = fragment;

	    FragmentTransaction ft = getSupportFragmentManager()
		    .beginTransaction();
	    ft.replace(R.id.fragment_details, bodyFragment, "listFragment");
	    ft.commit();

	} else {
	    FragmentSwapManager.changeFragment(this, R.id.fragment_details,
		    fragment, null, true, animation);
	    // FragmentSwapManager.changeFragment(this, bodyFragment, fragment,
	    // null, true, animation);
	    bodyFragment = fragment;
	}
    }
  

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	if (IMain.DEBUG)
		System.out.println("onActivityResult");
//	Toast.makeText(this, "resultCode: "+resultCode, Toast.LENGTH_SHORT).show();
//	    if (requestCode == 0) {
	        if (resultCode == RESULT_OK) {
	            String contents = intent.getStringExtra("SCAN_RESULT");
	            String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

	            Toast.makeText(this, "contents: "+contents + " format: "+format, Toast.LENGTH_SHORT).show();
	            setClienteId( contents );
	            setScan( true );
	            
	            // Handle successful scan
	        } else if (resultCode == RESULT_CANCELED) {
	            // Handle cancel
	        }
//	    }
    }

}
