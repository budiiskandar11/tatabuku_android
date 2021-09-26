package com.tatabuku.app.ui.pembelian.detail;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.ActivityDetailSupplierBinding;
import com.tatabuku.app.model.pembelian.DownPaymentResult;
import com.tatabuku.app.model.pembelian.InvoiceResult;
import com.tatabuku.app.model.pembelian.LunasDataResult;
import com.tatabuku.app.model.pembelian.OrderResult;
import com.tatabuku.app.model.pembelian.PembayaranItem;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.ui.pembelian.checkout.CheckoutPembelianActivity;
import com.tatabuku.app.ui.pembelian.dashboard.DashboardSupplierActivity;
import com.tatabuku.app.ui.pembelian.order.OrderPembelianActivity;
import com.tatabuku.app.ui.pembelian.order.OrderReturActivity;
import com.tatabuku.app.ui.pembelian.pembayaran.PembayaranActivity;
import com.tatabuku.app.ui.pembelian.tambah.dp.TambahDpPembelianActivity;
import com.tatabuku.app.ui.pembelian.penerimaan.PenerimaanBarangActivity;
import com.tatabuku.app.ui.pembelian.tambah.supplier.TambahSupplierActivity;
import com.tatabuku.app.ui.penjualan.checkout.CheckoutPenjualanActivity;
import com.tatabuku.app.ui.penjualan.tambah.dp.TambahDpCustomerActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.List;

public class DetailSupplierActivity extends AppCompatActivity implements DetailSupplierListener {
    public static final String ARG_DASHBOARD_TYPE = "ARG_DASHBOARD_TYPE";
    public static final String ARG_SUPPLIER_ID = "ARG_SUPPLIER_ID";

    private ActivityDetailSupplierBinding binding;
    private DetailSupplierListAdapter listAdapter;
    private LinearLayoutManager layoutManager;

    private DashboardSupplierActivity.DashboardType currentType;
    private Integer supplierId;
    private DetailSupplierViewModel viewModel;
    private String query = "";

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            query = binding.search.getText().toString();
            viewModel.searchData(supplierId, currentType, query);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supplierId = getIntent().getIntExtra(ARG_SUPPLIER_ID, 0);
        currentType = (DashboardSupplierActivity.DashboardType) getIntent().getSerializableExtra(ARG_DASHBOARD_TYPE);

        if (currentType == null) {
            currentType = DashboardSupplierActivity.DashboardType.PEMBELIAN;
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
        viewModel = new ViewModelProvider(this).get(DetailSupplierViewModel.class);
    }

