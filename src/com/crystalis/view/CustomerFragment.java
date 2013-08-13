package com.crystalis.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crystalis.adapters.CustomerAdapter;
import com.crystalis.adapters.ItemClientAdapter;
import com.crystalis.dataconnection.DataConnection;
import com.crystalis.dialog.CountryDialog;
import com.crystalis.dialog.RegionDialog;
import com.crystalis.getData.DownloadData;
import com.crystalis.interfaces.IMain;
import com.crystalis.listeners.AsyntaskCallBackListeners;
import com.crystalis.listeners.CountryDialogListener;
import com.crystalis.listeners.RegionDialogListener;
import com.crystalis.model.Customer;
import com.crystalis.model.DataManager;
import com.crystalis.sendData.IPost;
import com.crystalis.sendData.Post;
import com.crystalis.sendData.Put;
import com.crystalis.tools.ConnectionDetector;
import com.crystalis.tools.Parser;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CustomerFragment extends Fragment {

    private View viewMain;
    private String url;
    private Parser tools;
    private DownloadData download;
    private ListView clientesList;
    private CustomerAdapter adapter;
    private ArrayList<Customer> customersList;
    private ItemClientAdapter itemsAdapter;
    private ListView ItemsClientList;
    
    private EditText clienteId;
    private EditText nombre;
    private EditText calle;
    private TextView estado;
    private EditText cp;
    private TextView pais;
    private EditText rfc;
    private EditText telefono;
    private EditText email;
    private EditText numero;
    private EditText ciudad;
    private EditText movil;
    private ImageButton btn_nuevo;
    private ImageButton btn_aceptar;
    private ImageButton btn_update;
    private RelativeLayout row_cliente;
    private ImageButton btn_scan;
    
    private DataManager datamanager;
    private List<String> countryList;
    private List<String> regionList;
    private boolean scan;
    private String clientId;
    private ImageButton refresh;
    private ImageButton btn_mapa;
    private Bundle args;
    private ImageButton btn_pais;
    private ImageButton btn_state;
    private EditText distrito;
    private MapViewFragmentv2 mapa;
	private Geocoder geo;
	private List<Address> addresses;
	private Marker marker;
	private GoogleMap myMap;
	private int ZOOM = 18;

    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	viewMain = inflater.inflate(R.layout.clientes, container, false);
	
	setUpMapIfNeeded();
	
	countryList = new ArrayList<String>();
	regionList = new ArrayList<String>();

    	clientesList 	= (ListView) viewMain.findViewById(R.id.clientes_lista_clientes);
    	ItemsClientList = (ListView) viewMain.findViewById(R.id.clientes_detalle_pedidos);
    	
        refresh		= (ImageButton) viewMain.findViewById(R.id.clientes_btn_refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		
		callCustomers();
		
	    }
	});
    	
    	row_cliente = (RelativeLayout) viewMain.findViewById(R.id.clientes_row_cliente);
    		
        clienteId    	= (EditText) viewMain.findViewById(R.id.clientes_idcliente);
        nombre		= (EditText) viewMain.findViewById(R.id.clientes_nombre_cliente) ;
        calle		= (EditText) viewMain.findViewById(R.id.clientes_calle) ;
        numero		= (EditText) viewMain.findViewById(R.id.clientes_numero) ;
        ciudad		= (EditText) viewMain.findViewById(R.id.clientes_ciudad) ;
        distrito	= (EditText) viewMain.findViewById(R.id.clientes_distrito) ;
        estado		= (TextView) viewMain.findViewById(R.id.clientes_estado) ;
        cp		= (EditText) viewMain.findViewById(R.id.clientes_cp) ;
        pais		= (TextView) viewMain.findViewById(R.id.clientes_pais) ;

        rfc		= (EditText) viewMain.findViewById(R.id.clientes_rfc) ;
        telefono	= (EditText) viewMain.findViewById(R.id.clientes_telefono);
        movil		= (EditText) viewMain.findViewById(R.id.clientes_movil);
        email		= (EditText) viewMain.findViewById(R.id.clientes_email) ;
        btn_pais 	=(ImageButton) viewMain.findViewById(R.id.clientes_btn_pais);
        btn_pais.setOnClickListener(new View.OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		
		if(countryList.isEmpty())
		    callCountries();
		else
		    showCountryDialog();

	    }
	});
        
        btn_state	= (ImageButton) viewMain.findViewById(R.id.clientes_btn_state);
        btn_state.setOnClickListener(new View.OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		
		if(!pais.getText().toString().trim().isEmpty()){
		if(regionList.isEmpty())
		    callRegion(pais.getText().toString().trim());
		else
		    showRegionDialog();
		} else
		    Toast.makeText(getActivity(), "Seleccione un pais.", Toast.LENGTH_SHORT).show();
		
	    }
	});
        
        btn_scan = (ImageButton) viewMain.findViewById(R.id.clientes_btn_scan);
        btn_scan.setOnClickListener(new OnClickListener() {
	    

	    @Override
	    public void onClick(View v) {
	
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
	        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
	        startActivityForResult(intent, 0);
	        
	    }
	});
        
        btn_update = (ImageButton) viewMain.findViewById(R.id.clientes_btn_actualizar);
        btn_update.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {

		if ( !btn_nuevo.isSelected() ) {
		    if ( !btn_update.isSelected() )
			btn_actualizar_seleccionado();			
		    else if ( btn_update.isSelected() )
			btn_actualizar_no_seleccionado();
		}
	    }
	});
        
        btn_nuevo = (ImageButton) viewMain.findViewById(R.id.clientes_btn_nuevo);
        btn_nuevo.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		
		if ( !btn_update.isSelected() ) {
		    if ( !btn_nuevo.isSelected() )
			btn_nuevo_seleccionado();
		    else if ( btn_nuevo.isSelected() )
			btn_nuevo_no_seleccionado();
		}
		
	    }
	});
        
        btn_aceptar = (ImageButton) viewMain.findViewById(R.id.clientes_btn_aceptar);
        btn_aceptar.setVisibility(View.INVISIBLE);
        btn_aceptar.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		IPost data = new IPost();
		
		if (btn_nuevo.isSelected()){
		    
	            if( rfc.getText().toString().isEmpty() ) rfc.setText(IMain.RFC);

	        	    
		    if( validaCaptura() ){
			
			String postXml  =  data.HeaderXML +
				       data.startEntryCustomer + 
				       data.postidCustumer +
				       data.posttitleCustumer +
				       data.categoryCustumer +
				       data.setCustomerPOST( nombre.getText().toString().trim(), calle.getText().toString().trim(), numero.getText().toString().trim(), 
					       ciudad.getText().toString().trim(), distrito.getText().toString().trim(), estado.getText().toString().trim(), cp.getText().toString().trim(), 
					       pais.getText().toString().trim(), rfc.getText().toString().trim(), telefono.getText().toString().trim(), 
					       movil.getText().toString().trim(), email.getText().toString().trim() ) +
				       data.endEntryCustumer;
			
		    	new Post(getActivity(), tools.updateUrl(DataConnection.CUSTOMER_GETWAY_XML_POST,url), DataConnection.user, DataConnection.pwd, postXml, " Enviando ... ", new AsyntaskCallBackListeners() {
				
				@Override
				public void onTaskDone(String data) {
				    
				if(IMain.DEBUG)
				    System.out.println("postData:onTaskDone:response: "+data);
				
				if( Integer.parseInt(data) == IMain.STATUS_CODE_201 ){

					btn_nuevo_no_seleccionado();
					Toast.makeText(getActivity(), "Envio exitoso.", Toast.LENGTH_SHORT).show();
					clear();
					callCustomers();

				}
					
				}
			}).execute();
		    	
		    } else
			Toast.makeText(getActivity(), "Informacion incompleta.", Toast.LENGTH_SHORT).show();
		        
		}
		
		if (btn_update.isSelected()){
		    
			String urlput = tools.updateUrl(DataConnection.CUSTOMER_GETWAY_XML_PUT,url);
			urlput = urlput.replace("||", clienteId.getText().toString().trim() );
			
			String putXml  =  data.HeaderXML +
				       data.startEntryCustomer + 
				       data.putIdCustomer     .replace( "|", clienteId.getText().toString().trim() ) +
				       data.putTitleCustomer  .replace( "|", clienteId.getText().toString().trim() ) +
				       data.putUpdatedCustomer.replace( "$", "2013-07-17T23:34:14Z" ) +
				       data.categoryCustumer   +
				       data.putLinkCustomer   .replace( "|", clienteId.getText().toString().trim() ) +
				       data.setCustomerPUT( clienteId.getText().toString().trim(), nombre.getText().toString().trim(), calle.getText().toString().trim(), 
					                 numero.getText().toString().trim(), ciudad.getText().toString().trim(), distrito.getText().toString().trim(),
					                 estado.getText().toString().trim(), cp.getText().toString().trim(), pais.getText().toString().trim(),
					                 rfc.getText().toString().trim(), telefono.getText().toString().trim(), movil.getText().toString().trim(),
					                 email.getText().toString().trim() ) +
				       data.endEntryCustumer;

			new Put(getActivity(), urlput , DataConnection.user, DataConnection.pwd, putXml, " Enviando ... ", new AsyntaskCallBackListeners() {
				
				@Override
				public void onTaskDone(String data) {
				    
				if(IMain.DEBUG)
				    System.out.println("putData:onTaskDone:response: "+data);
				
				if( Integer.parseInt(data) == IMain.STATUS_CODE_204 ){

					btn_actualizar_no_seleccionado();
					Toast.makeText(getActivity(), "Envio exitoso.", Toast.LENGTH_SHORT).show();
					clear();
					callCustomers();

				}
					
				}
			}).execute();
			
		}

		   
	    }
	});
        
