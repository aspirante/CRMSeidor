package com.crystalis.model;

import com.crystalis.listeners.Response;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class ItemMaterial implements Response{
	@SerializedName("OrderId")
	String orderId;
	@SerializedName("Item")
	String item;
	@SerializedName("Material")
	String material;
	@SerializedName("Description")
	String description;
	@SerializedName("Plant")
	String plant;
	@SerializedName("Quantity")
	String quantity;
	@SerializedName("UoM")
	String uoM;
	@SerializedName("Value")
	String value;
       
	
	public ItemMaterial(String orderId, String item, String material,
			String description, String plant, String quantity, String uoM,
			String value) {

		this.orderId = orderId;
		this.item = item;
		this.material = material;
		this.description = description;
		this.plant = plant;
		this.quantity = quantity;
		this.uoM = uoM;
		this.value = value;
	}

	public ItemMaterial() {

	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getUoM() {
		return uoM;
	}

	public void setUoM(String uoM) {
		this.uoM = uoM;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public Response jsonToObject(String jsonString) {
	    ItemMaterial data = new Gson().fromJson(jsonString, this.getClass());
	    return data;
	}
	
	
	
}
