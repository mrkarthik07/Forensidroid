package com.death.androidconnection;

import com.example.badone.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MobileDataActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_data);
    }

    public void onToggleClicked(View view) {
        Intent intent = new Intent();
        intent.setClassName("com.android.settings",
                "com.android.settings.Settings$DataUsageSummaryActivity");
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                toggleButton.setChecked(networkInfo.isConnected());
                Toast.makeText(getApplicationContext(), networkInfo.getTypeName(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
