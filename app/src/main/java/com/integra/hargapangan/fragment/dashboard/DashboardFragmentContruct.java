package com.integra.hargapangan.fragment.dashboard;

public interface DashboardFragmentContruct {
    interface View{
        void toInfo();
        void toUpdate();
        void toKomoditas();
        void toPasar();

    }
    interface Presenter{
        void toInfo();
        void toUpdate();
        void toKomoditas();
        void toPasar();

    }
}
