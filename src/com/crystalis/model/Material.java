package com.crystalis.model;

import com.crystalis.listeners.Response;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Material implements Response{

    @SerializedName("MaterialId")
    private String materialId;
    
    @SerializedName("Descripcion")
    private String descripcion;
    
    @SerializedName("Stock")
    private String stock;
    
    @SerializedName("Precio")
    private  String precio;
	
	public Material(String materialId, String descripcion, String stock, String precio) {
		this.materialId = materialId;
		this.descripcion = descripcion;
		this.stock = stock;
		this.precio = precio;
	}

	public Material() {
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	@Override
    public Response jsonToObject(String jsonString) {
	Material info = new Gson().fromJson(jsonString, this.getClass());
	return info;
    }

}
