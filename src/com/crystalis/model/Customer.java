package com.crystalis.model;

import com.crystalis.listeners.Response;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Customer implements Response{
    
   @SerializedName("CustomerId")
   private String customerid;
   
   @SerializedName("Name")
   private String name;
   
   @SerializedName("Street")
   private String street;
   
   @SerializedName("Number")
   private String number;
   
   @SerializedName("City")
   private String city;
   
   @SerializedName("District")
   private String district;
   
   @SerializedName("State")
   private String state;
   
   @SerializedName("ZIP")
   private String zip;
   
   @SerializedName("Country")
   private String country;
   
   @SerializedName("RFC")
   private String rfc;
   
   @SerializedName("Tel")
   private String tel;
   
   @SerializedName("Movil")
   private String movil;
   
   @SerializedName("Email")
   private String email;

    /**
     * @param customerid
     * @param name
     * @param street
     * @param city
     * @param state
     * @param zip
     * @param country
     * @param rfc
     * @param tel
     * @param movil
     * @param email
     */
    public Customer(String customerid, String name, String street, String number, String city, String district, String state, String zip, String country, String rfc, String tel,
	    String movil, String email) {
	this.customerid = customerid;
	this.name = name;
	this.street = street;
	this.number = number;
	this.city = city;
	this.district = district;
	this.state = state;
	this.zip = zip;
	this.country = country;
	this.rfc = rfc;
	this.tel = tel;
	this.movil = movil;
	this.email = email;
    }

    /**
 * 
 */
    public Customer() {  }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public Response jsonToObject(String jsonString) {
	Customer data = new Gson().fromJson(jsonString, this.getClass());
	return data;
    }
}
