package com.tatabuku.app.ui.saldoPiutang;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tatabuku.app.R;
import com.tatabuku.app.databinding.ActivitySaldoPiutangBinding;
import com.tatabuku.app.model.penjualan.CustomerResult;
import com.tatabuku.app.ui.penjualan.dashboard.DashboardCustomerActivity;
import com.tatabuku.app.ui.penjualan.dashboard.DashboardCustomerListener;
import com.tatabuku.app.ui.penjualan.dashboard.DashboardCustomerViewModel;
import com.tatabuku.app.ui.penjualan.detail.DetailCustomerActivity;
import com.tatabuku.app.ui.penjualan.tambah.customer.TambahCustomerActivity;

import java.util.List;

public class ActivitySaldoPiutang extends AppCompatActivity {

    private ActivitySaldoPiutangBinding binding;
    private PiutangListCustomerAdapter listAdapter;
    private ActivitySaldoPiutangViewModel viewModel;
    private LinearLayoutManager layoutManager;
    private String query = "";
    private ActivitySaldoPiutangListener listener;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            query = binding.search.getText().toString();
            viewModel.fetchListData(query);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        configureViewModel();
        setupView();
        configureView();
        setupList();


    }
    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(ActivitySaldoPiutangViewModel.class);
    }

    private void setupView() {
            binding = ActivitySaldoPiutangBinding.inflate(getLayoutInflater());
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
        binding.search.addTextChangedListener(textWatcher);

        binding.sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.getIsSort().setValue(!viewModel.getIsSort().getValue());
            }
        });

        binding.addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivitySaldoPiutang.this, TambahCustomerActivity.class);
                startActivity(intent);
            }
        });

        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeContainer.setRefreshing(false);
            }
        });

        binding.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        listener = new ActivitySaldoPiutangListener() {
            @Override
            public void onItemClick(CustomerResult customerResult) {
                Intent intent = new Intent(ActivitySaldoPiutang.this, DetailCustomerActivity.class);
                intent.putExtra(DetailCustomerActivity.ARG_CUSTOMER_ID, customerResult.getId());
                startActivity(intent);
            }
        };

    }

    private void configureViewModel() {
        viewModel.fetchListData(query);
        viewModel.getIsSort().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.sort.setColorFilter(ContextCompat.getColor(ActivitySaldoPiutang.this, R.color.blue), PorterDuff.Mode.SRC_ATOP);
                } else {
                    binding.sort.setColorFilter(null);
                }
            }
        });

        viewModel.getListCustomer().observe(this, new Observer<List<CustomerResult>>() {
            @Override
            public void onChanged(List<CustomerResult> customerResults) {
                listAdapter.notifyDataSetChanged();
                binding.swipeContainer.setRefreshing(false);
            }
        });


    }


    private void setupList() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);

        listAdapter = new PiutangListCustomerAdapter(this, viewModel);
        listAdapter.setListener(listener);
        binding.list.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }


}