
package com.crystalis.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crystalis.interfaces.IMain;
import com.crystalis.model.ItemHeader;
import com.crystalis.view.R;
  
public class ItemHeaderAdapter extends BaseAdapter {

	private List<ItemHeader> items;
	private Context context;
	
	public ItemHeaderAdapter(List<ItemHeader> items, Context context) {

		this.items = items;
		this.context = context;
	}
	
	public View getView(int position, View v, ViewGroup parent){
		
		ViewHolderOrders viewHolder;
	
		if( v == null ){
			viewHolder = new ViewHolderOrders();
			
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(R.layout.item_detail_row, null);
			
			viewHolder.material 	= (TextView) v.findViewById(R.id.items_materials_row_pedido);
			viewHolder.descripcion 	= (TextView) v.findViewById(R.id.items_materials_row_material);
			viewHolder.plant 	= (TextView) v.findViewById(R.id.items_materials_row_cantidad);
			viewHolder.cantidad 	= (TextView) v.findViewById(R.id.items_materials_row_precio);
			viewHolder.um 		= (TextView) v.findViewById(R.id.item_detail_row_um);
			viewHolder.costo 	= (TextView) v.findViewById(R.id.item_detail_row_costo);
			
			v.setTag(viewHolder);		
			
		} else {
			viewHolder = (ViewHolderOrders) v.getTag();
		}
		
		ItemHeader item =  items.get(position);
		if(IMain.DEBUG)
			System.out.println( " getViewAdapter details sales: " + items.size() );

		if(item != null){
			
			try {
				viewHolder.material.setText  	( item.getMaterial() 	);
				viewHolder.descripcion.setText	( item.getDescription() );
				viewHolder.plant.setText	( item.getPlant() 	);
				viewHolder.cantidad.setText  	( item.getQuantity()	);
				viewHolder.um.setText     	( item.getUoM()		);
				viewHolder.costo.setText    	( "$"+item.getValue()	);
				
				if(IMain.DEBUG)				
					System.out.println( "ItemsAdapter:row: "+item.getOrderId() +">"+ item.getDescription()+">"+item.getPlant()+">"+item.getQuantity()+">"+item.getUoM()+">"+item.getValue());

				
			} catch (Exception e) { 
				e.printStackTrace();
			}			
		}
		return v;
	}
	
	public class ViewHolderOrders {
		
		TextView orderId;
		TextView item;
		TextView material;
		TextView descripcion;
		TextView plant;
		TextView cantidad;
		TextView um;
		TextView costo;
		
	}

	@Override
	public int getCount() {
		
		if(IMain.DEBUG)
			System.out.println(items.size());
		
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}
