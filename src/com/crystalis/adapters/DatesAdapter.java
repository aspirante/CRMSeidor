
package com.crystalis.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crystalis.interfaces.IMain;
import com.crystalis.model.DatesModel;
import com.crystalis.view.R;
  
public class DatesAdapter extends BaseAdapter {

	private List<DatesModel> Dates;
	private Context context;
	
	public DatesAdapter(List<DatesModel> Dates, Context context) {

		this.Dates = Dates;
		this.context = context;
	}
	
	public View getView(int position, View v, ViewGroup parent){
		
		ViewHolderOrders viewHolder;
	
		if( v == null ){
			viewHolder = new ViewHolderOrders();
			
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(R.layout.citas_row, null);
			
			viewHolder.customerId 	= (TextView) v.findViewById(R.id.citas_cliente_row);
			viewHolder.initialhour 	= (TextView) v.findViewById(R.id.citas_horai_row);
			viewHolder.endhour 	= (TextView) v.findViewById(R.id.citas_horaf_row);
			viewHolder.subject 	= (TextView) v.findViewById(R.id.citas_asunto_row);
			viewHolder.place 	= (TextView) v.findViewById(R.id.citas_lugar_row);
			viewHolder.status 	= (TextView) v.findViewById(R.id.citas_estatus_row);
			
			
			v.setTag(viewHolder);		
			
		} else {
			viewHolder = (ViewHolderOrders) v.getTag();
		}
		
		DatesModel date =  Dates.get(position);
		if(IMain.DEBUG)
			System.out.println( " getViewAdapter details sales: " + Dates.size() );

		if(Dates != null){
			
			try {
				
				String horainicial;
				horainicial = date.getinitialhour().substring(2,4)+":"+date.getinitialhour().substring(5,7)+" HRS.";
				String horafinal;
				horafinal = date.getendhour().substring(2,4)+":"+date.getendhour().substring(5,7)+" HRS.";
				
				
				viewHolder.customerId.setText  	( date.getcustomerId() );
				viewHolder.initialhour.setText	( horainicial );
				viewHolder.endhour.setText	( horafinal );
				viewHolder.subject.setText  	( date.getsubject());
				viewHolder.place.setText     	( date.getPlace());
				viewHolder.status.setText     	( date.getstatus());
				
				if(IMain.DEBUG)				
					System.out.println( "DatesAdapter:row: "+date.getcustomerId() +">"+ date.getinitialhour()+">"+date.getendhour()+">"+date.getsubject()+">"+date.getPlace()+">"+date.getstatus());

				
			} catch (Exception e) { 
				e.printStackTrace();
			}			
		}
		return v;
	}
	
	public class ViewHolderOrders {
		
		TextView customerId;
		TextView initialhour;
		TextView endhour;
		TextView subject;
		TextView place; 
		TextView status;
				
	}

	@Override
	public int getCount() {
		
		if(IMain.DEBUG)
			System.out.println(Dates.size());
		
		return Dates.size();
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
