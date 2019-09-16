package com.integra.hargapangan.fragment.dashboard;

public class DashboardFragmentPresenter implements DashboardFragmentContruct.Presenter {

    DashboardFragmentContruct.View view;

    public DashboardFragmentPresenter(DashboardFragmentContruct.View view) {
        this.view = view;
    }

    @Override
    public void toInfo() {
        view.toInfo();

    }

    @Override
    public void toUpdate() {
        view.toUpdate();
    }

    @Override
    public void toKomoditas() {
        view.toKomoditas();

    }

    @Override
    public void toPasar() {
        view.toPasar();

    }
}
