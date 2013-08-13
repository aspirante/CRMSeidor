package com.crystalis.model;

import com.crystalis.listeners.Response;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class DatesModel implements Response{
	
	@SerializedName("Customer")
	String customerId;
	@SerializedName("InitialHour")
	String initialhour;
	@SerializedName("EndHour")
	String endhour;
	@SerializedName("Subject")
	String subject;
	@SerializedName("Place")
	String place;
	@SerializedName("Status")
	String status;
	
       
	
	public DatesModel(String customerId, String initialhour, String endhour,
			String subject, String place, String status) {

		this.customerId = customerId;
		this.initialhour= initialhour;
		this.endhour = endhour;
		this.subject = subject;
		this.place = place;
		this.status = status;
		
	}

	public DatesModel() {

	}

	public String getcustomerId() {
		return customerId;
	}

	public void setcustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getinitialhour() {
		return initialhour;
	}

	public void setinitialhour(String initialhour) {
		this.initialhour = initialhour;
	}

	public String getendhour() {
		return endhour;
	}

	public void setendhour(String endhour) {
		this.endhour = endhour;
	}

	public String getsubject() {
		return subject;
	}

	public void setsubject(String subject) {
		this.subject = subject;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	public String getstatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	@Override
	public Response jsonToObject(String jsonString) {
	    DatesModel data = new Gson().fromJson(jsonString, this.getClass());
	    return data;
	}
}
