package com.crystalis.model;

import com.crystalis.listeners.Response;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Country implements Response {
    
    @SerializedName("CountryId")
    private String countryId;
    
    @SerializedName("Description")
    private String description;
        
    
    /**
     * 
     */
    public Country() { }


    /**
     * @param countryId
     * @param description
     */
    public Country(String countryId, String description) {
	this.countryId = countryId;
	this.description = description;
    }


    public String getCountryId() {
        return countryId;
    }


    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public Response jsonToObject(String jsonString) {
	Country data = new Gson().fromJson(jsonString, this.getClass());
	return data;
    }

}
