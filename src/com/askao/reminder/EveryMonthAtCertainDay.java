package com.askao.reminder;


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

public class EveryMonthAtCertainDay extends Activity {

	
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.certain_date_every_month);
		
		for(int i=0; i<31 ; i++){
			monthDays[i] = "" + (i+1); 
		}
		spinner_EveryMonthCertainDate = (Spinner) findViewById(R.id.spinner_EveryMonthCertainDate);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, monthDays);
		spinner_EveryMonthCertainDate.setAdapter(adapter);
		
		btn_submit = (Button) findViewById(R.id.btn_EveryMonthCertainDate);
		btn_submit.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				
				insertHabbit();
				finish();
			}
		});
		
	}
	
	public void insertHabbit (){
		String selected_day = spinner_EveryMonthCertainDate.getSelectedItem().toString();		
		timePicker_EveryMonthCertainDate = (TimePicker) findViewById(R.id.timePicker_EveryMonthCertainDate);
		hour = timePicker_EveryMonthCertainDate.getCurrentHour();
		minute = timePicker_EveryMonthCertainDate.getCurrentMinute();
		Bundle bundle = getIntent().getExtras();
		db = openOrCreateDatabase("Habbit", MODE_PRIVATE,null );
		db.execSQL("INSERT INTO habbits (name , not_type , not_times , times_counter " +
					", not_hour, not_min , not_day , not_month , not_year , notes ) VALUES('"+bundle.getString("habbit")+
					"' , '" +bundle.getString("noti_type")+ "' , 0 , 0 , " +hour+ " , " +minute+ " , "+selected_day+ " , 0 , 0"
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
