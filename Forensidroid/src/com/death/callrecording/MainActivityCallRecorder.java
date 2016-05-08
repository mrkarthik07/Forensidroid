package com.death.callrecording;

import java.io.IOException;
import java.io.InputStream;

import com.example.badone.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivityCallRecorder extends Activity {

	public TextView mTextView;
	private static final int CATEGORY_DETAIL = 1;
	public RadioButton radEnable;

	private static Resources res;
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainnew);

		res = getResources();

		SharedPreferences settings = this.getSharedPreferences(
				Constants.LISTEN_ENABLED, 0);
		boolean silentMode = settings.getBoolean("silentMode", true);

		if (silentMode)
			showDialog(CATEGORY_DETAIL);

		//setSharedPreferences(false);
		context = this.getBaseContext();

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	public static String getDataFromRawFiles(int id) throws IOException {
		InputStream in_s = res.openRawResource(id);

		byte[] b = new byte[in_s.available()];
		in_s.read(b);
		String value = new String(b);

		return value;
	}

	/**
	 * checks if an external memory card is available
	 * 
	 * @return
	 */
	public static int updateExternalStorageState() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return Constants.MEDIA_MOUNTED;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return Constants.MEDIA_MOUNTED_READ_ONLY;
		} else {
			return Constants.NO_MEDIA;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_mainnew, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		SharedPreferences settings = this.getSharedPreferences(
				Constants.LISTEN_ENABLED, 0);
		boolean silentMode = settings.getBoolean("silentMode", true);

		MenuItem menuEnableRecord = menu.findItem(R.id.menu_Enable_record);

		menuEnableRecord.setEnabled(silentMode);

		return super.onPrepareOptionsMenu(menu);
	}

	private void setSharedPreferences(boolean silentMode) {
		SharedPreferences settings = this.getSharedPreferences(
				Constants.LISTEN_ENABLED, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("silentMode", silentMode);
		editor.commit();

		Intent myIntent = new Intent(context, RecordService.class);
		myIntent.putExtra("commandType",
				silentMode ? Constants.RECORDING_DISABLED
						: Constants.RECORDING_ENABLED);
		myIntent.putExtra("silentMode", silentMode);
		context.startService(myIntent);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case CATEGORY_DETAIL:
			LayoutInflater li = LayoutInflater.from(this);
			View categoryDetailView = li.inflate(
					R.layout.startup_dialog_layout, null);

			AlertDialog.Builder categoryDetailBuilder = new AlertDialog.Builder(
					this);
//			categoryDetailBuilder.setTitle(this
//					.getString(R.string.dialog_welcome_screen));
			categoryDetailBuilder.setView(categoryDetailView);
			AlertDialog categoryDetail = categoryDetailBuilder.create();

			categoryDetail.setButton2("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							setSharedPreferences(false);
						}
					});

			return categoryDetail;
		
		default:
			break;
		}
		return null;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case CATEGORY_DETAIL:
			AlertDialog categoryDetail = (AlertDialog) dialog;
			radEnable = (RadioButton) categoryDetail
					.findViewById(R.id.radio_Enable_record);
			radEnable.setChecked(true);
			break;
		default:
			break;
		}
		super.onPrepareDialog(id, dialog);
	}
}