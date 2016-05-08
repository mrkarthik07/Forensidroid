package com.death.artifacts;

import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

public class SIMActivity extends Activity {
	String filepath = Environment.getExternalStorageDirectory().getAbsolutePath();
	public static final String FILE_DIRECTORY = "SIMDetails";
	private static final String TAG = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView view = new TextView(this);
		
		File folder = new File(filepath+File.separator+FILE_DIRECTORY);
		if (!folder.exists()) {
			try {
				folder.mkdir();
			} catch (Exception e) {
				Log.d(TAG, "Folder not created :"+e);
			}
		}
		File file = new File(folder+File.separator+"SIM.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
				FileOutputStream  writer = new FileOutputStream (file, true);
				writer.write("IMEI, softwareversion, SimState, MSISDN, CellLocation, MCC, MCCMNCNumber, NetworkOperatorName, ISOcountrycode, MCCMNC, SPN, SIM, IMSI, Networktype, PhoneType, CellInfo\n".getBytes());
			} catch (IOException e) {
				Log.d(TAG, "File not created :"+e);
			}
		}
		
		TelephonyManager mTelephonyMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		String imei = mTelephonyMgr.getDeviceId();
		String softwareversion = mTelephonyMgr.getDeviceSoftwareVersion();
		int SimState = mTelephonyMgr.getSimState();
		String MSISDN = mTelephonyMgr.getLine1Number();
		CellLocation CellLocation = mTelephonyMgr.getCellLocation();
		String MCC = mTelephonyMgr.getNetworkCountryIso();
		String MCCMNCNumber = mTelephonyMgr.getNetworkOperator();
		String NetworkOperatorName = mTelephonyMgr.getNetworkOperatorName();
		String ISOcountrycode = mTelephonyMgr.getSimCountryIso();
		String MCCMNC = mTelephonyMgr.getSimOperator();
		String SPN = mTelephonyMgr.getSimOperatorName();
		String SIM = mTelephonyMgr.getSimSerialNumber();
		String IMSI  = mTelephonyMgr.getSubscriberId();
		List<CellInfo> CellInfo = mTelephonyMgr.getAllCellInfo();
		int Networktype = mTelephonyMgr.getNetworkType();
		//int PhoneCount = mTelephonyMgr.getPhoneCount();
		int PhoneType = mTelephonyMgr.getPhoneType();
		String dummi ="";
		for (int i = 0; i < CellInfo.size(); i++) {
			dummi+=CellInfo;
		}
		String all = "";
		
		all="IMEI"+imei+", softwareversion :"+softwareversion+
				", SimState : "+SimState+
				", MSISDN : "+MSISDN+
				", CellLocation : "+CellLocation.toString()+
				", MCC : "+MCC+
				", MCCMNCNumber : "+MCCMNCNumber+
				", NetworkOperatorName : "+NetworkOperatorName+
				", ISOcountrycode : "+ISOcountrycode+
				", MCCMNC : "+MCCMNC+
				", SPN : "+SPN+
				", SIM : "+SIM+
				", IMSI : "+IMSI+
				", Networktype : "+Networktype+
				//", PhoneCount : "+PhoneCount+
				", PhoneType : "+PhoneType+
				", CellInfo : "+dummi+"\n";
		view.setText(all);
	    setContentView(view);
	    
	    try{
			   //BufferedWriter for performance, true to set append to file flag
			   BufferedWriter buf = new BufferedWriter(new FileWriter(file, true)); 
//			   buf.append(imei);buf.append(",");
//			   buf.append(softwareversion);buf.append(",");
//			   buf.append((char) SimState);buf.append(",");
//			   buf.append(MSISDN);buf.append(",");
//			   buf.append(CellLocation.toString());buf.append(",");
//			   buf.append(MCC);buf.append(",");
//			   buf.append(MCCMNCNumber);buf.append(",");
//			   buf.append(NetworkOperatorName);buf.append(",");
//			   buf.append(ISOcountrycode);buf.append(",");
//			   buf.append(MCCMNC);buf.append(",");
//			   buf.append(SPN);buf.append(",");
//			   buf.append(SIM);buf.append(",");
//			   buf.append(IMSI);buf.append(",");
//			   buf.append((char) Networktype);buf.append(",");
//			   buf.append((char) PhoneType);buf.append(",");
//			   buf.append(dummi);
			   buf.append(all);
			   buf.newLine();
			   buf.close();
			}catch (IOException e){
				Log.d(TAG, "SIM details not created :"+e);
			}
	    
	}
}
