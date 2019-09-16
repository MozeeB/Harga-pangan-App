package com.integra.hargapangan.activity.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.integra.hargapangan.DashboardActivity;
import com.integra.hargapangan.GlobalActivtiy;
import com.integra.hargapangan.R;
import com.integra.hargapangan.activity.info.InfoPanganActivity;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.btn_login_1)
    Button btnLogin1;
    @BindView(R.id.checkBox)
    CheckBox checkBox;

    private String username, password;
    private String hasilMD5Pass;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);



        editor = getSharedPreferences("komoditas", MODE_PRIVATE).edit();
        SharedPreferences sharedPreferences = getSharedPreferences("komoditas", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("ingat", false)){
            Intent in = new Intent(LoginActivity.this, DashboardActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);
            finish();
        }


    }

    @OnClick(R.id.btn_login_1)
    public void onViewClicked() {
        convert();
        checkValidate();
    }

    private void convert() {
        username = edtUsername.getText().toString();
        password = edtPassword.getText().toString();
        hasilMD5Pass = convertPassMd5(password);
    }

    private void checkValidate() {
        if (TextUtils.isEmpty(username)) {
            edtUsername.setError(getString(R.string.error_message));
            edtUsername.requestFocus();
        } else if (TextUtils.isEmpty(hasilMD5Pass)) {
            edtPassword.setError(getString(R.string.error_message));
            edtPassword.requestFocus();
        } else {
            OnLogin();
        }
    }

    private void OnLogin() {
        AndroidNetworking.post(GlobalActivtiy.LOGIN)
                .setPriority(Priority.HIGH)
                .addBodyParameter("username", username)
                .addBodyParameter("password", hasilMD5Pass)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                if (checkBox.isChecked()){
                                    editor.putBoolean("ingat", true);

                                }else {
                                    editor.putBoolean("ingat", false);
                                }
                                editor.apply();
                                Toasty.success(LoginActivity.this, "Berhasil Masuk!", Toasty.LENGTH_SHORT).show();
                                Intent in = new Intent(LoginActivity.this, DashboardActivity.class);
                                in.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                Hawk.put("username", username);
                                startActivity(in);
                                finish();

                            }else {
                                Toasty.error(LoginActivity.this, "Nama Pengguna dan Kata Sandi Salah!", Toasty.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public static String convertPassMd5(String pass) {
        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }

    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
            Intent a = new Intent(this, InfoPanganActivity.class);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        } else {
            Toasty.warning(getApplicationContext(), "Tekan sekali lagi", Toasty.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }
}
