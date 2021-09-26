package com.tatabuku.app.ui.penjualan.detail;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.ActivityDetailCustomerBinding;
import com.tatabuku.app.model.pembelian.PembayaranItem;
import com.tatabuku.app.model.penjualan.CustomerDetailTotalData;
import com.tatabuku.app.model.penjualan.DPData;
import com.tatabuku.app.model.penjualan.HutangData;
import com.tatabuku.app.model.penjualan.PenjualanData;
import com.tatabuku.app.ui.BaseActivity;
import com.tatabuku.app.ui.pembelian.detail.DetailSupplierActivity;
import com.tatabuku.app.ui.pembelian.pembayaran.PembayaranActivity;
import com.tatabuku.app.ui.pembelian.tambah.supplier.TambahSupplierActivity;
import com.tatabuku.app.ui.penjualan.checkout.CheckoutPenjualanActivity;
import com.tatabuku.app.ui.penjualan.dashboard.DashboardCustomerActivity;
import com.tatabuku.app.ui.penjualan.order.OrderPenjualanActivity;
import com.tatabuku.app.ui.penjualan.order.OrderReturPenjualanActivity;
import com.tatabuku.app.ui.penjualan.pembayaran.PembayaranCustomerActivity;
import com.tatabuku.app.ui.penjualan.pengiriman.PengirimanBarangActivity;
import com.tatabuku.app.ui.penjualan.tambah.customer.TambahCustomerActivity;
import com.tatabuku.app.ui.penjualan.tambah.dp.TambahDpCustomerActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.List;

public class DetailCustomerActivity extends BaseActivity implements DetailCustomerListener {

    public static final String ARG_DASHBOARD_TYPE = "ARG_DASHBOARD_TYPE";
    public static final String ARG_CUSTOMER_ID = "ARG_CUSTOMER_ID";

    private ActivityDetailCustomerBinding binding;
    private DetailCustomerListAdapter listAdapter;
    private DetailCustomerViewModel viewModel;
    private LinearLayoutManager layoutManager;

    private DashboardCustomerActivity.DashboardType currentType;
    private Integer customerId;
    private String query = "";

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            query = binding.search.getText().toString();
            viewModel.searchData(customerId, currentType, query);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customerId = getIntent().getIntExtra(ARG_CUSTOMER_ID, 0);
        currentType = (DashboardCustomerActivity.DashboardType) getIntent().getSerializableExtra(ARG_DASHBOARD_TYPE);

        if (currentType == null) {
            currentType = DashboardCustomerActivity.DashboardType.PENJUALAN;
        }

        setupViewModel();
        configureViewModel();
        setupView();
        configureView();

