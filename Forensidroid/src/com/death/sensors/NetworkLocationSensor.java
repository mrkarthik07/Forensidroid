package com.death.sensors;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.GpsStatus.Listener;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class NetworkLocationSensor extends Activity implements LocationListener{
	LocationManager locationManager ;
	String provider;
	TextView textView;
	private SensorValue address;
	String addressText;
	int numsat = 0;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Getting LocationManager object
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);        
       // if (LocationManager.GPS_PROVIDER.equals(null)) {
        	provider = LocationManager.NETWORK_PROVIDER;
                        
        if(provider!=null && !provider.equals("")){
        	
        	// Get the location from the given provider 
            Location location = locationManager.getLastKnownLocation(provider);
                        
            locationManager.requestLocationUpdates(provider, 20000, 1, this);
            
            
            if(location!=null)
            	onLocationChanged(location) ;
            else
            	Toast.makeText(getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
            
        }else{
        	Toast.makeText(getBaseContext(), "No Provider Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_mainnew, menu);
        return true;
    }
    
	@Override
	public void onLocationChanged(Location location) {
		
		//GPS Value
		//provider = LocationManager.GPS_PROVIDER;
		
			TextView gpsLongitude = (TextView) findViewById(R.id.gpsLongitude);
			TextView gpsLatitude = (TextView) findViewById(R.id.gpsLatitude);
			TextView gpsAltitude = (TextView) findViewById(R.id.gpsAltitude);
			TextView gpsBearing = (TextView) findViewById(R.id.gpsBearing);
			TextView gpsSpeed = (TextView) findViewById(R.id.gpsSpeed);
			TextView gpsAccuracy = (TextView) findViewById(R.id.gpsAccuracy);
			findViewById(R.id.gpsSatellite);
			findViewById(R.id.gpsAddress);
		
		gpsLongitude.setText("Longitude = "+ location.getLongitude());
		gpsLatitude.setText("Latitude = "+ location.getLatitude());
		gpsAltitude.setText("Altitude = "+ location.getAltitude());
		gpsBearing.setText("Bearing = "+ location.getBearing());
		gpsSpeed.setText("Speed = "+ location.getSpeed());
		gpsAccuracy.setText("Accuracy = "+ location.getAccuracy());
		//gpsSatellite.setText("Satellite = " address.setValue(addressText));
		
		//Writing it to a file
				String toBeLongi = gpsLongitude.getText().toString();
				String toBeLati = gpsLatitude.getText().toString();
				String toBeAcc = gpsAccuracy.getText().toString();
				String toBeAlti = gpsAltitude.getText().toString();
				String toBeBear = gpsBearing.getText().toString();
				String toBeSpee = gpsSpeed.getText().toString();
				
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub		
	}
	
	public void test() {
		Location location = locationManager.getLastKnownLocation(provider);
		if(provider!=null && provider.equals("gps")){
        	
        	// Get the location from the given provider 
           
            locationManager.requestLocationUpdates(provider, 20000, 1, this);
                        
            if(location!=null)
            	onLocationChanged(location) ;
            else
            	Toast.makeText(getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
            
        }else{
        	Toast.makeText(getBaseContext(), "No Provider Found", Toast.LENGTH_SHORT).show();
        }
		
		Geocoder myLocation = new Geocoder(getBaseContext().getApplicationContext(), Locale.getDefault());
		List<Address> list = null;
		try {
			list = myLocation.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
			if (list != null && list.size() > 0) {
				Address location1 = list.get(0);
				addressText = String.format("%s, %s, %s",
						location1.getMaxAddressLineIndex() > 0 ? location1.getAddressLine(0) : "",
								location1.getLocality(), // location.getAdminArea(), 
								location1.getCountryName());
				
			}
			else
				address.setValue("n/a");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		new Listener() {
			
			@Override
			public void onGpsStatusChanged(int event) {
				if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS){
					GpsStatus gpsstatus = locationManager.getGpsStatus(null);
					Iterable<GpsSatellite> gpsit = gpsstatus.getSatellites();
					
					for(GpsSatellite sat: gpsit){
						numsat++;
					}
					
					//notifyListeners();
				}
				
			}
		};
		
	}

}
