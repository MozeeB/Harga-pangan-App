package com.integra.hargapangan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.integra.hargapangan.R;
import com.integra.hargapangan.model.detailPangan.PerubahanItem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {


    private Context context;
    private List<PerubahanItem> perubahanItems;

    DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
    DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

    public DetailAdapter(Context context, List<PerubahanItem> perubahanItems) {
        this.context = context;
        this.perubahanItems = perubahanItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_detail, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final PerubahanItem perubahanItem = perubahanItems.get(i);

        formatRp.setCurrencySymbol("Rp.");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);


        viewHolder.tvNamaPasar.setText(perubahanItem.getNamaPasar());
        viewHolder.tvHargaSekarang.setText(kursIndonesia.format(perubahanItem.getHrgSkr()));
        viewHolder.tvHargaKemarin.setText(kursIndonesia.format(perubahanItem.getHrgKmr()));

    }

    @Override
    public int getItemCount() {
        return perubahanItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNamaPasar)
        TextView tvNamaPasar;
        @BindView(R.id.tvHargaSekarang)
        TextView tvHargaSekarang;
        @BindView(R.id.tvHargaKemarin)
        TextView tvHargaKemarin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}
