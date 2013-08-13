package com.crystalis.getData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.crystalis.interfaces.IMain;
import com.crystalis.listeners.AsyntaskCallBackListeners;
import com.crystalis.view.R;

public class DownloadData extends AsyncTask<Void, Void, String>{
	
	private ProgressDialog pDialog;
	private String url;
	private Activity activity;
	private String usr;
	private String pwd;
	private String msg ="";
	
	private AsyntaskCallBackListeners asyntaskCallBack;
	
	    private DefaultHttpClient client = new DefaultHttpClient();
	    
	    final HttpParams params = client.getParams();
	    HttpResponse response;
	    private String content =  null;
	    private boolean error = false;
	    
	    private int NOTIFICATION_ID = 1;
	    private Notification mNotification;
	    private NotificationManager mNotificationManager;
	    

	
    public DownloadData(Activity act, String url, String usr, String pwd, final String msg, AsyntaskCallBackListeners asyntaskCallBack) {

	this.activity = act;
	this.usr = usr;
	this.pwd = pwd;
	this.url = url;
	this.msg = msg;
	this.asyntaskCallBack = asyntaskCallBack;

	mNotificationManager = (NotificationManager) act
		.getSystemService(Context.NOTIFICATION_SERVICE);
	pDialog = new ProgressDialog(act);
    }
	

    @Override
    protected void onPreExecute() {
	// pDialog.setMessage(msg);
	// pDialog.setCancelable(false);
	// pDialog.setIndeterminate(false);
	pDialog.setIcon(R.drawable.ic_home);

	pDialog.show();
    }
	
	@Override
    protected String doInBackground(Void... empty) {

	try {
	    
	    HttpConnectionParams.setConnectionTimeout(params, IMain.REGISTRATION_TIMEOUT);
	    HttpConnectionParams.setSoTimeout(params, IMain.WAIT_TIMEOUT);
	    ConnManagerParams.setTimeout(params, IMain.WAIT_TIMEOUT);
	    
	    publishProgress(null);
	    
	    client.getCredentialsProvider().setCredentials(AuthScope.ANY,
		    new UsernamePasswordCredentials(usr, pwd));

	    if (IMain.DEBUG)
		System.out.println(" getDataConnection: requestUrl: " + url);

	    HttpGet getRequest = new HttpGet();

	    // buffer reader to read the response
	    BufferedReader bufferedReader = null;

	    try {
		getRequest.setURI(new URI(url));
	    } catch (URISyntaxException e) {
		e.printStackTrace();
	    }
	    getRequest.setHeader("Content-Type", "text/xml");
	    getRequest.setHeader("Host", getRequest.getURI().getHost());

	    response = client.execute(getRequest);

	    bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
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

	    if (IMain.DEBUG)
		System.out.println("getDataConnection: stringBuffer:"
			+ stringBuffer.toString());

	    content = stringBuffer.toString();

	} catch (Exception e) {
	    Log.w("HTTP4:", e);
	    content = e.getMessage();
	    error = true;
	    cancel(true);
	    pDialog.dismiss();

	}
	return content;

    }
	
    protected void onProgressUpdate(Void... v) {
	pDialog.setMessage(msg);
    }

    protected void onCancelled() {
	// createNotification("Error occured during data download",content);
	Toast.makeText(activity, IMain.MSG_ERROR_CONEXION, Toast.LENGTH_SHORT).show();

	// System.out.println("Error occured during data download!");

    }
	    
    @Override
    protected void onPostExecute(String result) {

	pDialog.dismiss();

	if (error) {
	    // createNotification("Data download ended abnormally!",content);
	    System.out.println("Error: Data download ended abnormally!");
	} else {
	    // createNotification("Data download is complete!","");
	    // Toast.makeText(activity, IMain.MSG_CARGA_COMPLETA,
	    // Toast.LENGTH_SHORT).show();

	    asyntaskCallBack.onTaskDone(result);
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
