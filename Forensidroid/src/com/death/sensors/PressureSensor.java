//not correct
package com.death.sensors;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PressureSensor extends Activity{
	
	private SensorManager mSensorManager;
	TextView tv1=null;
	
	protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_main);
	      
	      tv1 = (TextView) findViewById(R.id.textView2);
	      tv1.setVisibility(View.GONE);
	      
	      mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	      List<Sensor> mList= mSensorManager.getSensorList(Sensor.TYPE_ALL);
	      
	      for (int i = 1; i < mList.size(); i++) {
	         tv1.setVisibility(View.VISIBLE);
	         tv1.append("\n"+(i)+": " + mList.get(i).getName() + "\n" + mList.get(i).getVendor() + "\n" + mList.get(i).getVersion());
	      }
	   }
     

}
