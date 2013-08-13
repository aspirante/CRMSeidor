package com.crystalis.sendData;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.crystalis.interfaces.IMain;
import com.crystalis.listeners.AsyntaskCallBackListeners;

public class Put extends AsyncTask<Void, Void, String>{
    
    private String url;
    private Activity activity;
    private String usr;
    private String pwd;
    
    private String xml = null;
    private String token = null;
    private String result = null;
    private String msg = null;
    
    private AsyntaskCallBackListeners asyntaskCallBack;

    private DefaultHttpClient client = new DefaultHttpClient();
    final HttpParams params = client.getParams();

    private boolean error = false;

    private int NOTIFICATION_ID = 1;
    
    private Notification mNotification;
    private NotificationManager mNotificationManager;
    private ProgressDialog pDialog;
    private int resultCode = -1;



    public Put( Activity act, String url, String usr, String pwd, String xml, final String msg, AsyntaskCallBackListeners asyntaskCallBack ) {

	this.activity = act;
	this.usr = usr;
	this.pwd = pwd;
	this.url = url;
	this.xml = xml;
	this.msg = msg;
	
	this.asyntaskCallBack = asyntaskCallBack;

	mNotificationManager = (NotificationManager) act.getSystemService(Context.NOTIFICATION_SERVICE);
	
	pDialog = new ProgressDialog(act);

    }
    
    @Override
    protected void onPreExecute() {
	pDialog.show();
    }

    @Override
    protected String doInBackground(Void... empty) {

	try {

            HttpConnectionParams.setConnectionTimeout(params, IMain.REGISTRATION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, IMain.WAIT_TIMEOUT);
            ConnManagerParams.setTimeout(params, IMain.WAIT_TIMEOUT);
            
            publishProgress(null);
            
	    client.getCredentialsProvider().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(usr, pwd));

	    HttpGet get = new HttpGet(url);

	    get.setHeader("Content-Type", "application/atom+xml");
	    get.setHeader("X-CSRF-Token", "Fetch");

	    HttpResponse response = client.execute(get);
	    Header[] htmp = response.getHeaders("X-CSRF-Token");

	    if (IMain.DEBUG)
		System.out.println(" Url: " + url + "htmp[0].getValue(): " + htmp[0].getValue() +" Xml: "+xml);
		
	    HttpPut put = new HttpPut(url);

	    put.setHeader("Content-Type", "application/atom+xml");
	    put.setHeader("X-CSRF-Token", htmp[0].getValue());

	    StringEntity entity = new StringEntity(xml);
	    entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/atom+xml"));
	    put.setEntity(entity);

	    response = client.execute(put);

	    if(IMain.DEBUG)
		System.out.println(" StatusCode: " + response.getStatusLine().getStatusCode());
	    
	    if ( response.getStatusLine().getStatusCode() == IMain.STATUS_CODE_204 ){
		result = "ok";
		resultCode = response.getStatusLine().getStatusCode();

	    }

	} catch (Exception e) {
	    Log.w("HTTP4:", e);
	    result = e.getMessage();
	    error = true;
	    cancel(true);
	    pDialog.dismiss();

	}

	return result;
    }
	
    
    protected void onProgressUpdate(Void... v) {
	pDialog.setMessage(msg);

    }

    protected void onCancelled() {
	createNotification(IMain.MSG_ERROR_CONEXION, token);
	// Toast.makeText(activity, IMain.MSG_ERROR_CONEXION,
	// Toast.LENGTH_SHORT).show();

	if(IMain.DEBUG)
	    System.out.println("Error occured during data download!");

    }
	    
    @Override
    protected void onPostExecute(String result) {

	pDialog.dismiss();

	if (error) {
	    // createNotification("Data download ended abnormally!",content);
	    if (IMain.DEBUG)
		System.out.println("Error: Data download ended abnormally!");
	} else {
	    // createNotification("Data download is complete!","");
	    // Toast.makeText(activity, IMain.MSG_CARGA_COMPLETA,
	    // Toast.LENGTH_SHORT).show();

	    asyntaskCallBack.onTaskDone( String.valueOf(resultCode) );

	}

    }

    private void createNotification(String contentTitle, String contentText) {

	// Build the notification using Notification.Builder
	Notification.Builder builder = new Notification.Builder(activity)
		.setSmallIcon(android.R.drawable.stat_sys_download)
		.setAutoCancel(true).setContentTitle(contentTitle)
		.setContentText(contentText);

	// Get current notification
	mNotification = builder.getNotification();

	// Show the notification
	mNotificationManager.notify(NOTIFICATION_ID, mNotification);
    }    
}
