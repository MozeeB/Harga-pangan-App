package com.integra.hargapangan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.integra.hargapangan.GlobalActivtiy;
import com.integra.hargapangan.R;
import com.integra.hargapangan.activity.update.UpdatePresenter;
import com.integra.hargapangan.model.ResultItem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateAdapter extends RecyclerView.Adapter<UpdateAdapter.ViewHolder> {


    private Context context;
    private List<ResultItem> resultItemList;
    private UpdatePresenter updatePresenter;

    DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
    DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

    public UpdateAdapter(Context context, List<ResultItem> resultItemList, UpdatePresenter updatePresenter) {
        this.context = context;
        this.resultItemList = resultItemList;
        this.updatePresenter = updatePresenter;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_update, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final ResultItem resultItem = resultItemList.get(i);

        formatRp.setCurrencySymbol("Rp.");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        Glide.with(context)
                .load(GlobalActivtiy.URLICON + resultItem.getIconKomoditas() + ".png")
                .into(viewHolder.imgKomoditas);
        viewHolder.tvNamaKomoditas.setText(resultItem.getNamaKomoditas());
        viewHolder.tvHargaKomoditas.setText(kursIndonesia.format(resultItem.getHrgSkr()));
        viewHolder.tvUpdateAt.setText(resultItem.getUpdatedAt());


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePresenter.showDialog(resultItemList.get(i));
            }
        });

    }

    @Override
    public int getItemCount() {
        return resultItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgKomoditas)
        ImageView imgKomoditas;
        @BindView(R.id.tvNamaKomoditas)
        TextView tvNamaKomoditas;
        @BindView(R.id.tvHargaKomoditas)
        TextView tvHargaKomoditas;
        @BindView(R.id.tvUpdateAt)
        TextView tvUpdateAt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
