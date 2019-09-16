package com.integra.hargapangan.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseKomoditas{

	@SerializedName("result")
	private List<ResultItem> result;

	@SerializedName("success")
	private boolean success;

	public void setResult(List<ResultItem> result){
		this.result = result;
	}

	public List<ResultItem> getResult(){
		return result;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}
}