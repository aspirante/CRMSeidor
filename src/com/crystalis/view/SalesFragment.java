package com.crystalis.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.crystalis.adapters.HeaderAdapter;
import com.crystalis.adapters.ItemHeaderAdapter;
import com.crystalis.adapters.ItemModifyAdapter;
import com.crystalis.dataconnection.DataConnection;
import com.crystalis.dialog.DateDialog;
import com.crystalis.getData.DownloadData;
import com.crystalis.interfaces.IMain;
import com.crystalis.listeners.AsyntaskCallBackListeners;
import com.crystalis.model.Header;
import com.crystalis.model.ItemHeader;
import com.crystalis.sendData.Delete;
import com.crystalis.sendData.IPost;
import com.crystalis.sendData.Post;
import com.crystalis.tools.ConnectionDetector;
import com.crystalis.tools.Parser;
 

public class SalesFragment extends Fragment {
	
	private DownloadData download;
	
	private Parser tools;
	
	private ListView saleList;
	private ListView saleItemList;
	private ListView modifyList;
	
	private EditText filter;

	private TextView no_pedido;
	private TextView fecha;
	private TextView moneda;
	private TextView importe;
	private TextView clienteId;


	private Spinner clienteSpinner;
	private Spinner materialSpinner;


	private List<String> clientList;
	private List<String> materialList;

	private ArrayAdapter<String> clientesArrayAdapter;
	private ArrayAdapter<String> materialesArrayAdapter;
	
	private ItemModifyAdapter itemsModifyAdapter;
	private HeaderAdapter headersAdapter;
	private ItemHeaderAdapter itemsAdapter;

	private ArrayList<ItemHeader> addMaterialList;
	private ArrayList<Header> headersList;

	private ImageButton btn_search;
	private ImageButton btn_update;
	private ImageButton btn_accept;
	private ImageButton btn_fecha;
	private ImageButton btn_nuevo;
	private ImageButton btn_eliminar;
	private ImageButton btn_refresh;



	private Button btn_add_meterial;
	private View viewMain;
	
	
	private String url;
	private String documentType  = "";
	private String clienteSelect = "";
	private String titulo_update = "";


	private int position;

	private TableRow row_pedido;
	private TableRow row_importe;
	private TableRow row_moneda;
	
	private RelativeLayout row_entrega;
	private RelativeLayout row_fecha;
	private RelativeLayout row_cliente;

	private ImageButton edit_cliente;

	private ImageButton accept_edit_cliente;

	private TextView title_client_material;
	    
	public String getDocumentType() {
	    return documentType;
	}

