package com.crystalis.view;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.crystalis.interfaces.IMain;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewFragment extends FragmentActivity {

    private GoogleMap myMap;

    private Geocoder geo;
    private List<Address> addresses;
    private Marker marker;

    private Bundle extras;
    
    private final int ZOOM = 18;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment);
        
        geo = new Geocoder(getApplicationContext(),Locale.getDefault());
	extras = getIntent().getExtras();

   }
    
    public void  setPoint(String direccion) {
	if(IMain.DEBUG)
	    System.out.println("direccion: "+direccion.trim());
	try {
	    addresses = geo.getFromLocationName(direccion.trim(), 5);
	} catch (IOException e) {

	}   
	
	if (addresses != null && addresses.size() > 0) {
	    
	    System.out.println("addresses:" + addresses.toString());
	    Address address = addresses.get(0);
	    double lat = address.getLatitude();
	    double lng = address.getLongitude();
	                  
	                  if(IMain.DEBUG){
	          	    System.out.println("latitud: "+lat);
	          	    System.out.println("longitud: "+lng);
	                  }
	    LatLng latlng = new LatLng(lat, lng);
	    marker = myMap.addMarker(new MarkerOptions().position(latlng).title("Actual"));
	    myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, ZOOM));
	                  
	   }
   }
      
    private void setUpMapIfNeeded() {
        if (myMap == null){
            myMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            System.out.println(" == null ");

          if (myMap != null){ 
                setUpMap();
        	if(extras != null)   { 
        	    setPoint ( extras.getString(IMain.DIRECCION) );
        	}
                System.out.println(" != null ");
          }
        }

    }
     
    private void setUpMap() {

	myMap.getUiSettings().setCompassEnabled(false);
	myMap.getUiSettings().setRotateGesturesEnabled(false);
	myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	myMap.getUiSettings().setTiltGesturesEnabled(false);
    			
	myMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			
			public void onInfoWindowClick(Marker mark) {


			}
		});
    }
    

    @Override
    public void onResume() {
     // TODO Auto-generated method stub
     super.onResume();
     setUpMapIfNeeded();
     
    }
}
