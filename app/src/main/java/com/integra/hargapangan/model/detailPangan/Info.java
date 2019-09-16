package com.integra.hargapangan.model.detailPangan;

import com.google.gson.annotations.SerializedName;

public class Info{

	@SerializedName("data")
	private Data data;

	@SerializedName("hrg_min")
	private HrgMin hrgMin;

	@SerializedName("hrg_max")
	private HrgMax hrgMax;

	@SerializedName("hrg_rata")
	private HrgRata hrgRata;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setHrgMin(HrgMin hrgMin){
		this.hrgMin = hrgMin;
	}

	public HrgMin getHrgMin(){
		return hrgMin;
	}

	public void setHrgMax(HrgMax hrgMax){
		this.hrgMax = hrgMax;
	}

	public HrgMax getHrgMax(){
		return hrgMax;
	}

	public void setHrgRata(HrgRata hrgRata){
		this.hrgRata = hrgRata;
	}

	public HrgRata getHrgRata(){
		return hrgRata;
	}
}