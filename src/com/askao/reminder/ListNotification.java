package com.askao.reminder;

import java.util.Calendar;
import java.util.Vector;

import com.askao.reminder.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListNotification extends Activity
	{
	    ListView gtumcaDynamicListView;
	    Vector<GtuMcaBean> gtuMcaBean= new Vector<GtuMcaBean>();
	    MyCustomAdaptor adaptor;
	   
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.list_notifications);
	       
	        gtumcaDynamicListView=(ListView) findViewById(R.id.gtumcaDynamicListView);
	        //gtumcaDynamicListView.setBackgroundColor(Color.rgb(231, 249, 255));
	        SQLiteDatabase db = openOrCreateDatabase("Habbit", MODE_PRIVATE,null );
			Cursor cursor = db.rawQuery("SELECT * FROM habbits", null);
			cursor.moveToFirst();
			
			String time = "";
			
			while (cursor.isAfterLast() == false) {
				if(cursor.getInt(5) <=11){
					time = "at " + cursor.getInt(5) +":" + cursor.getInt(6) + " AM";
				}else{
					time = "at " + (cursor.getInt(5)-12) +":" + cursor.getInt(6) + " PM";
				}
				
				if (cursor.getString(2).equals("daily")){
					gtuMcaBean.add(new GtuMcaBean(cursor.getInt(0), cursor.getString(1), time, "Daily", cursor.getString(10)));	
				}else if (cursor.getString(2).equals("onceAtCertainDay")){
					gtuMcaBean.add(new GtuMcaBean(cursor.getInt(0), cursor.getString(1), time, "Once At Certain Day", cursor.getString(10)));	
				}else if (cursor.getString(2).equals("everyMonthAtCertainDay")){
					gtuMcaBean.add(new GtuMcaBean(cursor.getInt(0), cursor.getString(1), time, "Every Month At Certain Day", cursor.getString(10)));	
				}else if (cursor.getString(2).equals("everyNoOfDays")){
					gtuMcaBean.add(new GtuMcaBean(cursor.getInt(0), cursor.getString(1), time, "Every Number Of Days", cursor.getString(10)));	
				}
				
				cursor.moveToNext();
			}
	        db.close();
	        adaptor = new MyCustomAdaptor(ListNotification.this, R.layout.data, gtuMcaBean);
	        gtumcaDynamicListView.setAdapter(adaptor);
	        
	        gtumcaDynamicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
	            @Override  
	            public void onItemClick( AdapterView<?> parent, View item,int position, long id) {
	            	
	            	GtuMcaBean mrb =gtuMcaBean.elementAt(position);
	            	//Toast.makeText(getApplicationContext(), "You have selected" + mrb.noti_name ,Toast.LENGTH_SHORT).show();
	            	Intent i = new Intent();
	            	if(mrb.noti_type.equals("Daily")){
	            		i = new Intent("com.askao.reminder.EditDaily");	    		    	
	            	}else if(mrb.noti_type.equals("Once At Certain Day")){
	            		i = new Intent("com.askao.reminder.EditOnceAtCertainDay");	    		    	    		    	
	            	}else if(mrb.noti_type.equals("Every Month At Certain Day")){
	            		i = new Intent("com.askao.reminder.EditEveryMonthAtCertainDay");	    		    	    		    	
	            	}else if(mrb.noti_type.equals("Every Number Of Days")){
	            		i = new Intent("com.askao.reminder.EditEveryCertainNumberOfDays");	    		    	    		    	
	            	}
	            	Bundle extras = new Bundle();
    		    	extras.putInt("selected_id", mrb.id);          
    		    	i.putExtras(extras);
    		    	startActivityForResult(i, 1);
	            	finish();
	            }  
	          });  
	        
	 }

   }