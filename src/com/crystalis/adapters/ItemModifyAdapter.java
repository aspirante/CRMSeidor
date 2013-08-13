
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
 
public class ItemModifyAdapter extends BaseAdapter {

	private List<ItemHeader> items;
	private Context context;
	
	public ItemModifyAdapter(List<ItemHeader> items, Context context) {

		this.items = items;
		this.context = context;
	}
	
	public View getView(int position, View v, ViewGroup parent){
		
		ViewHolderOrders viewHolder;
	
		if( v == null ){
			viewHolder = new ViewHolderOrders();
			
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(R.layout.item_modify_row, null);
			
			viewHolder.item		= (TextView) v.findViewById(R.id.item_modify_row_item);
			viewHolder.material 	= (TextView) v.findViewById(R.id.item_modify_row_material);
			viewHolder.cantidad 	= (TextView) v.findViewById(R.id.item_modify_row_cantidad);

			v.setTag(viewHolder);		
			
		} else {
			viewHolder = (ViewHolderOrders) v.getTag();
		}
		
		ItemHeader item =  items.get(position);
		if(IMain.DEBUG)
			System.out.println( " ItemsModifyAdapter: " + items.size() );

		if(item != null){
			
			try {
				viewHolder.item.setText		( item.getItem() );
				viewHolder.material.setText  	( item.getMaterial() );
				viewHolder.cantidad.setText  	( item.getQuantity() );
				
				if(IMain.DEBUG)				
					System.out.println( "ItemsAdapter:row: "+item.getItem() +">"+ item.getMaterial()+">"+item.getQuantity() );

				
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		return v;
	}
	
	public class ViewHolderOrders {
		
		TextView item;
		TextView material;
		TextView cantidad;

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
