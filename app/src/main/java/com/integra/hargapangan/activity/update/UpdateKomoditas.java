package com.integra.hargapangan.activity.update;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.integra.hargapangan.DashboardActivity;
import com.integra.hargapangan.R;
import com.integra.hargapangan.adapter.UpdateAdapter;
import com.integra.hargapangan.model.ResponseKomoditas;
import com.integra.hargapangan.model.ResultItem;
import com.integra.hargapangan.network.RetrofitConfig;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateKomoditas extends AppCompatActivity implements UpdateContruct.View {

    @BindView(R.id.toolbar2)
    Toolbar toolbar2;
    @BindView(R.id.rv_update)
    RecyclerView rvUpdate;
    @BindView(R.id.fabUpdate)
    FloatingActionButton fabUpdate;
    @BindView(R.id.swifUpdate)
    SwipeRefreshLayout swifUpdate;
    @BindView(R.id.tvUpdateTgl)
    TextView dateView;
    @BindView(R.id.spnUpdate)
    Spinner spnUpdate;
    @BindView(R.id.shimmer_container_update)
    ShimmerFrameLayout shimmerContainerUpdate;


    private DatePickerDialog datePickerDialog;

    String tanggal;
    private int year, month, day;

    SimpleDateFormat dateFormat;
    static String[] bulan = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni"
            , "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    String formatTanggal;

    Context context;

    private int pasar = 0;

    private UpdateAdapter updateAdapter;

    private UpdatePresenter updatePresenter;


    DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
    DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_update);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar2);

        updatePresenter = new UpdatePresenter(this);

        swifUpdate.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swifUpdate.setRefreshing(false);
            }
        });

        context = getApplicationContext();
        rvUpdate.setHasFixedSize(true);
        rvUpdate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        tanggal = dateFormat.format(cal.getTime());


        showDate();
        getKomoditas();

        spinPasar();

    }

    private void spinPasar() {
        List<String> adapter = new ArrayList<>();
        adapter.add("Kab.Seleman");
        adapter.add("Kesamben");
        adapter.add("Lodoyo");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, adapter);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnUpdate.setAdapter(dataAdapter);

        spnUpdate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selected)) {
                    if (selected.equals("Kab.Seleman")) {
                        pasar = 0;
                        getKomoditas();
                    } else if (selected.equals("Kesamben")) {
                        pasar = 1;
                        getKomoditas();

                    } else if (selected.equals("Lodoyo")) {
                        pasar = 2;
                        getKomoditas();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showDate() {
        dateView.setText(formatTanggal);
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
            getKomoditas();
        }
    };


    @Override
    public void onSuccess(String message) {
        Toasty.success(this, "Update Berhasil!", Toasty.LENGTH_LONG).show();

    }

    @Override
    public void onFailed(String message) {
        Toasty.error(this, "Update Gagal!", Toasty.LENGTH_LONG).show();

    }

    @Override
    public void getKomoditas() {
        showProgress();
        RetrofitConfig.getInitRetrofit().getKomoditas(pasar, tanggal).enqueue(new Callback<ResponseKomoditas>() {
            @Override
            public void onResponse(Call<ResponseKomoditas> call, Response<ResponseKomoditas> response) {
                if (response.body() != null) {
                    ResponseKomoditas responseKomoditas = response.body();
                    if (responseKomoditas.getResult() != null) {
                        hideProgress();
                        showList(responseKomoditas.getResult());
                    }
                } else {
                    hideProgress();
                }
            }

            @Override
            public void onFailure(Call<ResponseKomoditas> call, Throwable t) {
                hideProgress();
            }
        });
    }


    public void showList(List itemKomoditas) {
        rvUpdate.setVisibility(View.VISIBLE);
        updateAdapter = new UpdateAdapter(this, itemKomoditas, updatePresenter);
        rvUpdate.setAdapter(updateAdapter);


    }

    @Override
    public void showProgress() {
        swifUpdate.setRefreshing(true);
        shimmerContainerUpdate.startShimmer();
    }

    @Override
    public void hideProgress() {
        swifUpdate.setRefreshing(false);
        shimmerContainerUpdate.startShimmer();
        shimmerContainerUpdate.setVisibility(View.GONE);

    }

    @Override
    public void showUpdateDialog(ResultItem resultItems) {

        formatRp.setCurrencySymbol("");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        final int id_harga = resultItems.getIdHarga();
        final int id_komoditas = resultItems.getIdKomoditas();


        final AlertDialog.Builder builder = new AlertDialog.Builder(UpdateKomoditas.this);
        builder.setTitle("Update " + resultItems.getNamaKomoditas());

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.update_dialog, (ViewGroup) findViewById(android.R.id.content), false);
        final EditText input = (EditText) viewInflated.findViewById(R.id.inputHarga);
        input.setHint(String.valueOf(resultItems.getHrgSkr()));
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String harga = input.getText().toString();
                updatePresenter.updatePangan(id_harga, id_komoditas, pasar, "integra", tanggal, Integer.parseInt(harga));
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
            Intent a = new Intent(this, DashboardActivity.class);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        } else {
            Toasty.warning(getApplicationContext(), "Tekan sekali lagi untuk kembali", Toasty.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getKomoditas();
    }

    @Override
    protected void onPause() {
        shimmerContainerUpdate.stopShimmer();
        super.onPause();
    }


}
