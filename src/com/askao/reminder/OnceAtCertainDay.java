package com.askao.reminder;

import com.askao.reminder.R;

import android.os.Bundle;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class OnceAtCertainDay extends Activity {

	private DatePicker datePicker_submitCertainDate;
	private TimePicker timePicker_submitCertainDate;
	private int hour;
	private int minute;
	SQLiteDatabase db;
	int day;
	int month;
	int year;
	private Button btn_submitCertainDate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.certain_date_once);
		
		btn_submitCertainDate = (Button) findViewById(R.id.btn_submitCertainDate);
		btn_submitCertainDate.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				insertHabbit();
				finish();
			}
		});
        
		
	}
	
	public void insertHabbit (){
		datePicker_submitCertainDate = (DatePicker) findViewById(R.id.datePicker_submitCertainDate);
		day = datePicker_submitCertainDate.getDayOfMonth();
		month = datePicker_submitCertainDate.getMonth();
		year = datePicker_submitCertainDate.getYear();
		timePicker_submitCertainDate = (TimePicker) findViewById(R.id.timePicker_submitCertainDate);
		hour = timePicker_submitCertainDate.getCurrentHour();
		minute = timePicker_submitCertainDate.getCurrentMinute();
		Bundle bundle = getIntent().getExtras();
		db = openOrCreateDatabase("Habbit", MODE_PRIVATE,null );
		db.execSQL("INSERT INTO habbits (name , not_type , not_times , times_counter " +
					", not_hour, not_min , not_day , not_month , not_year , notes ) VALUES('"+bundle.getString("habbit")+
					"' , '" +bundle.getString("noti_type")+ "' , 0 , 0 , " +hour+ " , " +minute+ " , "+day+ " , "+month+ " , "+year
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
