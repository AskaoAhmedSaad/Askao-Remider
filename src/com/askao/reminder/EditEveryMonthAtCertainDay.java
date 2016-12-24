package com.askao.reminder;

import com.askao.reminder.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditEveryMonthAtCertainDay extends Activity {

	
	private TimePicker timePicker_EveryMonthCertainDate;
	private int hour;
	private int minute;
	SQLiteDatabase db;
	int day;
	int month;
	int year;
	private Button btn_submit;
	String[] monthDays = new String[31];
	Spinner spinner_EveryMonthCertainDate;
	Bundle bundle;
	EditText edt_note;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_certain_date_every_month);
		
		bundle = getIntent().getExtras();
		timePicker_EveryMonthCertainDate = (TimePicker) findViewById(R.id.timePicker_EveryMonthCertainDate);
		edt_note = (EditText) findViewById(R.id.editNotesInput);
		for(int i=0; i<31 ; i++){
			monthDays[i] = "" + (i+1); 
		}
		spinner_EveryMonthCertainDate = (Spinner) findViewById(R.id.spinner_EveryMonthCertainDate);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, monthDays);
		spinner_EveryMonthCertainDate.setAdapter(adapter);

		db = openOrCreateDatabase("Habbit", MODE_PRIVATE,null );
		Cursor cursor = db.rawQuery("SELECT * FROM habbits WHERE _id =" + bundle.getInt("selected_id"), null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			Toast.makeText(getApplicationContext(), ""+cursor.getInt(7) , Toast.LENGTH_SHORT).show();
			timePicker_EveryMonthCertainDate.setCurrentHour(cursor.getInt(5));
			timePicker_EveryMonthCertainDate.setCurrentMinute(cursor.getInt(6));		
			spinner_EveryMonthCertainDate.setSelection((cursor.getInt(7)-1));
			edt_note.setText(cursor.getString(10));
			cursor.moveToNext();
		}
        db.close();
	
	    btn_submit = (Button) findViewById(R.id.btn_EveryMonthCertainDate);
		btn_submit.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Bundle bundle = getIntent().getExtras();
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
		timePicker_EveryMonthCertainDate = (TimePicker) findViewById(R.id.timePicker_EveryMonthCertainDate);
		hour = timePicker_EveryMonthCertainDate.getCurrentHour();
		minute = timePicker_EveryMonthCertainDate.getCurrentMinute();
		int selected_Day = Integer.parseInt(spinner_EveryMonthCertainDate.getSelectedItem().toString());
		Bundle bundle = getIntent().getExtras();
		db = openOrCreateDatabase("Habbit", MODE_PRIVATE,null );
		db.execSQL("UPDATE habbits SET not_hour = " + hour + " , not_min = " + minute +
				" , not_day = "+selected_Day
				+ " , notes = '" + edt_note.getText() + "'  WHERE _id = " +id+ " ;");
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
