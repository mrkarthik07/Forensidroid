package com.death.artifacts;

import java.util.Date;

import com.example.badone.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MySCursorAdapter extends CursorAdapter {

    private LayoutInflater cursorInflater;
    
    // Default constructor
    public MySCursorAdapter(Context context, Cursor cursor, int flags) {
    	super(context, cursor, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(
                      Context.LAYOUT_INFLATER_SERVICE);
    }

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
//		TextView tvDate = (TextView) view.findViewById(R.id.sdate);
//		TextView tvTitle = (TextView) view.findViewById(R.id.title);
//		TextView tvBoV = (TextView) view.findViewById(R.id.bov);
//		TextView tvURL = (TextView) view.findViewById(R.id.url);
//		ImageView ivFavicon = (ImageView) view.findViewById(R.id.favicon);
		
	   long date = cursor.getLong(2);
	   String title = cursor.getString(1);
		
//		long date = cursor.getLong(3);
//		String title = cursor.getString(5);
//	   String url = cursor.getString(1);
//	   byte[] icon = cursor.getBlob(6);
//	   String bokmrk = cursor.getString(4);
//	   String visits = cursor.getString(2);
//	   String id = cursor.getString(0);
	    
	 //  String names[] = cursor.getColumnNames();

	   
//	   tvDate.setText(new Date(date).toString());
////	   tvBoV.setText(bokmrk.equals("1")?"Bookmarked":"Visited");
//	   tvTitle.setText(title);
//	   tvURL.setText(url);
//	   if(icon != null){
//		   Bitmap bm = BitmapFactory.decodeByteArray(icon, 0, icon.length);
//		   ivFavicon.setImageBitmap(bm);
//	   }

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return cursorInflater.inflate(R.layout.link, parent, false);
	}

   
}

