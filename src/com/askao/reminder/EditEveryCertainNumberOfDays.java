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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditEveryCertainNumberOfDays extends Activity {

	
	private TimePicker timePicker_EveryCertainNumberOfDays;
	private int hour;
	private int minute;
	SQLiteDatabase db;
	Calendar calendar = Calendar.getInstance();
	int day = calendar.get(Calendar.DAY_OF_MONTH);
	int month = calendar.get(Calendar.MONTH);
	int year = calendar.get(Calendar.YEAR);
	private Button btn_submit;
	String[] monthDays = new String[100];
	Spinner spinner_EveryCertainNumberOfDays;
	Bundle bundle;
	EditText edt_note;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_certain_number_of_days);
		
		edt_note = (EditText) findViewById(R.id.editNotesInput);
		bundle = getIntent().getExtras();
		timePicker_EveryCertainNumberOfDays = (TimePicker) findViewById(R.id.timePicker_EveryCertainNumberOfDays);
		for(int i=0; i<100 ; i++){
			monthDays[i] = "" + (i+1); 
		}
		spinner_EveryCertainNumberOfDays = (Spinner) findViewById(R.id.spinner_EveryCertainNumberOfDays);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, monthDays);
		spinner_EveryCertainNumberOfDays.setAdapter(adapter);

		db = openOrCreateDatabase("Habbit", MODE_PRIVATE,null );
		Cursor cursor = db.rawQuery("SELECT * FROM habbits WHERE _id =" + bundle.getInt("selected_id"), null);
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			Toast.makeText(getApplicationContext(), ""+cursor.getInt(3) , Toast.LENGTH_SHORT).show();
			timePicker_EveryCertainNumberOfDays.setCurrentHour(cursor.getInt(5));
			timePicker_EveryCertainNumberOfDays.setCurrentMinute(cursor.getInt(6));		
			spinner_EveryCertainNumberOfDays.setSelection((cursor.getInt(3)-1));
			edt_note.setText(cursor.getString(10));
			cursor.moveToNext();
		}
        db.close();
		
		
        Button btn_submit = (Button) findViewById(R.id.btn_EveryCertainNumberOfDays);
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
		
		timePicker_EveryCertainNumberOfDays = (TimePicker) findViewById(R.id.timePicker_EveryCertainNumberOfDays);
		hour = timePicker_EveryCertainNumberOfDays.getCurrentHour();
		minute = timePicker_EveryCertainNumberOfDays.getCurrentMinute();
		int selected_NoDays = Integer.parseInt(spinner_EveryCertainNumberOfDays.getSelectedItem().toString());
		Bundle bundle = getIntent().getExtras();
		db = openOrCreateDatabase("Habbit", MODE_PRIVATE,null );
		db.execSQL("UPDATE habbits SET not_hour = " + hour + " , not_min = " + minute +
				", not_times = "+selected_NoDays
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
