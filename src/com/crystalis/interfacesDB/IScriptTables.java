package com.crystalis.interfacesDB;

import com.crystalis.interfaces.ITypeDataDB;

public interface IScriptTables {
	
	  // Database creation sql statement
	  public static final String TABLE_SALES_REPORT = 
		  "create table " + ITableNames.TABLE_REPORT_SALESORDER   + "(" 
		  + IColumnNamesTableReportSalesOrder.COLUMN_ORDERID      + ITypeDataDB.text + ","
		  + IColumnNamesTableReportSalesOrder.COLUMN_DOCUMENTTYPE + ITypeDataDB.text + ","
	      + IColumnNamesTableReportSalesOrder.COLUMN_DOCUMENTDATE + ITypeDataDB.text + ","
	      + IColumnNamesTableReportSalesOrder.COLUMN_CUSTOMERID   + ITypeDataDB.text + ","
	      + IColumnNamesTableReportSalesOrder.COLUMN_SALESORG     + ITypeDataDB.text + ","
	      + IColumnNamesTableReportSalesOrder.COLUMN_DISTCHANNEL  + ITypeDataDB.text + ","
	      + IColumnNamesTableReportSalesOrder.COLUMN_DIVISION     + ITypeDataDB.text + ","
	      + IColumnNamesTableReportSalesOrder.COLUMN_ORDERVALUE   + ITypeDataDB.text + ","
	      + IColumnNamesTableReportSalesOrder.COLUMN_CURRENCY     + ITypeDataDB.text + ","
	      + IColumnNamesTableReportSalesOrder.COLUMN_INVOICE      + ITypeDataDB.text 
     	  +");";

}
