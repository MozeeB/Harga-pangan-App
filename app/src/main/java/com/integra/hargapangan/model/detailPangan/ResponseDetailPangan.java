package com.integra.hargapangan.model.detailPangan;

import com.google.gson.annotations.SerializedName;

public class ResponseDetailPangan{

	@SerializedName("result")
	private Result result;

	@SerializedName("success")
	private boolean success;

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}
}