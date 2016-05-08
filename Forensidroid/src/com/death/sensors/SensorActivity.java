package com.death.sensors;

import com.example.badone.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class SensorActivity extends Activity implements SensorEventListener {
    private SensorManager mgr;
    private Sensor accelerometer;
    private TextView text;
    private int mRotation;
    //ambient
    private TextView temperaturelabel;
	private SensorManager mSensorManager;
	private Sensor mTemperature;
	private final static String NOT_SUPPORTED_MESSAGE = "Sorry, sensor not available for this device.";
//gravity
	private SensorManager gravitymgr;
    private Sensor gravitysensor;
    private TextView gravitytext;
    private float[] gravity = new float[3];
    private float[] motion = new float[3];
    private double ratio;
    private double mAngle;
    private int counter = 0;
    //GyrosocpeSensor
    private SensorManager gyromgr;
    private Sensor gyro;
    private TextView gyrotext;
    //LightSensor
    private SensorManager lightmgr;
    private Sensor light;
    private TextView lighttext;
    private String lightmsg = "";
    //LinearAcceleration
    private SensorManager sensorManager;
    private TextView lineartext;
    private long lastUpdate;
    Sensor linearaccess;
    //Magnetic
    private TextView magneticX;
    private TextView magneticY;
    private TextView magneticZ;
    private SensorManager magsensorManager = null;
    private Sensor magsensor;
    //Orientation
    private SensorManager oSensorManager;
    Sensor oaccelerometer;
    Sensor omagnetometer;
    float oazimut;
    float opitch;
    float oRoll;
    private TextView Azimuth;
    private TextView Pitch;
    private TextView Roll;
    private TextView orienlabel;
    //Proximity
    private SensorManager proximitymgr;
    private Sensor proximity;
    private TextView proximitytext;
    private String proximitymsg;
    //Pressure
    TextView TVAirPressure;
    private SensorManager presensorManager;
    private Sensor pressure;
    //Temperature
    private SensorManager tempmgr;
    private Sensor temp;
    private TextView temptext;
    private String tempmsg;
    //StepCounter
    private long timestamp;
	private TextView textViewStepCounter;
	private TextView textViewStepDetector;
	private Thread detectorTimeStampUpdaterThread;
	private Handler handler;
	private boolean isRunning = true;
	//SignificantMotion
	private  SensorManager smSensorManager;
    private  Sensor smSigMotion;
    private  TriggerEventListener smListener = new TriggerListener();
    private TextView smtv;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        
        mgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        accelerometer = mgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mTemperature= mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        
        
        gravitymgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        gravitysensor = mgr.getDefaultSensor(Sensor.TYPE_GRAVITY);
        
        gyromgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        gyro = gyromgr.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        
        lightmgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        light = lightmgr.getDefaultSensor(Sensor.TYPE_LIGHT);
        
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        linearaccess = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
	    
        magsensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        magsensor = magsensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        // Register magnetic sensor
        magsensorManager.registerListener(this, magsensor, SensorManager.SENSOR_DELAY_NORMAL);
        
        oSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        oaccelerometer = oSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        omagnetometer = oSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        
        proximitymgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        proximity = proximitymgr.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        
        presensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        pressure = presensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        
        tempmgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        temp = tempmgr.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
        
        smSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
	    smSigMotion = smSensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
        
        temperaturelabel = (TextView) findViewById(R.id.ambientTemperature);
        text = (TextView) findViewById(R.id.textView);
        gravitytext = (TextView) findViewById(R.id.gravitysensor);
        gyrotext = (TextView) findViewById(R.id.gyroscopesensor);
        lighttext = (TextView) findViewById(R.id.lightsensor);
	    lineartext = (TextView) findViewById(R.id.linearAccelerationSensor);
	 // Capture magnetic sensor related view elements
        magneticX = (TextView) findViewById(R.id.valMag_X);
        magneticY = (TextView) findViewById(R.id.valMag_Y);
        magneticZ = (TextView) findViewById(R.id.valMag_Z);
        Azimuth = (TextView) findViewById(R.id.Azimuth);
        Pitch = (TextView) findViewById(R.id.Pitch);
        Roll = (TextView) findViewById(R.id.Roll);
        proximitytext = (TextView) findViewById(R.id.proximitysensor);
        TVAirPressure=(TextView)findViewById(R.id.pressuresensor);
        temptext = (TextView) findViewById(R.id.temperaturesensor);
        smtv = (TextView) findViewById(R.id.sigmotion);
        
        WindowManager window = (WindowManager) this.getSystemService(WINDOW_SERVICE);
        int apiLevel = Integer.parseInt(Build.VERSION.SDK);
        if(apiLevel < 8) {
            mRotation = window.getDefaultDisplay().getOrientation();
        }
        else{
        	mRotation = window.getDefaultDisplay().getRotation();	
        }
        if (mTemperature == null) {				
			temperaturelabel.setText(NOT_SUPPORTED_MESSAGE);
		}
        
        textViewStepCounter = (TextView) findViewById(R.id.StepCounter);
		textViewStepDetector = (TextView) findViewById(R.id.StepDetector);

		registerForSensorEvents();
		setupDetectorTimestampUpdaterThread();
    }

    @Override
    protected void onResume() {
        mgr.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, mTemperature, SensorManager.SENSOR_DELAY_NORMAL);
        gravitymgr.registerListener(this, gravitysensor, SensorManager.SENSOR_DELAY_UI);
        gyromgr.registerListener(this, gyro, SensorManager.SENSOR_DELAY_GAME);
        lightmgr.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, linearaccess, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magsensor, SensorManager.SENSOR_DELAY_NORMAL);
        oSensorManager.registerListener(this, oaccelerometer, SensorManager.SENSOR_DELAY_UI);
        oSensorManager.registerListener(this, omagnetometer, SensorManager.SENSOR_DELAY_UI);
        proximitymgr.registerListener(this, proximity,SensorManager.SENSOR_DELAY_NORMAL);
        presensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL);
        tempmgr.registerListener(this, temp, SensorManager.SENSOR_DELAY_NORMAL);
        smSensorManager.requestTriggerSensor(smListener, smSigMotion);
      super.onResume();
    }

    @Override
    protected void onPause() {
        mgr.unregisterListener(this, accelerometer);
        mSensorManager.unregisterListener(this);
        gravitymgr.unregisterListener(this, gravitysensor);
        gyromgr.unregisterListener(this, gyro);
        lightmgr.unregisterListener(this, light);
        sensorManager.unregisterListener(this);
        magsensorManager.unregisterListener(this);
        oSensorManager.unregisterListener(this);
        proximitymgr.unregisterListener(this, proximity);
        presensorManager.unregisterListener(this, pressure);
        mgr.unregisterListener(this, temp);
        isRunning = false;
		detectorTimeStampUpdaterThread.interrupt();
		smSensorManager.cancelTriggerSensor(smListener, smSigMotion);
      super.onPause();
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    	lightmsg = String.format(sensor.getName() + " accuracy changed: " + accuracy +
    	        (accuracy==1?" (LOW)":(accuracy==2?" (MED)":" (HIGH)")) + "\n");
    	lighttext.setText(lightmsg);
    	lighttext.invalidate();
    }

    public void onSensorChanged(SensorEvent event) {
    	String msg = String.format("X: %8.4f\nY: %8.4f\nZ: %8.4f\nRotation: %d",
            event.values[0], event.values[1], event.values[2], mRotation);
    	text.setText(msg);
    	text.invalidate();
    	//Ambient%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    	float ambient_temperature = event.values[0];
		temperaturelabel.setText(String.valueOf(ambient_temperature) + getResources().getString(R.string.celsius));
		
		//Gravity%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		for(int i=0; i<3; i++) {
            gravity [i] = (float) (0.1 * event.values[i] + 0.9 * gravity[i]);
            motion[i] = event.values[i] - gravity[i];
		}
		ratio = gravity[1]/SensorManager.GRAVITY_EARTH;
		if(ratio > 1.0) ratio = 1.0;
		if(ratio < -1.0) ratio = -1.0;
		mAngle = Math.toDegrees(Math.acos(ratio));
		if(gravity[2] < 0) {
			mAngle = -mAngle;
		}
		if(counter++ % 10 == 0) {
			String gtavitymsg = String.format(
                "Raw values\nX: %8.4f\nY: %8.4f\nZ: %8.4f\n" +
                		"Gravity\nX: %8.4f\nY: %8.4f\nZ: %8.4f\n" +
                		"Motion\nX: %8.4f\nY: %8.4f\nZ: %8.4f\nAngle: %8.1f",
                		event.values[0], event.values[1], event.values[2],
                		gravity[0], gravity[1], gravity[2],
                		motion[0], motion[1], motion[2],
                		mAngle);
			gravitytext.setText(gtavitymsg);
			gravitytext.invalidate();
			counter=1;
		}
      //GyroScope%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		String gyromsg = String.format(
	              "X: %4.2f\nY: %4.2f\nZ: %4.2f\n" ,
	            event.values[0], event.values[1], event.values[2]);
		gyrotext.setText(gyromsg);
	    gyrotext.invalidate();
	    //Light%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	    lightmsg = String.format("SI lux units: %8.6f", event.values[0]);
	    lighttext.setText(lightmsg);
	    lighttext.invalidate();
	    //LinearAcceleration%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	    if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
	   	    float[] values = event.values;
		    // Movement
		    float x = values[0];
		    float y = values[1];
		    float z = values[2];

		    float accelationSquareRoot = (x * x + y * y + z * z)
		        / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
		    String linearmsg = String.format("X: %8.4f\nY: %8.4f\nZ: %8.4f\nAccelation: %8.4f\n",
		    		event.values[0], event.values[1], event.values[2], accelationSquareRoot);
		    lineartext.setText(linearmsg);
		    lineartext.invalidate();    
	    }
	    //Magnetic%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	    synchronized (this) {
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                magneticX.setText( Float.toString( event.values[0]));
                magneticY.setText( Float.toString( event.values[1]));
                magneticZ.setText( Float.toString( event.values[2]));
            }
        }
	    //Orientation%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	    float[] mGravity = null;
	    float[] mGeomagnetic = null;
	    int apiLevel = (Build.VERSION.SDK_INT);
        if(apiLevel > 8) {
        	if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        	      mGravity = event.values;
        	if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        	      mGeomagnetic = event.values;
        	if (mGravity != null && mGeomagnetic != null) {
        		float R[] = new float[9];
        		float I[] = new float[9];
        		boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
        		if (success) {
        			float orientation[] = new float[3];
        			SensorManager.getOrientation(R, orientation);
        			Azimuth.setText( Float.toString(orientation[0]));
                    Pitch.setText( Float.toString(orientation[1]));
                    Roll.setText( Float.toString(orientation[2]));
        		}
        	}  		
        }else if (event.sensor.getType()==Sensor.TYPE_ORIENTATION) {
        	Azimuth.setText( Float.toString( event.values[0]));
        	Pitch.setText( Float.toString( event.values[1]));
        	Roll.setText( Float.toString( event.values[2]));
		}else orienlabel.setText(R.string.orienlabel);
        //Proximity%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
        	if (event.values[0] == 0) {
        		proximitytext.setText("Near - "+Float.toString( event.values[0])+"cm");
        	} else {
        		proximitytext.setText("Far - "+Float.toString( event.values[0])+"cm");
        	}
        }
        //Pressure%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
        if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
        	//float[] values = event.values;
        	TVAirPressure.setText(Float.toString( event.values[0]));
        }else{
        	TVAirPressure.setText("No sensor value available");
        }
        //Temperature
        if (event.sensor.getType() == Sensor.TYPE_TEMPERATURE) {
        	temptext.setText(Float.toString( event.values[0] * 9 / 5 + 32)+"Celsius");
        }else{
        	temptext.setText("No sensor value available");
        }
        
    }
    public void registerForSensorEvents() {
		SensorManager sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		// Step Counter
		sManager.registerListener(new SensorEventListener() {

			@Override
			public void onSensorChanged(SensorEvent event) {
				float steps = event.values[0];
				textViewStepCounter.setText((int) steps + "");
			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {

			}
		}, sManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
				SensorManager.SENSOR_DELAY_UI);

		// Step Detector
		sManager.registerListener(new SensorEventListener() {

			@Override
			public void onSensorChanged(SensorEvent event) {
				// Time is in nanoseconds, convert to millis
				timestamp = event.timestamp / 1000000;
			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {

			}
		}, sManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR),
				SensorManager.SENSOR_DELAY_UI);
	}
    private void setupDetectorTimestampUpdaterThread() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				textViewStepDetector.setText(DateUtils
						.getRelativeTimeSpanString(timestamp));
			}
		};

		detectorTimeStampUpdaterThread = new Thread() {
			@Override
			public void run() {
				while (isRunning) {
					try {
						Thread.sleep(5000);
						handler.sendEmptyMessage(0);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};

		detectorTimeStampUpdaterThread.start();
	}
    class TriggerListener extends TriggerEventListener {
    	
        public void onTrigger(TriggerEvent event) {
             // Do Work.
        	// As it is a one shot sensor, it will be canceled automatically.
        // SensorManager.requestTriggerSensor(this, mSigMotion); needs to
        // be called again, if needed.
        	long currentTimeStamp = System.currentTimeMillis();
        	smtv.setText("Last movement triggered at "+String.valueOf(currentTimeStamp));
        }
    }
} 