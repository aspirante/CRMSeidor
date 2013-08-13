package com.crystalis.model;

import com.crystalis.listeners.Response;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Region implements Response{
    
    @SerializedName("CountryId")
    private String countryid;
    @SerializedName("Region")
    private String region;
    @SerializedName("Description")
    private String description;

    /**
     * 
     */
    public Region() { }

    /**
     * @param countryid
     * @param region
     * @param description
     */
    public Region(String countryid, String region, String description) {
	this.countryid = countryid;
	this.region = region;
	this.description = description;
    }

   public String getCountryid() {
        return countryid;
    }

   public void setCountryid(String countryid) {
        this.countryid = countryid;
    }

   public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   @Override
    public Response jsonToObject(String jsonString) {
	Region data = new Gson().fromJson(jsonString, this.getClass());
	return data;
    }

}
