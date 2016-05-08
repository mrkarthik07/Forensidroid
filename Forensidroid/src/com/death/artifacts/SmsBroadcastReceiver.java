package com.death.artifacts;

import android.telephony.SmsMessage;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";
    
    String filepath = Environment.getExternalStorageDirectory().getAbsolutePath();
	File folder = new File(filepath+File.separator+FILE_DIRECTORY);
    File file = new File(folder+File.separator+"SMS.txt");
	public static final String FILE_DIRECTORY = "SMS";

	private static final String TAG = null;
	
	String smsBody;
	String address;
	long date;

    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                smsBody = smsMessage.getMessageBody().toString();
                address = smsMessage.getOriginatingAddress();
                date = smsMessage.getTimestampMillis();

                smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr += smsBody;
                smsMessageStr += date + "\n";
            }
            Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();
            try{
				BufferedWriter buf = new BufferedWriter(new FileWriter(file, true)); 
            	buf.append(smsBody);buf.append(",");
            	buf.append(address);buf.append(",");
            	buf.append(Long.toString(date));
            	buf.newLine();buf.close();
            }catch (IOException e){
            	Log.d(TAG, "Inbox not created :"+e);
            }

            //this will update the UI with message
            SmsActivity inst = SmsActivity.instance();
            inst.updateList(smsMessageStr);
        }
    }
    
}