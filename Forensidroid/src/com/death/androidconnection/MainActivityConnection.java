package com.death.androidconnection;

import com.example.badone.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivityConnection extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
    }

    public void getBluetoothActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), BluetoothActivity.class);
        startActivity(intent);
    }

    public void getWifiActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), WiFiActivity.class);
        startActivity(intent);
    }

    public void getNfcActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), NfcActivity.class);
        startActivity(intent);
    }

    public void getMobileDataActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), MobileDataActivity.class);
        startActivity(intent);
    }
}