	public void setDocumentType(String documentType) {
	    this.documentType = documentType;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    	viewMain = inflater.inflate(R.layout.sales, container, false);
    		
    	filter = (EditText) viewMain.findViewById(R.id.sales_edit_filter_pedidos);
    	
    	row_pedido 	= (TableRow) viewMain.findViewById(R.id.sales_row_pedido);
    	row_entrega 	= (RelativeLayout) viewMain.findViewById(R.id.sales_row_entrega);
    	row_fecha 	= (RelativeLayout) viewMain.findViewById(R.id.sales_row_fecha);
    	row_cliente 	= (RelativeLayout) viewMain.findViewById(R.id.sales_row_cliente);
    	row_importe 	= (TableRow) viewMain.findViewById(R.id.sales_row_importe);
    	row_moneda 	= (TableRow) viewMain.findViewById(R.id.sales_row_moneda);   	
    	
    	no_pedido 	= (TextView) viewMain.findViewById(R.id.sales_no_pedido);
    	fecha 		= (TextView) viewMain.findViewById(R.id.sales_fecha);
    	importe 	= (TextView) viewMain.findViewById(R.id.sales_importe);
    	moneda 		= (TextView) viewMain.findViewById(R.id.sales_moneda);
    	clienteId	= (TextView) viewMain.findViewById(R.id.sales_clienteId);
    	
    	title_client_material	= (TextView) viewMain.findViewById(R.id.txt_title_material);
    	title_client_material.setVisibility(View.INVISIBLE);

    	saleList 	= (ListView) viewMain.findViewById(R.id.sales_headers_list);
    	saleItemList 	= (ListView) viewMain.findViewById(R.id.sales_items_list);
    	modifyList 	= (ListView) viewMain.findViewById(R.id.sales_modify_list);
    	
    	edit_cliente	= (ImageButton) viewMain.findViewById(R.id.sales_btn_edit_clientid);
    	edit_cliente.setVisibility(View.INVISIBLE);
    	edit_cliente.setOnClickListener( new OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {

		loadClients();
		clienteSpinner.setVisibility(View.VISIBLE);
		title_client_material.setVisibility(View.VISIBLE);
		accept_edit_cliente.setVisibility(View.VISIBLE);
		edit_cliente.setVisibility(View.INVISIBLE);
		
	    }
	});
    	
    	
    	accept_edit_cliente	= (ImageButton) viewMain.findViewById(R.id.sales_btn_accept_edit_clientid);
    	accept_edit_cliente.setVisibility(View.INVISIBLE);
    	accept_edit_cliente.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		
		clienteSpinner.setVisibility(View.INVISIBLE);
		title_client_material.setVisibility(View.INVISIBLE);
		accept_edit_cliente.setVisibility(View.INVISIBLE);
		edit_cliente.setVisibility(View.VISIBLE);

		
	    }
	});
    	
        RadioGroup radioGroup = (RadioGroup) viewMain.findViewById(R.id.sales_radiogroup_entrega);        
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup group, int checkedId) {
        	
		    switch(checkedId) {
		    case R.id.sales_entrega_radio_inmediato:
	                setDocumentType(IMain.ZSUP);
	            break;
		    case R.id.sales_entrega_radio_normal:
			setDocumentType(IMain.ZSER);
	            break;
		    }
            }
        });
        
        clienteSpinner = (Spinner) viewMain.findViewById(R.id.sales_spinner_clientes);
        clienteSpinner.setVisibility(View.INVISIBLE);
        clienteSpinner.setEnabled(false);
        clienteSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {


	    @Override
	    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
		
		clienteSelect = clienteSpinner.getItemAtPosition(position).toString();
		clienteId.setText(clienteSelect);
		
	    }

	    @Override
	    public void onNothingSelected(AdapterView<?> arg0) {
		
	    }
	});

    	btn_fecha = (ImageButton) viewMain.findViewById(R.id.sales_btn_fecha);
    	btn_fecha.setOnClickListener(new View.OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {

		   DateDialog newFragment = new DateDialog();
		   newFragment.setCallBack(ondate);
		   newFragment.show(getFragmentManager(), "datePicker");

	    }
	});
    	
    	btn_search = (ImageButton) viewMain.findViewById(R.id.sales_btn_search);
	btn_search.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {

		if (filter.length() == IMain.EMPTY)
		    Toast.makeText(getActivity(), IMain.TXT_ERROR_EMPTY, Toast.LENGTH_SHORT).show();
		else {

		    if (new ConnectionDetector(getActivity()).isConnectingToInternet())
			filterXHeader(filter.getText().toString().trim());
		     else
			Toast.makeText(getActivity(), IMain.MSG_ERROR_CONEXION, Toast.LENGTH_SHORT).show();
		}

	    }
	});
    	
    	btn_update = (ImageButton) viewMain.findViewById(R.id.sales_btn_update);	
    	btn_nuevo = (ImageButton) viewMain.findViewById(R.id.sales_btn_nuevo);
    	
    	btn_eliminar = (ImageButton) viewMain.findViewById(R.id.sales_btn_eliminar);
    	btn_eliminar.setOnClickListener(new View.OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		
		if ( !btn_nuevo.isSelected() && !btn_update.isSelected() ) {
		    if (!btn_eliminar.isSelected())
			btn_eliminar_seleccionado();	    
		    else if (btn_eliminar.isSelected())
			btn_eliminar_no_seleccionado();  
		}
	    }
	});
    	
    	btn_accept = (ImageButton) viewMain.findViewById(R.id.sales_btn_accept);	
    	btn_accept.setVisibility(View.INVISIBLE);
    	
	btn_update.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View arg0) {
		
		if ( !btn_nuevo.isSelected() && !btn_eliminar.isSelected() ) {
		    
		    if (btn_update.isSelected()) {
			
			btn_update.setSelected(false);
			btn_update.setImageResource(R.drawable.img_actualizar_gray);
			// ...Handle toggle off
			btn_accept.setVisibility(View.INVISIBLE);

		    } else {
			
			titulo_update = IMain.TXT_ACTUALIZAR_PEDIDO;
			
			btn_update.setSelected(true);
			btn_update.setImageResource(R.drawable.img_actualizar);
			// ...Handled toggle on
			btn_accept.setVisibility(View.VISIBLE);

//			loadClients();
		    }
		    
		}
	    }
	});
    	
	btn_nuevo.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
	
		if ( !btn_update.isSelected() && !btn_eliminar.isSelected() ) {
		    if ( !btn_nuevo.isSelected() )
			btn_nuevo_seleccionado();			
		    else if ( btn_nuevo.isSelected() )
			btn_nuevo_no_seleccionado();
		}
	    }
	});


	btn_accept.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {

		    if(IMain.DEBUG)
			System.out.println("getDocumentType():"+getDocumentType() +" fecha.getText(): " +fecha.getText()+" clienteSelect: "+clienteId.getText().toString()+" addMaterialList.size(): "+addMaterialList.size());

		    
		if (btn_nuevo.isSelected()){
		    
		    if(IMain.DEBUG)
			System.out.println("getDocumentType():"+getDocumentType() +" fecha.getText(): " +fecha.getText()+" clienteSelect: "+clienteId.getText().toString()+" addMaterialList.size(): "+addMaterialList.size());
		    
		    if ( !getDocumentType().isEmpty() && (fecha.getText().length() > IMain.EMPTY) && !clienteId.getText().toString().isEmpty() && (addMaterialList.size() > IMain.EMPTY) ) {

			IPost data = new IPost();
			String header = data.setHeader(getDocumentType(), clienteId.getText().toString(), fecha.getText().toString() );

			String items = "";
			
			if ( addMaterialList.size() > 1 ){
			    
			    for ( int i=0; i < addMaterialList.size(); i++ ){
				
				items += data.setItem( addMaterialList.get(i).getMaterial(), addMaterialList.get(i).getQuantity() );
				
			    }
			    		    
			}
			else if ( addMaterialList.size() == 1 ){
			    
			    items = data.setItem( addMaterialList.get(IMain.EMPTY).getMaterial(), addMaterialList.get(IMain.EMPTY).getQuantity() );

			}
			
			String body =  data.startBody + 
				       header +
				       data.halfBody  + 
				       items      + 
				       data.endBody;
			
			    if(IMain.DEBUG)
				    System.out.println("IPostData.header:"+body);
					    
		    	new Post(getActivity(), tools.updateUrl(DataConnection.SALESORDER_GETWAY_XML_POST,url), DataConnection.user, DataConnection.pwd, body, " Enviando ... ", new AsyntaskCallBackListeners() {
				
				@Override
				public void onTaskDone(String data) {
				    
				if(IMain.DEBUG)
				    System.out.println("postData:onTaskDone:response: "+data);
				
				if( Integer.parseInt(data) == IMain.STATUS_CODE_201 ){

					btn_nuevo_no_seleccionado();
					Toast.makeText(getActivity(), "Envio exitoso.", Toast.LENGTH_SHORT).show();
					pedidos();

				}else
				    Toast.makeText(getActivity(), "Pedido NO enviado.", Toast.LENGTH_SHORT).show();
					
				}
			}).execute();
		    }
		    else Toast.makeText(getActivity(), "Informacion incompleta.", Toast.LENGTH_SHORT).show();
		    
		} else if(btn_eliminar.isSelected()){
		    
		    String pedido = no_pedido.getText().toString();
		    
		    if(!pedido.isEmpty())
			delete( pedido );
		    else
			Toast.makeText(getActivity(), "No. No valido.", Toast.LENGTH_SHORT).show();
		}

	    }
	});
	
    	btn_refresh = (ImageButton) viewMain.findViewById(R.id.sales_btn_refresh);
    	btn_refresh.setOnClickListener(new View.OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {

		    pedidos();
		
	    }
	});   	
    	
    	clientList = new ArrayList<String>();
	materialList = new ArrayList<String>();
	addMaterialList = new ArrayList<ItemHeader>();
	
	btn_add_meterial = (Button) viewMain.findViewById(R.id.sales_btn_add_material);
	btn_add_meterial.setVisibility(View.INVISIBLE);
	btn_add_meterial.setOnClickListener(new View.OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
//		updateDialogItem(v);
		callMaterials();

	    }
	});

      return viewMain;       
    }

    public void btn_nuevo_seleccionado() {
	
	btn_nuevo.setSelected(true);
	clienteSpinner.setEnabled(true);

	btn_nuevo.setImageResource(R.drawable.img_nuevo);

	btn_accept.setVisibility(View.VISIBLE);
	
	btn_add_meterial.setVisibility(View.VISIBLE);
	
	row_pedido.setVisibility(View.INVISIBLE);
	row_importe.setVisibility(View.INVISIBLE);
	row_moneda.setVisibility(View.INVISIBLE);

    	edit_cliente.setVisibility(View.VISIBLE);
    	
	titulo_update = IMain.TXT_CREAR_PEDIDO;
	
    	final Calendar c = Calendar.getInstance();
    	int year = c.get(Calendar.YEAR);
    	int month = c.get(Calendar.MONTH);
    	int day = c.get(Calendar.DAY_OF_MONTH);
    	
    	String monthStr = (month < 10) ? ("0"+String.valueOf(month+1)):String.valueOf(month+1);
	String dayStr   = (day < 10) ? ("0"+String.valueOf(day)) :String.valueOf(day);    
    	
    	fecha.setText(String.valueOf(year) + "-" + String.valueOf(monthStr) + "-" + String.valueOf(dayStr));
	
	if(IMain.DEBUG)
	    System.out.println("btn_nuevo material: select");
	
	itemsModifyAdapter = new ItemModifyAdapter(tools.cleanItems(), getActivity());
	itemsModifyAdapter.notifyDataSetChanged();
	modifyList.setAdapter(itemsModifyAdapter);
	
    }
    

    public void btn_eliminar_seleccionado() {
	
	btn_eliminar.setSelected(true);
	btn_eliminar.setImageResource(R.drawable.img_eliminar_black);

    	row_entrega.setVisibility(View.INVISIBLE);
    	row_fecha.setVisibility(View.INVISIBLE);
    	row_cliente.setVisibility(View.INVISIBLE);
    	row_importe.setVisibility(View.INVISIBLE);
    	row_moneda.setVisibility(View.INVISIBLE);
    	
    	btn_accept.setVisibility(View.VISIBLE); 		
    }
    
    public void btn_eliminar_no_seleccionado() {
	
	btn_eliminar.setSelected(false);
	btn_eliminar.setImageResource(R.drawable.img_eliminar_gray);


    	row_entrega.setVisibility(View.VISIBLE);
    	row_fecha.setVisibility(View.VISIBLE);
    	row_cliente.setVisibility(View.VISIBLE);
    	row_importe.setVisibility(View.VISIBLE);
    	row_moneda.setVisibility(View.VISIBLE);
    	
    	btn_accept.setVisibility(View.INVISIBLE);
    	    	
    }
    
    public void btn_nuevo_no_seleccionado() {
	
	btn_nuevo.setSelected(false);
	clienteSpinner.setEnabled(false);

	btn_nuevo.setImageResource(R.drawable.img_nuevo_gray);
	// ...Handle toggle off
	btn_accept.setVisibility(View.INVISIBLE);
	btn_add_meterial.setVisibility(View.INVISIBLE);

	row_pedido.setVisibility(View.VISIBLE);
	row_importe.setVisibility(View.VISIBLE);
	row_moneda.setVisibility(View.VISIBLE);
	
    	edit_cliente.setVisibility(View.INVISIBLE);

	clearItemsModifyList();
	addMaterialList.clear();
    }
	
    OnDateSetListener ondate = new OnDateSetListener() {
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	    
	    String month = monthOfYear < 10 ? "0"+String.valueOf(monthOfYear+1):String.valueOf(monthOfYear+1);
	    String day   = dayOfMonth < 10 ? "0"+String.valueOf(dayOfMonth) :String.valueOf(dayOfMonth);

	    fecha.setText(String.valueOf(year) + "-" + month + "-" + day );
	}
    };
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setHasOptionsMenu(true);
    	tools = new Parser();
    	url = getArguments().getString(IMain.URL_KEY);

    }
    
    @Override
    public void onStart() {
    // TODO Auto-generated method stub
    super.onStart();
    pedidos();

    }
   
    public void delete(final String pedido){
	
	String urlfull= tools.updateUrl( DataConnection.SALESORDER_GETWAY_DELETE, url);
	urlfull = urlfull.replace("|", pedido);
	
	if(headersList.get(getPosition()).getInvoice().isEmpty())
	new Delete(getActivity(), urlfull, DataConnection.user, DataConnection.pwd, " Eliminando ... ", new AsyntaskCallBackListeners() {

		    @Override
		    public void onTaskDone(String data) {
			
			if (IMain.DEBUG)
			    System.out.println("SalesFragment: delete: pedido ("+ pedido +")" );
			if(data.equals("ok")){
			    btn_eliminar_no_seleccionado();
			    pedidos();
			    Toast.makeText(getActivity(), " Pedido No."+pedido+" eliminado.", Toast.LENGTH_SHORT).show();
			}

		    }
		    
	}).execute();
	else 
	    Toast.makeText(getActivity(), IMain.TXT_ACCION_NO_VALIDA, Toast.LENGTH_SHORT).show();
    }
    
    public void pedidos(){

	    download = new DownloadData(getActivity(), tools.updateUrl( DataConnection.SALESORDER_GETWAY_JSON, url), DataConnection.user, DataConnection.pwd, " Pedidos ... ", new AsyntaskCallBackListeners() {

			@Override
			public void onTaskDone(String data) {
			    
			    HeadersData(data);

			}
			
		    });
	    
	    download.execute();
  }
    
    
    public void filterXOrderId(String orderId){
    	
	String strReplace = tools.replacechr("|", DataConnection.FILTRO_ORDERID , orderId);

    	download = new DownloadData(getActivity(), tools.updateUrl(strReplace,url) , DataConnection.user, DataConnection.pwd, "Detalles ... ", new AsyntaskCallBackListeners() {
			
			@Override
			public void onTaskDone(String data) {
		    	
			    itemsModifyList(data);
				
			}
		});
    	download.execute();
    }
    
    

    public void filterXHeader(String orderId){ 
	    	
	    	String strReplace = tools.replacechr("|", DataConnection.FILTRO_HEADER , orderId);

    	download = new DownloadData(getActivity(), tools.updateUrl(strReplace,url) , DataConnection.user, DataConnection.pwd, "Buscando pedido ...", new AsyntaskCallBackListeners() {
			
			@Override
			public void onTaskDone(String  data) {
			    
			    	clearLists();
			    	filterHeaderData(data);
			}
		});
    	download.execute();
    }
    
    protected void clearLists() {
	
	clearHeadersList();
    	clearItemsList();
    	clearItemsModifyList();
   	
    }
    
    private void clearHeadersList(){
    	headersAdapter = new HeaderAdapter(tools.cleanHeaders(), getActivity());
    	headersAdapter.notifyDataSetChanged();
    	saleList.setAdapter( itemsAdapter ); 	
    }
    
    
    private void clearItemsList(){
    	itemsAdapter = new ItemHeaderAdapter(tools.cleanItems(), getActivity());
    	itemsAdapter.notifyDataSetChanged();
    	saleItemList.setAdapter( itemsAdapter ); 	
    }
    
    private void clearItemsModifyList(){
    	
    	itemsModifyAdapter = new ItemModifyAdapter(tools.cleanItems(), getActivity());
    	itemsModifyAdapter.notifyDataSetChanged();
    	modifyList.setAdapter(itemsModifyAdapter);
    	
    }

    private void HeadersData(String data) {
	
	headersList = new ArrayList<Header>();
	
	headersList = tools.HeadersJson(data);

	headersAdapter = new HeaderAdapter(headersList , getActivity());
	headersAdapter.notifyDataSetChanged();
    	saleList.setAdapter( headersAdapter );
    	saleList.setOnItemClickListener(new OnItemClickListener() {

			@Override 
			public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {
			    				
				TextView view1 = (TextView) view. findViewById(R.id.listorders_row_orderId); 

				setPosition(position);
				
				no_pedido.setText( ((TextView) view.findViewById(R.id.listorders_row_orderId)	  ).getText().toString() );
				fecha.setText	 ( ((TextView) view.findViewById(R.id.listorders_row_documentdate)).getText().toString() );
				importe.setText	 ( ((TextView) view.findViewById(R.id.listorders_row_importe)	  ).getText().toString() );
				moneda.setText	 ( ((TextView) view.findViewById(R.id.listorders_row_currency)	  ).getText().toString() );
				clienteId.setText( ((TextView) view.findViewById(R.id.listorders_row_custumerId)  ).getText().toString() );
		    	
				if (new ConnectionDetector(getActivity()).isConnectingToInternet() && !btn_update.isSelected() && !btn_nuevo.isSelected() && !btn_eliminar.isSelected()){

					filterXOrderId( view1.getText().toString() );
				    
				}
				else 
				    Toast.makeText(getActivity(), IMain.TXT_ACCION_NO_VALIDA, Toast.LENGTH_SHORT).show();
			}
		});
    }
    
    public void filterHeaderData(String data) {

	headersAdapter = new HeaderAdapter(tools.FilterHeaderJson(data), getActivity());
	headersAdapter.notifyDataSetChanged();
	saleList.setAdapter( headersAdapter );
	itemsModifyList(data);

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
		    	clienteSpinner.setSelection( clientList.indexOf( clienteSelect ) );
	
		    }
		}).execute();
	}    	
    }
    
    private void itemsModifyList(String data) {
    	
    	if(IMain.DEBUG)
    		System.out.println("data : "+ data);

    	itemsAdapter = new ItemHeaderAdapter(tools.itemHeaderJson(data), getActivity());
    	saleItemList.setAdapter( itemsAdapter );
    	
    	itemsModifyAdapter = new ItemModifyAdapter(tools.itemHeaderJson(data), getActivity());

    	modifyList.setAdapter(itemsModifyAdapter);
    	modifyList.setOnItemLongClickListener(new OnItemLongClickListener() {

	    @Override
	    public boolean onItemLongClick(AdapterView<?> arg0, View view,int arg2, long arg3) {

	    	if( btn_update.isSelected()){
	    	    updateDialogItem();
	    	} else if( btn_nuevo.isSelected() ){
	    		
	    	    	System.out.println("Pedido cancelado : "+ arg2);
	    	    	addMaterialList.remove(arg2);
	    	    	Toast.makeText(getActivity(), "Pedido cancelado.", Toast.LENGTH_SHORT).show();
	    	    	itemsModifyAdapter = new ItemModifyAdapter( addMaterialList, getActivity()); 
	    	    	itemsModifyAdapter.notifyDataSetChanged();
	    	    	modifyList.setAdapter(itemsModifyAdapter);
	    	}
	    	else
	    		Toast.makeText(getActivity(), IMain.TXT_ERROR_UPDATE, Toast.LENGTH_SHORT).show();
		return false;
	    }
	});
    }
  
    public void updateDialogItem(){
 
	final Dialog dialog = new Dialog(getActivity());		
	dialog.setContentView(R.layout.update_sales);			
	dialog.setTitle(titulo_update );			
	
	materialSpinner = (Spinner) dialog.findViewById(R.id.update_sales_spinner);	
	
        materialesArrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, materialList);
        materialesArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        materialesArrayAdapter.notifyDataSetChanged();
	materialSpinner.setAdapter(materialesArrayAdapter);
	
	
	final EditText numero = (EditText) dialog.findViewById(R.id.update_sales_cantidad);


	ImageButton dialogButtonIncrement = (ImageButton) dialog.findViewById(R.id.update_sales_increment);
	dialogButtonIncrement.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		int numeroup = Integer.parseInt(numero.getText().toString()) + 1;
		numero.setText(String.valueOf(numeroup));
	    }
	});
	
	ImageButton dialogButtonDecrement = (ImageButton) dialog.findViewById(R.id.update_sales_decrement);
	dialogButtonDecrement.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		
		int numeroup = Integer.parseInt(numero.getText().toString());

		if ( numeroup > 0 ){
		    numeroup = Integer.parseInt(numero.getText().toString()) - 1;
		    numero.setText(String.valueOf(numeroup));
		}
	    }
	});
	
	ImageButton dialogButtonAccept = (ImageButton) dialog.findViewById(R.id.update_sales_accept);		
	dialogButtonAccept.setOnClickListener(new OnClickListener(){			
	    @Override			
	    public void onClick(View v) {				
		TextView item_modify_row_material = (TextView) v.findViewById(R.id.item_modify_row_material);				
		EditText cantidad = (EditText) dialog.findViewById(R.id.update_sales_cantidad);				
		TextView item_modify_row_cantidad = (TextView) v.findViewById(R.id.item_modify_row_cantidad);			    
		if( btn_update.isSelected() ){				
		    item_modify_row_material.setText(materialSpinner.getSelectedItem().toString());			
		    item_modify_row_cantidad.setText(cantidad.getText().toString());			    
		} else if( btn_nuevo.isSelected() ){			
		    ItemHeader orderItems = new ItemHeader(IMain.EMPTY_STR, IMain.EMPTY_STR, materialSpinner.getSelectedItem().toString(), IMain.EMPTY_STR, IMain.EMPTY_STR, cantidad.getText().toString(), IMain.EMPTY_STR, IMain.EMPTY_STR);   						
		    addMaterialList.add(orderItems);
		    itemsModifyAdapter = new ItemModifyAdapter( addMaterialList, getActivity());   					   	
		    itemsModifyAdapter.notifyDataSetChanged();   					   	
		    modifyList.setAdapter(itemsModifyAdapter);			    
		}				
		dialog.dismiss();			
	    }			
	});			
	ImageButton dialogButtonCancel = (ImageButton) dialog.findViewById(R.id.update_sales_cancel); 				
	dialogButtonCancel.setOnClickListener(new OnClickListener() { 					
	    @Override				
	    public void onClick(View v) {  						
		dialog.dismiss(); 					
	    } 				
	});
	dialog.show();
    }
    
    
    private void callMaterials() {

	if ( materialList.isEmpty() ) {

	    new DownloadData(getActivity(), tools.updateUrl( DataConnection.MATERIALS_GETWAY, url), DataConnection.user, DataConnection.pwd, "Cargando Materiales... ", new AsyntaskCallBackListeners() {

			@Override
			public void onTaskDone(String data) {
			    	materialList = tools.getLabelsMaterials(data);
				updateDialogItem();

			}
		    }).execute();
	} else
	    updateDialogItem();


    }

    @Override
    public void onDestroy() {
	super.onDestroy();
//	addMaterialList.clear();

    }

    public int getPosition() {
	return position;
    }

    public void setPosition(int position) {
	this.position = position;
    }
  

}
