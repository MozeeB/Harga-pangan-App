package com.integra.hargapangan.activity.detailInfoPangan;

import com.integra.hargapangan.model.detailPangan.Info;
import com.integra.hargapangan.model.detailPangan.PerubahanItem;

import java.util.List;

public interface DetailPanganContruct {
    interface View{
        void onSuccess(String message);
        void onFailed(String message);
        void showDetails(List<PerubahanItem> perubahanItems);
        void onGetDetailPangan();
        void showInfoHarga(Info itemList);
        void getInfoHarga();

    }
    interface Presenter{
        void onGetDetailPangan();
        void getInfoHarga();

    }
}
