package com.tatabuku.app.ui.rekening.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.tatabuku.app.R;
import com.tatabuku.app.databinding.ActivityDashboardRekeningBinding;
import com.tatabuku.app.model.rekening.DashboardRekeningHeader;
import com.tatabuku.app.model.rekening.DashboardRekeningModel;
import com.tatabuku.app.ui.fix_asset.dashboard.DashboardFixAssetActivity;
import com.tatabuku.app.ui.fix_asset.list_asset.ListAssetActivity;
import com.tatabuku.app.ui.rekening.create_bank_account.CreateBankAccountActivity;
import com.tatabuku.app.ui.rekening.detail.RekeningDetailActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.List;

public class DashboardRekeningActivity extends AppCompatActivity {

    private ActivityDashboardRekeningBinding binding;
    private LinearLayoutManager layoutManager;
    private DashboardRekeningListAdapter listAdapter;
    private DashboardRekeningViewModel viewModel;
    private DashboardRekeningListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        configureViewModel();
        setupView();
        configureView();
        setupList();
        fetchData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchData();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(DashboardRekeningViewModel.class);
    }

    private void configureViewModel() {
        viewModel.getHeader().observe(this, new Observer<DashboardRekeningHeader>() {
            @Override
            public void onChanged(DashboardRekeningHeader header) {
                binding.headerLastUpdate.setText("Update : " + StringHelper.formatInvoiceDate(header.getTanggalUpdate()));
                binding.headerTotalAmount.setText(StringHelper.numberFormatWithDecimal(header.getTotalBankKas()));
                binding.nominalDebit.setText(StringHelper.numberFormat(header.getTotalDebit()));
                binding.nominalCredit.setText(StringHelper.numberFormat(header.getTotalCredit()));

                Double total = header.getTotalDebit() + header.getTotalCredit();
                int debitPercent = (int) (header.getTotalDebit() / total * 100);
                int creditPercent = (int) (header.getTotalCredit() / total * 100);

                binding.percentDebit.setText(debitPercent + "%");
                binding.percentCredit.setText(creditPercent + "%");
                binding.progressBarDebit.setProgress(debitPercent > 0 ? debitPercent : 1);
                binding.progressBarCredit.setProgress(creditPercent > 0 ? creditPercent : 1);
            }
        });

        viewModel.getRekenings().observe(this, new Observer<List<DashboardRekeningModel>>() {
            @Override
            public void onChanged(List<DashboardRekeningModel> rekenings) {
                listAdapter.notifyDataSetChanged();
                binding.swipeContainer.setRefreshing(false);
            }
        });
    }

    private void setupView() {
        binding = ActivityDashboardRekeningBinding.inflate(getLayoutInflater());
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

        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });

        binding.addRekeningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardRekeningActivity.this, CreateBankAccountActivity.class);
                startActivity(intent);
            }
        });

        listener = new DashboardRekeningListener() {
            @Override
            public void onItemClick(DashboardRekeningModel rekening) {
                Intent intent = new Intent(DashboardRekeningActivity.this, RekeningDetailActivity.class);
                intent.putExtra(RekeningDetailActivity.SELECTED_REKENING_ID, rekening.getId());
                intent.putExtra(RekeningDetailActivity.SELECTED_REKENING_NAME, rekening.getName());
                startActivity(intent);
            }
        };
    }

    private void setupList() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);

        listAdapter = new DashboardRekeningListAdapter(viewModel);
        listAdapter.setListener(listener);
        binding.list.setAdapter(listAdapter);
    }

    private void fetchData() {
        viewModel.fetchDashboardData();
    }


}