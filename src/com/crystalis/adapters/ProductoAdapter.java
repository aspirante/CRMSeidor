
package com.crystalis.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crystalis.interfaces.IMain;
import com.crystalis.model.Material;
import com.crystalis.view.R;

public class ProductoAdapter extends BaseAdapter {

	private List<Material> materials;
	private Context context;
	
	public ProductoAdapter(List<Material> materials, Context context) {

		this.materials = materials;
		this.context = context;

	}
	
	public View getView(int position, View v, ViewGroup parent){
		
		ViewHolderOrders viewHolder;
	
		if( v == null ){
			viewHolder = new ViewHolderOrders();
			
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(R.layout.productos_row, null);

			viewHolder.materialId 	= (TextView) v.findViewById(R.id.productos_row_material);
			viewHolder.descripcion 	= (TextView) v.findViewById(R.id.productos_row_descripcion);
			viewHolder.stock 		= (TextView) v.findViewById(R.id.productos_row_stock);
			viewHolder.precio 		= (TextView) v.findViewById(R.id.productos_row_precio);

			v.setTag(viewHolder);		
			
		} else {
			viewHolder = (ViewHolderOrders) v.getTag();
		}
		
		Material material =  materials.get(position);
		if(IMain.DEBUG)
			System.out.println( " getViewAdapter ORDERS: " + materials.size() );

		if(materials != null){
			
			try {
				
				viewHolder.materialId.setText ( material.getMaterialId()  );
				viewHolder.descripcion.setText( material.getDescripcion() );
				viewHolder.stock.setText      ( material.getStock()       );
				viewHolder.precio.setText     ( material.getPrecio()      );
				
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		return v;
	}
	
	public class ViewHolderOrders {
		TextView materialId;
		TextView descripcion;
		TextView stock;
		TextView precio;
	}

	@Override
	public int getCount() {
		
		if(IMain.DEBUG)
			System.out.println(" Productos Adapter: "+materials.size());
		
		return materials.size();
	}

	@Override
	public Object getItem(int position) {

		return null;
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

}
