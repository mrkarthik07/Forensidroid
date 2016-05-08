package com.death.artifacts;

import com.death.callrecording.MainActivityCallRecorder;
import com.death.sensors.SensorActivity;
import com.example.badone.R;
import com.death.sensors.GPSLocationSensor;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_mainnew, menu);
		return true;
	}

	
	public void goTo(View view)
	{
		EditText et = (EditText)findViewById(R.id.value);

		int choice = Integer.parseInt(et.getText().toString());
		Toast.makeText(MainActivity.this, choice + " ", Toast.LENGTH_LONG).show();
		Intent intent ;
		switch(choice)
		{
		case 1:
			intent = new Intent(MainActivity.this,SmsActivity.class);
			startActivity(intent);
			break;
		case 2:
			intent = new Intent(MainActivity.this,CallLogActivity.class);
			startActivity(intent);
			break;
		case 3:
			intent = new Intent(MainActivity.this,AppInfoActivity.class);
			startActivity(intent);
			break;
		case 4:
			intent = new Intent(MainActivity.this,WiFiActivity.class);
			startActivity(intent);
			break;
		case 5:
			intent = new Intent(MainActivity.this,SIMActivity.class);
			startActivity(intent);
			break;
		case 6:
			intent = new Intent(MainActivity.this,BrowserActivity.class);
			startActivity(intent);
			break;
		case 7:
			intent = new Intent(MainActivity.this,CalendarActivity.class);
			startActivity(intent);
			break;
		case 8:
			intent = new Intent(MainActivity.this,ContactsActivity.class);
			startActivity(intent);
			break;
		case 9:
			intent = new Intent(MainActivity.this,DictionaryActivity.class);
			startActivity(intent);
			break;
		case 10:
			intent = new Intent(MainActivity.this,MainActivityCallRecorder.class);
			startActivity(intent);
			break;
		case 11:
			intent = new Intent(MainActivity.this,SensorActivity.class);
			startActivity(intent);
			break;
		case 12:
			intent = new Intent(MainActivity.this,GPSLocationSensor.class);
			startActivity(intent);
			break;
		default:
			Toast.makeText(MainActivity.this, "Try again mate ", Toast.LENGTH_LONG).show();
			break;
		}
		
	}
}
