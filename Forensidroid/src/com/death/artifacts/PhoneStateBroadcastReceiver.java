package com.death.artifacts;

import android.util.Log;

import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class PhoneStateBroadcastReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
        	 Set<String> keys = intentExtras.keySet();
        	 for (String key : keys) {
                 Log.i("MYAPP##", key + "="+ intentExtras.getString(key));
                 if(intentExtras.getString(key).equals("IDLE"))
                 {
                	 Toast.makeText(context, (CharSequence) keys, Toast.LENGTH_SHORT).show();
                 }
        	 }
            
            //this will update the UI with message
        	 CallLogActivity inst = CallLogActivity.instance();
        	 //inst.updateList(Set); 
        }
    }   
}