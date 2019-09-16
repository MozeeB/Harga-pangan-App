package com.integra.hargapangan.model.login;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin{

	@SerializedName("result")
	private String result;

	@SerializedName("success")
	private boolean success;

	public String getResult(){
		return result;
	}

	public boolean isSuccess(){
		return success;
	}
}