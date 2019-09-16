package com.integra.hargapangan.model;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("id_harga")
	private int idHarga;

	@SerializedName("id_komoditas")
	private int idKomoditas;

	@SerializedName("id_kategori")
	private int idKategori;

	@SerializedName("analisa")
	private int analisa;

	@SerializedName("konstanta_pengali")
	private Object konstantaPengali;

	@SerializedName("color")
	private String color;

	@SerializedName("icon_komoditas")
	private String iconKomoditas;

	@SerializedName("hrg_kmr")
	private int hrgKmr;

	@SerializedName("active")
	private int active;

	@SerializedName("selisih")
	private int selisih;

	@SerializedName("hrg_skr")
	private int hrgSkr;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("nama_pendek")
	private String namaPendek;

	@SerializedName("satuan_komoditas")
	private String satuanKomoditas;

	@SerializedName("nama_komoditas")
	private String namaKomoditas;

	@SerializedName("status")
	private String status;

	public void setIdHarga(int idHarga){
		this.idHarga = idHarga;
	}

	public int getIdHarga(){
		return idHarga;
	}

	public void setIdKomoditas(int idKomoditas){
		this.idKomoditas = idKomoditas;
	}

	public int getIdKomoditas(){
		return idKomoditas;
	}

	public void setIdKategori(int idKategori){
		this.idKategori = idKategori;
	}

	public int getIdKategori(){
		return idKategori;
	}

	public void setAnalisa(int analisa){
		this.analisa = analisa;
	}

	public int getAnalisa(){
		return analisa;
	}

	public void setKonstantaPengali(Object konstantaPengali){
		this.konstantaPengali = konstantaPengali;
	}

	public Object getKonstantaPengali(){
		return konstantaPengali;
	}

	public void setColor(String color){
		this.color = color;
	}

	public String getColor(){
		return color;
	}

	public void setIconKomoditas(String iconKomoditas){
		this.iconKomoditas = iconKomoditas;
	}

	public String getIconKomoditas(){
		return iconKomoditas;
	}

	public void setHrgKmr(int hrgKmr){
		this.hrgKmr = hrgKmr;
	}

	public int getHrgKmr(){
		return hrgKmr;
	}

	public void setActive(int active){
		this.active = active;
	}

	public int getActive(){
		return active;
	}

	public void setSelisih(int selisih){
		this.selisih = selisih;
	}

	public int getSelisih(){
		return selisih;
	}

	public void setHrgSkr(int hrgSkr){
		this.hrgSkr = hrgSkr;
	}

	public int getHrgSkr(){
		return hrgSkr;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setNamaPendek(String namaPendek){
		this.namaPendek = namaPendek;
	}

	public String getNamaPendek(){
		return namaPendek;
	}

	public void setSatuanKomoditas(String satuanKomoditas){
		this.satuanKomoditas = satuanKomoditas;
	}

	public String getSatuanKomoditas(){
		return satuanKomoditas;
	}

	public void setNamaKomoditas(String namaKomoditas){
		this.namaKomoditas = namaKomoditas;
	}

	public String getNamaKomoditas(){
		return namaKomoditas;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}