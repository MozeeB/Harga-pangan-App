package com.integra.hargapangan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.integra.hargapangan.activity.info.InfoPanganActivity;

public class SplashScreen extends AppCompatActivity {

    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        editor = getSharedPreferences("komoditas", MODE_PRIVATE).edit();
        SharedPreferences sharedPreferences = getSharedPreferences("komoditas", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("ingat", false)){
            setUpDelay();
        }else {
            setUpDelay2();
        }
    }


    private void setUpDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(SplashScreen.this, DashboardActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                finish();
            }
        }, 4000);
    }

    private void setUpDelay2() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(SplashScreen.this, InfoPanganActivity.class);
                startActivity(in);
                finish();
            }
        }, 4000);
    }

}
