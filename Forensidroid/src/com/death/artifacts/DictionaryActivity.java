package com.death.artifacts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.UserDictionary;
import android.util.Log;
import android.widget.TextView;

public class DictionaryActivity extends Activity {
	String filepath = Environment.getExternalStorageDirectory().getAbsolutePath();
	File file = null;
	public static final String FILE_DIRECTORY = "DICTIONARY";
	private static final String TAG = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView view = new TextView(this);
		Uri uri = UserDictionary.Words.CONTENT_URI;
		
		File folder = new File(filepath+File.separator+FILE_DIRECTORY);
		if (!folder.exists()) {
			try {
				folder.mkdir();
			} catch (Exception e) {
				Log.d(TAG, "Folder not created :"+e);
			}
		}
		File file = new File(folder+File.separator+"Dictionary.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
				FileOutputStream  writer = new FileOutputStream (file, true);
				writer.write("ID, WORD, FREQUENCY, LOCALE, APP_ID, SHORTCUT\n".getBytes());
			} catch (IOException e) {
				Log.d(TAG, "File not created :"+e);
			}
		}
		
		String id = BaseColumns._ID;
		String word = UserDictionary.Words.WORD;
		String frequency = UserDictionary.Words.FREQUENCY;
		String locale = UserDictionary.Words.LOCALE;
		String appId = UserDictionary.Words.APP_ID;
		String shortcut = UserDictionary.Words.SHORTCUT;
		//Uri uri = Uri.parse("content://user_dictionary");
		Cursor cur = getContentResolver().query(uri, null, null, null,null);
		String wordd = "";
	    while (cur.moveToNext()) {
	    	wordd += "Id :" +  cur.getString(cur.getColumnIndex(id))+ 
	    			", Word :"+cur.getString(cur.getColumnIndex(word))+ 
	    			", Frequency :"+cur.getString(cur.getColumnIndex(frequency))+
	    			", Locale :"+cur.getString(cur.getColumnIndex(locale))+
	    			", AppId :"+cur.getString(cur.getColumnIndex(appId))+
	    			", Shortcut :"+cur.getString(cur.getColumnIndex(shortcut))+"\n"; 
	    	try{
				   //BufferedWriter for performance, true to set append to file flag
	    	BufferedWriter buf = new BufferedWriter(new FileWriter(file, true)); 
			buf.append(cur.getString(cur.getColumnIndex(id)));buf.append(",");
			buf.append(cur.getString(cur.getColumnIndex(word)));buf.append(",");
			buf.append(cur.getString(cur.getColumnIndex(frequency)));buf.append(",");
			buf.append(cur.getString(cur.getColumnIndex(locale)));buf.append(", ");
			buf.append(cur.getString(cur.getColumnIndex(appId)));buf.append(", ");
			buf.append(cur.getString(cur.getColumnIndex(shortcut)));
			buf.newLine();
			buf.close();
		}catch (IOException e){
			Log.d(TAG, "Dictionary file not created :"+e);
		}
	    }
	    view.setText(wordd);
	    setContentView(view);
	    
	    
		
	}	
}