    private void configureViewModel() {

        viewModel.getIsSort().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.sort.setColorFilter(ContextCompat.getColor(DetailSupplierActivity.this, R.color.orange), PorterDuff.Mode.SRC_ATOP);
                } else {
                    binding.sort.setColorFilter(null);
                }
                setCurrentType(currentType);
            }
        });

        viewModel.getOrderList().observe(this, new Observer<List<OrderResult>>() {
            @Override
            public void onChanged(List<OrderResult> orderResults) {
                binding.swipeContainer.setRefreshing(false);
                listAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getInvoiceList().observe(this, new Observer<List<InvoiceResult>>() {
            @Override
            public void onChanged(List<InvoiceResult> invoiceResults) {
                binding.swipeContainer.setRefreshing(false);
                listAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getDpList().observe(this, new Observer<List<DownPaymentResult>>() {
            @Override
            public void onChanged(List<DownPaymentResult> downPaymentResults) {
                binding.swipeContainer.setRefreshing(false);
                listAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getPaymentList().observe(this, new Observer<List<PembayaranItem>>() {
            @Override
            public void onChanged(List<PembayaranItem> pembayaranItems) {
                binding.swipeContainer.setRefreshing(false);
                listAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getSupplierResult().observe(this, new Observer<SupplierResult>() {
            @Override
            public void onChanged(SupplierResult supplierResult) {
                try {
                    Glide.with(DetailSupplierActivity.this).asBitmap()
                            .load(Base64.decode(supplierResult.getImage(), Base64.DEFAULT))
                            .into(binding.image2);
                } catch (Exception e) {

                }
                binding.name.setText(supplierResult.getName());
                binding.address.setText(supplierResult.getStreet() + " " + supplierResult.getStreet2());
                binding.phone.setText(supplierResult.getPhone());

                switch (currentType) {
                    case PEMBELIAN:
                        binding.leftValue.setText(StringHelper.numberFormat(supplierResult.getPurchaseOrderHari()));
                        binding.middleValue.setText(StringHelper.numberFormat(supplierResult.getPurchaseOrderBulan()));
                        binding.rightValue.setText(StringHelper.numberFormat(supplierResult.getPurchaseOrderTotal()));
                        break;
                    case HUTANG:
                    case LUNAS:
                        binding.middleValue.setText(StringHelper.numberFormat(supplierResult.getHutangBulan()));
                        binding.rightValue.setText(StringHelper.numberFormat(supplierResult.getHutangTahun()));
                        binding.leftValue.setText(StringHelper.numberFormat(supplierResult.getHutangHari()));
                        binding.progressValue.setText(StringHelper.numberFormat(supplierResult.getDebit()));
                        binding.progress.setProgress(50);
                        break;
                    case UANGMUKA:
                        binding.progressValue.setText(StringHelper.numberFormat(supplierResult.getDpTotal()));
                        binding.progress.setProgress(50);
                        binding.dpMasuk.setText(StringHelper.numberFormat(supplierResult.getDpPembelianMasuk()));
                        binding.dpKeluar.setText(StringHelper.numberFormat(supplierResult.getDpPembelianKeluar()));
                        break;
                    case PEMBAYARAN:
                        binding.leftValue.setText(StringHelper.numberFormat(supplierResult.getPaymentHari()));
                        binding.middleValue.setText(StringHelper.numberFormat(supplierResult.getPaymentBulan()));
                        binding.rightValue.setText(StringHelper.numberFormat(supplierResult.getPaymentTahun()));
                        break;
                }

            }
        });
    }

    private void setCurrentType(DashboardSupplierActivity.DashboardType type) {
        currentType = type;
        listAdapter.setCurrentType(type);

        binding.tabPembelian.setSelected(false);
        binding.tabHutang.setSelected(false);
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
            case HUTANG:
                binding.valueLayout.setVisibility(View.VISIBLE);
                binding.textLayout.setVisibility(View.VISIBLE);
                binding.progressLayout.setVisibility(View.VISIBLE);
                binding.tabHutang.setSelected(true);
                binding.leftText.setText(R.string.belum_jatuh_tempo);
                binding.middleText.setText(R.string.hutang_1_30_hari);
                binding.rightText.setText(R.string.hutang_31_60_hari);
                binding.progressText.setText(R.string.total_hutang);
                break;
//            case LUNAS:
//                binding.valueLayout.setVisibility(View.VISIBLE);
//                binding.textLayout.setVisibility(View.VISIBLE);
//                binding.progressLayout.setVisibility(View.VISIBLE);
//                binding.tabLunas.setSelected(true);
//                binding.leftText.setText(R.string.belum_jatuh_tempo);
//                binding.middleText.setText(R.string.hutang_1_30_hari);
//                binding.rightText.setText(R.string.hutang_31_60_hari);
//                binding.progressText.setText(R.string.total_hutang);
//                break;
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
            case PEMBELIAN:
                binding.valueLayout.setVisibility(View.VISIBLE);
                binding.textLayout.setVisibility(View.VISIBLE);
                binding.pembelianFooter.setVisibility(View.VISIBLE);
                binding.tabPembelian.setSelected(true);
                break;
            case PEMBAYARAN:
                binding.valueLayout.setVisibility(View.VISIBLE);
                binding.textLayout.setVisibility(View.VISIBLE);
                binding.tabPembayaran.setSelected(true);
                break;
        }

        viewModel.fetchData(supplierId, type);
    }

    private void setupView() {
        binding = ActivityDetailSupplierBinding.inflate(getLayoutInflater());
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
                setCurrentType(DashboardSupplierActivity.DashboardType.PEMBELIAN);
            }
        });

        binding.tabHutang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.search.removeTextChangedListener(textWatcher);
                binding.search.setText("");
                binding.search.addTextChangedListener(textWatcher);
                setCurrentType(DashboardSupplierActivity.DashboardType.HUTANG);
            }
        });

        binding.tabUangMuka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.search.removeTextChangedListener(textWatcher);
                binding.search.setText("");
                binding.search.addTextChangedListener(textWatcher);
                setCurrentType(DashboardSupplierActivity.DashboardType.UANGMUKA);
            }
        });

        binding.tabLunas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.search.removeTextChangedListener(textWatcher);
                binding.search.setText("");
                binding.search.addTextChangedListener(textWatcher);
                setCurrentType(DashboardSupplierActivity.DashboardType.LUNAS);
            }
        });

        binding.tabPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.search.removeTextChangedListener(textWatcher);
                binding.search.setText("");
                binding.search.addTextChangedListener(textWatcher);
                setCurrentType(DashboardSupplierActivity.DashboardType.PEMBAYARAN);
            }
        });

        binding.addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.getSupplierResult().getValue() != null) {
                    Intent intent = new Intent(DetailSupplierActivity.this, OrderPembelianActivity.class);
                    intent.putExtra(OrderPembelianActivity.ARG_SUPPLIER, viewModel.getSupplierResult().getValue());
                    startActivity(intent);
                }
            }
        });

        binding.retur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.getSupplierResult().getValue() != null) {
                    Intent intent = new Intent(DetailSupplierActivity.this, OrderReturActivity.class);
                    intent.putExtra(OrderPembelianActivity.ARG_SUPPLIER, viewModel.getSupplierResult().getValue());
                    startActivity(intent);
                }
            }
        });

        binding.addDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailSupplierActivity.this, TambahDpPembelianActivity.class);
                intent.putExtra(TambahDpPembelianActivity.ARG_SUPPLIER, viewModel.getSupplierResult().getValue());
                startActivity(intent);
            }
        });

        binding.refundDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailSupplierActivity.this, TambahDpPembelianActivity.class);
                intent.putExtra(TambahDpPembelianActivity.ARG_SUPPLIER, viewModel.getSupplierResult().getValue());
                intent.putExtra(TambahDpPembelianActivity.ARG_IS_REFUND, true);
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
                Intent intent = new Intent(DetailSupplierActivity.this, TambahSupplierActivity.class);
                intent.putExtra(TambahSupplierActivity.ARG_PARTNER_ID, supplierId);
                startActivity(intent);
            }
        });
    }

    private void setupList() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);

        listAdapter = new DetailSupplierListAdapter(this, viewModel);
        listAdapter.setListener(this);
        binding.list.setAdapter(listAdapter);
    }

    @Override
    public void onCheckout(Integer id) {
        if (viewModel.getSupplierResult().getValue() != null) {
            Intent intent = new Intent(this, CheckoutPembelianActivity.class);
            intent.putExtra(CheckoutPembelianActivity.ARG_ORDER_ID, id);
            intent.putExtra(CheckoutPembelianActivity.ARG_SUPPLIER, viewModel.getSupplierResult().getValue());
            startActivity(intent);
        }
    }

    @Override
    public void onTerimaBarang(Integer id) {
        if (viewModel.getSupplierResult().getValue() != null) {
            Intent intent = new Intent(this, PenerimaanBarangActivity.class);
            intent.putExtra(PenerimaanBarangActivity.ARG_ORDER_ID, id);
            intent.putExtra(PenerimaanBarangActivity.ARG_SUPPLIER, viewModel.getSupplierResult().getValue());
            startActivity(intent);
        }
    }

    @Override
    public void onBayarHutang(Integer id, Boolean isEdit) {
        Intent intent = new Intent(this, PembayaranActivity.class);
        intent.putExtra(PembayaranActivity.ARG_ORDER_ID, id);
        intent.putExtra(PembayaranActivity.ARG_IS_EDIT, isEdit);
        startActivity(intent);
    }

    @Override
    public void onEditDP(Integer id) {
        if (viewModel.getSupplierResult().getValue() != null) {
            Intent intent = new Intent(this, TambahDpPembelianActivity.class);
            intent.putExtra(PenerimaanBarangActivity.ARG_SUPPLIER, viewModel.getSupplierResult().getValue());
            intent.putExtra(TambahDpPembelianActivity.ARG_DP_ID, id);
            startActivity(intent);
        }
    }

    @Override
    public void onViewOrder(Integer id) {
        if (viewModel.getSupplierResult().getValue() != null) {
            Intent intent = new Intent(this, CheckoutPembelianActivity.class);
            intent.putExtra(CheckoutPembelianActivity.ARG_ORDER_ID, id);
            intent.putExtra(CheckoutPembelianActivity.ARG_SUPPLIER, viewModel.getSupplierResult().getValue());
            intent.putExtra(CheckoutPembelianActivity.ARG_IS_VIEW, true);
            startActivity(intent);
        }
    }

    @Override
    public void onViewPayment(Integer id) {
        Intent intent = new Intent(this, PembayaranActivity.class);
        intent.putExtra(PembayaranActivity.ARG_ORDER_ID, id);
        intent.putExtra(PembayaranActivity.ARG_IS_EDIT, true);
        intent.putExtra(PembayaranActivity.ARG_IS_FROM_PAYMENT, true);
        startActivity(intent);
    }
}