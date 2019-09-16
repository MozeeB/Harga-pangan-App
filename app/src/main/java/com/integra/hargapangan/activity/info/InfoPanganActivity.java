package com.integra.hargapangan.activity.info;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.integra.hargapangan.GlobalActivtiy;
import com.integra.hargapangan.R;
import com.integra.hargapangan.activity.login.LoginActivity;
import com.integra.hargapangan.adapter.AdapterPangan;
import com.integra.hargapangan.model.ResponseKomoditas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class InfoPanganActivity extends AppCompatActivity {

    @BindView(R.id.tgl_info)
    TextView dateView;
    @BindView(R.id.shimmer_container)
    ShimmerFrameLayout shimmerContainer;
    @BindView(R.id.rv_pangan)
    RecyclerView rvPangan;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.img_login)
    ImageView imgLogin;

    private AdapterPangan adapterPangan;

    private DatePickerDialog datePickerDialog;

    String tanggal;
    private int year, month, day;

    SimpleDateFormat dateFormat;
    static String[] bulan = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni"
            , "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    String formatTanggal;

    Context context;


    private String pasar = String.valueOf(0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pangan);
        ButterKnife.bind(this);


        swipeRefresh.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);
            }
        });

        context = getApplicationContext();
        rvPangan.setHasFixedSize(true);
        rvPangan.setLayoutManager(new GridLayoutManager(context, 3));

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        tanggal = dateFormat.format(cal.getTime());

        showDate();
        bacaInfoPangan();

        spinPasar();
        imgLogin.setVisibility(View.VISIBLE);

    }

    private void spinPasar() {
        List<String> adapter = new ArrayList<>();
        adapter.add("Kab.Seleman");
        adapter.add("Kesamben");
        adapter.add("Lodoyo");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, adapter);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selected)) {
                    if (selected.equals("Kab.Seleman")) {
                        pasar = String.valueOf(0);
                        bacaInfoPangan();
                    } else if (selected.equals("Kesamben")) {
                        pasar = String.valueOf(1);
                        bacaInfoPangan();

                    } else if (selected.equals("Lodoyo")) {
                        pasar = String.valueOf(2);
                        bacaInfoPangan();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @SuppressWarnings("dea" + "precation")
    public void setDate(View view) {
        showDialog(999);
        Toasty.warning(getApplicationContext(), "Silahkan Pilih", Toasty.LENGTH_SHORT).show();
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            datePickerDialog = new DatePickerDialog(this, myDateListener, year, month, day);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000);
            Calendar minDate = Calendar.getInstance();

            minDate.set(Calendar.DAY_OF_MONTH, 01);
            minDate.set(Calendar.MONTH, 10);
            minDate.set(Calendar.YEAR, 2017);

            datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
            datePickerDialog.show();
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int y, int m, int d) {
            year = y;
            month = m;
            day = d;

            Calendar cal = new GregorianCalendar(y, m, d);
            tanggal = dateFormat.format(cal.getTime());
            formatTanggal = day + " " + bulan[cal.get(Calendar.MONTH)] + " " + year;

            showDate();
            bacaInfoPangan();
        }
    };


    private void showDate() {
        dateView.setText(formatTanggal);
    }

    private void bacaInfoPangan() {
        showProgress();
        AndroidNetworking.post( GlobalActivtiy.DATA + "data-harga/" + pasar + "/" + tanggal)
                .setPriority(Priority.HIGH)
                .addHeaders(GlobalActivtiy.URLAUTH, GlobalActivtiy.URLHEADER)
                .build()
                .getAsObject(ResponseKomoditas.class, new ParsedRequestListener<ResponseKomoditas>() {
                    @Override
                    public void onResponse(ResponseKomoditas response) {
                        hideProgress();
                        showList(response.getResult());
                    }

                    @Override
                    public void onError(ANError anError) {
                        hideProgress();
                        Toasty.error(InfoPanganActivity.this, "Failed!", Toasty.LENGTH_LONG).show();

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bacaInfoPangan();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shimmerContainer.stopShimmer();
    }

    public void showList(List itemKomoditas) {
        rvPangan.setVisibility(View.VISIBLE);
        adapterPangan = new AdapterPangan(this, itemKomoditas);
        rvPangan.setAdapter(adapterPangan);

    }

    public void showProgress() {
        swipeRefresh.setRefreshing(true);
        shimmerContainer.startShimmer();
    }

    public void hideProgress() {
        swipeRefresh.setRefreshing(false);
        shimmerContainer.stopShimmer();
        shimmerContainer.setVisibility(View.GONE);
    }

    @OnClick(R.id.img_login)
    public void onViewClicked() {
        startActivity(new Intent(InfoPanganActivity.this, LoginActivity.class));
    }

    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        } else {
            Toasty.warning(getApplicationContext(), "Tekan sekali lagi untuk keluar", Toasty.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }
}
