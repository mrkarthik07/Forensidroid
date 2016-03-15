package com.death.sensors;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class GyrosocpeSensor extends Activity implements SensorEventListener {
    private SensorManager mgr;
    private Sensor gyro;
    private TextView text;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        gyro = mgr.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        text = (TextView) findViewById(R.id.gyroscopesensortext);
    }

    @Override
    protected void onResume() {
        mgr.registerListener(this, gyro, SensorManager.SENSOR_DELAY_GAME);
      super.onResume();
    }

    @Override
    protected void onPause() {
        mgr.unregisterListener(this, gyro);
      super.onPause();
    }

  public void onAccuracyChanged(Sensor sensor, int accuracy) {

  }

  public void onSensorChanged(SensorEvent event) {
	  
	  String msg = String.format(
              "X: %4.2f\nY: %4.2f\nZ: %4.2f\n" ,
            event.values[0], event.values[1], event.values[2]);
//    String msg = "X: " + event.values[0] + "\n" +
//        "Y: " + event.values[1] + "\n" +
//        "Z: " + event.values[2] + "\n";
    text.setText(msg);
    text.invalidate();
  }
}