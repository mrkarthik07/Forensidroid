package com.death.artifacts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.example.badone.R;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class AppInfoActivity extends Activity {
	private static final String TAG = null;
	public static final String FILE_DIRECTORY = "AppInfo";
	
	String filepath = Environment.getExternalStorageDirectory().getAbsolutePath();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_appinfo);
		TextView text = new TextView(this);
		File folder = new File(filepath+File.separator+FILE_DIRECTORY);
		File file = new File(folder+File.separator+"AppInfo.csv");
		if (!folder.exists()) {
			folder.mkdir();
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
				BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
				buf.append("Installed package, Source dir, Launch Activity, App Name");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
//		text = (TextView) findViewById(R.id.textViewappinfo);
//		text.setVisibility(View.GONE);
			
		final PackageManager pm = getApplicationContext().getPackageManager();
		//get a list of installed apps.
		List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

		for (ApplicationInfo packageInfo : packages) {
		    Log.d(TAG, "Installed package :" + packageInfo.packageName);
		    Log.d(TAG, "Source dir : " + packageInfo.sourceDir);
		    Log.d(TAG, "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName)); 
		    Log.d(TAG, "App Name :"+ pm.getApplicationLabel(packageInfo)); 
		}
		String word = "";
		for (ApplicationInfo packageInfo : packages) {
			word+=packageInfo.packageName+", "+packageInfo.sourceDir+", "+pm.getApplicationLabel(packageInfo)+"\n";
			try{
				
				
				//BufferedWriter for performance, true to set append to file flag
				BufferedWriter buf = new BufferedWriter(new FileWriter(file, true)); 
				buf.append(packageInfo.packageName);buf.append(",");
				buf.append(packageInfo.sourceDir);buf.append(",");
				  // buf.append((CharSequence) pm.getLaunchIntentForPackage(packageInfo.packageName));buf.append(", ");
				buf.append(pm.getApplicationLabel(packageInfo));
				buf.newLine();
				buf.close();
			}catch (IOException e){
				e.printStackTrace();
			}
		}
		text.setText(word);
		setContentView(text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_mainnew, menu);
		return true;
	}

	
}
