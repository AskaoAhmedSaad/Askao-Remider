package com.askao.reminder;

import com.askao.reminder.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

	public class SelectType extends Activity  {
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			//---get the Bundle object passed in---
			setContentView(R.layout.select_type);
			Bundle bundle = getIntent().getExtras();
			Toast.makeText(this, bundle.getString("notes"), Toast.LENGTH_SHORT).show();
			
			Button btn_daily = (Button) findViewById(R.id.btn_daily);
			btn_daily.setOnClickListener(new View.OnClickListener() {			
				@Override
				public void onClick(View v) {
					Intent i = new 
			    			Intent("com.askao.reminder.Daily");
			    	
			    	Bundle extras = new Bundle();
			    	extras.putString("noti_type", "daily");
			    	Bundle bundle = getIntent().getExtras();
			    	extras.putString("habbit", bundle.getString("habbit"));
			    	extras.putString("notes", bundle.getString("notes"));
			    	//---attach the Bundle object to the Intent object---
			    	i.putExtras(extras);                
			    	//---start the activity to get a result back---
			    	startActivityForResult(i, 1);
			    	finish();
				}
			});
			
		  Button btn_onceAtCertainDay = (Button) findViewById(R.id.btn_onceAtCertainDay);
		  btn_onceAtCertainDay.setOnClickListener(new View.OnClickListener() {			
				@Override
				public void onClick(View v) {
					Intent i = new 
			    			Intent("com.askao.reminder.OnceAtCertainDay");
			    	
			    	Bundle extras = new Bundle();
			    	extras.putString("noti_type", "onceAtCertainDay");
			    	Bundle bundle = getIntent().getExtras();
			    	extras.putString("habbit", bundle.getString("habbit")); 
			    	extras.putString("notes", bundle.getString("notes"));
			    	//---attach the Bundle object to the Intent object---
			    	i.putExtras(extras);                

			    	//---start the activity to get a result back---
			    	startActivityForResult(i, 1);
			    	finish();
				}
			});
		  
		  Button btn_everyMonthAtCertainDay = (Button) findViewById(R.id.btn_everyMonthAtCertainDay);
		  btn_everyMonthAtCertainDay.setOnClickListener(new View.OnClickListener() {			
				@Override
				public void onClick(View v) {
					Intent i = new 
			    			Intent("com.askao.reminder.EveryMonthAtCertainDay");
			    	
			    	Bundle extras = new Bundle();
			    	extras.putString("noti_type", "everyMonthAtCertainDay");
			    	Bundle bundle = getIntent().getExtras();
			    	extras.putString("habbit", bundle.getString("habbit")); 
			    	extras.putString("notes", bundle.getString("notes"));
			    	//---attach the Bundle object to the Intent object---
			    	i.putExtras(extras);                

			    	//---start the activity to get a result back---
			    	startActivityForResult(i, 1);
			    	finish();
				}
			});
		  
		  Button btn_everyNoOfDays = (Button) findViewById(R.id.btn_everyNoOfDays);
		  btn_everyNoOfDays.setOnClickListener(new View.OnClickListener() {			
				@Override
				public void onClick(View v) {
					Intent i = new 
			    			Intent("com.askao.reminder.EveryCertainNumberOfDays");
			    	
			    	Bundle extras = new Bundle();
			    	extras.putString("noti_type", "everyNoOfDays");
			    	Bundle bundle = getIntent().getExtras();
			    	extras.putString("habbit", bundle.getString("habbit")); 
			    	extras.putString("notes", bundle.getString("notes"));
			    	//---attach the Bundle object to the Intent object---
			    	i.putExtras(extras);                

			    	//---start the activity to get a result back---
			    	startActivityForResult(i, 1);
			    	finish();
				}
			});
	}

	
	
}
