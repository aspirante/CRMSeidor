
package com.crystalis.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crystalis.interfaces.IMain;
import com.crystalis.model.ItemMaterial;
import com.crystalis.view.R;
  
public class ItemMaterialAdapter extends BaseAdapter {

	private List<ItemMaterial> items;
	private Context context;
	
	public ItemMaterialAdapter(List<ItemMaterial> items, Context context) {

		this.items = items;
		this.context = context;
	}
	
	public View getView(int position, View v, ViewGroup parent){
		
		ViewHolderOrders viewHolder;
	
		if( v == null ){
			viewHolder = new ViewHolderOrders();
			
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(R.layout.items_materials_row, null);
			
			viewHolder.orderId 	= (TextView) v.findViewById(R.id.items_materials_row_pedido);
			viewHolder.material	= (TextView) v.findViewById(R.id.items_materials_row_material);
			viewHolder.cantidad	= (TextView) v.findViewById(R.id.items_materials_row_cantidad);
			viewHolder.costo 	= (TextView) v.findViewById(R.id.items_materials_row_precio);
			
			v.setTag(viewHolder);		
			
		} else {
			viewHolder = (ViewHolderOrders) v.getTag();
		}
		
		ItemMaterial item =  items.get(position);
		if(IMain.DEBUG)
			System.out.println( " getViewAdapter details sales: " + items.size() );

		if(item != null){
			
			try {
				viewHolder.orderId.setText  ( item.getOrderId()     );
				viewHolder.material.setText ( item.getMaterial()    );
				viewHolder.cantidad.setText ( item.getQuantity()    );
				viewHolder.costo.setText    ( "$"+item.getValue()   );
				
				if(IMain.DEBUG)				
					System.out.println( "ItemsMaterialsAdapter:row: "+item.getOrderId() +">"+ item.getDescription()+">"+item.getPlant()+">"+item.getQuantity()+">"+item.getUoM()+">"+item.getValue());

				
			} catch (Exception e) { 
				e.printStackTrace();
			}			
		}
		return v;
	}
	
	public class ViewHolderOrders {
		
		TextView orderId;
		TextView material;
		TextView cantidad;
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
