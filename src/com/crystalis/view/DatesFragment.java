package com.crystalis.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.crystalis.adapters.DatesAdapter;
import com.crystalis.dataconnection.DataConnection;
import com.crystalis.dialog.DateDialog;
import com.crystalis.dialog.TimeDialog;
import com.crystalis.getData.DownloadData;
import com.crystalis.interfaces.IMain;
import com.crystalis.listeners.AsyntaskCallBackListeners;
import com.crystalis.model.DatesModel;
import com.crystalis.sendData.IPost;
import com.crystalis.sendData.Post;
import com.crystalis.sendData.Put;
import com.crystalis.tools.Parser;
 

public class DatesFragment extends Fragment {
	
	private DownloadData download;
	private Parser tools;
	private ListView Dates;
	private CalendarView calendario;
	
	private ImageButton btn_actualizar;
	private ImageButton btn_nuevo;
	private ImageButton btn_aceptar;
	private ImageButton btn_acept_estatus;
	private ImageButton btn_lapiz;
	
	private Spinner spiner_estatus;
	private Spinner clienteSpinner;
	
	private TextView txt_estatus;
	private TextView txt_view_estatus;
	
	private List<String> clientList;
	
	private DatesAdapter datesAdapter;
	private ArrayAdapter<String> clientesArrayAdapter;

	private View viewMain;
	
	private String url;
	private String clienteSelect = "";
	private EditText cliente,asunto, lugar;
	private TextView horainicio;
	private TextView horafin;
	
	public String fechaFilter = "00000000";
	public String fechaToSend    = "00000000";
	private ImageButton btn_hora_inicio;

	private ImageButton btn_hora_fin;
	private TextView fecha;
	private SimpleDateFormat dateFormat;
	private ImageButton btn_fecha;

	private String horaInicio;
	private String horaFin;

	private String fechaInit;


	
	public String getHoraInicio() {
	    return horaInicio;
	}


	public void setHoraInicio(String horaInicio) {
	    this.horaInicio = horaInicio;
	}


	public String getHoraFin() {
	    return horaFin;
	}


	public void setHoraFin(String horaFin) {
	    this.horaFin = horaFin;
	}


	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    	viewMain = inflater.inflate(R.layout.citas, container, false);
    	
    	Dates 		= (ListView) viewMain.findViewById(R.id.citas_detalle_citas);    	
    	cliente 	= (EditText) viewMain.findViewById(R.id.citas_txt_cliente);
    	fecha		= (TextView) viewMain.findViewById(R.id.citas_fecha);
    	horainicio 	= (TextView) viewMain.findViewById(R.id.citas_txt_horainicio);
    	horafin 	= (TextView) viewMain.findViewById(R.id.citas_txt_horafin);
    	asunto 		= (EditText) viewMain.findViewById(R.id.citas_txt_asunto);
    	lugar 		= (EditText) viewMain.findViewById(R.id.citas_txt_lugar);
    	
    	
    	fecha.setText( tools.convertDate(dateFormat.format(new Date()), IMain.FORMAT_DD_MM_YYYY, IMain.FORMAT_YYYYMMDD) );