        setupList();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentType(currentType);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(DetailCustomerViewModel.class);
    }

    private void configureViewModel() {

        viewModel.getIsSort().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.sort.setColorFilter(ContextCompat.getColor(DetailCustomerActivity.this, R.color.blue), PorterDuff.Mode.SRC_ATOP);
                } else {
                    binding.sort.setColorFilter(null);
                }
                setCurrentType(currentType);
            }
        });

        viewModel.getPenjualanList().observe(this, new Observer<List<PenjualanData>>() {
            @Override
            public void onChanged(List<PenjualanData> penjualanData) {
                binding.swipeContainer.setRefreshing(false);
                listAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getHutangList().observe(this, new Observer<List<HutangData>>() {
            @Override
            public void onChanged(List<HutangData> hutangData) {
                binding.swipeContainer.setRefreshing(false);
                listAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getDpList().observe(this, new Observer<List<DPData>>() {
            @Override
            public void onChanged(List<DPData> dpData) {
                binding.swipeContainer.setRefreshing(false);
                listAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getPembayaranList().observe(this, new Observer<List<PembayaranItem>>() {
            @Override
            public void onChanged(List<PembayaranItem> pembayaranItems) {
                binding.swipeContainer.setRefreshing(false);
                listAdapter.notifyDataSetChanged();
            }
        });
        viewModel.getTotalData().observe(this, new Observer<CustomerDetailTotalData>() {
            @Override
            public void onChanged(CustomerDetailTotalData data) {
                try {
                    Glide.with(DetailCustomerActivity.this).asBitmap()
                            .load(Base64.decode(data.getImage(), Base64.DEFAULT))
                            .into(binding.image2);
                } catch (Exception e) {

                }
                binding.name.setText(data.getName());
                binding.address.setText(data.getStreet() + " " + data.getStreet2());
//                binding.phone.setText(data.getPhone());

                switch (currentType) {
                    case PENJUALAN:
                        binding.leftValue.setText(StringHelper.numberFormat(data.getOrderHari()));
                        binding.middleValue.setText(StringHelper.numberFormat(data.getOrderBulan()));
                        binding.rightValue.setText(StringHelper.numberFormat(data.getOrderTotal()));
                        break;
                    case PIUTANG:
                        binding.middleValue.setText(StringHelper.numberFormat(data.getPiutang30()));
                        binding.rightValue.setText(StringHelper.numberFormat(data.getPiutang60()));
                        binding.leftValue.setText(StringHelper.numberFormat(data.getPiutangHari()));
                        binding.progressValue.setText(StringHelper.numberFormat(data.getPiutangTotal()));
                        binding.progress.setProgress(50);
                        break;
                    case UANGMUKA:
                        binding.progressValue.setText(StringHelper.numberFormat(data.getSaldoDp()));
                        binding.progress.setProgress(50);
                        binding.dpMasuk.setText(StringHelper.numberFormat(data.getDpMasuk()));
                        binding.dpKeluar.setText(StringHelper.numberFormat(data.getDpKeluar()));
                        break;
                    case PEMBAYARAN:
                        binding.leftValue.setText(StringHelper.numberFormat(data.getPaymentHari()));
                        binding.middleValue.setText(StringHelper.numberFormat(data.getPaymentBulan()));
                        binding.rightValue.setText(StringHelper.numberFormat(data.getPaymentTotal()));
                        break;
                }

            }
        });
    }

    private void setCurrentType(DashboardCustomerActivity.DashboardType type) {
        currentType = type;
        listAdapter.setCurrentType(type);

        binding.tabPenjualan.setSelected(false);
        binding.tabPiutang.setSelected(false);
        binding.tabUangMuka.setSelected(false);
        binding.tabLunas.setSelected(false);
        binding.tabPembayaran.setSelected(false);

        binding.progressLayout.setVisibility(View.GONE);
        binding.dpValue.setVisibility(View.GONE);
        binding.valueLayout.setVisibility(View.GONE);
        binding.textLayout.setVisibility(View.GONE);

        binding.leftText.setText(R.string.hari_ini);
        binding.middleText.setText(R.string.bulan_ini);
        binding.rightText.setText(R.string.tahun_ini);

        binding.dpFooter.setVisibility(View.GONE);
        binding.pembelianFooter.setVisibility(View.GONE);

        binding.leftValue.setText(StringHelper.numberFormat(0.0));
        binding.middleValue.setText(StringHelper.numberFormat(0.0));
        binding.rightValue.setText(StringHelper.numberFormat(0.0));
        switch (type) {
            case PIUTANG:
                binding.valueLayout.setVisibility(View.VISIBLE);
                binding.textLayout.setVisibility(View.VISIBLE);
                binding.progressLayout.setVisibility(View.VISIBLE);
                binding.tabPiutang.setSelected(true);
                binding.leftText.setText(R.string.belum_jatuh_tempo);
                binding.middleText.setText(R.string.hutang_1_30_hari);
                binding.rightText.setText(R.string.hutang_31_60_hari);
                binding.progressText.setText(R.string.total_hutang);
                break;
            case LUNAS:
                binding.valueLayout.setVisibility(View.VISIBLE);
                binding.textLayout.setVisibility(View.VISIBLE);
//                binding.progressLayout.setVisibility(View.VISIBLE);
                binding.tabLunas.setSelected(true);
//                binding.leftText.setText(R.string.belum_jatuh_tempo);
//                binding.middleText.setText(R.string.hutang_1_30_hari);
//                binding.rightText.setText(R.string.hutang_31_60_hari);
//                binding.progressText.setText(R.string.total_hutang);
                break;
            case UANGMUKA:
                binding.progressLayout.setVisibility(View.VISIBLE);
                binding.progressText.setText(R.string.total_dp);
                binding.dpValue.setVisibility(View.VISIBLE);
                binding.dpFooter.setVisibility(View.VISIBLE);
                binding.tabUangMuka.setSelected(true);
                break;
            case PENJUALAN:
                binding.valueLayout.setVisibility(View.VISIBLE);
                binding.textLayout.setVisibility(View.VISIBLE);
                binding.pembelianFooter.setVisibility(View.VISIBLE);
                binding.tabPenjualan.setSelected(true);
                break;
            case PEMBAYARAN:
                binding.valueLayout.setVisibility(View.VISIBLE);
                binding.textLayout.setVisibility(View.VISIBLE);
                binding.tabPembayaran.setSelected(true);
                break;
        }

        viewModel.searchData(customerId, currentType, query);
    }

    private void setupView() {
        binding = ActivityDetailCustomerBinding.inflate(getLayoutInflater());
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
                setCurrentType(DashboardCustomerActivity.DashboardType.PENJUALAN);
            }
        });

        binding.tabPiutang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.search.removeTextChangedListener(textWatcher);
                binding.search.setText("");
                binding.search.addTextChangedListener(textWatcher);
                setCurrentType(DashboardCustomerActivity.DashboardType.PIUTANG);
            }
        });

        binding.tabUangMuka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.search.removeTextChangedListener(textWatcher);
                binding.search.setText("");
                binding.search.addTextChangedListener(textWatcher);
                setCurrentType(DashboardCustomerActivity.DashboardType.UANGMUKA);
            }
        });

        binding.tabLunas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.search.removeTextChangedListener(textWatcher);
                binding.search.setText("");
                binding.search.addTextChangedListener(textWatcher);
                setCurrentType(DashboardCustomerActivity.DashboardType.LUNAS);
            }
        });

        binding.tabPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.search.removeTextChangedListener(textWatcher);
                binding.search.setText("");
                binding.search.addTextChangedListener(textWatcher);
                setCurrentType(DashboardCustomerActivity.DashboardType.PEMBAYARAN);
            }
        });

        binding.addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.getTotalData().getValue() != null) {
                    Intent intent = new Intent(DetailCustomerActivity.this, OrderPenjualanActivity.class);
                    intent.putExtra(OrderPenjualanActivity.ARG_CUSTOMER, viewModel.getTotalData().getValue());
                    startActivity(intent);
                }
            }
        });

        binding.retur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.getTotalData().getValue() != null) {
                    Intent intent = new Intent(DetailCustomerActivity.this, OrderReturPenjualanActivity.class);
                    intent.putExtra(OrderReturPenjualanActivity.ARG_CUSTOMER, viewModel.getTotalData().getValue());
                    startActivity(intent);
                }
            }
        });

        binding.addDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailCustomerActivity.this, TambahDpCustomerActivity.class);
                intent.putExtra(TambahDpCustomerActivity.ARG_CUSTOMER_ID, customerId);
                startActivity(intent);
            }
        });

        binding.refundDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailCustomerActivity.this, TambahDpCustomerActivity.class);
                intent.putExtra(TambahDpCustomerActivity.ARG_CUSTOMER_ID, customerId);
                intent.putExtra(TambahDpCustomerActivity.ARG_IS_REFUND, true);
                startActivity(intent);
            }
        });

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

        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailCustomerActivity.this, TambahCustomerActivity.class);
                intent.putExtra(TambahCustomerActivity.ARG_PARTNER_ID, customerId);
                startActivity(intent);
            }
        });
    }

    private void setupList() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);

        listAdapter = new DetailCustomerListAdapter(this, viewModel);
        listAdapter.setListener(this);
        binding.list.setAdapter(listAdapter);
    }

    @Override
    public void onEditDp(Integer id) {
        Intent intent = new Intent(this, TambahDpCustomerActivity.class);
        intent.putExtra(TambahDpCustomerActivity.ARG_CUSTOMER_ID, customerId);
        intent.putExtra(TambahDpCustomerActivity.ARG_DP_ID, id);
        startActivity(intent);
    }

    @Override
    public void onCheckout(Integer id) {
        if (viewModel.getTotalData().getValue() != null) {
            Intent intent = new Intent(this, CheckoutPenjualanActivity.class);
            intent.putExtra(CheckoutPenjualanActivity.ARG_ORDER_ID, id);
            intent.putExtra(CheckoutPenjualanActivity.ARG_CUSTOMER, viewModel.getTotalData().getValue());
            startActivity(intent);
        }
    }

    @Override
    public void onViewOrder(Integer id) {
        if (viewModel.getTotalData().getValue() != null) {
            Intent intent = new Intent(this, CheckoutPenjualanActivity.class);
            intent.putExtra(CheckoutPenjualanActivity.ARG_ORDER_ID, id);
            intent.putExtra(CheckoutPenjualanActivity.ARG_CUSTOMER, viewModel.getTotalData().getValue());
            intent.putExtra(CheckoutPenjualanActivity.ARG_IS_VIEW, true);
            startActivity(intent);
        }
    }

    @Override
    public void onKirimBarang(Integer id) {
        if (viewModel.getTotalData().getValue() != null) {
            Intent intent = new Intent(this, PengirimanBarangActivity.class);
            intent.putExtra(PengirimanBarangActivity.ARG_ORDER_ID, id);
            intent.putExtra(PengirimanBarangActivity.ARG_CUSTOMER, viewModel.getTotalData().getValue());
            startActivity(intent);
        }
    }

    @Override
    public void onBayarHutang(Integer id, Boolean isEdit) {
        Intent intent = new Intent(this, PembayaranCustomerActivity.class);
        intent.putExtra(PembayaranCustomerActivity.ARG_ORDER_ID, id);
        intent.putExtra(PembayaranCustomerActivity.ARG_IS_EDIT, isEdit);
        startActivity(intent);
    }

    @Override
    public void onViewPayment(Integer id) {
        Intent intent = new Intent(this, PembayaranCustomerActivity.class);
        intent.putExtra(PembayaranCustomerActivity.ARG_ORDER_ID, id);
        intent.putExtra(PembayaranCustomerActivity.ARG_IS_EDIT, true);
        intent.putExtra(PembayaranCustomerActivity.ARG_IS_FROM_PAYMENT, true);
        startActivity(intent);
    }
}