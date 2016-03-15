package com.death.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.death.sensors.GPSLocationSensor;

import android.app.Activity;

public class appendLog extends Activity{
	
	public appendLog(String toBeLongi, String toBeLati, String toBeAcc, String toBeAlti, String toBeSeci) {
		File file = new File("gpsLsog.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
				writer.write("Latitude, Longitude, ALTITUDE, Accuracy, Seconds ago");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try{
		   //BufferedWriter for performance, true to set append to file flag
		   BufferedWriter buf = new BufferedWriter(new FileWriter(file, true)); 
		   buf.append(toBeLati);buf.append(", ");
		   buf.append(toBeLongi);buf.append(", ");
		   buf.append(toBeAlti);buf.append(", ");
		   buf.append(toBeAcc);buf.append(", ");
		   buf.append(toBeSeci);
		   buf.newLine();
		   buf.close();
		}catch (IOException e){
		      e.printStackTrace();
		}
	}
	
	public appendLog(String gpsLongi, String gpsLati, String gpsAcc, String gpsAlti, String gpsBear, String gpsSpee ) {
		File file = new File("sdcard/gpsLsog.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
				writer.write("Latitude, Longitude, ALTITUDE, Accuracy, Seconds ago");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try{
		   //BufferedWriter for performance, true to set append to file flag
		   BufferedWriter buf = new BufferedWriter(new FileWriter(file, true)); 
		   buf.append(gpsLongi);buf.append(", ");
		   buf.append(gpsLati);buf.append(", ");
		   buf.append(gpsAcc);buf.append(", ");
		   buf.append(gpsAlti);buf.append(", ");
		   buf.append(gpsBear);
		   buf.append(gpsSpee);
		   buf.newLine();
		   buf.close();
		}catch (IOException e){
		      e.printStackTrace();
		}
	}
	

}
