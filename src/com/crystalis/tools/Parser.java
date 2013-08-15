package com.crystalis.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.crystalis.db.DataSource;
import com.crystalis.interfaces.IMain;
import com.crystalis.listeners.Response;
import com.crystalis.model.Country;
import com.crystalis.model.Customer;
import com.crystalis.model.DatesModel;
import com.crystalis.model.Header;
import com.crystalis.model.Imagen;
import com.crystalis.model.ItemHeader;
import com.crystalis.model.ItemMaterial;
import com.crystalis.model.Material;
import com.crystalis.model.Region;

public class Parser {

	private Response response;
	private DataSource datasource;
	
	 public Parser(Context context) {
		    datasource = new DataSource(context);
		    datasource.open();
	 }
	 
	 public Parser() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Header> HeadersJson(String Json) {
		
	    	ArrayList<Header> SalesOrdersHeaders = new ArrayList<Header>();
	    	
		JSONObject JSONData;
		JSONObject JSONRoot;


		try {
		    JSONData = new JSONObject (Json);
		    JSONRoot = JSONData.getJSONObject(IMain.ROOT_JSON);
		    JSONArray resultsArray = JSONRoot.getJSONArray(IMain.RESULTS_JSON);
		    for(int i=0; i < resultsArray.length(); i++){
			JSONObject json_data = resultsArray.getJSONObject(i);
			response = new Header().jsonToObject(json_data.toString());
			Header infoServer = (Header) response;
			Header header = new Header(infoServer.getOrderId(), infoServer.getDocumentType(), infoServer.getDocumentDate(), infoServer.getCustomerId(), 
						     infoServer.getSalesOrg(), infoServer.getDistChannel(), infoServer.getDivision(), infoServer.getOrderValue(), 
						     infoServer.getCurrency(), infoServer.getInvoice());
			SalesOrdersHeaders.add(header);
		    }		    
		} catch (JSONException e) {
		    e.printStackTrace();
		}
		

	return SalesOrdersHeaders;
	}
	
