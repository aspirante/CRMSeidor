package com.crystalis.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.crystalis.interfacesDB.IColumnNamesTableReportSalesOrder;
import com.crystalis.interfacesDB.ITableNames;

public class DataSource {
	
	  // Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  
	  public DataSource() {
		// TODO Auto-generated constructor stub
	}

	  public DataSource(Context context) {
		  dbHelper = new MySQLiteHelper(context);

	}
	  
	  public void open() throws SQLException{
		  database = dbHelper.getWritableDatabase();
	  }
	  
	  public void close(){
		  dbHelper.close();
	  }
	  	  
	  public void insertSale( String orderid, String documenttype, String documentdate,  String customerid, String salesorg, 
			  				  String distchannel, String division, String ordervalue, String currency, String invoice ){
		  
		  ContentValues values = new ContentValues();
		  values.put(IColumnNamesTableReportSalesOrder.COLUMN_ORDERID, orderid);
		  values.put(IColumnNamesTableReportSalesOrder.COLUMN_DOCUMENTTYPE, documenttype);
		  values.put(IColumnNamesTableReportSalesOrder.COLUMN_DOCUMENTDATE, documentdate);
		  values.put(IColumnNamesTableReportSalesOrder.COLUMN_CUSTOMERID, customerid);
		  values.put(IColumnNamesTableReportSalesOrder.COLUMN_SALESORG, salesorg);
		  values.put(IColumnNamesTableReportSalesOrder.COLUMN_DISTCHANNEL, distchannel);
		  values.put(IColumnNamesTableReportSalesOrder.COLUMN_DIVISION, division);
		  values.put(IColumnNamesTableReportSalesOrder.COLUMN_ORDERVALUE, ordervalue);
		  values.put(IColumnNamesTableReportSalesOrder.COLUMN_CURRENCY, currency);
		  values.put(IColumnNamesTableReportSalesOrder.COLUMN_INVOICE, invoice);
		  
		  database.insert(ITableNames.TABLE_REPORT_SALESORDER, null, values);
	  }
	  
	  private String query = "SELECT customerid,count(*) as sales, sum(ordervalue) as total FROM salesorder where invoice != '' group by customerid";
	  
	public Cursor getDataSalesToReport() {
		return database.rawQuery(query,null);
	}
	  

}
