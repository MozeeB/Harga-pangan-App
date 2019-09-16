package com.integra.hargapangan.network;

import com.integra.hargapangan.GlobalActivtiy;
import com.integra.hargapangan.model.ResponseKomoditas;
import com.integra.hargapangan.model.detailPangan.ResponseDetailPangan;
import com.integra.hargapangan.model.login.ResponseLogin;
import com.integra.hargapangan.model.update.ResponseUpdatePangan;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @Headers({GlobalActivtiy.BASE_AUTH})
    @GET("data-harga/{id_pasar}/{tanggal}")
    Call<ResponseKomoditas> getKomoditas(@Path("id_pasar") int id_pasar,
                                         @Path("tanggal") String tanggal);

    //getDetail
    @Headers({GlobalActivtiy.BASE_AUTH})
    @GET("{id}/{tanggal}")
    Call<ResponseDetailPangan> getDetailPangan(@Path("id") int id,
                                               @Path("tanggal") String tanggal);

    @Headers({GlobalActivtiy.BASE_AUTH})
    @GET("{id}/{tanggal}")
    Call<ResponseDetailPangan> getInfoHarga(@Path("id") int id,
                                            @Path("tanggal") String tanggal);


    //login
    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseLogin> onLogin(@Field("username") String username,
                                @Field("password") String password);

    //update
    @Headers({GlobalActivtiy.BASE_AUTH})
    @GET("update-harga/{id_harga}/{id_komoditas}/{id_pasar}/{user_id}/{tanggal}/{harga}")
    Call<ResponseUpdatePangan>  updatePangan(@Path("id_harga") int id_harga,
                                             @Path("id_komoditas") int id_komoditas,
                                             @Path("id_pasar") int id_pasar,
                                             @Path("user_id") String user_id,
                                             @Path("tanggal") String tanggal,
                                             @Path("harga") int harga);



}
