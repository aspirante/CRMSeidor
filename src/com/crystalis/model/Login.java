package com.crystalis.model;

import com.crystalis.listeners.Response;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;


public class Login implements Response{
    
//    @SerializedName("__metadata")
//    private String __metadata;
    
    @SerializedName("UserId")
    private String UserId;

    @SerializedName("Pass")
    private String Pass;

    @SerializedName("Message")
    private String Message;
    
    @SerializedName("Useralias")
    private String useralias;

    public String getUserId() {
	return UserId;
    }

    public void setUserId(String userId) {
	UserId = userId;
    }

    public String getPass() {
	return Pass;
    }

    public void setPass(String pass) {
	Pass = pass;
    }

    public String getMessage() {
	return Message;
    }

    public void setMessage(String message) {
	Message = message;
    }

    public String getUseralias() {
        return useralias;
    }

    public void setUseralias(String useralias) {
        this.useralias = useralias;
    }

    @Override
    public Response jsonToObject(String jsonString) {
	Login info = new Gson().fromJson(jsonString, this.getClass());
	return info;
    }
}
