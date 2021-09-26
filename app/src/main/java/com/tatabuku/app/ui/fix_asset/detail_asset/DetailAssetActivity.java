package com.tatabuku.app.ui.fix_asset.detail_asset;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import com.bumptech.glide.Glide;
import com.tatabuku.app.databinding.ActivityDetailAssetBinding;
import com.tatabuku.app.model.fixed_asset.DetailAssetHeader;
import com.tatabuku.app.model.fixed_asset.DetailAssetInfo;
import com.tatabuku.app.ui.fix_asset.jual_asset.JualAssetActivity;
import com.tatabuku.app.util.StringHelper;

import java.io.Serializable;

public class DetailAssetActivity extends AppCompatActivity {
    public static final String SELECTED_ASSET_ID = "SELECTED_ASSET_ID";

    private ActivityDetailAssetBinding binding;
    private LinearLayoutManager layoutManager;
    private DetailAssetAdapter listAdapter;
    private DetailAssetViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        setupViewModel();
        configureView();
        configureViewModel();
        setupList();

        configureIntent();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(DetailAssetViewModel.class);
    }

    private void configureViewModel() {
        viewModel.getHeader().observe(this, new Observer<DetailAssetHeader>() {
            @Override
            public void onChanged(DetailAssetHeader detailAssetHeader) {
                binding.merkAsset.setText(detailAssetHeader.getAssetName());
                binding.kodeAsset.setText(detailAssetHeader.getNumber());
//                binding.tahunAsset.setText(detailAssetHeader.getTahun());
                binding.hargabeliAsset.setText(StringHelper.numberFormat(detailAssetHeader.getNilaiPerolehan()));
                binding.depresiasiAsset.setText(StringHelper.numberFormat(detailAssetHeader.getDepresiasi()));
                binding.nilaibukuAsset.setText(StringHelper.numberFormat(detailAssetHeader.getNilaiBuku()));
//                binding.iconAsset.setImageResource(0);
                binding.fotoAsset.setImageResource(0);

                try {
                    Glide.with(DetailAssetActivity.this).asBitmap()
                            .load(Base64.decode(detailAssetHeader.getImage(), Base64.DEFAULT))
                            .into(binding.fotoAsset);
                } catch (Exception e) {

                }
            }
        });

        viewModel.getInfo().observe(this, new Observer<DetailAssetInfo>() {
            @Override
            public void onChanged(DetailAssetInfo detailAssetInfo) {
                binding.kategoriAsset.setText(detailAssetInfo.getAssetCategory());
                binding.usiaDepresiasi.setText(detailAssetInfo.getUsiaDepresiasi() + " Tahun");
                binding.tanggalBeli.setText(StringHelper.formatInvoiceDate(detailAssetInfo.getTahunPembelian()));
            }
        });

        viewModel.getInfo().observe(this, new Observer<DetailAssetInfo>() {
            @Override
            public void onChanged(DetailAssetInfo detailAssetInfo) {
                listAdapter.notifyDataSetChanged();
                binding.swipeContainer.setRefreshing(false);
            }
        });
    }

    private void setupView(){
        binding = ActivityDetailAssetBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void configureView() {
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.jualAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailAssetActivity.this, JualAssetActivity.class);
                intent.putExtra(JualAssetActivity.JUAL_ASSET_HEADER_INFO, viewModel.getHeader().getValue());
                startActivity(intent);
            }
        });

        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refreshPage();
            }
        });
    }

    private void setupList(){
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        binding.listDetailAsset.setLayoutManager(layoutManager);
        listAdapter = new DetailAssetAdapter(viewModel);
        binding.listDetailAsset.setAdapter(listAdapter);
    }

    private void configureIntent() {
        Intent intent = getIntent();
        int asset_id = intent.getIntExtra(SELECTED_ASSET_ID, 0);
        viewModel.fetchAssetDetail(asset_id);
    }
}