	public void HeadersJsonToDB(String Json) {
		
	datasource.open();
	
	JSONObject JSONData;
	JSONObject JSONRoot;


	try {
	    JSONData = new JSONObject (Json);
	    JSONRoot = JSONData.getJSONObject(IMain.ROOT_JSON);
	    JSONArray resultsArray = JSONRoot.getJSONArray(IMain.RESULTS_JSON);
	    for(int i=0; i < resultsArray.length(); i++){
		JSONObject json_data = resultsArray.getJSONObject(i);
		response = new Header().jsonToObject(json_data.toString());
		Header infoServer = (Header) response;
		datasource.insertSale(infoServer.getOrderId(), infoServer.getDocumentType(), infoServer.getDocumentDate(), infoServer.getCustomerId(), 
					     infoServer.getSalesOrg(), infoServer.getDistChannel(), infoServer.getDivision(), infoServer.getOrderValue(), 
					     infoServer.getCurrency(), infoServer.getInvoice());

	    }		    
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	
	datasource.close();
	
	System.out.println("data saved into DB ");
}
	
	public void getDataReport1(){
		datasource.open();
		Cursor cursor = datasource.getDataSalesToReport();
		if(cursor.isFirst()){
				do{
					String dato1 = cursor.getColumnName(cursor.getColumnIndex("customerid"));
					
					if(IMain.DEBUG)
						System.out.println("getDataReport1: customerid: "+dato1);
				}while(cursor.moveToNext());
		}
		datasource.close();
	}
	
	public ArrayList<Header> FilterHeaderJson(String json) {
		
	    	ArrayList<Header> SalesOrdersHeaders = new ArrayList<Header>();
		JSONObject JSONData;
		String dataHeader  = null;

		try {
		    JSONData = new JSONObject (json);
		    dataHeader  = JSONData.getJSONObject(IMain.ROOT_JSON).toString();
			response = new Header().jsonToObject(dataHeader);
			Header infoServer = (Header) response;
			Header data = new Header(infoServer.getOrderId(), infoServer.getDocumentType(), infoServer.getDocumentDate(), infoServer.getCustomerId(), 
						     infoServer.getSalesOrg(), infoServer.getDistChannel(), infoServer.getDivision(), infoServer.getOrderValue(), 
						     infoServer.getCurrency(), infoServer.getInvoice());
			SalesOrdersHeaders.add(data);
		} catch (JSONException e) {
		    e.printStackTrace();
		}
		

	return SalesOrdersHeaders;
	}
	
    public ArrayList<ItemHeader> itemHeaderJson(String json) {
	
	ArrayList<ItemHeader> itemsList = new ArrayList<ItemHeader>();

	JSONObject JSONData;
	JSONObject JSONRoot;
	JSONObject JSONRoot1;

	try {
	    JSONData = new JSONObject (json);
	    JSONRoot = JSONData.getJSONObject(IMain.ROOT_JSON);
	    JSONRoot1 = JSONRoot.getJSONObject(IMain.SOITEMS_JSON);

	    JSONArray resultsArray = JSONRoot1.getJSONArray(IMain.RESULTS_JSON);
	    for(int i=0; i < resultsArray.length(); i++){
		
		JSONObject json_data = resultsArray.getJSONObject(i);
		response = new ItemHeader().jsonToObject(json_data.toString());
		ItemHeader infoServer = (ItemHeader) response;
		ItemHeader item = new ItemHeader(infoServer.getOrderId(), infoServer.getItem(), infoServer.getMaterial(), infoServer.getDescription(), infoServer.getPlant(), 
				       infoServer.getQuantity(), infoServer.getUoM(), infoServer.getValue());
			
		itemsList.add(item);
	    }		    
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	
	return itemsList;
    }
	
    
	public ArrayList<Material> parserMaterialsJson(String Json) {
		
		ArrayList<Material> materials = new ArrayList<Material>();

		JSONObject JSONData;
		JSONObject JSONRoot;

		try {
		    JSONData = new JSONObject (Json);
		    JSONRoot = JSONData.getJSONObject(IMain.ROOT_JSON);
		    JSONArray resultsArray = JSONRoot.getJSONArray(IMain.RESULTS_JSON);

			for (int i = 0; i < resultsArray.length(); i++) {
				JSONObject json_data = resultsArray.getJSONObject(i);
				response = new Material().jsonToObject(json_data.toString());
				Material infoServer = (Material) response;

				Material material = new Material(infoServer.getMaterialId(), infoServer.getDescripcion(), infoServer.getStock(), infoServer.getPrecio());
				materials.add(material);
			}
		} catch (JSONException e) {
				e.getMessage();

		}
	return materials;
	}
	
	public ArrayList<ItemMaterial> parserItemMaterialJson(String Json) {
		
		ArrayList<ItemMaterial> itemmaterials = new ArrayList<ItemMaterial>();

		JSONObject JSONData;
		JSONObject JSONRoot;

		try {
		    JSONData = new JSONObject (Json);
		    JSONRoot = JSONData.getJSONObject(IMain.ROOT_JSON);
		    JSONArray resultsArray = JSONRoot.getJSONArray(IMain.RESULTS_JSON);

			for (int i = 0; i < resultsArray.length(); i++) {
				JSONObject json_data = resultsArray.getJSONObject(i);
				response = new ItemMaterial().jsonToObject(json_data.toString());
				ItemMaterial infoServer = (ItemMaterial) response;

				ItemMaterial item = new ItemMaterial(infoServer.getOrderId(), infoServer.getItem(), infoServer.getMaterial(), infoServer.getDescription(), infoServer.getPlant(), infoServer.getQuantity(), infoServer.getUoM(), infoServer.getValue());
				itemmaterials.add(item);
			}
		} catch (JSONException e) {
				e.getMessage();

		}
	return itemmaterials;
	}
	
public Drawable parseImageJson(String Json) {
    	
    	Drawable Imagen = null;

		JSONObject JSONData;
		JSONObject JSONRoot;

		try {
		    JSONData = new JSONObject (Json);
		    JSONRoot = JSONData.getJSONObject(IMain.ROOT_JSON);
		    JSONArray resultsArray = JSONRoot.getJSONArray(IMain.RESULTS_JSON);

			for (int i = 0; i < resultsArray.length(); i++) {
				JSONObject json_data = resultsArray.getJSONObject(i);
				response = new Imagen().jsonToObject(json_data.toString());
				Imagen infoServer = (Imagen) response;

				Imagen img = new Imagen(infoServer.getImagen());
			//	Imagen.setI(img);
			}
		} catch (JSONException e) {
				e.getMessage();

		}
	return Imagen;
    }
	
public ArrayList<DatesModel> parserDatesJson(String Json) {
	
	ArrayList<DatesModel> dates = new ArrayList<DatesModel>();

	JSONObject JSONData;
	JSONObject JSONRoot;

	try {
	    JSONData = new JSONObject (Json);
	    JSONRoot = JSONData.getJSONObject(IMain.ROOT_JSON);
	    JSONArray resultsArray = JSONRoot.getJSONArray(IMain.RESULTS_JSON);

		for (int i = 0; i < resultsArray.length(); i++) {
			JSONObject json_data = resultsArray.getJSONObject(i);
			response = new DatesModel().jsonToObject(json_data.toString());
			DatesModel infoServer = (DatesModel) response;

			DatesModel date = new DatesModel(infoServer.getcustomerId(), infoServer.getinitialhour(), infoServer.getendhour(),
					infoServer.getsubject(), infoServer.getPlace(), infoServer.getstatus());
			dates.add(date);
		}
	} catch (JSONException e) {
			e.getMessage();

	}
return dates;
}

	public ArrayList<Customer> parserCustomersJson(String Json) {
		
		ArrayList<Customer> customerList = new ArrayList<Customer>();

		JSONObject JSONData;
		JSONObject JSONRoot;

		try {
		    JSONData = new JSONObject (Json);
		    JSONRoot = JSONData.getJSONObject(IMain.ROOT_JSON);
		    JSONArray resultsArray = JSONRoot.getJSONArray(IMain.RESULTS_JSON);

			for (int i = 0; i < resultsArray.length(); i++) {
			    
				JSONObject json_data = resultsArray.getJSONObject(i);
				response = new Customer().jsonToObject(json_data.toString());
				Customer infoServer = (Customer) response;

				Customer customer = new Customer( infoServer.getCustomerid(), infoServer.getName(), infoServer.getStreet(), infoServer.getNumber(), 
								 infoServer.getCity(), infoServer.getDistrict(), infoServer.getState(), infoServer.getZip(),
								 infoServer.getCountry(), infoServer.getRfc(), infoServer.getTel(), infoServer.getMovil(),
								 infoServer.getEmail() );
				
				customerList.add(customer);
			}
		} catch (JSONException e) {
				e.getMessage();

		}
	return customerList;
	}
	
	public ArrayList<Country> parserCountryJson(String Json) {
		
		ArrayList<Country> countryList = new ArrayList<Country>();

		JSONObject JSONData;
		JSONObject JSONRoot;

		try {
		    JSONData = new JSONObject (Json);
		    JSONRoot = JSONData.getJSONObject(IMain.ROOT_JSON);
		    JSONArray resultsArray = JSONRoot.getJSONArray(IMain.RESULTS_JSON);

			for (int i = 0; i < resultsArray.length(); i++) {
			    
				JSONObject json_data = resultsArray.getJSONObject(i);
				response = new Country().jsonToObject(json_data.toString());
				Country infoServer = (Country) response;

				Country country = new Country( infoServer.getCountryId(), infoServer.getDescription() );				
				countryList.add(country);
			}
		} catch (JSONException e) {
				e.getMessage();

		}
	return countryList;
	}
	
	public ArrayList<Region> parseRegionJson(String Json) {
		
		ArrayList<Region> regionList = new ArrayList<Region>();

		JSONObject JSONData;
		JSONObject JSONRoot;
		JSONObject JSONSubRoot;

		try {
		    JSONData = new JSONObject (Json);
		    JSONRoot = JSONData.getJSONObject(IMain.ROOT_JSON);
		    JSONSubRoot = JSONRoot.getJSONObject(IMain.SUBROOT_REGIONS_JSON);
		    JSONArray resultsArray = JSONSubRoot.getJSONArray(IMain.RESULTS_JSON);

			for (int i = 0; i < resultsArray.length(); i++) {
			    
				JSONObject json_data = resultsArray.getJSONObject(i);
				response = new Region().jsonToObject(json_data.toString());
				Region infoServer = (Region) response;

				Region region = new Region(infoServer.getCountryid(), infoServer.getRegion(), infoServer.getDescription());			
				regionList.add(region);
			}
		} catch (JSONException e) {
				e.getMessage();

		}
	return regionList;
	}
	
	public ArrayList<Customer> filterCustumerJson(String json) {
		
	    	ArrayList<Customer> custumerHeader = new ArrayList<Customer>();
		JSONObject JSONData;
		String dataHeader  = null;

		try {
		    JSONData = new JSONObject (json);
		    dataHeader  = JSONData.getJSONObject(IMain.ROOT_JSON).toString();
			response = new Customer().jsonToObject(dataHeader);
			Customer infoServer = (Customer) response;
			Customer data = new Customer( infoServer.getCustomerid(), infoServer.getName(), infoServer.getStreet(), infoServer.getNumber(), 
					  	     infoServer.getCity(), infoServer.getDistrict(), infoServer.getState(), infoServer.getZip(),
					  	     infoServer.getCountry(), infoServer.getRfc(), infoServer.getTel(), infoServer.getMovil(), infoServer.getEmail() );
			
			custumerHeader.add(data);
		} catch (JSONException e) {
		    e.printStackTrace();
		}
		

	return custumerHeader;
	}
	

	public ArrayList<Header> cleanHeaders() {
		
		ArrayList<Header> OrderItemsList = new ArrayList<Header>();
		Header header = new Header(IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR);
		OrderItemsList.add(header);
		
	return OrderItemsList;
	}
	
	public ArrayList<ItemHeader> cleanItems() {
		
		ArrayList<ItemHeader> OrderItemsList = new ArrayList<ItemHeader>();
		ItemHeader itemheader = new ItemHeader(IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR);
		OrderItemsList.add(itemheader);
		
	return OrderItemsList;
	}
	
	public ArrayList<Material> cleanMaterials() {
		
		ArrayList<Material> OrderItemsList = new ArrayList<Material>();
		Material material = new Material (IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR);
		OrderItemsList.add(material);
		
	return OrderItemsList;
	}
	
	public ArrayList<ItemMaterial> cleanItemMaterials() {
		
		ArrayList<ItemMaterial> OrderItemsList = new ArrayList<ItemMaterial>();
		ItemMaterial itemmaterial = new ItemMaterial(IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR, IMain.EMPTY_STR);
		OrderItemsList.add(itemmaterial);
		
	return OrderItemsList;
	}
	
	public String getDataConnection(String requestUrl, String user, String pwd) throws URISyntaxException,
			ClientProtocolException, IOException {

		DefaultHttpClient client = new DefaultHttpClient();
		client.getCredentialsProvider().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(user, pwd));

		if(IMain.DEBUG)
			System.out.println(" getDataConnection: requestUrl: "+requestUrl);
		
		HttpGet getRequest = new HttpGet();
		// buffer reader to read the response
		BufferedReader bufferedReader = null;
		// placeholder for the service response
		HttpResponse response = null;
		try {
			getRequest.setURI(new URI(requestUrl));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		getRequest.setHeader("Content-Type", "text/xml");
		getRequest.setHeader("Host", getRequest.getURI().getHost());

		response = client.execute(getRequest);

		bufferedReader = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		StringBuffer stringBuffer = new StringBuffer("");
		String line = "";
		try {
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
			}
			bufferedReader.close();
		} catch (IOException e) {
			Log.e("IO exception", e.toString());
		}

		if(IMain.DEBUG)
			System.out.println("getDataConnection: stringBuffer:"+stringBuffer.toString());

		// XML response, needs to be parsed as described below.
		return stringBuffer.toString();
	}

	
	public String parserDate(String date){
		 String d = date.replace("/", "");
		 d = d.replace("(", "");
		 d = d.replace(")", "");
		 d = d.replace("Date", "");
			System.out.println( " d :"+ d.toString() );

		Date dateCurrent = new Date(Long.parseLong(d));
		return dateCurrent.toString().trim();
		
	}
	
	public String convertDate(String dateIn, int dateFormatOut, int setFormatDate){
		String strdate = "";

    	if(IMain.DEBUG)
    		System.out.println(" convertDate: datestart: " + dateIn);

		if( dateIn != null && dateIn.length() > 0 && !dateIn.equals("")){ 
		SimpleDateFormat f = new SimpleDateFormat(IMain.formatsDateTime[setFormatDate]);
		 Date date = null ;
		 try {  
		     date = f.parse(dateIn);
		     if(IMain.DEBUG)
		    	 System.out.println("date  "+date);		     
		 } catch (ParseException e) {   
		     e.printStackTrace();  
		 }
	
		switch (dateFormatOut){
		
			case  IMain.FORMAT_HH_MM:
				strdate = IMain.formatsDateTime[dateFormatOut];
				break;
				
			case  IMain.FORMAT_HH_MM_SS:
				strdate = IMain.formatsDateTime[dateFormatOut];
				break;				
				
			case IMain.FORMAT_YYYYMMDD:
				strdate = IMain.formatsDateTime[dateFormatOut];
				break;
		
			case IMain.FORMAT_YYYY_MM_DD_HH_MM:
				strdate = IMain.formatsDateTime[dateFormatOut];
				break;	
				
			case IMain.FORMAT_YYYY_MM_DD_T_HH_MM_SS_SSS:
				strdate = IMain.formatsDateTime[dateFormatOut];
				break;
				
			case IMain.FORMAT_HH_MM_MMM_DD:
				strdate = IMain.formatsDateTime[dateFormatOut];
				break;

			case IMain.FORMAT_MMM_DD_YYYY_HH_MM:
				strdate = IMain.formatsDateTime[dateFormatOut];
				break;	
				
			case IMain.FORMAT_HH_MM_SS_MMM_DD_YYYY:
				strdate = IMain.formatsDateTime[dateFormatOut];
				break;			
				
			case IMain.FORMAT_MMM_DD_YYYY:
				strdate = IMain.formatsDateTime[dateFormatOut];
				break;
				
			case IMain.FORMAT_MMM_DD_YYYY_HH_MM_AMPM:
				strdate = IMain.formatsDateTime[dateFormatOut];
				break;
				
			case IMain.FORMAT_YYYY_MM_DD_HH_MM_SLASH:
				strdate = IMain.formatsDateTime[dateFormatOut];
				break;		
				
			case IMain.FORMAT_DD_MMM_YYYY:
				strdate = IMain.formatsDateTime[dateFormatOut];
				break;
			case IMain.FORMAT_DDMMYYYY:
				strdate = IMain.formatsDateTime[dateFormatOut];
				break;
			case IMain.FORMAT_DD_MM_YYYY:
				strdate = IMain.formatsDateTime[dateFormatOut];
				break;
			case IMain.FORMAT_YYYY_MM_DD:
				strdate = IMain.formatsDateTime[dateFormatOut];
				break;
				
		}

		SimpleDateFormat fs = new SimpleDateFormat(strdate);
		String dateOut = fs.format(date);
		if(IMain.DEBUG)	
			System.out.println( " format end  " +  dateOut);
		return dateOut;
		}
	return "";
	}
	
	public String replacechr(String chr, String cad, String val){		
		return cad.replace("|", val);
	}


    public List<String> getLabelsMaterials(String data) {

	List<String> Ids = new ArrayList<String>();

	List<Material> materialId = new ArrayList<Material>();
	materialId = parserMaterialsJson(data);

	if (IMain.DEBUG)
	    System.out.println("getLabelsMaterials: data size: " + materialId.size());

	for (int i = 0; i < materialId.size(); i++)
	    Ids.add(materialId.get(i).getMaterialId().toString());

	return Ids;
    }
    
    public List<String> getLabelsClientes(String data) {

	List<String> Ids = new ArrayList<String>();

	List<Customer> customerId = new ArrayList<Customer>();
	customerId = parserCustomersJson(data);

	if (IMain.DEBUG)
	    System.out.println("getLabelsCustomers: data size: " + customerId.size());

	for (int i = 0; i < customerId.size(); i++)
	    Ids.add(customerId.get(i).getCustomerid().toString());

	return Ids;
    }
    
    
    public List<String> getLabelsCountry(String data) {

	List<String> Ids = new ArrayList<String>();

	List<Country> countryId = new ArrayList<Country>();
	countryId = parserCountryJson(data);

	if (IMain.DEBUG)
	    System.out.println("getLabelsCountry: data size: " + countryId.size());

	for (int i = 0; i < countryId.size(); i++)
	    Ids.add(countryId.get(i).getCountryId().toString());

	return Ids;
    }
    
    public List<String> getLabelsRegion(String data) {

	List<String> Ids = new ArrayList<String>();

	List<Region> regionid = new ArrayList<Region>();
	regionid = parseRegionJson(data);

	if (IMain.DEBUG)
	    System.out.println("getLabelsRegion: data size: " + regionid.size());

	for (int i = 0; i < regionid.size(); i++)
	    Ids.add(regionid.get(i).getRegion().toString());

	return Ids;
    }
    
    
	
	
    public String updateUrl(String urlfull,String urlpart){
    	return urlfull.replace("$$", urlpart);
    }

    public String fixDate_YYYY_MM_DD(int year, int month, int day) {

	String fixmonth = month < 10 ? "0" + String.valueOf(month + 1) : String.valueOf(month + 1);
	String fixday = day < 10 ? "0" + String.valueOf(day) : String.valueOf(day);

	return String.valueOf(year) + "-" + fixmonth + "-" + fixday;
    }
    
    public String fixDate_DD_MM_YYYY(int year, int month, int day) {

	String fixmonth = month < 10 ? "0" + String.valueOf(month + 1) : String.valueOf(month + 1);
	String fixday = day < 10 ? "0" + String.valueOf(day) : String.valueOf(day);

	return fixday + "-" + fixmonth + "-" +String.valueOf(year);
    }
    
    public String fixDate_YYYYMMDD(int year, int month, int day) {

	String fixmonth = month < 10 ? "0" + String.valueOf(month + 1) : String.valueOf(month + 1);
	String fixday = day < 10 ? "0" + String.valueOf(day) : String.valueOf(day);

	return String.valueOf(year) + fixmonth + fixday ;
    }
    
    
    public String fixTime(int hour, int minute) {

	String fixhour = hour < 10 ? "0" + String.valueOf(hour) : String.valueOf(hour);
	String fixminute = minute < 10 ? "0" + String.valueOf(minute) : String.valueOf(minute);

	return fixhour + ":" + fixminute;
    }
    
	
}
