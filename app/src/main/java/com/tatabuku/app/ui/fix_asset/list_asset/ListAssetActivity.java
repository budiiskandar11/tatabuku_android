package com.tatabuku.app.ui.fix_asset.list_asset;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tatabuku.app.databinding.ActivityListAssetBinding;
import com.tatabuku.app.model.fixed_asset.ListAssetHeader;
import com.tatabuku.app.model.fixed_asset.ListAssetModel;
import com.tatabuku.app.ui.fix_asset.tambah_asset.TambahAssetActivity;
import com.tatabuku.app.ui.fix_asset.detail_asset.DetailAssetActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.List;

public class ListAssetActivity extends AppCompatActivity {
    public static final String SELECTED_ASSET_CATEGORY_ID = "SELECTED_ASSET_CATEGORY_ID";

    private ActivityListAssetBinding binding;
    private LinearLayoutManager layoutManager;
    private ListAssetAdapter listAdapter;
    private ListAssetViewModel viewModel;
    private ListAssetListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        setupViewModel();
        configureViewModel();
        configureView();
        setupList();
        configureIntent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.refreshList();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(ListAssetViewModel.class);
    }

    private void configureViewModel() {
        viewModel.getListAssetHeader().observe(this, new Observer<ListAssetHeader>() {
            @Override
            public void onChanged(ListAssetHeader listAssetHeader) {
                binding.headerTitle.setText(listAssetHeader.getCategoryName());
                binding.nomPembelian.setText(StringHelper.numberFormat(listAssetHeader.getPembelian()));
                binding.nomDepresiasi.setText(StringHelper.numberFormat(listAssetHeader.getDepresiasi()));
                binding.nomNilaibook.setText(StringHelper.numberFormat(listAssetHeader.getNilaiBuku()));

                Double total = listAssetHeader.getPembelian() + listAssetHeader.getDepresiasi() + listAssetHeader.getNilaiBuku();

                int progressBeli = (int) (listAssetHeader.getPembelian() / total * 100);
                int progressDepresiasi = (int) (listAssetHeader.getDepresiasi() / total * 100);
                int progressNilaiBuku =(int) (listAssetHeader.getNilaiBuku() / total * 100);

                binding.progressBarBeli.setProgress(progressBeli > 0 ? progressBeli : 1);
                binding.progressBarDepresiasi.setProgress(progressDepresiasi > 0 ? progressDepresiasi : 1);
                binding.progressBarBuku.setProgress(progressNilaiBuku > 0 ? progressNilaiBuku : 1);
            }
        });

        viewModel.getListAssetModels().observe(this, new Observer<List<ListAssetModel>>() {
            @Override
            public void onChanged(List<ListAssetModel> listAssetModels) {
                listAdapter.notifyDataSetChanged();
                binding.swipeContainer.setRefreshing(false);
            }
        });
    }

    private void setupView(){
        binding = ActivityListAssetBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void setupList(){
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        binding.listTransaksi.setLayoutManager(layoutManager);

        listAdapter = new ListAssetAdapter(this, viewModel);
        listAdapter.setListener(listener);
        binding.listTransaksi.setAdapter(listAdapter);
    }

    private void configureIntent(){
        Intent intent = getIntent();
        int categ_id = intent.getIntExtra(SELECTED_ASSET_CATEGORY_ID, 0);
        viewModel.fetchListAsset(categ_id);
    }

    private void configureView(){
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.tambahAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.getListAssetHeader().getValue() != null) {
                    Intent intent = new Intent(ListAssetActivity.this, TambahAssetActivity.class);
                    intent.putExtra(TambahAssetActivity.TAMBAH_ASSET_CATEG_ID, viewModel.getCateg_id());
                    intent.putExtra(TambahAssetActivity.TAMBAH_ASSET_CATEG_NAME, viewModel.getListAssetHeader().getValue().getCategoryName());
                    startActivity(intent);
                }
            }
        });

        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refreshList();
            }
        });

        listener = new ListAssetListener() {
            @Override
            public void onClickItem(ListAssetModel asset) {
                Intent intent = new Intent(ListAssetActivity.this, DetailAssetActivity.class);
                intent.putExtra(DetailAssetActivity.SELECTED_ASSET_ID, asset.getAssetId());
                startActivity(intent);
            }
        };
    }
}