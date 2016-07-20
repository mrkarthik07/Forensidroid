package com.death.artifacts;

import java.util.ArrayList;
import java.util.List;

import com.death.androidconnection.MainActivityConnection;
import com.death.callrecording.MainActivityCallRecorder;
import com.death.sensors.GPSLocationSensor;
import com.death.sensors.SensorActivity;
import com.example.badone.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import monitor.code.reechee88.filemonitorapp.MainActivityMonitor;

public class MyAndroidAppActivity extends Activity{
	private Spinner spinner1;
	private Button btnSubmit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addItemsOnSpinner2();
		addListenerOnButton();
		addListenerOnSpinnerItemSelection();

	}

	//add items into spinner dynamically
	public void addItemsOnSpinner2() {

		spinner1 = (Spinner) findViewById(R.id.spinner1);
		List<String> list = new ArrayList<String>();
		list.add("SMS");
		list.add("CallLog");
		list.add("AppInfo");
		list.add("WiFi");
		list.add("SIM");
		list.add("Browser Artefacts");
		list.add("Calendar Events");
		list.add("Contacts");
		list.add("Dictionary");
		list.add("CallRecorder");
		list.add("Sensor");
		list.add("GPSLocation");
		list.add("Connections");
		list.add("File Monitor");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(dataAdapter);
	}

	public void addListenerOnSpinnerItemSelection(){
		
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}
	
	//get the selected dropdown list value
	public void addListenerOnButton() {

		spinner1 = (Spinner) findViewById(R.id.spinner1);
		//spinner2 = (Spinner) findViewById(R.id.spinner2);
		
		btnSubmit = (Button) findViewById(R.id.go);

		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Intent intent ;
				switch(String.valueOf(spinner1.getSelectedItem()))
				{
				case "SMS":
					intent = new Intent(MyAndroidAppActivity.this,SmsActivity.class);
					startActivity(intent);
					break;
				case "CallLog":
					intent = new Intent(MyAndroidAppActivity.this,CallLogActivity.class);
					startActivity(intent);
					break;
				case "AppInfo":
					intent = new Intent(MyAndroidAppActivity.this,AppInfoActivity.class);
					startActivity(intent);
					break;
				case "WiFi":
					intent = new Intent(MyAndroidAppActivity.this,WiFiDemo.class);
					startActivity(intent);
					break;
				case "SIM":
					intent = new Intent(MyAndroidAppActivity.this,SIMActivity.class);
					startActivity(intent);
					break;
				case "Browser Artefacts":
					intent = new Intent(MyAndroidAppActivity.this,BrowserActivity.class);
					startActivity(intent);
					break;
				case "Calendar Events":
					intent = new Intent(MyAndroidAppActivity.this,CalendarActivity.class);
					startActivity(intent);
					break;
				case "Contacts":
					intent = new Intent(MyAndroidAppActivity.this,ContactsActivity.class);
					startActivity(intent);
					break;
				case "Dictionary":
					intent = new Intent(MyAndroidAppActivity.this,DictionaryActivity.class);
					startActivity(intent);
					break;
				case "CallRecorder":
					intent = new Intent(MyAndroidAppActivity.this,MainActivityCallRecorder.class);
					startActivity(intent);
					break;
				case "Sensor":
					intent = new Intent(MyAndroidAppActivity.this,SensorActivity.class);
					startActivity(intent);
					break;
				case "GPSLocation":
					intent = new Intent(MyAndroidAppActivity.this,GPSLocationSensor.class);
					startActivity(intent);
					break;
				case "Connections":
					intent = new Intent(MyAndroidAppActivity.this,MainActivityConnection.class);
					startActivity(intent);
					break;	
				case "File Monitor":
					intent = new Intent(MyAndroidAppActivity.this,MainActivityMonitor.class);
					startActivity(intent);
					break;
				default:
					Toast.makeText(MyAndroidAppActivity.this, "Try again mate ", Toast.LENGTH_LONG).show();
					break;
				}

				Toast.makeText(MyAndroidAppActivity.this,
						"OnClickListener : " + 
						"\nSpinner 1 : " + String.valueOf(spinner1.getSelectedItem()),
						Toast.LENGTH_SHORT).show();
			}

		});

	}
}
