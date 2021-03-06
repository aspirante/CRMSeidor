package com.crystalis.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity{
	private static final int SPLASH_TIME = 2 * 1000;// 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
      
        try {
        new Handler().postDelayed(new Runnable() {
 
            public void run() {
                 
                Intent intent = new Intent(SplashActivity.this, /*QRCodeActivity.class*/LoginActivity.class);
                startActivity(intent);
 
                SplashActivity.this.finish();
 
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
 
            }
             
             
        }, SPLASH_TIME);
         
        new Handler().postDelayed(new Runnable() {
              public void run() {
        	  
                     } 
                }, SPLASH_TIME);
        } catch(Exception e){}
    }
 
     
    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }		
}
