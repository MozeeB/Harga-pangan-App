package com.integra.hargapangan.fragment.dashboard;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.integra.hargapangan.R;
import com.integra.hargapangan.activity.info.InfoPanganActivity2;
import com.integra.hargapangan.activity.update.UpdateKomoditas;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements DashboardFragmentContruct.View {


    @BindView(R.id.img_info)
    ImageView imgInfo;
    @BindView(R.id.img_update_harga)
    ImageView imgUpdateHarga;
    @BindView(R.id.img_komoditas)
    ImageView imgKomoditas;
    @BindView(R.id.img_pasar)
    ImageView imgPasar;
    Unbinder unbinder;
    @BindView(R.id.carouselView)
    CarouselView carouselView;
    int[] imgSample = {R.drawable.sayur, R.drawable.satu, R.drawable.dua, R.drawable.tiga, R.drawable.empat};

    private DashboardFragmentPresenter presenter;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        carouselView.setImageListener(imageListener);
        carouselView.setPageCount(imgSample.length);

        presenter = new DashboardFragmentPresenter(this);
        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.toInfo();
            }
        });
       imgUpdateHarga.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               presenter.toUpdate();
           }
       });
       imgKomoditas.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               presenter.toKomoditas();
           }
       });
       imgPasar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               presenter.toPasar();
           }
       });
    }
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(imgSample[position]);
        }
    };

    @Override
    public void toInfo() {
        startActivity(new Intent(getActivity(), InfoPanganActivity2.class));
    }

    @Override
    public void toUpdate() {
        startActivity(new Intent(getActivity(), UpdateKomoditas.class));
    }

    @Override
    public void toKomoditas() {
        Toast.makeText(getActivity(), "Data Komoditas", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toPasar() {
        Toast.makeText(getActivity(), "Data Pasar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
