package com.tatabuku.app.ui.saldoPiutang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.tatabuku.app.R;
import com.tatabuku.app.databinding.ActivitySaldoPiutangBinding;
import com.tatabuku.app.model.penjualan.CustomerResult;
import com.tatabuku.app.ui.penjualan.dashboard.DashboardCustomerActivity;
import com.tatabuku.app.ui.penjualan.dashboard.DashboardCustomerListAdapter;
import com.tatabuku.app.ui.penjualan.dashboard.DashboardCustomerListener;

import java.util.List;

public class ActivitySaldoPiutang extends AppCompatActivity {

    private ActivitySaldoPiutangBinding binding;
    private PiutangListCustomerAdapter listAdapter;
    private ActivitySaldoPiutangViewModel viewModel;
    private LinearLayoutManager layoutManager;

    private DashboardCustomerListener listener;


//    private DashboardCustomerActivity.DashboardType currentType;
    private String query = "";


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
        viewModel.fetchListPenjualan(query);


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
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(ActivitySaldoPiutangViewModel.class);
    }

    private void configureViewModel() {
        viewModel.getIsSort().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.sortBoolean.setColorFilter(ContextCompat.getColor(ActivitySaldoPiutang.this, R.color.blue), PorterDuff.Mode.SRC_ATOP);
                } else {
                    binding.sortBoolean.setColorFilter(null);
                }
            }
        });

        viewModel.getListCustomer().observe(this, new Observer<List<CustomerResult>>() {
            @Override
            public void onChanged(List<CustomerResult> customerResults) {
                viewModel.fetchListPenjualan(query);
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
    }


}