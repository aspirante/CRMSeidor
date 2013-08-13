package com.crystalis.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.crystalis.adapters.ItemMaterialAdapter;
import com.crystalis.adapters.ProductoAdapter;
import com.crystalis.dataconnection.DataConnection;
import com.crystalis.getData.DownloadData;
import com.crystalis.getData.DownloadImage;
import com.crystalis.interfaces.IMain;
import com.crystalis.listeners.AsyntaskCallBackListeners;
import com.crystalis.listeners.TaskImage;
import com.crystalis.tools.ConnectionDetector;
import com.crystalis.tools.Parser;
 

public class ProductsFragment extends Fragment {
	
	private DownloadData download;
	private DownloadImage downloadimage;
	private Parser tools;
	private ListView detailList;
	private ListView materialList; 
	
	private ProductoAdapter materialAdapter;
	private ItemMaterialAdapter itemMaterialAdapter;

	private View viewMain;
	
	private ImageView img;
	
	private String url;
	private TextView materialId;
	private TextView descripcion;


	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    	viewMain = inflater.inflate(R.layout.productos, container, false);
    	
    	materialList = (ListView) viewMain.findViewById(R.id.productos_lista_de_productos);
    	detailList   = (ListView) viewMain.findViewById(R.id.productos_lista_de_pedidos);
    	
    	materialId 	= (TextView) viewMain.findViewById(R.id.productos_producto);
    	descripcion	= (TextView) viewMain.findViewById(R.id.productos_descripcion);
    	img = (ImageView) viewMain.findViewById(R.id.img_del_producto);

        return viewMain;       
    }
 
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setHasOptionsMenu(true);
    	tools = new Parser();
    	url = getArguments().getString(IMain.URL_KEY);

    	callProducts();


    }
   
    public void callProducts(){

    	download = new DownloadData(getActivity(), tools.updateUrl( DataConnection.MATERIALS_GETWAY, url ), DataConnection.user, DataConnection.pwd, "Productos ... ", new AsyntaskCallBackListeners() {
			
			@Override
			public void onTaskDone(String data) {
				dataMaterials(data);
				
			}
		});
    	download.execute();
    }
    
    public void GetImage(String materialId){
    	
    	
    	String imagelink = tools.replacechr("|", DataConnection.IMAGE_URL ,materialId);
    	downloadimage = new DownloadImage(getActivity(),imagelink, "Cargando Imagen" ,new TaskImage() {
  		  
  		  public void onImageTaskDone(Bitmap map){
  		  SetImagen(map);
  		  }
  	  });
  	    downloadimage.execute();
  }
    
    public void filterXMaterialId(final String materialId){
    	
	String strReplace = tools.replacechr("|", DataConnection.FILTRO_MATERIALID , /*"DE-1307C"*/materialId);

    	download = new DownloadData(getActivity(), tools.updateUrl(strReplace,url) , DataConnection.user, DataConnection.pwd, "Detalles ... ", new AsyntaskCallBackListeners() {
			
			@Override
			public void onTaskDone(String data) {
		    	
				dataDatailMaterials(data);
				GetImage(materialId);
				
			}
		});
    	download.execute();
    }
    
//    protected void clearLists() {
//	
//    	clearItemsList();
//    	clearItemsModifyList();
//   	
//    }
    
    private void clearMaterialsList(){
	materialAdapter = new ProductoAdapter(tools.cleanMaterials(), getActivity());
	materialAdapter.notifyDataSetChanged();
    	materialList.setAdapter( materialAdapter ); 	
    }
    
    private void clearDetailList(){
    	
	itemMaterialAdapter = new ItemMaterialAdapter(tools.cleanItemMaterials(), getActivity());
	itemMaterialAdapter.notifyDataSetChanged();
    	detailList.setAdapter(itemMaterialAdapter);
    	
    }
    
    private void clearImage(){
    	
    	img.setImageBitmap(null);
        	
        }

    private void dataMaterials(String data) {

	materialAdapter = new ProductoAdapter(tools.parserMaterialsJson(data) , getActivity());

	materialList.setAdapter( materialAdapter );
	materialList.setOnItemClickListener(new OnItemClickListener() {

			@Override 
			public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {
//				TextView materialId = (TextView) view. findViewById(R.id.productos_row_material); 
				
		    	materialId.setText  ( ((TextView) view.findViewById(R.id.productos_row_material)	 ).getText().toString() );
		    	descripcion.setText	( ((TextView) view.findViewById(R.id.productos_row_descripcion)  ).getText().toString() );

			if (new ConnectionDetector(getActivity()).isConnectingToInternet()){
				clearImage();
				clearDetailList();
			    filterXMaterialId( ((TextView) view. findViewById(R.id.productos_row_material)).getText().toString() );
			}
			else 
			    Toast.makeText(getActivity(), IMain.MSG_ERROR_CONEXION, Toast.LENGTH_SHORT).show();	
			}
		});
    }

    
	private void dataDatailMaterials(String data) {

		if (IMain.DEBUG)
			System.out.println("data : " + data);

		itemMaterialAdapter= new ItemMaterialAdapter(tools.parserItemMaterialJson(data), getActivity());
		detailList.setAdapter( itemMaterialAdapter );

	}
	
	private void SetImagen(Bitmap bm){
		img.setImageBitmap(bm);
	}
    
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
  

}
