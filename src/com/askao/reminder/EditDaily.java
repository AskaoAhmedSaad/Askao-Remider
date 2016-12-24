package com.askao.reminder;


import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.askao.reminder.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

public class EditDaily extends Activity {
	private TimePicker timePicker;
	private int hour;
	private int minute;
	SQLiteDatabase db;
	private Button btn_submitDialy;
	Bundle bundle;
	EditText edt_note;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_daily);
		bundle = getIntent().getExtras();
		timePicker = (TimePicker) findViewById(R.id.timePickerDaily);
		edt_note = (EditText) findViewById(R.id.editNotesInput);
		
		db = openOrCreateDatabase("Habbit", MODE_PRIVATE,null );
        
		Cursor cursor = db.rawQuery("SELECT * FROM habbits WHERE _id =" + bundle.getInt("selected_id"), null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			/*Toast.makeText(this, cursor.getString(1) +" , "+ cursor.getString(2) + " ,"+ cursor.getString(3)+
		 " , "+cursor.getString(4)+ " , " +cursor.getString(5)+ " , "+cursor.getString(6)+ " , "+cursor.getString(7)
		 + " , "+cursor.getString(8) , 
					Toast.LENGTH_SHORT).show();*/
			timePicker.setCurrentHour(cursor.getInt(5));
			timePicker.setCurrentMinute(cursor.getInt(6));
			edt_note.setText(cursor.getString(10));
			
			cursor.moveToNext();
		}
        db.close();
        
        btn_submitDialy = (Button) findViewById(R.id.btn_submitDialy);
        btn_submitDialy.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				
				updateNotification(bundle.getInt("selected_id"));
				startActivity(new Intent("com.askao.reminder.ListNotification"));
				finish();
				
			}
		});
        
        Button btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Bundle bundle = getIntent().getExtras();
				db = openOrCreateDatabase("Habbit", MODE_PRIVATE,null );
				db.execSQL("DELETE FROM habbits WHERE _id = " +bundle.getInt("selected_id")+ " ;");								
				db.close(); 
				startActivity(new Intent("com.askao.reminder.ListNotification"));
				finish();
			}
		});
		
	}
	
	public void updateNotification (int id){
		
		hour = timePicker.getCurrentHour();
		minute = timePicker.getCurrentMinute();
		Bundle bundle = getIntent().getExtras();
		db = openOrCreateDatabase("Habbit", MODE_PRIVATE,null );
		db.execSQL("UPDATE habbits SET not_hour = " + hour + " , not_min = " + minute +
				" , notes = '" + edt_note.getText() + "'  WHERE _id = " +id+ " ;");								
		db.close(); 
	}
	
    @Override
    public void onBackPressed() {
    	finish();
    	startActivity(new Intent("com.askao.reminder.ListNotification"));
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
}
