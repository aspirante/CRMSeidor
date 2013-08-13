package com.crystalis.model;

import android.graphics.drawable.Drawable;

import com.crystalis.listeners.Response;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Imagen implements Response{
	
	@SerializedName("Imagen")
    private Drawable Imagen;
    
	
	public Imagen(Drawable Imagen) {
	    this.Imagen = Imagen;
	    
	}
	
	public Imagen() {
	}
	public Drawable getImagen() {
		return Imagen;
	}

	public void setImagen(Drawable Imagen) {
		this.Imagen = Imagen;
	}
	

	@Override
	public Response jsonToObject(String jsonString) {
	    Imagen data = new Gson().fromJson(jsonString, this.getClass());
	return data;
	}
}
