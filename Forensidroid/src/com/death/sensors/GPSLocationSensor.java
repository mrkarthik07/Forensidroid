package com.death.sensors;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.death.logging.appendLog;
import com.death.sensors.R;

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


public class GPSLocationSensor extends Activity implements LocationListener{
	
	LocationManager locationManager ;
	String provider;
	TextView textView;
	private SensorValue address;
	String addressText;
	int numsat = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Getting LocationManager object
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);        
		provider = LocationManager.GPS_PROVIDER;
        if(provider!=null && !provider.equals("")){
        	// Get the location from the given provider 
            Location location = locationManager.getLastKnownLocation(provider);
            locationManager.requestLocationUpdates(provider, 0, 0, this);
                        
            if(location!=null)
            	onLocationChanged(location) ;
            else
            	Toast.makeText(getBaseContext(), "GPS Location can't be retrieved", Toast.LENGTH_SHORT).show();
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
		// Getting reference to TextView tv_longitude
		TextView tvLongitude = (TextView)findViewById(R.id.tv_longitude);
		TextView tvLatitude = (TextView)findViewById(R.id.tv_latitude);
		TextView tvAltitude = (TextView) findViewById(R.id.tv_altitude);
		TextView tvAccuracy = (TextView) findViewById(R.id.tv_accuracy);
		long now = Calendar.getInstance().getTimeInMillis();
		TextView tvSeconds = (TextView) findViewById(R.id.tv_seconds);
		
		// Setting Current Longitude
		tvLongitude.setText("Longitude:" + location.getLongitude());
		// Setting Current Latitude
		tvLatitude.setText("Latitude:" + location.getLatitude() );
		tvAltitude.setText("Altitude:" + location.getAltitude());
		tvAccuracy.setText("Accuracy:" + location.getAccuracy());
		tvSeconds.setText("Seconds ago:" + (now - location.getTime())/1000);
		
	//	textView.setText(String.format("Latitude = %s, Longitude = %s"+ ", Accuracy = %f" + ", %d seconds ago",	location.getLatitude(), location.getLongitude(),location.getAccuracy(), (now - location.getTime()) / 1000));
		
		//Writing it to a file
		String toBeLongi = tvLongitude.getText().toString();
		String toBeLati = tvLatitude.getText().toString();
		String toBeAcc = tvAccuracy.getText().toString();
		String toBeAlti = tvAltitude.getText().toString();
		String toBeSeci = tvSeconds.getText().toString();
					
		appendLog appendLog = new appendLog(toBeLongi, toBeLati, toBeAcc, toBeAlti, toBeSeci);
		
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