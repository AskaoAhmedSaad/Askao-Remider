package com.askao.reminder;


import com.askao.reminder.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditOnceAtCertainDay extends Activity {

	private DatePicker datePicker_submitCertainDate;
	private TimePicker timePicker_submitCertainDate;
	private int hour;
	private int minute;
	SQLiteDatabase db;
	int day;
	int month;
	int year;
	private Button btn_submitCertainDate;
	EditText edt_note;	
	Bundle bundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_certain_date_once);
		
		timePicker_submitCertainDate = (TimePicker) findViewById(R.id.timePicker_submitCertainDate);
		datePicker_submitCertainDate = (DatePicker) findViewById(R.id.datePicker_submitCertainDate);
		edt_note = (EditText) findViewById(R.id.editNotesInput);
		bundle = getIntent().getExtras();
		
		db = openOrCreateDatabase("Habbit", MODE_PRIVATE,null );
		Cursor cursor = db.rawQuery("SELECT * FROM habbits WHERE _id =" + bundle.getInt("selected_id"), null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {

			timePicker_submitCertainDate.setCurrentHour(cursor.getInt(5));
			timePicker_submitCertainDate.setCurrentMinute(cursor.getInt(6));
			datePicker_submitCertainDate.init(cursor.getInt(9), cursor.getInt(8), cursor.getInt(7), null);
			edt_note.setText(cursor.getString(10));
			cursor.moveToNext();
		}
        db.close();
		
		btn_submitCertainDate = (Button) findViewById(R.id.btn_submitCertainDate);
		btn_submitCertainDate.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Bundle bundle = getIntent().getExtras();
				updateNotification(bundle.getInt("selected_id"));
				// ************* Test ********************
				db = openOrCreateDatabase("Habbit", MODE_PRIVATE,null );
				Cursor cursor = db.rawQuery("SELECT * FROM habbits WHERE _id =" + bundle.getInt("selected_id"), null);
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
		
					Toast.makeText(getApplicationContext(), cursor.getString(0) + "/" +cursor.getString(1) + "/" +
						cursor.getString(2) + "/"  +cursor.getString(3) + "/"  +
									cursor.getString(4) + "/"  + cursor.getString(5) + "/"  +cursor.getString(6) + "/"  +
									cursor.getString(7) + "/" + cursor.getString(8) + "/"  + cursor.getString(9), Toast.LENGTH_LONG).show();
		
				
					cursor.moveToNext();
				}
		        db.close();
		        // ********************************************************
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
		
		datePicker_submitCertainDate = (DatePicker) findViewById(R.id.datePicker_submitCertainDate);
		day = datePicker_submitCertainDate.getDayOfMonth();
		month = datePicker_submitCertainDate.getMonth();
		year = datePicker_submitCertainDate.getYear();
		timePicker_submitCertainDate = (TimePicker) findViewById(R.id.timePicker_submitCertainDate);
		hour = timePicker_submitCertainDate.getCurrentHour();
		minute = timePicker_submitCertainDate.getCurrentMinute();
		db = openOrCreateDatabase("Habbit", MODE_PRIVATE,null );
		db.execSQL("UPDATE habbits SET not_hour = " + hour + " , not_min = " + minute +
				", not_day = " +day+ ", not_month = "+month+" , not_year = "+year
				+" , notes = '" + edt_note.getText() + "'  WHERE _id = " +id+ " ;");			
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
