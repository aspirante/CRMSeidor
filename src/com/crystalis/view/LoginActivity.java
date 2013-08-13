package com.crystalis.view;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.crystalis.dataconnection.DataConnection;
import com.crystalis.getData.DownloadData;
import com.crystalis.interfaces.IMain;
import com.crystalis.listeners.AsyntaskCallBackListeners;
import com.crystalis.listeners.Response;
import com.crystalis.model.DataManager;
import com.crystalis.model.Login;
import com.crystalis.settings.SettingsPreference;
import com.crystalis.tools.Parser;

public class LoginActivity extends Activity {

    private ImageButton btn_settings;
    private ImageButton btn_accept;
    private ImageButton btn_cancel;
    private EditText usr;
    private EditText pwd;
    private SharedPreferences SP;
    private String url;
    private Response response;
    private DataManager datamanager;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        datamanager = DataManager.getInstance();
        
	SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	url = SP.getString("url", "NA");
	
        
        usr = (EditText) findViewById(R.id.login_edit_usr);

        pwd = (EditText) findViewById(R.id.login_edit_pwd);

        
        btn_settings = (ImageButton) findViewById(R.id.login_ic_settings_btn);
        btn_settings.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(LoginActivity.this,SettingsPreference.class); 
				startActivity(i);			
			}
		});
        
        btn_accept = (ImageButton) findViewById(R.id.login_btn_accept);
        btn_accept.setOnClickListener(new View.OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {		
		verifylogin();
	    }
	});
        
        btn_cancel = (ImageButton) findViewById(R.id.login_btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		usr.setText("");
		pwd.setText("");
	    }
	});
        
    }
      
    public void verifylogin() {

	if(!url.equals("NA")){
	    
	String updateurl = new Parser().updateUrl(DataConnection.LOGIN_GATEWAY_JSON, url);
	updateurl = updateurl.replace("usr",usr.getText().toString().trim());
	updateurl = updateurl.replace("pwd",pwd.getText().toString().trim());

	
	DownloadData download = new DownloadData(this, updateurl, DataConnection.user, DataConnection.pwd, "Validando datos... ", new AsyntaskCallBackListeners() {
	    
		    @Override
		    public void onTaskDone(String data) {
			
			if(IMain.DEBUG)
			    System.out.println(data);
			
			JSONObject JSONData = null;
			try {
			    JSONData = new JSONObject (data);
			} catch (JSONException e) {
			    e.printStackTrace();
			}
			String loginData = null;
			try {
			    loginData = JSONData.getJSONObject(IMain.ROOT_JSON).toString();
			} catch (JSONException e) {
			    e.printStackTrace();
			}

			response = new Login().jsonToObject(loginData);
			Login infoServer = (Login) response;

			if (infoServer.getMessage().equals("OK")) {

			    
			    datamanager.setUseralias(infoServer.getUseralias().toString().trim());

			    Intent i = new Intent(LoginActivity.this, DemoActivity.class);
			    startActivity(i);
			    LoginActivity.this.finish();

			} else {
			    Toast.makeText(getApplicationContext(),infoServer.getMessage(), Toast.LENGTH_SHORT).show();
			}
		    }
		});
	download.execute();
	
	} else
	    Toast.makeText(getApplicationContext(), "Asignar URL valida.", Toast.LENGTH_SHORT).show();
    }
	
    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }
}
