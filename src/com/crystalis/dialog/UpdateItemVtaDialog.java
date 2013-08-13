package com.crystalis.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.crystalis.view.R;

public class UpdateItemVtaDialog extends Dialog implements
android.view.View.OnClickListener{
    
    public Activity c;
    public Dialog d;
  

    private ImageButton accept;
    private ImageButton cancel;

    public UpdateItemVtaDialog(Activity a) {
	super(a);
	c = a;
    }

    @Override
    public void onClick(View v) {
	switch (v.getId()) {
	    case R.id.update_sales_accept:
	      c.finish();
	      break;
	    case R.id.update_sales_cancel:
	      dismiss();
	      break;
	    default:
	      break;
	    }
	    dismiss();	
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      setContentView(R.layout.update_sales);
      accept = (ImageButton) findViewById(R.id.update_sales_accept);
      cancel = (ImageButton) findViewById(R.id.update_sales_cancel);
      accept.setOnClickListener(this);
      cancel.setOnClickListener(this);

    }
    

}
