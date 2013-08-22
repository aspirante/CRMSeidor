package com.crystalis.interfaces;

public interface IQuerys {
	
	  public String querySales = "SELECT customerid,count(*) as total, sum(ordervalue) as suma FROM salesorder where invoice != '' group by customerid";

	  public String queryInvoiced = "SELECT count(*) as total FROM salesorder where invoice != ''";
	  public String queryNoIncoived = "SELECT count(*) as total FROM salesorder where invoice ==''";


}
