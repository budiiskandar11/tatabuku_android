package com.tatabuku.app.ui.pembelian.dashboard;

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

import com.google.android.material.tabs.TabLayoutMediator;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.ActivityDashboardSupplierBinding;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.ui.pembelian.detail.DetailSupplierActivity;
import com.tatabuku.app.ui.pembelian.tambah.supplier.TambahSupplierActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.Collections;
import java.util.List;

public class DashboardSupplierActivity extends AppCompatActivity {

    public enum DashboardType {
        PEMBELIAN, HUTANG, LUNAS, PEMBAYARAN, UANGMUKA
    }

    private ActivityDashboardSupplierBinding binding;
//    private DashboardSupplierPagerAdapter pagerAdapter;
    private DashboardSupplierListAdapter listAdapter;
    private LinearLayoutManager layoutManager;

    private DashboardType currentType;
    private String query = "";

    private DashboardSupplierListener listener;

    private DashboardSupplierViewModel viewModel;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            query = binding.search.getText().toString();
            viewModel.fetchSupplierList(currentType, query);
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

//        setupViewPager();
        setupList();

        setCurrentType(DashboardType.PEMBELIAN);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(DashboardSupplierViewModel.class);
    }

    private void configureViewModel() {
        viewModel.getIsSort().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.sort.setColorFilter(ContextCompat.getColor(DashboardSupplierActivity.this, R.color.blue), PorterDuff.Mode.SRC_ATOP);
                } else {
                    binding.sort.setColorFilter(null);
                }
                setCurrentType(currentType);
            }
        });

        viewModel.getTotalDashboardData().observe(this, new Observer<String[]>() {
            @Override
            public void onChanged(String[] strings) {
                binding.leftValue.setText(strings[0]);
                binding.middleValue.setText(strings[1]);
                binding.rightValue.setText(strings[2]);
            }
        });
        viewModel.getSupplierList().observe(this, new Observer<List<SupplierResult>>() {
            @Override
            public void onChanged(List<SupplierResult> supplierResults) {
                listAdapter.setCurrentType(currentType);
                listAdapter.notifyDataSetChanged();
                binding.swipeContainer.setRefreshing(false);
            }
        });
//        viewModel.getFaveSupplierList().observe(this, new Observer<List<SupplierResult>>() {
//            @Override
//            public void onChanged(List<SupplierResult> supplierResults) {
//                pagerAdapter.setCurrentType(currentType);
//                pagerAdapter.notifyDataSetChanged();
//            }
//        });
    }

    private void setupView() {
        binding = ActivityDashboardSupplierBinding.inflate(getLayoutInflater());
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

        binding.tabPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.search.removeTextChangedListener(textWatcher);
                binding.search.setText("");
                binding.search.addTextChangedListener(textWatcher);
                setCurrentType(DashboardType.PEMBELIAN);
            }
        });

        binding.tabHutang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.search.removeTextChangedListener(textWatcher);
                binding.search.setText("");
                binding.search.addTextChangedListener(textWatcher);
                setCurrentType(DashboardType.HUTANG);
            }
        });

        binding.tabPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.search.removeTextChangedListener(textWatcher);
                binding.search.setText("");
                binding.search.addTextChangedListener(textWatcher);
                setCurrentType(DashboardType.PEMBAYARAN);
            }
        });

        binding.tabUangMuka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.search.removeTextChangedListener(textWatcher);
                binding.search.setText("");
                binding.search.addTextChangedListener(textWatcher);
                setCurrentType(DashboardType.UANGMUKA);
            }
        });

        binding.addSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardSupplierActivity.this, TambahSupplierActivity.class);
                startActivity(intent);
            }
        });

        listener = new DashboardSupplierListener() {
            @Override
            public void onItemClick(SupplierResult result) {
                Intent intent = new Intent(DashboardSupplierActivity.this, DetailSupplierActivity.class);
                intent.putExtra(DetailSupplierActivity.ARG_DASHBOARD_TYPE, currentType);
                intent.putExtra(DetailSupplierActivity.ARG_SUPPLIER_ID, result.getId());
                startActivity(intent);
            }
        };

        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setCurrentType(currentType);
            }
        });

        binding.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void setCurrentType(DashboardType type) {
        currentType = type;
        viewModel.getSupplierList().setValue(Collections.emptyList());
        listAdapter.notifyDataSetChanged();
        viewModel.getFaveSupplierList().setValue(Collections.emptyList());
//        pagerAdapter.notifyDataSetChanged();

        binding.tabPembelian.setSelected(false);
        binding.tabHutang.setSelected(false);
        binding.tabUangMuka.setSelected(false);
        binding.tabPembayaran.setSelected(false);

        binding.leftText.setText(R.string.hari_ini);
        binding.middleText.setText(R.string.bulan_ini);
        binding.rightText.setText(R.string.tahun_ini);

        binding.leftValue.setText(StringHelper.numberFormat(0.0));
        binding.middleValue.setText(StringHelper.numberFormat(0.0));
        binding.rightValue.setText(StringHelper.numberFormat(0.0));

        switch (type) {
            case HUTANG:
            case LUNAS:
                binding.tabHutang.setSelected(true);
//                binding.topCardBackground.setImageResource(R.drawable.background_red);
                binding.title.setText(R.string.total_hutang);
                binding.leftText.setText(R.string.hutang_0_hari);
                binding.middleText.setText(R.string.hutang_1_30_hari);
                binding.rightText.setText(R.string.hutang_31_60_hari);
                break;
            case UANGMUKA:
                binding.tabUangMuka.setSelected(true);
//                binding.topCardBackground.setImageResource(R.drawable.background_orange);
//                binding.iconHeader.setImageResource(R.drawable.percentage);
                binding.title.setText(R.string.total_saldo_dp);
                break;
            case PEMBELIAN:
                binding.tabPembelian.setSelected(true);
//                binding.topCardBackground.setImageResource(R.drawable.background_blue);
//                binding.iconHeader.setImageResource(R.drawable.shopping_basket);
                binding.title.setText(R.string.total_pembelian);
                break;
            case PEMBAYARAN:
                binding.tabPembayaran.setSelected(true);
//                binding.topCardBackground.setImageResource(R.drawable.background_green);
//                binding.iconHeader.setImageResource(R.drawable.payment_method);
                binding.title.setText(R.string.total_pembayaran);
                break;
        }

        viewModel.fetchCurrentType(currentType);
    }

//    private void setupViewPager() {
//        pagerAdapter = new DashboardSupplierPagerAdapter(this, viewModel);
//        pagerAdapter.setListener(listener);
//        binding.viewPager.setAdapter(pagerAdapter);
//
//        TabLayoutMediator mediator = new TabLayoutMediator(binding.intoTabLayout, binding.viewPager, (tab, position) -> {
//        });
//        mediator.attach();
//    }

    private void setupList() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);

        listAdapter = new DashboardSupplierListAdapter(this, viewModel);
        listAdapter.setListener(listener);
        binding.list.setAdapter(listAdapter);
    }
}