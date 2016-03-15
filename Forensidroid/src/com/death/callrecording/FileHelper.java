package com.death.callrecording;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.text.format.DateFormat;
import android.util.Log;

public class FileHelper {
	/**
	 * returns absolute file directory
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getFilename(String phoneNumber) throws Exception {
		String filepath = null;
		String myDate = null;
		File file = null;
		if (phoneNumber == null)
			throw new Exception("Phone number can't be empty");
		try {
			filepath = getFilePath();

			file = new File(filepath, Constants.FILE_DIRECTORY);

			if (!file.exists()) {
				file.mkdirs();
			}

			myDate = (String) DateFormat.format("yyyyMMddkkmmss", new Date());

			// Clean characters in file name
			phoneNumber = phoneNumber.replaceAll("[\\*\\+-]", "");
			if (phoneNumber.length() > 10) {
				phoneNumber.substring(phoneNumber.length() - 10,
						phoneNumber.length());
			}
		} catch (Exception e) {
			Log.e(Constants.TAG, "Exception " + phoneNumber);
			e.printStackTrace();
		}

		return (file.getAbsolutePath() + "/d" + myDate + "p" + phoneNumber + ".3gp");
	}

	public static String getFilePath() {
		// TODO: Change to user selected directory
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}
}