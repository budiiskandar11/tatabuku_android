package com.tatabuku.app.ui.penjualan.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.ActivityDashboardCustomerBinding;
import com.tatabuku.app.model.penjualan.CustomerResult;
import com.tatabuku.app.model.penjualan.DashboardCustomerTotalResult;
import com.tatabuku.app.model.penjualan.DashboardTotalPenjualanResult;
import com.tatabuku.app.model.penjualan.SaleChannel;
import com.tatabuku.app.model.penjualan.TopTenProduct;
import com.tatabuku.app.ui.pembelian.dashboard.DashboardSupplierActivity;
import com.tatabuku.app.ui.penjualan.tambah.customer.TambahCustomerActivity;
import com.tatabuku.app.ui.penjualan.detail.DetailCustomerActivity;
import com.tatabuku.app.util.StringHelper;
import com.tatabuku.app.view.LegendView;

import java.util.ArrayList;
import java.util.List;

public class DashboardCustomerActivity extends AppCompatActivity {

    final int[] pieColors = {R.color.blue,
            R.color.colorPrimary,
            R.color.red,
            R.color.orange,
            R.color.green,
            R.color.gray,
            R.color.black,
            R.color.yellow,
            R.color.accounting_dark_blue,
            R.color.background_blue};

    public enum DashboardType {
        PENJUALAN, PIUTANG, LUNAS,  PEMBAYARAN, UANGMUKA
    }

    private ActivityDashboardCustomerBinding binding;
    private DashboardCustomerListAdapter listAdapter;
    private DashboardCustomerPagerAdapter pagerAdapter;
    private LinearLayoutManager layoutManager;
    private DashboardCustomerViewModel viewModel;

    private DashboardType currentType;
    private String query = "";

    private DashboardCustomerListener listener;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            query = binding.search.getText().toString();
            viewModel.fetchListData(currentType, query);
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

        setupViewPager();
        setupList();

