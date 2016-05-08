package com.death.artifacts;

import android.util.Log;
import android.widget.TextView;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;

public class WiFiActivity extends Activity {
	String filepath = Environment.getExternalStorageDirectory().getAbsolutePath();
	public static final String FILE_DIRECTORY = "WiFi";
	private static final String TAG = null;
	File folder = new File(filepath+File.separator+FILE_DIRECTORY);

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//TextView view = new TextView(this);
		getWrite();
		getCurrentSsid(getBaseContext());
		//tContentView(view);
	}

	private void getWrite() {
		
		if (!folder.exists()) {
			try {
				folder.mkdir();
			} catch (Exception e) {
				Log.d(TAG, "WiFI Folder not created :"+e);
			}
		}
		
		
	}

	public void getCurrentSsid(Context context) {

		String ssid = null;
		TextView view = new TextView(this);
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo.isConnected()) {
			final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		    final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
		    if (connectionInfo != null && !(connectionInfo.getSSID().equals(""))) {
		        //if (connectionInfo != null && !StringUtil.isBlank(connectionInfo.getSSID())) {
		    	ssid = connectionInfo.getSSID();
		    }
		    
		    connManager.getAllNetworks();
		    
		 // Get WiFi status MARAKANA
		    WifiInfo info = wifiManager.getConnectionInfo();
		    String textStatus = "";
		    textStatus += "\n\nWiFi Status: " + info.toString();

		    List<ScanResult> results = wifiManager.getScanResults();

		    int count = 1;
		    String etWifiList = "";
		    for (ScanResult result : results) {
		    	etWifiList += count++ + ". " + result.SSID + " : " + result.level + "\n" +
		    			result.BSSID + "\n" + result.capabilities + result.frequency +"\n";
		    			//result.channelWidth+
		               
		    }
		    Log.v(TAG, "from SO: \n"+etWifiList);

		    // List stored networks
		    List<WifiConfiguration> configs = wifiManager.getConfiguredNetworks();
		    for (WifiConfiguration config : configs) {
		        textStatus+= "\n\n" + config.toString();
		    }
		    Log.v(TAG,"from marakana: \n"+textStatus);
		    
		    String superduper = "Currently using : "+ssid+"\n"+etWifiList+textStatus;
		    view.setText(superduper);
		    setContentView(view);
		    
			File file = new File(folder+File.separator+"WiFiList.txt");
			if (!file.exists()) {
				try {
					file.createNewFile();
					FileOutputStream  writer = new FileOutputStream (file, true);
					writer.write("SSID, Level, BSSID, Capabilities, Frequency\n".getBytes());
				} catch (IOException e) {
					Log.d(TAG, "File not created :"+e);
				}
			}
			
			try {
				BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
				buf.append(etWifiList);buf.append(", ");
				buf.newLine();buf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
			
			File file2 = new File(folder+File.separator+"CurrentlyUsing.txt");
			if (!file2.exists()) {
				try {
					file2.createNewFile();
					FileOutputStream  writer = new FileOutputStream (file2, true);
					writer.write("Currently using : \n".getBytes());
				} catch (IOException e) {
					Log.d(TAG, "File not created :"+e);
				}
			}
			
			try {
				BufferedWriter buf = new BufferedWriter(new FileWriter(file2, true));
				buf.append(ssid);buf.append(", ");
				buf.newLine();buf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
			
			File file3 = new File(folder+File.separator+"WifiConfiguration.txt");
			if (!file3.exists()) {
				try {
					file3.createNewFile();
					FileOutputStream  writer = new FileOutputStream (file3, true);
					writer.write("Configuration : \n".getBytes());
				} catch (IOException e) {
					Log.d(TAG, "File not created :"+e);
				}
			}
			
			try {
				BufferedWriter buf = new BufferedWriter(new FileWriter(file3, true));
				buf.append(textStatus);buf.append(", ");
				buf.newLine();buf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
	
	}
		
	}	
}
