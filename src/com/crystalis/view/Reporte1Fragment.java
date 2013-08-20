package com.crystalis.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.crystalis.dataconnection.DataConnection;
import com.crystalis.getData.DownloadData;
import com.crystalis.interfaces.IMain;
import com.crystalis.listeners.AsyntaskCallBackListeners;
import com.crystalis.tools.Parser;
 
/**
* A straightforward example of using AndroidPlot to plot some data.
*/
public class Reporte1Fragment extends Fragment{
 
    private View viewMain;
	private WebView webview;
	private WebSettings webSettings;
	private ImageButton btn_report1;   
	private ImageButton btn_report2;
	private ImageButton btn_report3;
	
	
	String typeColumnChart = "ColumnChart";
	String typeLineChart   = "LineChart";
	String typeComboChart  = "AreaChart";
   
	private Parser tools;
	private String url;
    
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
	viewMain = inflater.inflate(R.layout.report_sales_layout, container, false);

	 webview = (WebView) viewMain.findViewById(R.id.webView1);
	 btn_report1 = (ImageButton) viewMain.findViewById(R.id.sample_xy_plot_btn_report1);
	 btn_report1.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
	
			String dataColumns = getDataSales();

			draw(setDataToDraw(dataColumns, typeColumnChart, "Ventas", "Cliente", "Pedidos"));
					
		}
	});
	 
	 btn_report2 = (ImageButton) viewMain.findViewById(R.id.sample_xy_plot_btn_report2);
	 btn_report2.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {

			String dataColumns = getDataSales();

			draw(setDataToDraw(dataColumns, typeLineChart, "Ventas", "Cliente", "Pedidos"));


		}
	});
	 
	 btn_report3 = (ImageButton) viewMain.findViewById(R.id.sample_xy_plot_btn_report3);
	 btn_report3.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {

			String dataColumns = getDataSales();

			draw(setDataToDraw(dataColumns, typeComboChart, "Ventas", "Cliente", "Pedidos"));

		}
	});
	 


     webSettings = webview.getSettings();
    webSettings.setJavaScriptEnabled(true);

    
        
        return viewMain;
    }

    
    public String getDataSales(){
    	
		String[] customers = tools.getCustomersReport1();			
		String[] total	   = tools.getTotalReport1();			
		String data = null ;			

		String x =",";			
		
		for(int a = 0 ; a < customers.length; a ++){

			System.out.println("a: "+ a +" row: "+"['"+customers[a]+"',"+total[a]+"]");

			if( a == 0 )				
				data = "['"+customers[a]+"',"+total[a]+"]" + x;					
			else{
				data += "['"+customers[a]+"',"+total[a]+"]";
				if( a < customers.length - 1 )
					data += x;					
			}
			
		}
		
		if(IMain.DEBUG)
			System.out.println("columns: "+data);

		
		return data;
    }
    
    public String setDataToDraw(String dataColumns, String type,String title, String nameColumn1, String nameColumn2) {
   	
    	
        String columnChart2 = 
        		"<html>"
                + "  <head>"
                + "    <script type=\"text/javascript\" src=\"jsapi.js\"></script>"
                + "    <script type=\"text/javascript\">"
                + "      google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});"
                + "      google.setOnLoadCallback(drawChart);"
                + "      function drawChart() {"
                + "        var data = google.visualization.arrayToDataTable(["
                + "          ['"+nameColumn1+"', '"+nameColumn2+"'],"
//                + "          ['2010'   ,  1000], ['2011'   ,  1170], ['2012'   ,  660 ], ['2013'   ,  1030]"
				+ dataColumns
                + "        ]);"
                + "        var options = {"
                + "			width: '100%',"
                + "			height: '100%',"
                + "          title: '"+title+"',"
                + "          hAxis: {title: '"+nameColumn1+"', titleTextStyle: {color: 'red'}, textStyle: {fontSize:7}}"
                + "        };"
                + "        var chart = new google.visualization."+type+"(document.getElementById('chart_div'));"
                + "        chart.draw(data, options);"
                + "      }"
                + "    </script>"
                + "  </head>"
                + "  <body>"
                + "    <div id='chart_div'style=\"width: 100%; height: 100%; \"></div>"
                + "  </body>" 
                + "</html>";
        
        return columnChart2;
        
	}

	public void draw(String content){
    	
        webview.requestFocusFromTouch();
        webview.loadDataWithBaseURL( "file:///android_asset/", content, "text/html", "utf-8", null );  
    	
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    	tools = new Parser(getActivity().getApplicationContext());
    	url = getArguments().getString(IMain.URL_KEY);
 
    }
    
    public void pedidos(){

	    new DownloadData(getActivity(), tools.updateUrl( DataConnection.SALESORDER_GETWAY_JSON, url), DataConnection.user, DataConnection.pwd, " Pedidos ... ", new AsyntaskCallBackListeners() {

			@Override
			public void onTaskDone(String data) {
			    
			    saveData(data);

			}
			
		    }).execute();
  }

	public void saveData(String data) {

		tools.HeadersJsonToDB(data);
		
	}
    
	@Override
		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
			pedidos();

		}
}