    	btn_fecha = (ImageButton) viewMain.findViewById(R.id.citas_btn_fecha);
    	btn_fecha.setOnClickListener(new View.OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {

		   DateDialog newFragment = new DateDialog();
		   newFragment.setCallBack(ondate);
		   newFragment.show(getFragmentManager(), "datePickerCitas");

	    }
	});
    	
    	
    	btn_hora_inicio = (ImageButton) viewMain.findViewById(R.id.citas_btn_hora_inicio);
    	btn_hora_inicio.setOnClickListener(new View.OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		showTimeDialogInicio();		
	    }
	});
    	
    	btn_hora_fin = (ImageButton) viewMain.findViewById(R.id.citas_btn_hora_fin);
    	btn_hora_fin.setOnClickListener(new View.OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		showTimeDialogFin();		
		
	    }
	});
    	
    	btn_aceptar 	= (ImageButton) viewMain.findViewById(R.id.citas_btn_alta);
    	btn_aceptar.setVisibility(View.INVISIBLE);
    	
    	btn_acept_estatus 	= (ImageButton) viewMain.findViewById(R.id.citas_btn_acept_estatus);
    	btn_acept_estatus.setVisibility(View.INVISIBLE);
    	
    	btn_lapiz 	= (ImageButton) viewMain.findViewById(R.id.citas_btn_estatus);
    	btn_lapiz.setVisibility(View.INVISIBLE);
    	
    	spiner_estatus	= (Spinner) viewMain.findViewById(R.id.citas_spiner_estatus);
    	spiner_estatus.setVisibility(View.INVISIBLE);
    	
    	clienteSpinner	= (Spinner) viewMain.findViewById(R.id.citas_clientes_spinner);
    	clienteSpinner.setVisibility(View.INVISIBLE);
    	
    	txt_estatus	= (TextView) viewMain.findViewById(R.id.citas_txt_estatus);
    	
    	
    	txt_view_estatus	= (TextView) viewMain.findViewById(R.id.citas_txt_view_estatus);
    	txt_view_estatus.setVisibility(View.INVISIBLE);
    	
    	String []status={"Pendiente por confirmar","Confirmado","Cancelado","Hecho"};        
	     ArrayAdapter<String> adapterestatus = new ArrayAdapter<String>(getActivity(),
	    	android.R.layout.simple_spinner_item, status);
	     spiner_estatus.setAdapter(adapterestatus);
	     
	     clientList = new ArrayList<String>();
	     	     
    	
    	
    	btn_nuevo 	= (ImageButton) viewMain.findViewById(R.id.citas_btn_nuevo);	
    	btn_nuevo.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
					if(!btn_nuevo.isSelected() && !btn_actualizar.isSelected()){
						if(!btn_nuevo.isSelected()){
							loadClients();
							boton_nuevo_seleccionado();
						}	
					}
			
				
				else if (btn_nuevo.isSelected()) {
					boton_nuevo_no_seleccionado();
				}
				
			}
		});


    	btn_actualizar 	= (ImageButton) viewMain.findViewById(R.id.citas_btn_actualizar);
    	btn_actualizar.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
					if(!btn_actualizar.isSelected() && !btn_nuevo.isSelected()){
						if(!btn_actualizar.isSelected()){
				    		
							boton_actualizar_seleccionado();
						}
				}
				else if (btn_actualizar.isSelected()){
					boton_actualizar_no_seleccionado();					
				}
			}
		});
    	    	
    	disabled();
    	filterXdates();
    	
        return viewMain;       
    }
 
    OnDateSetListener ondate = new OnDateSetListener() {
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {

	    fecha.setText( tools.fixDate_DD_MM_YYYY(year, monthOfYear, dayOfMonth) );
	    fechaToSend =  tools.fixDate_YYYY_MM_DD(year, monthOfYear, dayOfMonth);

	    
	}
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setHasOptionsMenu(true);
    	
    	tools = new Parser();
    	url = getArguments().getString(IMain.URL_KEY);
    	dateFormat = new SimpleDateFormat(IMain.formatsDateTime[IMain.FORMAT_YYYYMMDD]);

    	initialDates();
    	
    	callDates(fechaFilter);

    }
    

    public void initialDates(){

    	fechaFilter = tools.convertDate(dateFormat.format(new Date()), IMain.FORMAT_YYYYMMDD, IMain.FORMAT_YYYYMMDD );
    	fechaToSend    = tools.convertDate(dateFormat.format(new Date()), IMain.FORMAT_YYYY_MM_DD, IMain.FORMAT_YYYYMMDD);
    	fechaInit = fechaToSend;
    	    	
    	if(IMain.DEBUG)
    	    System.out.println( "Date: fechaFilter: " + fechaFilter + " fechaPut: " + fechaToSend);
     }
    
    public void callDates(String fecha){
    	
    	String strReplace = tools.replacechr("|", DataConnection.FILTROxCITAS ,fecha);
    	download = new DownloadData(getActivity(), tools.updateUrl(strReplace, url ), DataConnection.user, DataConnection.pwd, "Cargando Citas ... ", new AsyntaskCallBackListeners() {
			
			@Override
			public void onTaskDone(String data) {
				dataDates(data);
				
			}
		});
    	download.execute();
    }
    
    private void dataDates(String data) {
	
	ArrayList<DatesModel> dates = new ArrayList<DatesModel>();
	
	dates = tools.parserDatesJson(data);

	datesAdapter = new DatesAdapter(dates, getActivity());

	Dates.setAdapter( datesAdapter );
	Dates.setOnItemClickListener(new OnItemClickListener() {

			@Override 
			public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {
				
		    	cliente.setText  ( ((TextView) view.findViewById(R.id.citas_cliente_row)	 ).getText().toString() );
		    	horainicio.setText	( ((TextView) view.findViewById(R.id.citas_horai_row)  ).getText().toString() );
		    	setHoraInicio(horainicio.getText().toString().trim());
		    	horafin.setText	( ((TextView) view.findViewById(R.id.citas_horaf_row)  ).getText().toString() );
		    	setHoraFin(horafin.getText().toString().trim());
		    	asunto.setText	( ((TextView) view.findViewById(R.id.citas_asunto_row)  ).getText().toString() );
		    	lugar.setText	( ((TextView) view.findViewById(R.id.citas_lugar_row)  ).getText().toString() );
		    	txt_estatus.setText	( ((TextView) view.findViewById(R.id.citas_estatus_row)  ).getText().toString() );
		    	
		    	
			}

		});
    }
    
    public void cleardates(){
    	
    	cliente.setText  ("");
    	horainicio.setText	("");
    	horafin.setText	("");
    	asunto.setText	("");
    	lugar.setText	("");
    	txt_estatus.setText("");
    }
    
    public void disabled(){
    	cliente.setEnabled(false);
    	horainicio.setEnabled(false);
    	horafin.setEnabled(false);
    	asunto.setEnabled(false);
    	lugar.setEnabled(false);
    }
    
    public void enabled(){
    	cliente.setEnabled(false);
    	horainicio.setEnabled(true);
    	horafin.setEnabled(true);
    	asunto.setEnabled(true);
    	lugar.setEnabled(true);
    }  
    
    private boolean validaCaptura() {
    	
        if(	!cliente.getText().toString().isEmpty() &&
        	!horainicio.getText().toString().isEmpty() &&
            !horafin.getText().toString().isEmpty()  &&
            !asunto.getText().toString().isEmpty() &&
            !lugar.getText().toString().isEmpty() )  
            
            return true;
        
        
        return false;
        
    }
    
 public void boton_nuevo_seleccionado(){
    	
    	clienteSpinner.setVisibility(View.VISIBLE);
		clienteSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
			
			clienteSelect = clienteSpinner.getItemAtPosition(position).toString();
			cliente.setText(clienteSelect);
			
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> arg0) {
			
		    }
		});
		cleardates();
		enabled();
		btn_nuevo.setImageResource(R.drawable.img_nuevo);
		btn_nuevo.setSelected(true);
		
		btn_lapiz.setVisibility(View.VISIBLE);
		btn_lapiz.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!btn_lapiz.isSelected()){
				btn_lapiz.setVisibility(View.INVISIBLE);
				btn_lapiz.setSelected(true);
				btn_acept_estatus.setVisibility(View.VISIBLE);
				spiner_estatus.setVisibility(View.VISIBLE);
				txt_view_estatus.setVisibility(View.VISIBLE);
				
				}
				
			}	
		});
		
		btn_acept_estatus.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				btn_acept_estatus.setVisibility(View.INVISIBLE);
				btn_lapiz.setVisibility(View.VISIBLE);
				btn_lapiz.setSelected(false);
				txt_view_estatus.setVisibility(View.INVISIBLE);
				spiner_estatus.setVisibility(View.INVISIBLE);
				txt_estatus.setText(spiner_estatus.getSelectedItem().toString());
			}	
		});
		
		btn_aceptar.setVisibility(View.VISIBLE);
		btn_aceptar.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
								
				IPost data = new IPost();
				
				if( validaCaptura() ){
					String fechaPost, horainpost, horafinpost;
					fechaPost = fechaToSend/*fechaFilter.substring(4,8)+"-"+fechaFilter.substring(2,4)+"-"+fechaFilter.substring(0,2)*/+"T00:00:00";
					horainpost = "PT"+horainicio.getText().toString().trim().substring(0,2)+"H"+horainicio.getText().toString().trim().substring(3,5)+"M00S";
					horafinpost = "PT"+horafin.getText().toString().trim().substring(0,2)+"H"+horafin.getText().toString().trim().substring(3,5)+"M00S";
					
					String postXml  =  data.HeaderXML +
						       data.PostStartEntryDates + 
						       data.postDateid +
						       data.posttitleCustumer +
						       data.categoryDates +
						       data.setDatePost( cliente.getText().toString().trim(), horainpost, horafinpost, 
							       fechaPost,asunto.getText().toString().trim(), lugar.getText().toString().trim(),txt_estatus.getText().toString().trim() ) +
						       data.endEntryCustumer;
					
					if(IMain.DEBUG)
					    System.out.println("postXml: "+postXml);
					
				    	new Post(getActivity(), tools.updateUrl(DataConnection.DATES_GATEWAY_XML_POST,url), DataConnection.user, DataConnection.pwd, postXml, " Enviando ... ", new AsyntaskCallBackListeners() {
						
						@Override
						public void onTaskDone(String data) {
						    
						if(IMain.DEBUG)
						    System.out.println("postData:onTaskDone:response: "+data);
						
						if( Integer.parseInt(data) == IMain.STATUS_CODE_201 ){


							Toast.makeText(getActivity(), "Envio exitoso.", Toast.LENGTH_SHORT).show();
							cleardates();
							boton_nuevo_no_seleccionado();
							callDates(fechaFilter);

						}
							
						}
					}).execute();
				    	
				    } else
					Toast.makeText(getActivity(), "Informacion incompleta.", Toast.LENGTH_SHORT).show();
			}
		});
    }
    
 public void boton_nuevo_no_seleccionado(){
	 	disabled();
	 	clienteSpinner.setVisibility(View.INVISIBLE);
		btn_lapiz.setVisibility(View.INVISIBLE);
		btn_aceptar.setVisibility(View.INVISIBLE);
		btn_nuevo.setImageResource(R.drawable.img_nuevo_gray);
		btn_nuevo.setSelected(false);
 }
   
 public void boton_actualizar_seleccionado(){
	 enabled();
		
		btn_lapiz.setVisibility(View.VISIBLE);
		btn_actualizar.setImageResource(R.drawable.img_actualizar);
		btn_actualizar.setSelected(true);
		btn_aceptar.setVisibility(View.VISIBLE);
		
		btn_lapiz.setVisibility(View.VISIBLE);
		btn_lapiz.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!btn_lapiz.isSelected()){
				btn_lapiz.setVisibility(View.INVISIBLE);
				btn_lapiz.setSelected(true);
				btn_acept_estatus.setVisibility(View.VISIBLE);
				spiner_estatus.setVisibility(View.VISIBLE);
				txt_view_estatus.setVisibility(View.VISIBLE);
				
				}
				
			}	
		});
		
		btn_acept_estatus.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				btn_acept_estatus.setVisibility(View.INVISIBLE);
				btn_lapiz.setVisibility(View.VISIBLE);
				btn_lapiz.setSelected(false);
				txt_view_estatus.setVisibility(View.INVISIBLE);
				spiner_estatus.setVisibility(View.INVISIBLE);
				txt_estatus.setText(spiner_estatus.getSelectedItem().toString());
			}	
		});
		
		btn_aceptar.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			
				IPost data = new IPost();
				String urlput = tools.updateUrl(DataConnection.DATES_GATEWAY_XML_PUT,url);
				urlput = urlput.replace("||", fechaFilter );
				
				System.out.println("fechaToSend: update: "+fechaToSend);
				
				String fechaHeaderPut = fechaInit+"T00:00:00Z";
				String fechaNew = null;

				String horaIniOld = "PT"+getHoraInicio().substring(0,2)+"H"+getHoraInicio().substring(3,5)+"M00S";
				String horaFinOld = "PT"+getHoraFin().substring(0,2)+"H"+getHoraFin().substring(3,5)+"M00S";
				String horaIniNew = "PT"+horainicio.getText().toString().trim().substring(0,2)+"H"+horainicio.getText().toString().trim().substring(3,5)+"M00S";
				String horaFinNew = "PT"+horafin.getText().toString().trim().substring(0,2)+"H"+horafin.getText().toString().trim().substring(3,5)+"M00S";
								
				if(!fechaInit.equals(fechaToSend))
				    fechaNew = 	fechaToSend +"T00:00:00";
				else
				    fechaNew = 	fechaInit +"T00:00:00";
				    
				String putXml  =  data.HeaderXML +
					       data.putStartEntryDates + 
					       data.putIdDates     .replace( "|", fechaFilter) +
					       data.putTitleDates  .replace( "|", fechaFilter ) +
					       data.putUpdateDates.replace( "$", fechaHeaderPut ) +
					       data.categoryDates   +
					       data.putLinkDates   .replace( "|", fechaFilter ) +
					       data.setDatePut( cliente.getText().toString().trim(), horaIniOld, horaFinOld, fechaNew, 
						       	       horaIniNew, horaFinNew, asunto.getText().toString().trim(), lugar.getText().toString().trim(),
						       	       txt_estatus.getText().toString().trim() ) +
					       data.endEntryCustumer;
				if(IMain.DEBUG)
				    System.out.println("putXml: "+ putXml);

				new Put(getActivity(), urlput , DataConnection.user, DataConnection.pwd, putXml, " Enviando ... ", new AsyntaskCallBackListeners() {
					
					@Override
					public void onTaskDone(String data) {
					    
					if(IMain.DEBUG)
					    System.out.println("putData:onTaskDone:response: "+data);
					
					if( Integer.parseInt(data) == IMain.STATUS_CODE_204 ){

						boton_actualizar_no_seleccionado();
						Toast.makeText(getActivity(), "Envio exitoso.", Toast.LENGTH_SHORT).show();
						
						callDates(fechaFilter);
						}
					}
				}).execute();			    
			}
		});
}
 
 public void boton_actualizar_no_seleccionado(){
	 	disabled();
		btn_lapiz.setVisibility(View.INVISIBLE);
		btn_aceptar.setVisibility(View.INVISIBLE);
		btn_actualizar.setImageResource(R.drawable.img_actualizar_gray);
		btn_actualizar.setSelected(false);
 }
 
 public void filterXdates(){
	 
     		calendario 	= (CalendarView)viewMain.findViewById(R.id.citas_calendario);
		calendario.setOnDateChangeListener(new OnDateChangeListener() {

	        @Override
	        public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
	        		
	        		fechaFilter = tools.fixDate_YYYYMMDD(year, month, day);
	        		fechaToSend    = tools.fixDate_YYYY_MM_DD(year, month, day);
	        		fecha.setText(tools.fixDate_DD_MM_YYYY(year, month, day));
	        		cleardates();
	        		callDates(fechaFilter);
	        		}
	       		});			
            		
    }
 
    
    private void loadClients(){
    	
    	
    	if(clientList.isEmpty()){
    	    
    	new DownloadData(getActivity(), tools.updateUrl( DataConnection.CUSTOMERS_GATEWAY_JSON, url), DataConnection.user, DataConnection.pwd, " Cargando Clientes ... ", new AsyntaskCallBackListeners() {

    		    @Override
    		    public void onTaskDone(String data) {

    			clientList = tools.getLabelsClientes(data);
    			
    			clientesArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, clientList);
    			clientesArrayAdapter.notifyDataSetChanged();
    		    clienteSpinner.setAdapter(clientesArrayAdapter);
    		    }
    		}).execute();
    	}	
        }

    
    protected void showTimeDialogInicio() {

	TimeDialog newFragment = new TimeDialog(true);
	newFragment.setCallBack(ontimeInicio);
        newFragment.show(getFragmentManager(), "timePickerInicio");
    }
    
    protected void showTimeDialogFin() {

	TimeDialog newFragment = new TimeDialog(true);
	newFragment.setCallBack(ontimeFin);
        newFragment.show(getFragmentManager(), "timePickerFin");
    }
    
    
    OnTimeSetListener ontimeInicio = new OnTimeSetListener() {

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

	    Toast.makeText(getActivity(), tools.fixTime(hourOfDay, minute), Toast.LENGTH_SHORT).show();
	    horainicio.setText(tools.fixTime(hourOfDay, minute));
	    
	}
    };
    
    OnTimeSetListener ontimeFin = new OnTimeSetListener() {

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

	    Toast.makeText(getActivity(), tools.fixTime(hourOfDay, minute), Toast.LENGTH_SHORT).show();
	    horafin.setText(tools.fixTime(hourOfDay, minute));
	    
	}
    };

    @Override
    public void onDestroy() {
	super.onDestroy();
    }	
}
