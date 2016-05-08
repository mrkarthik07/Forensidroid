package com.death.artifacts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

public class BrowserActivity extends Activity {

	private static final String TAG = null;
	String filepath = Environment.getExternalStorageDirectory().getAbsolutePath();
	File file = null;
	public static final String FILE_DIRECTORY = "Browser";
	private static final String DATE_FORMAT = "dd-MM-yyyy, hh:mm:ss.SSS";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView view =new TextView(this);
		File folder = new File(filepath+File.separator+FILE_DIRECTORY);
		if (!folder.exists()) {
			try {
				folder.mkdir();
			} catch (Exception e) {
				Log.d(TAG, "Folder not created :"+e);
			}
		}
		File file = new File(folder+File.separator+"Bookmark.txt");
		File file2 = new File(folder+File.separator+"Search.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
				FileOutputStream  writer = new FileOutputStream (file, true);
				writer.write("ID, URL, VISITS, DATE, BOOKMARK, TITLE\n".getBytes());
			} catch (IOException e) {
				Log.d(TAG, "Bookmark File not created :"+e);
			}
		}
		if (!file2.exists()) {
			try {
				file2.createNewFile();
				FileOutputStream  writer = new FileOutputStream (file, true);
				writer.write("ID, SEARCH, DATE\n".getBytes());
			} catch (IOException e) {
				Log.d(TAG, "Search File not created :"+e);
			}
		}
		
		String word = "";
		String wordi = "";
		
		Uri uri = Uri.parse("content://browser/bookmarks");
	    
	    Cursor cur = getContentResolver().query(uri, null, null, null,null);
	    if (cur.moveToFirst()) {
	    	do{
	    		try{
	    			word+=cur.getString(0)+", "+cur.getString(1)+", "+cur.getString(2)+", "+
	    					getDate(Long.valueOf(cur.getString(3)), DATE_FORMAT)+", "+
	    					cur.getString(4)+", "+cur.getString(5)+"\n";
					   //BufferedWriter for performance, true to set append to file flag
	    			BufferedWriter buf = new BufferedWriter(new FileWriter(file, true)); 
	    			buf.append(cur.getString(0));buf.append(",");
	    			buf.append(cur.getString(1));buf.append(",");
	    			buf.append(cur.getString(2));buf.append(",");
	    			buf.append(getDate(Long.valueOf(cur.getString(3)), DATE_FORMAT));buf.append(",");
	    			buf.append(cur.getString(4));buf.append(",");
	    			buf.append(cur.getString(5));
	    			buf.newLine();buf.close();
	    		}catch (IOException e){
	    			Log.d(TAG, "Bookmark file not created :"+e);
	    		}	
	    	}while(cur.moveToNext());
	    }
	    
		Uri uris = Uri.parse("content://browser/searches");
        Cursor mCur = getContentResolver().query(uris, null, null, null, null);
        if (mCur.moveToFirst()) {
            do {            	
            	try{
            		wordi+=mCur.getString(0)+", "+mCur.getString(1)+", "+
            				getDate(Long.valueOf(mCur.getString(2)), DATE_FORMAT)+"\n";
					   //BufferedWriter for performance, true to set append to file flag
            		BufferedWriter buf = new BufferedWriter(new FileWriter(file2, true)); 
	    			buf.append(mCur.getString(0));buf.append(",");
	    			buf.append(mCur.getString(1));buf.append(",");
	    			buf.append(getDate(Long.valueOf(mCur.getString(2)), DATE_FORMAT));
	    			buf.newLine();buf.close();
	    		}catch (IOException e){
	    			Log.d(TAG, "Bookmark file not created :"+e);
	    		}
                
            }while (mCur.moveToNext());
        }
        view.setText("Searches\n"+wordi+"History and Bookmarks\n"+word);  
        setContentView(view);
	}
	private static String getDate(final long milliSeconds, final String dateFormat) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return new SimpleDateFormat(dateFormat).format(calendar.getTime());
    }
}