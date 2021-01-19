package com.tatabuku.app.ui.fix_asset.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.tatabuku.app.databinding.ActivityDashboardFixAssetBinding;
import com.tatabuku.app.model.fixed_asset.DashboardAssetHeader;
import com.tatabuku.app.model.fixed_asset.DashboardAssetModel;
import com.tatabuku.app.ui.fix_asset.list_asset.ListAssetActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.List;

public class DashboardFixAssetActivity extends AppCompatActivity {
    private ActivityDashboardFixAssetBinding binding;
    private DashboardFixAssetListAdapter listAdapter;
    private GridLayoutManager layoutManager;
    private DashboardFixAssetListener listener;
    private DashboardFixAssetViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        configureView();
        setupViewModel();
        configureViewModel();
        setupList();
        fetchData();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(DashboardFixAssetViewModel.class);
    }

    private void configureViewModel() {
        viewModel.getAssetHeader().observe(this, new Observer<DashboardAssetHeader>() {
            @Override
            public void onChanged(DashboardAssetHeader dashboardAssetHeader) {
                binding.totalNilaiAsset.setText(StringHelper.numberFormat(dashboardAssetHeader.getTotalAssetValue()));
                binding.jumlahAsset.setText(dashboardAssetHeader.getJumlahAsset().toString() + " Unit");
            }
        });

        viewModel.getAssetList().observe(this, new Observer<List<DashboardAssetModel>>() {
            @Override
            public void onChanged(List<DashboardAssetModel> dashboardAssetModels) {
                listAdapter.notifyDataSetChanged();
                binding.swipeContainer.setRefreshing(false);
            }
        });
    }

    private void setupView(){
        binding = ActivityDashboardFixAssetBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

    }

    private void configureView(){
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });

        listener = new DashboardFixAssetListener() {
            @Override
            public void onItemClick(DashboardAssetModel asset) {
                Intent intent = new Intent(DashboardFixAssetActivity.this, ListAssetActivity.class);
                intent.putExtra(ListAssetActivity.SELECTED_ASSET_CATEGORY_ID, asset.getCategId());
                startActivity(intent);
            }
        };

    }

    private void fetchData() {
        viewModel.fetchDashboardData();
    }

    private void setupList() {
        layoutManager = new GridLayoutManager(this, 2);
        binding.recycleView.setLayoutManager(layoutManager);

        listAdapter = new DashboardFixAssetListAdapter(viewModel);
        listAdapter.setListener(listener);
        binding.recycleView.setAdapter(listAdapter);
    }
}
