package com.askao.reminder;

import java.sql.Time;
import java.util.Calendar;

import com.askao.reminder.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;

public class NotiService extends Service implements Runnable {
	
	private Handler customHandler = new Handler();
	SQLiteDatabase db;
	private NotificationManager mNManager; 
	private int NOTIFY_ID=0;
	private int currentTime = 0;
	
	  @Override
	  public int onStartCommand(Intent intent, int flags, int startId) {
	    //TODO do something useful
		  run();
		  return Service.START_STICKY;
	  }

	  @Override
	  public IBinder onBind(Intent intent) {
	  //TODO for communication return IBinder implementation
	    return null;
	  }

		public void run() {
			
			int current_hour = new Time(System.currentTimeMillis()).getHours();
			int current_minute = new Time(System.currentTimeMillis()).getMinutes();
			Calendar calendar = Calendar.getInstance();
			int current_day = calendar.get(Calendar.DAY_OF_MONTH);
			int current_month = calendar.get(Calendar.MONTH);
			int current_year = calendar.get(Calendar.YEAR);
			// Toast.makeText(getApplicationContext(), "" + current_hour + ":" + current_minute, Toast.LENGTH_SHORT).show();
			
			if(currentTime != current_minute){
				db = openOrCreateDatabase("Habbit", MODE_PRIVATE,null );
	
				Cursor cursor = db.rawQuery("SELECT * FROM habbits", null);
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					if (cursor.getString(2).equals("daily")){
						if (current_hour == cursor.getInt(5) && current_minute == cursor.getInt(6)){
							sendNotification(cursor.getString(1),cursor.getString(10));
						}
					}else if (cursor.getString(2).equals("onceAtCertainDay")){
							if (current_year == cursor.getInt(9) && current_month == cursor.getInt(8) && current_day == cursor.getInt(7) &&
								current_hour == cursor.getInt(5) && current_minute == cursor.getInt(6)){
							sendNotification(cursor.getString(1),cursor.getString(10));
													}
					}else if (cursor.getString(2).equals("everyMonthAtCertainDay")){
												if (current_day == cursor.getInt(7) && current_hour == cursor.getInt(5) && current_minute == cursor.getInt(6)){
							sendNotification(cursor.getString(1),cursor.getString(10));
							
						}
					}else if (cursor.getString(2).equals("everyNoOfDays")){
												if (current_hour == cursor.getInt(5) && current_minute == cursor.getInt(6)){						
							if(cursor.getInt(4) == calendar.get(Calendar.DAY_OF_YEAR)){
								sendNotification(cursor.getString(1),cursor.getString(10));
								int notivalue = calendar.get(Calendar.DAY_OF_YEAR)+cursor.getInt(3);
								if(calendar.get(Calendar.YEAR) % 4 == 0){
									if (notivalue <= 366){
										db.execSQL("UPDATE habbits SET times_counter = " + notivalue + "  WHERE _id = " +cursor.getInt(0)+ " ;");
									}else{
										int remindervalue = calendar.get(Calendar.DAY_OF_YEAR)+cursor.getInt(3) - 366;
										db.execSQL("UPDATE habbits SET times_counter = " + remindervalue + " WHERE _id = " +cursor.getInt(0)+ " ;");
									}
								}else{
									if (notivalue <= 365){
										db.execSQL("UPDATE habbits SET times_counter = " + notivalue + "  WHERE _id = " +cursor.getInt(0)+ " ;");								
									}else{
										int remindervalue = calendar.get(Calendar.DAY_OF_YEAR)+cursor.getInt(3) - 365;
										db.execSQL("UPDATE habbits SET times_counter = " + remindervalue + "  WHERE _id = " +cursor.getInt(0)+ " ;");								
									}
								}
								Cursor cursor1 = db.rawQuery("SELECT * FROM habbits where _id = " +cursor.getInt(0)+ ";", null);
								cursor1.moveToFirst();
							}
						}
					}
					
					cursor.moveToNext();
				}
		        db.close();
			}    
			customHandler.postDelayed(this, 10000);
			
			currentTime = new Time(System.currentTimeMillis()).getMinutes();
		}
	
	private void sendNotification (String noti_type,String message){
		 // show notification ***********************************
 		String ns = Context.NOTIFICATION_SERVICE;
        mNManager = (NotificationManager) getSystemService(ns); 
        final Notification msg = new Notification(R.drawable.ic_launcher,
                "It's time for " + noti_type,
                System.currentTimeMillis());
        
        Context context = getApplicationContext(); 
        CharSequence contentTitle = "Askao Reminder - it's time for " + noti_type; 
        CharSequence contentText = message; 
        Intent msgIntent = new Intent("com.askao.reminder.SelectTime"); 
        PendingIntent intent = 
            PendingIntent.getActivity(this, 
                    0, msgIntent, 
                    Intent.FLAG_ACTIVITY_NEW_TASK); 
        
       msg.sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.korean_notification);
       msg.flags |= Notification.FLAG_AUTO_CANCEL;
        msg.setLatestEventInfo(context, 
                contentTitle, contentText, intent); 
        mNManager.notify(NOTIFY_ID, msg);
        NOTIFY_ID++;
    // ****************************************************
	}
} 

