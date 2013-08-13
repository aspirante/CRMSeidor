package com.crystalis.model;

import com.crystalis.listeners.Response;
import com.google.gson.Gson;

public class ItemHeader implements Response{
	
	private String OrderId;
	private String Item;
	private String Material;
	private String Description;
	private String Plant;
	private String Quantity;
	private String UoM;
	private String Value;
	
	/**
	 * @param orderId
	 * @param item
	 * @param material
	 * @param description
	 * @param plant
	 * @param quantity
	 * @param uoM
	 * @param value
	 */
	public ItemHeader(String orderId, String item, String material,
		String description, String plant, String quantity, String uoM,
		String value) {
	    OrderId = orderId;
	    Item = item;
	    Material = material;
	    Description = description;
	    Plant = plant;
	    Quantity = quantity;
	    UoM = uoM;
	    Value = value;
	}
	
	
	
	/**
	 * 
	 */
	public ItemHeader() {}



	public String getOrderId() {
	    return OrderId;
	}
	public void setOrderId(String orderId) {
	    OrderId = orderId;
	}
	public String getItem() {
	    return Item;
	}
	public void setItem(String item) {
	    Item = item;
	}
	public String getMaterial() {
	    return Material;
	}
	public void setMaterial(String material) {
	    Material = material;
	}
	public String getDescription() {
	    return Description;
	}
	public void setDescription(String description) {
	    Description = description;
	}
	public String getPlant() {
	    return Plant;
	}
	public void setPlant(String plant) {
	    Plant = plant;
	}
	public String getQuantity() {
	    return Quantity;
	}
	public void setQuantity(String quantity) {
	    Quantity = quantity;
	}
	public String getUoM() {
	    return UoM;
	}
	public void setUoM(String uoM) {
	    UoM = uoM;
	}
	public String getValue() {
	    return Value;
	}
	public void setValue(String value) {
	    Value = value;
	}

	@Override
	public Response jsonToObject(String jsonString) {
	    ItemHeader data = new Gson().fromJson(jsonString, this.getClass());
	return data;
	}
}