        setCurrentType(DashboardType.PENJUALAN);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(DashboardCustomerViewModel.class);
    }

    private void configureViewModel() {

        viewModel.getIsSort().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.sort.setColorFilter(ContextCompat.getColor(DashboardCustomerActivity.this, R.color.blue), PorterDuff.Mode.SRC_ATOP);
                } else {
                    binding.sort.setColorFilter(null);
                }
                setCurrentType(currentType);
            }
        });
        viewModel.getTotalPenjualanData().observe(this, new Observer<DashboardTotalPenjualanResult>() {
            @Override
            public void onChanged(DashboardTotalPenjualanResult dashboardTotalPenjualanResult) {
                binding.valueHariIni.setText(StringHelper.numberFormatWithDecimal(dashboardTotalPenjualanResult.getHeader().getTotalSaleHari()));
                Double percentHariIni = dashboardTotalPenjualanResult.getHeader().getTotalSaleHari() / dashboardTotalPenjualanResult.getHeader().getTargetSaleHari() * 100;
                binding.percentHariIni.setText(percentHariIni.intValue() + "%");
                binding.progressHariIni.setProgress(percentHariIni.intValue());

                binding.valueBulanIni.setText(StringHelper.numberFormatWithDecimal(dashboardTotalPenjualanResult.getHeader().getTotalSaleBulan()));
                Double percentBulanIni = dashboardTotalPenjualanResult.getHeader().getTotalSaleBulan() / dashboardTotalPenjualanResult.getHeader().getTargetSaleBulan() * 100;
                binding.percentBulanIni.setText(percentBulanIni.intValue() + "%");
                binding.progressBulanIni.setProgress(percentBulanIni.intValue());

                binding.valueTahunIni.setText(StringHelper.numberFormatWithDecimal(dashboardTotalPenjualanResult.getHeader().getTotalSaleTahun()));
                Double percentTahunIni = dashboardTotalPenjualanResult.getHeader().getTotalSaleTahun() / dashboardTotalPenjualanResult.getHeader().getTargetSaleTahun() * 100;
                binding.percentTahunIni.setText(percentTahunIni.intValue() + "%");
                binding.progressTahunIni.setProgress(percentTahunIni.intValue());

//                for (SaleChannel channel : dashboardTotalPenjualanResult.getSaleChannel()) {
//                    if (channel.getTeamName().equalsIgnoreCase("Offline")) {
//                        binding.offlineValue.setText(StringHelper.numberFormatWithDecimal(channel.getAmountTotal()));
//                    } else if (channel.getTeamName().equalsIgnoreCase("Online")) {
//                        binding.onlineValue.setText(StringHelper.numberFormatWithDecimal(channel.getAmountTotal()));
//                    }
//                }

                setupPieChart(dashboardTotalPenjualanResult.getTopTenProduct());
            }
        });

        viewModel.getTotalCustomerData().observe(this, new Observer<DashboardCustomerTotalResult>() {
            @Override
            public void onChanged(DashboardCustomerTotalResult dashboardCustomerTotalResult) {
                switch (currentType) {
                    case PIUTANG:
                    case LUNAS:
                        binding.leftValue.setText(StringHelper.numberFormat(dashboardCustomerTotalResult.getTotalHutang().getPiutangTotal()));
                        binding.middleValue.setText(StringHelper.numberFormat(dashboardCustomerTotalResult.getTotalHutang().getTotalPiutang30()));
                        binding.rightValue.setText(StringHelper.numberFormat(dashboardCustomerTotalResult.getTotalHutang().getTotalPiutang60()));
                        break;
                    case PEMBAYARAN:
                        binding.leftValue.setText(StringHelper.numberFormat(dashboardCustomerTotalResult.getResult().getHariIni()));
                        binding.middleValue.setText(StringHelper.numberFormat(dashboardCustomerTotalResult.getResult().getBulanIni()));
                        binding.rightValue.setText(StringHelper.numberFormat(dashboardCustomerTotalResult.getResult().getTahunIni()));
                        break;
                    case UANGMUKA:
                        binding.leftValue.setText(StringHelper.numberFormat(dashboardCustomerTotalResult.getResult().getDpKeluar()));
                        binding.middleValue.setText(StringHelper.numberFormat(dashboardCustomerTotalResult.getResult().getDpMasuk()));
                        binding.rightValue.setText(StringHelper.numberFormat(dashboardCustomerTotalResult.getResult().getSaldoDp()));
                        break;
                }
            }
        });

        viewModel.getListCustomer().observe(this, new Observer<List<CustomerResult>>() {
            @Override
            public void onChanged(List<CustomerResult> customerResults) {
                listAdapter.setCurrentType(currentType);
                binding.swipeContainer.setRefreshing(false);
            }
        });

        viewModel.getListFaveCustomer().observe(this, new Observer<List<CustomerResult>>() {
            @Override
            public void onChanged(List<CustomerResult> customerResults) {
                pagerAdapter.setCurrentType(currentType);
            }
        });
    }

    private void setupPieChart(List<TopTenProduct> items) {


        binding.pieChart.setHoleRadius(80);
        binding.pieChart.setMinOffset(0);
        binding.pieChart.getDescription().setEnabled(false);
        binding.pieChart.setDrawCenterText(false);
        binding.pieChart.setDrawEntryLabels(false);
        binding.pieChart.setRotationEnabled(false);
        binding.pieChart.setTouchEnabled(false);
        binding.pieChart.getLegend().setEnabled(false);

        binding.legend.removeAllViewsInLayout();

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : pieColors) colors.add(ContextCompat.getColor(this, c));

        List<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            TopTenProduct item = items.get(i);
            entries.add(new PieEntry(item.getPercentageQty().floatValue(), ""));

            LegendView legendView = new LegendView(this, null);
            legendView.setText(item.getProductName());
            legendView.setPercent(item.getPercentageQty() + "%");
            legendView.setColor(colors.get(i % pieColors.length));
            binding.legend.addView(legendView);
        }
        PieDataSet set = new PieDataSet(entries, "");

        set.setColors(colors);
        set.setDrawValues(false);
        PieData data = new PieData(set);
        binding.pieChart.setData(data);
        binding.pieChart.invalidate(); // refresh


    }

    private void setupView() {
        binding = ActivityDashboardCustomerBinding.inflate(getLayoutInflater());
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

        binding.tabPenjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.search.removeTextChangedListener(textWatcher);
                binding.search.setText("");
                binding.search.addTextChangedListener(textWatcher);
                setCurrentType(DashboardType.PENJUALAN);
            }
        });

        binding.tabPiutang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.search.removeTextChangedListener(textWatcher);
                binding.search.setText("");
                binding.search.addTextChangedListener(textWatcher);
                setCurrentType(DashboardType.PIUTANG);
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

        binding.addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardCustomerActivity.this, TambahCustomerActivity.class);
                startActivity(intent);
            }
        });

        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setCurrentType(currentType);
                binding.swipeContainer.setRefreshing(false);
            }
        });

        binding.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        listener = new DashboardCustomerListener() {
            @Override
            public void onItemClick(CustomerResult customerResult) {
                Intent intent = new Intent(DashboardCustomerActivity.this, DetailCustomerActivity.class);
                intent.putExtra(DetailCustomerActivity.ARG_DASHBOARD_TYPE, currentType);
                intent.putExtra(DetailCustomerActivity.ARG_CUSTOMER_ID, customerResult.getId());
                startActivity(intent);
            }
        };
    }

    private void setCurrentType(DashboardType type) {
        currentType = type;
        binding.scrollview.scrollTo(0, 0);

        binding.tabPenjualan.setSelected(false);
        binding.tabPiutang.setSelected(false);
        binding.tabUangMuka.setSelected(false);
        binding.tabPembayaran.setSelected(false);

        binding.leftText.setText(R.string.hari_ini);
        binding.middleText.setText(R.string.bulan_ini);
        binding.rightText.setText(R.string.tahun_ini);

        binding.leftValue.setText(StringHelper.numberFormat(0.0));
        binding.middleValue.setText(StringHelper.numberFormat(0.0));
        binding.rightValue.setText(StringHelper.numberFormat(0.0));
        binding.penjualanCard.setVisibility(View.GONE);
        binding.headerCard.setVisibility(View.GONE);
        binding.penjualanDetailLayout.setVisibility(View.GONE);
        binding.textInputLayout.setVisibility(View.VISIBLE);
        binding.addCustomer.setVisibility(View.VISIBLE);

        switch (type) {
            case PIUTANG:
                binding.headerCard.setVisibility(View.VISIBLE);
                binding.tabPiutang.setSelected(true);
//                binding.topCardBackground.setImageResource(R.drawable.background_red);
                binding.title.setText(R.string.total_piutang);
                binding.leftText.setText(R.string.belum_terlambat_bayar);
                binding.middleText.setText(R.string.hutang_1_30_hari);
                binding.rightText.setText(R.string.hutang_31_60_hari);
                break;
            case UANGMUKA:
                binding.headerCard.setVisibility(View.VISIBLE);
                binding.tabUangMuka.setSelected(true);
//                binding.topCardBackground.setImageResource(R.drawable.background_orange);
                binding.title.setText(R.string.total_saldo_dp);
                binding.leftText.setText(R.string.dp_keluar);
                binding.middleText.setText(R.string.dp_masuk);
                binding.rightText.setText(R.string.saldo_dp);
                break;
            case PENJUALAN:
                binding.penjualanDetailLayout.setVisibility(View.VISIBLE);
                binding.penjualanCard.setVisibility(View.VISIBLE);
                binding.tabPenjualan.setSelected(true);
//                binding.topCardBackground.setImageResource(R.drawable.background_blue);
                binding.title.setText(R.string.total_penjualan);
                break;
            case PEMBAYARAN:
                binding.headerCard.setVisibility(View.VISIBLE);
                binding.tabPembayaran.setSelected(true);
//                binding.topCardBackground.setImageResource(R.drawable.background_green);
                binding.title.setText(R.string.total_pembayaran);
                binding.leftText.setText(R.string.hari_ini);
                binding.middleText.setText(R.string.bulan_ini);
                binding.rightText.setText(R.string.tahun_ini);
                break;
        }

        viewModel.fetchCurrentType(currentType, query);
    }

    private void setupViewPager() {
        pagerAdapter = new DashboardCustomerPagerAdapter(this, viewModel);
        pagerAdapter.setListener(listener);
//        binding.viewPager.setAdapter(pagerAdapter);

//        TabLayoutMediator mediator = new TabLayoutMediator(binding.intoTabLayout, binding.viewPager, (tab, position) -> {
//        });
//        mediator.attach();
    }

    private void setupList() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);

        listAdapter = new DashboardCustomerListAdapter(this, viewModel);
        listAdapter.setListener(listener);
        binding.list.setAdapter(listAdapter);
    }
}