//        btn_mapa = (ImageButton) viewMain.findViewById(R.id.clientes_btn_mapa);
//        btn_mapa.setOnClickListener(new View.OnClickListener() {
//	    
//	    @Override
//	    public void onClick(View v) {
//
//		args.putString(IMain.DIRECCION, calle.getText().toString()+","+ciudad.getText().toString()+","+estado.getText().toString()+","+pais.getText().toString());
//		Intent i = new Intent(getActivity(), MapViewFragment.class);
//		i.putExtra(IMain.DIRECCION,  calle.getText().toString()+","+ciudad.getText().toString()+","+estado.getText().toString()+","+pais.getText().toString());
//		startActivity(i); // v1
//
//	    }
//	});
        
        desable();
        
	return viewMain;
    }

    public void btn_nuevo_seleccionado() {
	
	btn_nuevo.setSelected(true);
	btn_nuevo.setImageResource(R.drawable.img_nuevo);
	row_cliente.setVisibility(View.INVISIBLE);
	clear();
	enable();
        btn_aceptar.setVisibility(View.VISIBLE);
    }
    
    
    public void btn_nuevo_no_seleccionado() {
    	clear();
	btn_nuevo.setSelected(false);
	btn_nuevo.setImageResource(R.drawable.img_nuevo_gray);
	row_cliente.setVisibility(View.VISIBLE);

	desable();
	btn_aceptar.setVisibility(View.INVISIBLE);
     	    	
     }
    
    public void btn_actualizar_seleccionado() {
	
	btn_update.setSelected(true);
	btn_update.setImageResource(R.drawable.img_actualizar);
	enable();
        btn_aceptar.setVisibility(View.VISIBLE);
    }
    
    
    public void btn_actualizar_no_seleccionado() {
	
	btn_update.setSelected(false);
	btn_update.setImageResource(R.drawable.img_actualizar_gray);
	desable();
	btn_aceptar.setVisibility(View.INVISIBLE);
     	    	
     }
    
    private void desable() {
	
        clienteId.setEnabled(false);
        nombre.setEnabled(false);
        calle.setEnabled(false);
        numero.setEnabled(false);
        ciudad.setEnabled(false);
        distrito.setEnabled(false);
        cp.setEnabled(false);
        btn_pais.setEnabled(false);
        btn_state.setEnabled(false);
        rfc.setEnabled(false);
        telefono.setEnabled(false);
        movil.setEnabled(false);
        email.setEnabled(false);
        
    }
    
    private void enable() {
	
        nombre.setEnabled(true);
        calle.setEnabled(true);
        numero.setEnabled(true);
        ciudad.setEnabled(true);
        distrito.setEnabled(true);
        cp.setEnabled(true);
        btn_pais.setEnabled(true);
        btn_state.setEnabled(true);
        rfc.setEnabled(true);
        telefono.setEnabled(true);
        movil.setEnabled(true);
        email.setEnabled(true);
        
    }
    
    private void clear() {
	
        nombre.setText("");
        calle.setText("");
        numero.setText("");
        ciudad.setText("");
        distrito.setText("");
        estado.setText("");
        cp.setText("");
        pais.setText("");
        rfc.setText("");
        telefono.setText("");
        movil.setText("");
        email.setText("");
        
    }
    
    private boolean validaCaptura() {
	
        if( !nombre.getText().toString().isEmpty() &&
            !calle.getText().toString().isEmpty()  &&
            !numero.getText().toString().isEmpty() &&
            !ciudad.getText().toString().isEmpty() &&
            !estado.getText().toString().isEmpty() &&
            !cp.getText().toString().isEmpty() 	   &&
            !pais.getText().toString().isEmpty() )  
            
            return true;
        
        
        return false;
        
    }
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	tools = new Parser();
	args = new Bundle();
	
    	url = getArguments().getString(IMain.URL_KEY);
    	scan = getArguments().getBoolean("scan");
    	clientId = getArguments().getString("clienteId");
    	datamanager = DataManager.getInstance();
        geo = new Geocoder(getActivity(),Locale.getDefault());
        

    	System.out.println("scan: "+ scan);
    	System.out.println("clientId: "+ clientId);
    	System.out.println("getArguments: "+ getArguments());

    	if(!scan)
    	    callCustomers();
    	else
    	    callCustomer(clientId);
    }
    
    public void callCustomers(){

    	download = new DownloadData(getActivity(), tools.updateUrl( DataConnection.CUSTOMERS_GATEWAY_JSON, url ), DataConnection.user, DataConnection.pwd, " Cargando Clientes ... ", new AsyntaskCallBackListeners() {
			
			@Override
			public void onTaskDone(String data) {
				dataCustomers(data,false);
				
			}
		});
    	download.execute();
    }
    
    public void callCustomer(String clientId){

	String custumerUrl = tools.updateUrl( DataConnection.FILTRO_CUSTUMERS_CUSTOMERID_JSON, url );
	custumerUrl = custumerUrl.replace("|", clientId);
	
    	download = new DownloadData(getActivity(), custumerUrl, DataConnection.user, DataConnection.pwd, " Cargando Cliente ... ", new AsyntaskCallBackListeners() {
			
			@Override
			public void onTaskDone(String data) {
			    
			    dataCustomers(data, true);
				
			}
		});
    	download.execute();
    	
    	filterXClienteId(clientId);
    	
    }

    
    
    public void callCountries(){

    	download = new DownloadData(getActivity(), tools.updateUrl( DataConnection.COUNTRY_GATEWAY_JSON, url ), DataConnection.user, DataConnection.pwd, IMain.TXT_CARGANDO+" "+IMain.TXT_PAISES+IMain.TXT_DOTS, new AsyntaskCallBackListeners() {
			
			@Override
			public void onTaskDone(String data) {
			    
			    countryList = tools.getLabelsCountry(data);
			    datamanager.setCountryList(countryList);
			    showCountryDialog();

			}
		});
    	download.execute();
    }
    
    public void callRegion(String country){
	
	String regionUrl = tools.updateUrl( DataConnection.REGION_GATEWAY_JSON, url );
	regionUrl = regionUrl.replace("|", country);

    	download = new DownloadData(getActivity(), regionUrl, DataConnection.user, DataConnection.pwd, IMain.TXT_CARGANDO+" "+IMain.TXT_PAISES+IMain.TXT_DOTS, new AsyntaskCallBackListeners() {
			
			@Override
			public void onTaskDone(String data) {
			    
			    regionList = tools.getLabelsRegion(data);
			    datamanager.setRegionList(regionList);
			    showRegionDialog();

			}
		});
    	download.execute();
    }

    protected void showRegionDialog() {

	DialogFragment regiondialog = new RegionDialog( new RegionDialogListener() {
	    
	    @Override
	    public void onDialogPositiveClick(DialogFragment dialog) { }
	    
	    @Override
	    public void onDialogNegativeClick(DialogFragment dialog) { }
	    
	    @Override
	    public void onDialogItemSelectedClick(String item) {
	    	if(IMain.DEBUG)
	    		System.out.println("data : items: "+ item);
	    	
		estado.setText(item);			
	    }
	},regionList);
	
	regiondialog.show(getFragmentManager(), "RegionDialog");

    }

    public void showCountryDialog() {

	DialogFragment countrydialog = new CountryDialog(new CountryDialogListener() {
	    
	    @Override
	    public void onDialogPositiveClick(DialogFragment dialog) { }
	    
	    @Override
	    public void onDialogNegativeClick(DialogFragment dialog) { }

	    @Override
	    public void onDialogItemSelectedClick(String item) {

	    	if(IMain.DEBUG)
	    		System.out.println("data : items: "+ item);	

		pais.setText(item);
	    }
	}, countryList);
	countrydialog.show(getFragmentManager(), "CountryDialog");
    }
    

    public void dataCustomers(String data,boolean filter) {
	
	customersList = new ArrayList<Customer>();
	
	if(!filter)
	    customersList = tools.parserCustomersJson(data);
	else
	    customersList = tools.filterCustumerJson(data);
	
	adapter = new CustomerAdapter(customersList, getActivity());
	clientesList.setAdapter(adapter);
	clientesList.setOnItemClickListener(new OnItemClickListener() {

		@Override
	    public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		
	        clienteId.setText ( customersList.get(position).getCustomerid() );
	        nombre.setText	  ( customersList.get(position).getName() 	);
	        calle.setText	  ( customersList.get(position).getStreet() 	);
	        numero.setText	  ( customersList.get(position).getNumber() 	);
	        ciudad.setText	  ( customersList.get(position).getCity() 	);
	        distrito.setText  ( customersList.get(position).getDistrict()	);
	        estado.setText    ( customersList.get(position).getState() 	);
	        cp.setText        ( customersList.get(position).getZip()	);
	        pais.setText      ( customersList.get(position).getCountry()	);
	        rfc.setText       ( customersList.get(position).getRfc()	);
	        telefono.setText  ( customersList.get(position).getTel()	);
	        movil.setText 	  ( customersList.get(position).getTel()	);
	        email.setText     ( customersList.get(position).getEmail()	);
	        
//	        mapa.setPoint(calle.getText().toString()+","+ciudad.getText().toString()+","+estado.getText().toString()+","+pais.getText().toString());
	        
	        setPoint(calle.getText().toString()+","+ciudad.getText().toString()+","+estado.getText().toString()+","+pais.getText().toString());
//	        mapa.setArguments(args);
	        
//			args.putString(IMain.DIRECCION, calle.getText().toString()+","+ciudad.getText().toString()+","+estado.getText().toString()+","+pais.getText().toString());
//			Intent i = new Intent(getActivity(), MapViewFragment.class);
//			i.putExtra(IMain.DIRECCION,  calle.getText().toString()+","+ciudad.getText().toString()+","+estado.getText().toString()+","+pais.getText().toString());
//			startActivity(i); // v1
	        

	        if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
		    filterXClienteId(((TextView) view.findViewById(R.id.clientes_nocliente_row)).getText().toString());
		} else
		    Toast.makeText(getActivity(), IMain.MSG_ERROR_CONEXION,Toast.LENGTH_SHORT).show();
	    }
	});
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
    
    private void setUpMapIfNeeded() {
        if (myMap == null){
            myMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            System.out.println(" == null ");

          if (myMap != null){ 
                setUpMap();
               System.out.println(" != null ");
          }
        }

    }
    
    
    private void killOldMap() {
        SupportMapFragment mapFragment = ((SupportMapFragment) getActivity()
                .getSupportFragmentManager().findFragmentById(R.id.map));

        if(mapFragment != null) {
            FragmentManager fM = getFragmentManager();
            fM.beginTransaction().remove(mapFragment).commit();
        }

    }
     @Override
    public void onDestroyView() {
    	// TODO Auto-generated method stub
    	super.onDestroyView();
    	killOldMap();
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
	                  
			if (IMain.DEBUG) {
				System.out.println("latitud: " + lat);
				System.out.println("longitud: " + lng);
			}
	    LatLng latlng = new LatLng(lat, lng);
	    marker = myMap.addMarker(new MarkerOptions().position(latlng).title("Actual"));
	    myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, ZOOM ));
	                  
	   }
   }
    

    public void filterXClienteId(String clienteId) {

	String strReplace = tools.replacechr("|", DataConnection.FILTRO_HEADER_CUSTOMERID , clienteId);

    	download = new DownloadData(getActivity(), tools.updateUrl(strReplace,url) , DataConnection.user, DataConnection.pwd, " Cargando detalles ... ", new AsyntaskCallBackListeners() {
			
			@Override
			public void onTaskDone(String data) {
		    	
			    items(data);
				
			}
		});
    	download.execute();
    	
    }

    protected void items(String data) {
    	
	    	if(IMain.DEBUG)
	    		System.out.println("data : items"+ data);

	    	itemsAdapter = new ItemClientAdapter(tools.HeadersJson(data), getActivity());
	    	itemsAdapter.notifyDataSetChanged();
	    	ItemsClientList.setAdapter( itemsAdapter );

    }
}
