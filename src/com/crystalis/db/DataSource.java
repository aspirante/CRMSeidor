package com.crystalis.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.crystalis.interfaces.IMain;
import com.crystalis.interfaces.IQuerys;
import com.crystalis.interfacesDB.IColumnNamesTableReportSalesOrder;
import com.crystalis.interfacesDB.IScriptTables;
import com.crystalis.interfacesDB.ISettingsDB;
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
	  
	  
	public Cursor getDataSalesToReport() {
		return database.rawQuery(IQuerys.querySales,null);
	}
	
	public Cursor getInvoicedReport() {
		return database.rawQuery(IQuerys.queryInvoiced,null);
	}
	
	public Cursor getNotInvoicedReport() {
		return database.rawQuery(IQuerys.queryNoIncoived,null);
	}
	
	public boolean isEmptyTableSales( String name) {
		
		Cursor cursor = database.rawQuery("select count(*) from " + name,null);
		
		if(cursor.getCount() == IMain.EMPTY)
			return true;
		return false;
		
	}
	
	public void clearTable( String name ) { database.execSQL(ISettingsDB.DROPTABLE_TEXT + name); }
	
	public void createTableSales() { database.execSQL(IScriptTables.TABLE_SALES_REPORT); }

}
