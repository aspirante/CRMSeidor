
package com.crystalis.adapters;

import java.sql.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crystalis.interfaces.IMain;
import com.crystalis.model.Header;
import com.crystalis.view.R;
  
public class ItemClientAdapter extends BaseAdapter {


	private List<Header> orders;
	private Context context;
	
	public ItemClientAdapter(List<Header> orders, Context context) {

		this.orders = orders;
		this.context = context;
	}
	
	public View getView(int position, View v, ViewGroup parent){
		
		ViewHolderOrders viewHolder;
	
		if( v == null ){
			viewHolder = new ViewHolderOrders();
			
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(R.layout.clientes_items_row, null);

			viewHolder.orderId 	= (TextView) v.findViewById(R.id.clientes_items_pedido_row);
			viewHolder.documentDate = (TextView) v.findViewById(R.id.clientes_items_fecha_row);
			viewHolder.customerId 	= (TextView) v.findViewById(R.id.clientes_items_cliente_row);
			viewHolder.importe 	= (TextView) v.findViewById(R.id.clientes_items_importe_row);
			viewHolder.currency 	= (TextView) v.findViewById(R.id.clientes_items_moneda_row);
			viewHolder.invoice 	= (TextView) v.findViewById(R.id.clientes_items_nofactura_row);

			v.setTag(viewHolder);		
			
		} else {
			viewHolder = (ViewHolderOrders) v.getTag();
		}
		
		Header order =  orders.get(position);
		if(IMain.DEBUG)
			System.out.println( " getViewAdapter ORDERS: " + orders.size() );

		if(order != null){
			
			try {
				viewHolder.orderId.setText  ( order.getOrderId() + "" );

				 String d = order.getDocumentDate().replace("/", "");
				 d = d.replace("(", "");
				 d = d.replace(")", "");
				 d = d.replace("Date", "");

				Date date = new Date(Long.parseLong(d.trim()));
				
				viewHolder.documentDate.setText( date.toString()/*tools.convertDate( tools.parserDate(order.getDocumentDate()) , IMain.FORMAT_DD_MMM_YYYY)*/);
				viewHolder.customerId.setText  ( order.getCustomerId() + "" );
				viewHolder.importe.setText     ( "$"+order.getOrderValue()   );
				viewHolder.currency.setText    ( order.getCurrency()    );
				viewHolder.invoice.setText     ( order.getInvoice()    );

			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		return v;
	}
	
	public class ViewHolderOrders {
		TextView orderId;
		TextView documentType;
		TextView documentDate;
		TextView customerId;
		TextView importe;
		TextView currency;
		TextView invoice;
	}

	@Override
	public int getCount() {
		
		if(IMain.DEBUG)
			System.out.println(" DetailsClientsAdapter: "+orders.size());
		
		return orders.size();
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
