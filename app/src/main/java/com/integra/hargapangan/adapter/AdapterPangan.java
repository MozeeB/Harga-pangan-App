package com.integra.hargapangan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.integra.hargapangan.GlobalActivtiy;
import com.integra.hargapangan.R;
import com.integra.hargapangan.activity.detailInfoPangan.DetailInfoPangan;
import com.integra.hargapangan.model.ResultItem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterPangan extends RecyclerView.Adapter<AdapterPangan.ViewHolder> {



    private Context context;
    private List<ResultItem> itemKomoditas;

    DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
    DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

    public AdapterPangan(Context context, List<ResultItem> itemKomoditas) {
        this.context = context;
        this.itemKomoditas = itemKomoditas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pangan, viewGroup, false));
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final ResultItem resultItem = itemKomoditas.get(i);

        formatRp.setCurrencySymbol("");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        Glide.with(context)
                .load(GlobalActivtiy.URLICON + resultItem.getIconKomoditas() + ".png")
                .into(viewHolder.imgIconKmdts);

        viewHolder.tvNamaKomoditas.setText(resultItem.getNamaKomoditas());
        viewHolder.tvHarga.setText(kursIndonesia.format(resultItem.getHrgSkr()));
        viewHolder.tvSatuanKomoditas.setText(" /" + resultItem.getSatuanKomoditas());

        if (resultItem.getStatus().equals("tetap")) {
            viewHolder.llstatus.setBackgroundColor(R.color.colorPrimary);
            viewHolder.tvStatus.setText(resultItem.getStatus());
            viewHolder.imgStatus.setImageResource(R.drawable.ic_equal);
            viewHolder.backKom.setBackgroundResource(R.drawable.bg_rata);

        } else if (resultItem.getStatus().equals("turun")) {
            viewHolder.llstatus.setBackgroundColor(R.color.harga_rendah);
            viewHolder.tvStatus.setText(resultItem.getStatus());
            viewHolder.imgStatus.setImageResource(R.drawable.ic_arrow_downward_black_24dp);
            viewHolder.backKom.setBackgroundResource(R.drawable.bg_success);

        } else if (resultItem.getStatus().equals("naik")){
            viewHolder.llstatus.setBackgroundColor(R.color.harga_tinggi);
            viewHolder.tvStatus.setText(resultItem.getStatus());
            viewHolder.imgStatus.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
            viewHolder.backKom.setBackgroundResource(R.drawable.bg_up);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailInfoPangan.class);
                intent.putExtra("namaKom", resultItem.getNamaKomoditas());
                intent.putExtra("idKomoditas", resultItem.getIdKomoditas());
                intent.putExtra("idKategori", resultItem.getIdKategori());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemKomoditas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGambar)
        ImageView imgIconKmdts;
        @BindView(R.id.tvnama)
        TextView tvNamaKomoditas;
        @BindView(R.id.tvharga)
        TextView tvHarga;
        @BindView(R.id.tvsatuan)
        TextView tvSatuanKomoditas;
        @BindView(R.id.tvstatus)
        TextView tvStatus;
        @BindView(R.id.llstatus)
        LinearLayout llstatus;
        @BindView(R.id.ivstatus)
        ImageView imgStatus;
        @BindView(R.id.rlitem)
        RelativeLayout backKom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
