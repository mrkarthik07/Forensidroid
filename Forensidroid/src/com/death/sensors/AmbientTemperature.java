// Need to see the output

package com.death.sensors;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class AmbientTemperature extends Activity implements SensorEventListener {
	private TextView temperaturelabel;
	private SensorManager mSensorManager;
	private Sensor mTemperature;
	private final static String NOT_SUPPORTED_MESSAGE = "Sorry, sensor not available for this device.";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		temperaturelabel = (TextView) findViewById(R.id.ambientTemperature);
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH){
			mTemperature= mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);	// requires API level 14.
		}
		if (mTemperature == null) {				
			temperaturelabel.setText(NOT_SUPPORTED_MESSAGE);
		}		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_mainnew, menu);
		return true;
	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mTemperature, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {                 
		super.onPause();
		mSensorManager.unregisterListener(this);
	}


	@Override
	public void onSensorChanged(SensorEvent event) {
		float ambient_temperature = event.values[0];
		temperaturelabel.setText("Ambient Temperature:\n " + String.valueOf(ambient_temperature) + getResources().getString(R.string.celsius));		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// Do something here if sensor accuracy changes.
	}
}