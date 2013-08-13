
package com.crystalis.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crystalis.interfaces.IMain;
import com.crystalis.model.Customer;
import com.crystalis.view.R;

public class CustomerAdapter extends BaseAdapter {

	private List<Customer> customers;
	private Context context;
	
	public CustomerAdapter(List<Customer> customers, Context context) {

		this.customers = customers;
		this.context = context;

	}
	
	public View getView(int position, View v, ViewGroup parent){
		
		ViewHolderOrders viewHolder;
	
		if( v == null ){
			viewHolder = new ViewHolderOrders();
			
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(R.layout.clientes_headers_row, null);

			viewHolder.customerId 	= (TextView) v.findViewById(R.id.clientes_nocliente_row);
			viewHolder.name 	= (TextView) v.findViewById(R.id.clientes_name_row);
			viewHolder.address 	= (TextView) v.findViewById(R.id.clientes_address_row);
			viewHolder.state 	= (TextView) v.findViewById(R.id.clientes_state_row);
			viewHolder.rfc 		= (TextView) v.findViewById(R.id.clientes_rfc_row);


			v.setTag(viewHolder);		
			
		} else {
			viewHolder = (ViewHolderOrders) v.getTag();
		}
		
		Customer customer =  customers.get(position);
		if(IMain.DEBUG)
			System.out.println( " getViewAdapter ORDERS: " + customers.size() );

		if(customer != null){
			
			try {
				
				viewHolder.customerId.setText ( customer.getCustomerid()  );
				viewHolder.name.setText	      ( customer.getName()	  );
				viewHolder.address.setText    ( customer.getStreet()      );
				viewHolder.state.setText      ( customer.getState()       );
				viewHolder.rfc.setText        ( customer.getRfc()	  );

				
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		return v;
	}
	
	public class ViewHolderOrders {
		TextView customerId;
		TextView name;
		TextView address;
		TextView state;
		TextView rfc;
	}

	@Override
	public int getCount() {
		
		if(IMain.DEBUG)
			System.out.println(" Customers Adapter: "+customers.size());
		
		return customers.size();
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
