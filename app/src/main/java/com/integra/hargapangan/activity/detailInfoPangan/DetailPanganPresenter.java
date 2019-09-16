package com.integra.hargapangan.activity.detailInfoPangan;

public class DetailPanganPresenter implements DetailPanganContruct.Presenter {

    private DetailPanganContruct.View view;

    public DetailPanganPresenter(DetailPanganContruct.View view) {
        this.view = view;
    }

    @Override
    public void onGetDetailPangan() {
        view.onGetDetailPangan();
    }

    @Override
    public void getInfoHarga() {
        view.getInfoHarga();

    }
}
