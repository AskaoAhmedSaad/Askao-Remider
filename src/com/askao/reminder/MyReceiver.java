package com.askao.reminder;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

public class MyReceiver extends BroadcastReceiver {

	SQLiteDatabase db;
	
	  @Override
	  public void onReceive(Context context, Intent intent) {
	    // assumes WordService is a registered service
	 
			Intent i= new Intent(context, NotiService.class);
			// potentially add data to the intent
			i.putExtra("KEY1", "Value to be used by the service");
			context.startService(i);

	  }	 
  
} 