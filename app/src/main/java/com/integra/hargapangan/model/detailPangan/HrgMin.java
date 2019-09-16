package com.integra.hargapangan.model.detailPangan;

import com.google.gson.annotations.SerializedName;

public class HrgMin{

	@SerializedName("harga")
	private int harga;

	@SerializedName("nama_pasar")
	private String namaPasar;

	public void setHarga(int harga){
		this.harga = harga;
	}

	public int getHarga(){
		return harga;
	}

	public void setNamaPasar(String namaPasar){
		this.namaPasar = namaPasar;
	}

	public String getNamaPasar(){
		return namaPasar;
	}
}