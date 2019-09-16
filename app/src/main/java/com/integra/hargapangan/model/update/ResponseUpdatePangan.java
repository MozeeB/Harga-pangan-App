package com.integra.hargapangan.model.update;

import com.google.gson.annotations.SerializedName;

public class ResponseUpdatePangan{

	@SerializedName("msg")
	private String msg;

	@SerializedName("success")
	private boolean success;

	public String getMsg(){
		return msg;
	}

	public boolean isSuccess(){
		return success;
	}
}