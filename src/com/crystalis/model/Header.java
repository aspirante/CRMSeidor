package com.crystalis.model;

import com.crystalis.listeners.Response;
import com.google.gson.Gson;

public class Header implements Response {
	
    	String OrderId;
	String DocumentType;
	String DocumentDate;//>2012-06-27T00:00:00</d:DocumentDate>
	String CustomerId;
	String SalesOrg;
	String DistChannel;
	String Division;
	String OrderValue;
	String Currency;
	String Invoice;

	/**
	 * @param orderId
	 * @param documentType
	 * @param documentDate
	 * @param customerId
	 * @param salesOrg
	 * @param distChannel
	 * @param division
	 * @param orderValue
	 * @param currency
	 * @param invoice
	 */
	public Header(String orderId, String documentType,
		String documentDate, String customerId, String salesOrg,
		String distChannel, String division, String orderValue,
		String currency, String invoice) {
	    OrderId = orderId;
	    DocumentType = documentType;
	    DocumentDate = documentDate;
	    CustomerId = customerId;
	    SalesOrg = salesOrg;
	    DistChannel = distChannel;
	    Division = division;
	    OrderValue = orderValue;
	    Currency = currency;
	    Invoice = invoice;
	}

	public Header() {}

	public String getOrderId() {
	    return OrderId;
	}

	public void setOrderId(String orderId) {
	    OrderId = orderId;
	}

	public String getDocumentType() {
	    return DocumentType;
	}

	public void setDocumentType(String documentType) {
	    DocumentType = documentType;
	}

	public String getDocumentDate() {
	    return DocumentDate;
	}

	public void setDocumentDate(String documentDate) {
	    DocumentDate = documentDate;
	}

	public String getCustomerId() {
	    return CustomerId;
	}

	public void setCustomerId(String customerId) {
	    CustomerId = customerId;
	}

	public String getSalesOrg() {
	    return SalesOrg;
	}

	public void setSalesOrg(String salesOrg) {
	    SalesOrg = salesOrg;
	}

	public String getDistChannel() {
	    return DistChannel;
	}

	public void setDistChannel(String distChannel) {
	    DistChannel = distChannel;
	}

	public String getDivision() {
	    return Division;
	}

	public void setDivision(String division) {
	    Division = division;
	}

	public String getOrderValue() {
	    return OrderValue;
	}

	public void setOrderValue(String orderValue) {
	    OrderValue = orderValue;
	}

	public String getCurrency() {
	    return Currency;
	}

	public void setCurrency(String currency) {
	    Currency = currency;
	}

	public String getInvoice() {
	    return Invoice;
	}

	public void setInvoice(String invoice) {
	    this.Invoice = invoice;
	}

	@Override
	public Response jsonToObject(String jsonString) {
	    Header data = new Gson().fromJson(jsonString, this.getClass());
	return data;
	}	
}
