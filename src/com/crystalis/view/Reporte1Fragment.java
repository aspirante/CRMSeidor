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
 
//    private XYPlot plot;
    private View viewMain;
	private WebView webview;
	private WebSettings webSettings;
	private ImageButton btn_report1;   
	private ImageButton btn_report2;
	private ImageButton btn_report3;
	
	
	
    String columnChart = "<html>"
            + "  <head>"
            + "    <script type=\"text/javascript\" src=\"jsapi.js\"></script>"
            + "    <script type=\"text/javascript\">"
            + "      google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});"
            + "      google.setOnLoadCallback(drawChart);"
            + "      function drawChart() {"
            + "        var data = google.visualization.arrayToDataTable(["
            + "          ['Year', 'Sales', 'Expenses'],"
            + "          ['2010',  1000,      400],"
            + "          ['2011',  1170,      460],"
            + "          ['2012',  660,       1120],"
            + "          ['2013',  1030,      540]"
            + "        ]);"
            + "        var options = {"
            + "          title: 'Truiton Performance',"
            + "          hAxis: {title: 'Year', titleTextStyle: {color: 'red'}}"
            + "        };"
            + "        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));"
            + "        chart.draw(data, options);"
            + "      }"
            + "    </script>"
            + "  </head>"
            + "  <body>"
            + "    <div id=\"chart_div\" style=\"width: 700px; height: 380px;\"></div>"
            + "  </body>" + "</html>";
    
    String lineChart = "<html>"
            + "  <head>"
            + "    <script type=\"text/javascript\" src=\"jsapi.js\"></script>"
            + "    <script type=\"text/javascript\">"
            + "      google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});"
            + "      google.setOnLoadCallback(drawChart);"
            + "      function drawChart() {"
            + "        var data = google.visualization.arrayToDataTable(["
            + "          ['Year', 'Sales', 'Expenses'],"
            + "          ['2010',  1000,      400],"
            + "          ['2011',  1170,      460],"
            + "          ['2012',  660,       1120],"
            + "          ['2013',  1030,      540]"
            + "        ]);"
            + "        var options = {"
            + "          title: 'Truiton Performance',"
            + "          hAxis: {title: 'Year', titleTextStyle: {color: 'red'}}"
            + "        };"
            + "        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));"
            + "        chart.draw(data, options);"
            + "      }"
            + "    </script>"
            + "  </head>"
            + "  <body>"
            + "    <div id=\"chart_div\" style=\"width: 700px; height: 380px;\"></div>"
            + "  </body>" + "</html>";
    
    
    String comboChart = "<html>"
            + "  <head>"
            + "    <script type=\"text/javascript\" src=\"jsapi.js\"></script>"
            + "    <script type=\"text/javascript\">"
            + "      google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});"
            + "      google.setOnLoadCallback(drawChart);"
            + "      function drawChart() {"
            + "        var data = google.visualization.arrayToDataTable(["
            + "          ['Month', 'Bolivia', 'Ecuador', 'Madagascar', 'Papua New Guinea', 'Rwanda', 'Average'],"
            + "          ['2004/05',  165,      938,         522,             998,           450,      614.6],"
            + "          ['2006/07',  157,      1167,        587,             807,           397,      623],"
            + "          ['2007/08',  139,      1110,        615,             968,           215,      609.4],"
            + "          ['2008/09',  136,      691,         629,             1026,          366,      569.6]"
            + "        ]);"
            + "        var options = {"
            + "          title: 'Monthly Coffee Production by Country',"
            + "			  vAxis: {title: 'Cups'},"
            + "          hAxis: {title: 'Month'},"
            + "			 seriesType: 'bars',"
            + " 		 series: {5: {type: 'line'}}"
            + "        };"
            + "        var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));"
            + "        chart.draw(data, options);"
            + "      }"
            + "    </script>"
            + "  </head>"
            + "  <body>"
            + "    <div id=\"chart_div\" style=\"width: 700px; height: 380px;\"></div>"
            + "  </body>" + "</html>";
	private Parser tools;
	private DownloadData download;
	private String url;
    
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
	viewMain = inflater.inflate(R.layout.simple_xy_plot_example, container, false);

/*        // initialize our XYPlot reference:
        plot = (XYPlot) viewMain.findViewById(R.id.mySimpleXYPlot);
 
        // Create a couple arrays of y-values to plot:
        Number[] series1Numbers = {1, 8, 5, 2, 7, 4};
        Number[] series2Numbers = {4, 6, 3, 8, 2, 10};
 
        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Series1");                             // Set the display title of the series
 
        // same as above
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");
 
        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(getActivity(),
                R.xml.line_point_formatter_with_plf1);
 
        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);
 
        // same as above:
        LineAndPointFormatter series2Format = new LineAndPointFormatter();
        series2Format.setPointLabelFormatter(new PointLabelFormatter());
        series2Format.configure(getActivity(),
                R.xml.line_point_formatter_with_plf2);
        plot.addSeries(series2, series2Format);
 
        // reduce the number of range labels
        plot.setTicksPerRangeLabel(3);*/
	
	 webview = (WebView) viewMain.findViewById(R.id.webView1);
	 btn_report1 = (ImageButton) viewMain.findViewById(R.id.sample_xy_plot_btn_report1);
	 btn_report1.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {

//			draw(columnChart);
		tools.getDataReport1();
		}
	});
	 
	 btn_report2 = (ImageButton) viewMain.findViewById(R.id.sample_xy_plot_btn_report2);
	 btn_report2.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {

			draw(lineChart);
		}
	});
	 
	 btn_report3 = (ImageButton) viewMain.findViewById(R.id.sample_xy_plot_btn_report3);
	 btn_report3.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {

			draw(comboChart);
		}
	});
	 


     webSettings = webview.getSettings();
    webSettings.setJavaScriptEnabled(true);

    
        
        return viewMain;
    }
    
    public void draw(String content){
    	
        webview.requestFocusFromTouch();
        webview.loadDataWithBaseURL( "file:///android_asset/", content, "text/html", "utf-8", null );  
    	
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    	tools = new Parser(getActivity());
    	url = getArguments().getString(IMain.URL_KEY);

 
        // fun little snippet that prevents users from taking screenshots
        // on ICS+ devices :-)
//        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
 
/*        setContentView(R.layout.simple_xy_plot_example);
 
        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
 
        // Create a couple arrays of y-values to plot:
        Number[] series1Numbers = {1, 8, 5, 2, 7, 4};
        Number[] series2Numbers = {4, 6, 3, 8, 2, 10};
 
        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Series1");                             // Set the display title of the series
 
        // same as above
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");
 
        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(getApplicationContext(),
                R.xml.line_point_formatter_with_plf1);
 
        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);
 
        // same as above:
        LineAndPointFormatter series2Format = new LineAndPointFormatter();
        series2Format.setPointLabelFormatter(new PointLabelFormatter());
        series2Format.configure(getApplicationContext(),
                R.xml.line_point_formatter_with_plf2);
        plot.addSeries(series2, series2Format);
 
        // reduce the number of range labels
        plot.setTicksPerRangeLabel(3);
        plot.getGraphWidget().setDomainLabelOrientation(-45);*/
 
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