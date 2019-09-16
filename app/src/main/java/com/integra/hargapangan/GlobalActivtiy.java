package com.integra.hargapangan;

import android.app.Application;
import android.util.Base64;

import com.androidnetworking.AndroidNetworking;
import com.orhanobut.hawk.Hawk;


public class GlobalActivtiy extends Application {

    public native String getNativeKey1();
    public native String getLoginKey();
    public native String getBaseUrlIcon();
    public native String getBaseUrlDetail();
    public native String getBaseUrlHeader();
    public native String getBaseUrlAuth();
    public native String getBaseUpdate();

    public static String key1, key2, key3, key4, key5, key6, key7;
    public static  String DATA;
    public static  String LOGIN, URLICON, URLDETAIL, URLHEADER, URLAUTH, URLUPDATE;

    @Override
    public void onCreate() {
        super.onCreate();

        key1 = new String(Base64.decode(getNativeKey1(),Base64.DEFAULT));
        DATA = key1;
        key2 = new String(Base64.decode(getLoginKey(),Base64.DEFAULT));
        LOGIN = key2;
        key3 = new String(Base64.decode(getBaseUrlIcon(), Base64.DEFAULT));
        URLICON = key3;
        key4 = new String(Base64.decode(getBaseUrlDetail(), Base64.DEFAULT));
        URLDETAIL = key4;
        key5 = new String(Base64.decode(getBaseUrlHeader(), Base64.DEFAULT));
        URLHEADER = key5;
        key6 = new String(Base64.decode(getBaseUrlAuth(), Base64.DEFAULT));
        URLAUTH = key6;
        key7 = new String(Base64.decode(getBaseUpdate(),Base64.DEFAULT));
        URLUPDATE = key7;

        AndroidNetworking.initialize(getApplicationContext());
        Hawk.init(this).build();




    }
}
