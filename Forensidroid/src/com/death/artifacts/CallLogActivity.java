package com.death.artifacts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.example.badone.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CallLogActivity extends Activity {
	 private static final String DATE_FORMAT = "dd-MM-yyyy, hh:mm:ss.SSS";
	    String filepath = Environment.getExternalStorageDirectory().getAbsolutePath();
	    File folder = new File(filepath+File.separator+FILE_DIRECTORY);
	    File file = new File(folder+File.separator+"CallLog.txt");
		public static final String FILE_DIRECTORY = "CallLog";
		private static final String TAG = null;
		int count;

	    private static CallLogActivity inst;
	    ArrayList<String> CallLogList = new ArrayList<String>();
	    ListView CallLogView;
	    ArrayAdapter arrayAdapter;
	    
	    public static CallLogActivity instance() {
	        return inst;
	    }

	    @Override
	    public void onStart() {
	        super.onStart();
	        inst = this;
	    }

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        oncreate();
			
	        setContentView(R.layout.activity_psbroadcast);
	        CallLogView = (ListView) findViewById(R.id.textview_call);
	        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CallLogList);
	        CallLogView.setAdapter(arrayAdapter);

	        refreshCallLog();
	        
	    }

		private void oncreate() {
	    	if (!folder.exists()) {
				try {
					folder.mkdir();
				} catch (Exception e) {
					Log.d(TAG, "Folder not created :"+e);
				}
			}
			
			if (!file.exists()) {
				try {
					file.createNewFile();
					FileOutputStream  writer = new FileOutputStream (file, true);
					writer.write("Phone Number, Msg, Time\n".getBytes());
				} catch (IOException e) {
					Log.d(TAG, "File not created :"+e);
				}
			}	
		}
		
		public void refreshCallLog() {
	        ContentResolver contentResolver = getContentResolver();
	        Uri uri = Calls.CONTENT_URI;
	        
	        Cursor CallLogCursor = contentResolver.query(uri, null, null, null, null);
	        int indexDate = CallLogCursor.getColumnIndex(CallLog.Calls.DATE);
	        int indexDuration = CallLogCursor.getColumnIndex(CallLog.Calls.DURATION);
	        int indexNumber = CallLogCursor.getColumnIndex(CallLog.Calls.NUMBER);
	        int indexType = CallLogCursor.getColumnIndex((CallLog.Calls.TYPE));
	        
	        if (!CallLogCursor.moveToFirst()) return;
	        arrayAdapter.clear();
	        do {
	        	String str = CallLogCursor.getString(indexType) +
	                    "\n" + CallLogCursor.getString(indexNumber) +
	            		"\n" + getDate(Long.valueOf(CallLogCursor.getString(indexDate)), DATE_FORMAT)+
	            		"\n" + CallLogCursor.getString(indexDuration);
	            arrayAdapter.add(str);
	            
	            try{
					BufferedWriter buf = new BufferedWriter(new FileWriter(file, true)); 
	            	buf.append(CallLogCursor.getString(indexType));buf.append(":");
	            	buf.append(CallLogCursor.getString(indexNumber));buf.append(",");
	            	buf.append(getDate(Long.valueOf(CallLogCursor.getString(indexDate)), DATE_FORMAT));buf.append(",");
	            	buf.append(CallLogCursor.getString(indexDuration));
	            	buf.newLine();buf.close();
	            }catch (IOException e){
	            	Log.d(TAG, "Inbox not created :"+e);
	            }
	        } while (CallLogCursor.moveToNext());	        
	    }

	    public void updateList(final String smsMessage) {
	        arrayAdapter.insert(smsMessage, 0);
	        arrayAdapter.notifyDataSetChanged();

	    }

	    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
	        try {
	            String[] smsMessages = CallLogList.get(pos).split("\n");
	            String address = smsMessages[0];
	            String smsMessage = "";
	            for (int i = 1; i < smsMessages.length; ++i) {
	                smsMessage += smsMessages[i];
	            }

	            String smsMessageStr = address + "\n";
	            smsMessageStr += smsMessage;
	            Toast.makeText(this, smsMessageStr, Toast.LENGTH_SHORT).show();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    private static String getDate(final long milliSeconds, final String dateFormat) {
	        final Calendar calendar = Calendar.getInstance();
	        calendar.setTimeInMillis(milliSeconds);
	        return new SimpleDateFormat(dateFormat).format(calendar.getTime());
	    }   	    
}