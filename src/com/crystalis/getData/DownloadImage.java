package com.crystalis.getData;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.crystalis.listeners.TaskImage;

public class DownloadImage extends AsyncTask<Void, Void, Bitmap>{
	
	private ProgressDialog pDialog;	    	
    
    private Activity act;
    private TaskImage task;
    private String url;

    private String msg;
    
    public DownloadImage(Activity act,String url,String msg, TaskImage task){
	this.act = act;
	this.task = task;
	this.url = url;
	this.msg = msg;
}
    
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        pDialog = new ProgressDialog(act);
        pDialog.setMessage(msg);
        pDialog.setCancelable(true);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.show();
         
    }
    
    @Override
    protected Bitmap doInBackground(Void... empty) {
        // TODO Auto-generated method stub
        Log.i("doInBackground" , "Entra en doInBackground");
        publishProgress(null);
        Bitmap imagen = descargarImagen(url);
        return imagen;
    }
     
    @Override
    protected void onPostExecute(Bitmap result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        pDialog.dismiss();
        task.onImageTaskDone(result);
    }
    
    private Bitmap descargarImagen(String imageHttpAddress){
        URL imageUrl = null;
        Bitmap imagen = null;
        try{
            imageUrl = new URL(imageHttpAddress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imagen = BitmapFactory.decodeStream(conn.getInputStream());
        }catch(IOException ex){
            ex.printStackTrace();
        }
         
        return imagen;
    }

}
