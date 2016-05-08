package com.death.artifacts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SmsActivity extends Activity implements OnItemClickListener {
	
    private static final String DATE_FORMAT = "dd-MM-yyyy, hh:mm:ss.SSS";
    String filepath = Environment.getExternalStorageDirectory().getAbsolutePath();
	//File file = null;
    File folder = new File(filepath+File.separator+FILE_DIRECTORY);
    File file = new File(folder+File.separator+"SMS.txt");
	public static final String FILE_DIRECTORY = "SMS";
	private static final String TAG = null;
	int count;

    private static SmsActivity inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> smsMessagesList1 = new ArrayList<String>();
    ListView smsListView1;
    ArrayAdapter arrayAdapter1;

    public static SmsActivity instance() {
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
		
        setContentView(R.layout.activity_sms);
        smsListView = (ListView) findViewById(R.id.SMSList);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        smsListView.setAdapter(arrayAdapter);
        smsListView1 = (ListView) findViewById(R.id.SMSListSent);
        arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesList1);
        smsListView1.setAdapter(arrayAdapter1);
      //  smsListView1.setOnItemClickListener(this);

        refreshSmsInbox();
        refreshSmsSent();
        refreshSmsOutbox();
        refreshSmsDraft();
    }

    private void refreshSmsDraft() {
    	ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/draft"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();
        do {
            try{
				BufferedWriter buf = new BufferedWriter(new FileWriter(file, true)); 
            	buf.append(smsInboxCursor.getString(indexAddress));buf.append(",");
            	buf.append(smsInboxCursor.getString(indexBody));buf.append(",");
            	buf.append(getDate(Long.valueOf(smsInboxCursor.getString(4)), DATE_FORMAT));
            	buf.newLine();buf.close();
            }catch (IOException e){
            	Log.d(TAG, "Inbox not created :"+e);
            }
        } while (smsInboxCursor.moveToNext());
		
	}

	private void refreshSmsOutbox() {
    	ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/outbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();
        do {
            try{
				BufferedWriter buf = new BufferedWriter(new FileWriter(file, true)); 
            	buf.append(smsInboxCursor.getString(indexAddress));buf.append(",");
            	buf.append(smsInboxCursor.getString(indexBody));buf.append(",");
            	buf.append(getDate(Long.valueOf(smsInboxCursor.getString(4)), DATE_FORMAT));
            	buf.newLine();buf.close();
            }catch (IOException e){
            	Log.d(TAG, "Inbox not created :"+e);
            }
        } while (smsInboxCursor.moveToNext());
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

	private void refreshSmsSent() {
    	ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/sent"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter1.clear();
        do {
            String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                    "\n" + smsInboxCursor.getString(indexBody) +
            		"\n" + getDate(Long.valueOf(smsInboxCursor.getString(4)), DATE_FORMAT);
            arrayAdapter1.add(str);
            
            try{
				BufferedWriter buf = new BufferedWriter(new FileWriter(file, true)); 
            	buf.append(smsInboxCursor.getString(indexAddress));buf.append(",");
            	buf.append(smsInboxCursor.getString(indexBody));buf.append(",");
            	buf.append(getDate(Long.valueOf(smsInboxCursor.getString(4)), DATE_FORMAT));
            	buf.newLine();buf.close();
            }catch (IOException e){
            	Log.d(TAG, "Inbox not created :"+e);
            }
        } while (smsInboxCursor.moveToNext());
	}

	public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();
        do {
            String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                    "\n" + smsInboxCursor.getString(indexBody) +
            		"\n" + getDate(Long.valueOf(smsInboxCursor.getString(4)), DATE_FORMAT);
            arrayAdapter.add(str);
            
            try{
				BufferedWriter buf = new BufferedWriter(new FileWriter(file, true)); 
            	buf.append(smsInboxCursor.getString(indexAddress));buf.append(",");
            	buf.append(smsInboxCursor.getString(indexBody));buf.append(",");
            	buf.append(getDate(Long.valueOf(smsInboxCursor.getString(4)), DATE_FORMAT));
            	buf.newLine();buf.close();
            }catch (IOException e){
            	Log.d(TAG, "Inbox not created :"+e);
            }
        } while (smsInboxCursor.moveToNext());
    }

    public void updateList(final String smsMessage) {
        arrayAdapter.insert(smsMessage, 0);
        arrayAdapter.notifyDataSetChanged();

    }

    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        try {
            String[] smsMessages = smsMessagesList.get(pos).split("\n");
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