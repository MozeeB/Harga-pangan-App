package com.integra.hargapangan.activity.detailInfoPangan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.integra.hargapangan.R;
import com.integra.hargapangan.adapter.DetailAdapter;
import com.integra.hargapangan.model.detailPangan.Info;
import com.integra.hargapangan.model.detailPangan.PerubahanItem;
import com.integra.hargapangan.model.detailPangan.ResponseDetailPangan;
import com.integra.hargapangan.network.RetrofitConfig;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailInfoPangan extends AppCompatActivity implements DetailPanganContruct.View {

    @BindView(R.id.tv_harga_hari_ini_sekarang)
    TextView tvHargaHariIniSekarang;
    @BindView(R.id.tv_detail_hargaRata)
    TextView tvDetailHargaRata;
    @BindView(R.id.tv_harga_pasar_pakem)
    TextView tvHargaPasarPakem;
    @BindView(R.id.tv_detail_hargaTertinggi)
    TextView tvDetailHargaTertinggi;
    @BindView(R.id.tv_harga_terndah)
    TextView tvHargaTerndah;
    @BindView(R.id.tv_detail_hargaTerendah)
    TextView tvDetailHargaTerendah;
    @BindView(R.id.tv_tanggal_detail)
    TextView tvTanggalDetail;
    @BindView(R.id.rv_detail)
    RecyclerView rvDetail;
    @BindView(R.id.fabDetail)
    FloatingActionButton fabDetail;
    @BindView(R.id.namKOmDetail)
    TextView namKOmDetail;
    private int idKom;
    private Context context;

    private DetailAdapter detailAdapter;

    private DetailPanganPresenter presenter;

    String tanggal;
    private int year, month, day;

    SimpleDateFormat dateFormat;
    static String[] bulan = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni"
            , "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    String formatTanggal;

    private DatePickerDialog datePickerDialog;

    DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
    DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail);
        ButterKnife.bind(this);

        namKOmDetail.setText(getIntent().getStringExtra("namaKom"));
        idKom = getIntent().getIntExtra("idKomoditas", 0);

        formatRp.setCurrencySymbol("Rp ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        context = getApplicationContext();
        rvDetail.setHasFixedSize(true);
        rvDetail.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        presenter = new DetailPanganPresenter(this);
        presenter.onGetDetailPangan();

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        tanggal = dateFormat.format(cal.getTime());
        showDate();
        presenter.getInfoHarga();





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
            onGetDetailPangan();
        }
    };


    private void showDate() {
        tvTanggalDetail.setText(formatTanggal);
    }

    @SuppressWarnings("dea" + "precation")
    public void setDate(View view) {
        showDialog(999);
        Toasty.warning(getApplicationContext(), "Silahkan Pilih", Toasty.LENGTH_SHORT).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        onGetDetailPangan();
    }

    @Override
    public void onSuccess(String message) {
        Toasty.success(this, "Successfully!", Toasty.LENGTH_LONG).show();

    }

    @Override
    public void onFailed(String message) {
        Toasty.warning(this, "Sedang Menyiapkan....", Toasty.LENGTH_LONG).show();


    }

    @Override
    public void showDetails(List<PerubahanItem> perubahanItems) {
        detailAdapter = new DetailAdapter(this, perubahanItems);
        rvDetail.setAdapter(detailAdapter);
        rvDetail.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


    }

    @Override
    public void onGetDetailPangan() {
        int id = idKom;
        String tgl = tanggal;
        RetrofitConfig.getInitRetrofit2().getDetailPangan(id, tgl).enqueue(new Callback<ResponseDetailPangan>() {
            @Override
            public void onResponse(Call<ResponseDetailPangan> call, Response<ResponseDetailPangan> response) {
                if (response.body() != null){
                    ResponseDetailPangan responseDetailPangan = response.body();
                    if (response.body().getResult() != null){
                        showDetails(responseDetailPangan.getResult().getPerubahan());

                    }
                }else {
                    Toasty.error(DetailInfoPangan.this, "No Data Found!", Toasty.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDetailPangan> call, Throwable t) {
                Toasty.warning(DetailInfoPangan.this, "Sedang Menyiapkan....", Toasty.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void showInfoHarga(Info itemList) {
        tvHargaHariIniSekarang.setText(kursIndonesia.format(itemList.getHrgRata().getHarga()));
        tvHargaPasarPakem.setText(kursIndonesia.format(itemList.getHrgMax().getHarga()));
        tvHargaTerndah.setText(kursIndonesia.format(itemList.getHrgMin().getHarga()));

        tvDetailHargaRata.setText(itemList.getHrgRata().getNamaPasar());
        tvDetailHargaTertinggi.setText(itemList.getHrgMax().getNamaPasar());
        tvDetailHargaTerendah.setText(itemList.getHrgMin().getNamaPasar());

    }


    @Override
    public void getInfoHarga() {
        int id = idKom;
        String tgl = tanggal;
        RetrofitConfig.getInitRetrofit2().getInfoHarga(id, tgl).enqueue(new Callback<ResponseDetailPangan>() {
            @Override
            public void onResponse(Call<ResponseDetailPangan> call, Response<ResponseDetailPangan> response) {
                if (response.body() != null){
                    ResponseDetailPangan responseDetailPangan = response.body();
                    if (response.body().getResult().getInfo() != null){
                        showInfoHarga(responseDetailPangan.getResult().getInfo());

                    }
                }else {
                    Toasty.warning(DetailInfoPangan.this, "Sedang Menyiapkan....", Toasty.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseDetailPangan> call, Throwable t) {
                Toasty.warning(DetailInfoPangan.this, "Sedang Menyiapkan....", Toasty.LENGTH_LONG).show();

            }
        });

    }


}
