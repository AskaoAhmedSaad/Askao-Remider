package com.askao.reminder;


import java.util.Calendar;

import com.askao.reminder.R;

import android.os.Bundle;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;

public class EveryCertainNumberOfDays extends Activity {

	
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
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.certain_number_of_days);
		
		for(int i=0; i<100 ; i++){
			monthDays[i] = "" + (i+1); 
		}
		spinner_EveryCertainNumberOfDays = (Spinner) findViewById(R.id.spinner_EveryCertainNumberOfDays);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, monthDays);
		spinner_EveryCertainNumberOfDays.setAdapter(adapter);
		
		btn_submit = (Button) findViewById(R.id.btn_EveryCertainNumberOfDays);
		btn_submit.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				
				insertHabbit();
				finish();
			}
		});
		
	}
	
	public void insertHabbit (){
		int selected_NoDays = Integer.parseInt(spinner_EveryCertainNumberOfDays.getSelectedItem().toString());
		int notivalue = calendar.get(Calendar.DAY_OF_YEAR)+ selected_NoDays;
				timePicker_EveryCertainNumberOfDays = (TimePicker) findViewById(R.id.timePicker_EveryCertainNumberOfDays);
		hour = timePicker_EveryCertainNumberOfDays.getCurrentHour();
		minute = timePicker_EveryCertainNumberOfDays.getCurrentMinute();
		Bundle bundle = getIntent().getExtras();
		db = openOrCreateDatabase("Habbit", MODE_PRIVATE,null );
		db.execSQL("INSERT INTO habbits (name , not_type , not_times , times_counter " +
					", not_hour, not_min , not_day , not_month , not_year , notes ) VALUES('"+bundle.getString("habbit")+
					"' , '" +bundle.getString("noti_type")+ "' , "+selected_NoDays+" ,"+ notivalue + " , " +hour+ " , " +minute+ " , "+day+ " , "+month+ " , "+year
					+" , '"+bundle.getString("notes")+"' );");
		
		db.close();	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
