package com.askao.reminder;



import com.askao.reminder.R;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {
	SQLiteDatabase db;
	final Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		db = openOrCreateDatabase("Habbit", MODE_PRIVATE,null );
		db.execSQL("CREATE TABLE IF NOT EXISTS habbits (_id INTEGER PRIMARY KEY, name varchar, not_type varchar, not_times int , times_counter int " +
				", not_hour int, not_min int , not_day int , not_month int , not_year int, notes varchar );");
		
		ImageButton btn_salah = (ImageButton) findViewById(R.id.btn_salah);
		btn_salah.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				addNoteAlert("salah");
			}
		});
		
		ImageButton btn_sleep = (ImageButton) findViewById(R.id.btn_sleep);
		btn_sleep.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				addNoteAlert("sleep");
			}
		});
		
		ImageButton btn_wakeup = (ImageButton) findViewById(R.id.btn_wakeup);
		btn_wakeup.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				addNoteAlert("wakeup");          
			}
		});
		
		ImageButton btn_food = (ImageButton) findViewById(R.id.btn_food);
		btn_food.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				addNoteAlert("food");          
			}
		});

		ImageButton btn_work = (ImageButton) findViewById(R.id.btn_work);
		btn_work.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				addNoteAlert("work");          
			}
		});
		
		ImageButton btn_meeting = (ImageButton) findViewById(R.id.btn_meeting);
		btn_meeting.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				addNoteAlert("meeting");          
			}
		});
		
		ImageButton btn_study = (ImageButton) findViewById(R.id.btn_study);
		btn_study.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				addNoteAlert("study");          
			}
		});
		
		ImageButton btn_sport = (ImageButton) findViewById(R.id.btn_sport);
		btn_sport.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {				
				addNoteAlert("sport");  
			}
		});
		
		
		ImageButton btn_custom = (ImageButton) findViewById(R.id.btn_custom);
		btn_custom.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// get prompts.xml view				
				LayoutInflater layoutInflater = LayoutInflater.from(context);
				View promptView = layoutInflater.inflate(R.layout.prompt_input, null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				// set prompts.xml to be the layout file of the alertdialog builder
				alertDialogBuilder.setView(promptView);
				final EditText input = (EditText) promptView.findViewById(R.id.userInput);

				// setup a dialog window
				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										addNoteAlert(filterInput("" + input.getText()));          
									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,	int id) {
										dialog.cancel();
									}
								});

				// create an alert dialog
				AlertDialog alertD = alertDialogBuilder.create();
				alertD.show();

			}
		});
		
		Button btn_listNoti = (Button) findViewById(R.id.btn_listNoti);
		btn_listNoti.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				startActivity(new Intent("com.askao.reminder.ListNotification"));
			}
		});
		
		
	}


	
	//************************** addNoteAlert ******************************
	private void addNoteAlert(final String habbit){
		Toast.makeText(this, habbit, Toast.LENGTH_SHORT).show();

		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View promptView = layoutInflater.inflate(R.layout.prompt_addnotes, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		// set prompts.xml to be the layout file of the alertdialog builder
		alertDialogBuilder.setView(promptView);
		final EditText input = (EditText) promptView.findViewById(R.id.addNotesInput);		
		// setup a dialog window
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent i = new Intent("com.askao.reminder.SelectType");
						    	Bundle extras = new Bundle();
						    	extras.putString("habbit", habbit);
								extras.putString("notes", filterInput("" + input.getText()));
								//---attach the Bundle object to the Intent object---
						    	i.putExtras(extras);                
						    	//---start the activity to get a result back---
						    	startActivityForResult(i, 1);
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int id) {
								dialog.cancel();
							}
						});

		// create an alert dialog
		AlertDialog alertD = alertDialogBuilder.create();
		alertD.show(); 	
	}
//******************************************************************************

//******************* Filter Input ***************************************	
	public String filterInput(String input){
		String filterInput = "";
		String regx = "\\\"\'<>:;=";
	    char[] ca = regx.toCharArray();
	    for (char c : ca) {
	        input = input.replace(""+c, "");
	    }
		filterInput = input;
		return filterInput;
	}
// ***********************************************************************
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
