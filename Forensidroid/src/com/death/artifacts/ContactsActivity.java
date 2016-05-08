package com.death.artifacts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.example.badone.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ContactsActivity extends Activity {
	TextView textDetail;
	String filepath = Environment.getExternalStorageDirectory().getAbsolutePath();
	File file = null;
	public static final String FILE_DIRECTORY = "ContactInfo";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		textDetail = (TextView) findViewById(R.id.textView1);
		
		readContacts();
	}

	public void readContacts() {
		ContentResolver cr = getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		String phone = null;
		String emailContact = null;
		String emailType = null;
		String image_uri = "";
		Bitmap bitmap = null;
		String id;
		String name;
		
		File folder = new File(filepath+File.separator+FILE_DIRECTORY);
		if (!folder.exists()) {
			folder.mkdir();
		}
		File file = new File(folder+File.separator+"ContactInfo.csv");
		if (!file.exists()) {
			try {
				file.createNewFile();
				FileOutputStream  writer = new FileOutputStream (file, true);
				writer.write("Name, Number, Email, Type".getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String word = "";
		
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
				name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

				image_uri = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
				
				word+=id+", "+name;
				if (Integer.parseInt(cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					
					FileOutputStream writer;
					try {
						writer = new FileOutputStream (file, true);
						try {
							writer.write("\n".getBytes());
							writer.write(name.getBytes());
							writer.write(",".getBytes());
						} catch (IOException e) {
							e.printStackTrace();
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					
					Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID
							+ " = ?", new String[] { id }, null);
					while (pCur.moveToNext()) {
						phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						word+=", "+phone;
						try {
							writer = new FileOutputStream (file, true);
							try {
								writer.write(phone.getBytes());
								writer.write(",".getBytes());
							} catch (IOException e) {
								e.printStackTrace();
							}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}
					pCur.close();

					Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
							null,ContactsContract.CommonDataKinds.Email.CONTACT_ID
							+ " = ?", new String[] { id }, null);
					while (emailCur.moveToNext()) {
						emailContact = emailCur.getString(emailCur
								.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
						emailType = emailCur.getString(emailCur
								.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
						word+=", "+emailContact+", "+emailType+"\n";
						try {
							writer = new FileOutputStream (file, true);
							try {
								writer.write(emailContact.getBytes());
								writer.write(",".getBytes());
								writer.write(emailType.getBytes());
								writer.write(",".getBytes());
							} catch (IOException e) {
								e.printStackTrace();
							}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}
					emailCur.close();
				}
    
				if (image_uri != null) {
					System.out.println(Uri.parse(image_uri));
					try {
						bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
								Uri.parse(image_uri));

					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}	
			}
		}
		textDetail.setText(word);
	}
}