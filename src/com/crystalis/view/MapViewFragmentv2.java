package com.crystalis.view;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crystalis.interfaces.IMain;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewFragmentv2 extends Fragment {

    private GoogleMap myMap;

    private Geocoder geo;
    private List<Address> addresses;
    private Marker marker;

    private Bundle extras;
    
    private final int ZOOM = 18;

	private String address;
    
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	if(IMain.DEBUG)
    	    System.out.println(" onCreateView ");
    	
    	View v = inflater.inflate(R.layout.map_fragmentv2, null, false);
    	setUpMapIfNeeded();

    	return v;
    }
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	if(IMain.DEBUG)
    	    System.out.println(" onCreate ");
    	
//        geo = new Geocoder(getActivity(),Locale.getDefault());
//        extras = getIntent().getExtras();

   }
    
    public void  setPoint(String direccion) {
	if(IMain.DEBUG)
	    System.out.println(" setPoint: direccion: "+direccion.trim());
	try {
	    addresses = geo.getFromLocationName(direccion.trim(), 5);
	} catch (IOException e) {
		System.out.println(" IOException e: "+ e.toString());

	}   
	
	if (addresses != null && addresses.size() > 0) {
	    
		if(IMain.DEBUG)
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
            myMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            System.out.println(" == null ");

          if (myMap != null){ 
                setUpMap();
        	if(extras != null)   { 
//        	    setPoint ( extras.getString(IMain.DIRECCION) );
//        		setPoint ( addresses );
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
