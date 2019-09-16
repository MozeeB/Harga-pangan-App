package com.integra.hargapangan.model.detailPangan;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("icon_komoditas")
	private String iconKomoditas;

	@SerializedName("nama_komoditas")
	private String namaKomoditas;

	public void setIconKomoditas(String iconKomoditas){
		this.iconKomoditas = iconKomoditas;
	}

	public String getIconKomoditas(){
		return iconKomoditas;
	}

	public void setNamaKomoditas(String namaKomoditas){
		this.namaKomoditas = namaKomoditas;
	}

	public String getNamaKomoditas(){
		return namaKomoditas;
	}
}