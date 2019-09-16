package com.integra.hargapangan.model.detailPangan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("perubahan")
	private List<PerubahanItem> perubahan;

	@SerializedName("info")
	private Info info;

	public void setPerubahan(List<PerubahanItem> perubahan){
		this.perubahan = perubahan;
	}

	public List<PerubahanItem> getPerubahan(){
		return perubahan;
	}

	public void setInfo(Info info){
		this.info = info;
	}

	public Info getInfo(){
		return info;
	}
}