package com.integra.hargapangan.model.detailPangan;

import com.google.gson.annotations.SerializedName;

public class PerubahanItem{

	@SerializedName("id_pasar")
	private int idPasar;

	@SerializedName("hrg_skr")
	private int hrgSkr;

	@SerializedName("foto")
	private String foto;

	@SerializedName("nama_pasar")
	private String namaPasar;

	@SerializedName("hrg_kmr")
	private int hrgKmr;

	@SerializedName("selisih")
	private int selisih;

	@SerializedName("status")
	private String status;

	public void setIdPasar(int idPasar){
		this.idPasar = idPasar;
	}

	public int getIdPasar(){
		return idPasar;
	}

	public void setHrgSkr(int hrgSkr){
		this.hrgSkr = hrgSkr;
	}

	public int getHrgSkr(){
		return hrgSkr;
	}

	public void setFoto(String foto){
		this.foto = foto;
	}

	public String getFoto(){
		return foto;
	}

	public void setNamaPasar(String namaPasar){
		this.namaPasar = namaPasar;
	}

	public String getNamaPasar(){
		return namaPasar;
	}

	public void setHrgKmr(int hrgKmr){
		this.hrgKmr = hrgKmr;
	}

	public int getHrgKmr(){
		return hrgKmr;
	}

	public void setSelisih(int selisih){
		this.selisih = selisih;
	}

	public int getSelisih(){
		return selisih;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}