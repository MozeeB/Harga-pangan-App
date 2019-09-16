package com.integra.hargapangan.activity.update;

import com.integra.hargapangan.model.ResultItem;
import com.integra.hargapangan.model.update.ResponseUpdatePangan;
import com.integra.hargapangan.network.RetrofitConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePresenter implements UpdateContruct.Presenter {

    private UpdateContruct.View view;

    public UpdatePresenter(UpdateContruct.View view) {
        this.view = view;
    }


    @Override
    public void updatePangan(int id_harga, int id_komoditas, int id_pasar, String user_id, String tanggal, int harga) {
        view.showProgress();
        RetrofitConfig.getInitRetrofit3().updatePangan(id_harga, id_komoditas, id_pasar, user_id, tanggal, harga)
                .enqueue(new Callback<ResponseUpdatePangan>() {
                    @Override
                    public void onResponse(Call<ResponseUpdatePangan> call, Response<ResponseUpdatePangan> response) {
                        view.hideProgress();
                        if (response.body().isSuccess() == true){
                            view.onSuccess(response.toString());
                            view.getKomoditas();
                        }else {
                            view.hideProgress();
                            view.onFailed(response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUpdatePangan> call, Throwable t) {
                        view.hideProgress();
                        view.onFailed(t.toString());
                    }
                });
    }




    @Override
    public void showDialog(ResultItem resultItems) {
        view.showUpdateDialog(resultItems);

    }


}
