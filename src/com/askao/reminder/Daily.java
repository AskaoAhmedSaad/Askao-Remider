package com.askao.reminder;


import java.util.Calendar;


import com.askao.reminder.R;

import android.os.Bundle;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class Daily extends Activity {
	private TimePicker timePicker;
	private int hour;
	private int minute;
	SQLiteDatabase db;
	Calendar calendar = Calendar.getInstance();
	int day = calendar.get(Calendar.DAY_OF_MONTH);
	int month = calendar.get(Calendar.MONTH);
	int year = calendar.get(Calendar.YEAR);
	private Button btn_submitDialy;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daily);
		Bundle bundle = getIntent().getExtras();
        
        btn_submitDialy = (Button) findViewById(R.id.btn_submitDialy);
        btn_submitDialy.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				insertHabbit();
				finish();
			}
		});
        
	}
	
	public void insertHabbit (){
		timePicker = (TimePicker) findViewById(R.id.timePicker1);
		hour = timePicker.getCurrentHour();
		minute = timePicker.getCurrentMinute();
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
