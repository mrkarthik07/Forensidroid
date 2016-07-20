package com.death.artifacts;

import com.example.badone.R;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.widget.Chronometer;
import android.widget.ListView;

public class BrowserActivity extends Activity {

	ListView listView;
	ListView listView1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browser);
		listView = (ListView) findViewById(R.id.listView1);
//		listView1 = (ListView) findViewById(R.id.listView2);
		getBrowserHist();
	}
  
	public void getBrowserHist()  {

		Uri uri = Uri.parse("content://com.android.chrome.browser/bookmarks");
		Uri uris = Uri.parse("content://com.android.chrome.browser/searches");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        
        Cursor scursor = getContentResolver().query(uris, null, null, null, null);
        	 
        MyCursorAdapter myCursorAdapter = new MyCursorAdapter(this,cursor,0);
		 listView.setAdapter(myCursorAdapter);  
		 
//		 MySCursorAdapter mysCursorAdapter = new MySCursorAdapter(this,scursor,0);
//		 listView1.setAdapter(mysCursorAdapter); 
      }
}