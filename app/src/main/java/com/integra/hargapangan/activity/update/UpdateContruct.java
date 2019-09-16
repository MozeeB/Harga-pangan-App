package com.integra.hargapangan.activity.update;


import com.integra.hargapangan.model.ResultItem;

import java.util.List;

public interface UpdateContruct {
    interface View{
        void onSuccess(String message);
        void onFailed(String message);
        void getKomoditas();
        void showList(List itemKomoditas);
        void showProgress();
        void hideProgress();
        void showUpdateDialog(ResultItem resultItems);

    }
    interface Presenter{
        void updatePangan(int id_harga, int id_komoditas, int id_pasar, String user_id, String tanggal , int harga);
        void showDialog(ResultItem resultItems);
    }
}
