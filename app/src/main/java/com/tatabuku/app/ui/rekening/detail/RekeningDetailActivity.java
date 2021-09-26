package com.tatabuku.app.ui.rekening.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.tatabuku.app.databinding.ActivityRekeningDetailBinding;
import com.tatabuku.app.databinding.RekeningDetailListItemBinding;
import com.tatabuku.app.model.rekening.RekeningDetailHeader;
import com.tatabuku.app.model.rekening.RekeningDetailTransaction;
import com.tatabuku.app.ui.BaseActivity;
import com.tatabuku.app.ui.pembelian.pembayaran.PembayaranActivity;
import com.tatabuku.app.ui.penjualan.pembayaran.PembayaranCustomerActivity;
import com.tatabuku.app.ui.rekening.dashboard.DashboardRekeningListener;
import com.tatabuku.app.ui.rekening.catat_biaya.CatatBiayaActivity;
import com.tatabuku.app.ui.rekening.dashboard.DashboardRekeningViewModel;
import com.tatabuku.app.ui.rekening.pindah_buku.PindahBukuActivity;
import com.tatabuku.app.ui.rekening.rincian_biaya.RincianBiayaActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.List;

public class RekeningDetailActivity extends BaseActivity {

    public static final String SELECTED_REKENING_ID = "SELECTED_REKENING_ID";
    public static final String SELECTED_REKENING_NAME = "SELECTED_REKENING_NAME";

    private ActivityRekeningDetailBinding binding;
    private LinearLayoutManager layoutManager;
    private RekeningDetailListAdapter listAdapter;
    private RekeningDetailListener listener;
    private RekeningDetailViewModel viewModel;

    private TextWatcher searchTextWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        configureViewModel();
        setupView();
        configureView();
        setupList();

        configureIntent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.fetchByType(RekeningDetailViewModel.RekeningType.SEMUA);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(RekeningDetailViewModel.class);
    }

    private void configureViewModel() {
        viewModel.getHeader().observe(this, new Observer<RekeningDetailHeader>() {
            @Override
            public void onChanged(RekeningDetailHeader header) {
                binding.totalBalance.setText(StringHelper.numberFormatWithDecimal(header.getSaldo()));
                binding.revenueIncome.setText(StringHelper.numberFormatWithDecimal(header.getMasuk()));
                binding.revenueOutcome.setText(StringHelper.numberFormatWithDecimal(header.getKeluar()));
            }
        });

        viewModel.getTransactions().observe(this, new Observer<List<RekeningDetailTransaction>>() {
            @Override
            public void onChanged(List<RekeningDetailTransaction> rekeningDetailTransactions) {
                listAdapter.notifyDataSetChanged();
                binding.swipeContainer.setRefreshing(false);
            }
        });

        viewModel.getOnError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideLoading();
                showAlert("Perhatian", s);
            }
        });
    }

    private void setupView() {
        binding = ActivityRekeningDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSelectedTab(0);
    }

    private void configureView() {
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        searchTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = binding.search.getText().toString();
                viewModel.fetchByName(query);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };

        binding.search.addTextChangedListener(searchTextWatcher);

        binding.buttonCatatBiaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RekeningDetailActivity.this, CatatBiayaActivity.class);
                intent.putExtra(CatatBiayaActivity.CATAT_BIAYA_BANK_ID, viewModel.getBank_id());
                intent.putExtra(CatatBiayaActivity.CATAT_BIAYA_BANK_NAME, viewModel.getBank_name());
                intent.putExtra(CatatBiayaActivity.CATAT_BIAYA_TYPE, "biaya");
                startActivity(intent);
            }
        });

        binding.buttonPindahBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RekeningDetailActivity.this, PindahBukuActivity.class);
                intent.putExtra(PindahBukuActivity.BANK_FROM_ID, viewModel.getBank_id());
                startActivity(intent);
            }
        });

        binding.buttonTerimaUang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RekeningDetailActivity.this, CatatBiayaActivity.class);
                intent.putExtra(CatatBiayaActivity.CATAT_BIAYA_BANK_ID, viewModel.getBank_id());
                intent.putExtra(CatatBiayaActivity.CATAT_BIAYA_BANK_NAME, viewModel.getBank_name());
                intent.putExtra(CatatBiayaActivity.CATAT_BIAYA_TYPE, "pemasukan");
                startActivity(intent);
            }
        });

        binding.tabSemua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedTab(0);
                binding.search.removeTextChangedListener(searchTextWatcher);
                binding.search.setText("");
                binding.search.addTextChangedListener(searchTextWatcher);
                viewModel.setSelectedTab(RekeningDetailViewModel.RekeningType.SEMUA);
            }
        });

        binding.tabUangMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedTab(1);
                binding.search.removeTextChangedListener(searchTextWatcher);
                binding.search.setText("");
                binding.search.addTextChangedListener(searchTextWatcher);
                viewModel.setSelectedTab(RekeningDetailViewModel.RekeningType.UANGMASUK);
            }
        });

        binding.tabUangKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedTab(2);
                binding.search.removeTextChangedListener(searchTextWatcher);
                binding.search.setText("");
                binding.search.addTextChangedListener(searchTextWatcher);
                viewModel.setSelectedTab(RekeningDetailViewModel.RekeningType.UANGKELUAR);
            }
        });

        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refreshTab();
            }
        });

        listener = new RekeningDetailListener() {
            @Override
            public void onItemClick(RekeningDetailTransaction rekening) {
                String origin = rekening.getLink().getOrigin();
                String id = rekening.getLink().getId();

                if (id == null || id.isEmpty() || origin == null) {
                    viewModel.getOnError().setValue("Terjadi Kesalahan");
                    return;
                }

                switch (origin) {
                    case "pemasukan":
                    case "biaya":
                        // to Catat Biaya
                        Intent intent = new Intent(RekeningDetailActivity.this, RincianBiayaActivity.class);
                        intent.putExtra(RincianBiayaActivity.POSTED_VOUCHER_ID, id);
                        intent.putExtra(RincianBiayaActivity.BANK_NAME, viewModel.getBank_name());
                        intent.putExtra(RincianBiayaActivity.BANK_FROM, viewModel.getBank_id());
                        startActivity(intent);
                        break;
                    case "pindahbuku":
                        // to Pindah Buku
                        break;
                    case "downpayment":
                        // to ??
                        break;
                    case "pembelian":
                        Intent pembelianIntent = new Intent(RekeningDetailActivity.this, PembayaranActivity.class);
                        try {
                            int orderId = Integer.parseInt(id);
                            pembelianIntent.putExtra(PembayaranActivity.ARG_ORDER_ID, orderId);
                            pembelianIntent.putExtra(PembayaranActivity.ARG_IS_VIEW, true);
                            startActivity(pembelianIntent);
                        } catch(NumberFormatException ex) {

                        }
                        break;

                    case "penjualan" :
                        Intent penjualan = new Intent(RekeningDetailActivity.this, PembayaranCustomerActivity.class);
                        try {
                            int orderId = Integer.parseInt(id);
                            penjualan.putExtra(PembayaranCustomerActivity.ARG_ORDER_ID, orderId);
                            penjualan.putExtra(PembayaranCustomerActivity.ARG_IS_VIEW, true);
                            startActivity(penjualan);
                        } catch(NumberFormatException ex) {

                        }
                    default:
                        Log.e("Link type not handled", origin);
                        break;
                }
            }
        };
    }

    private void setSelectedTab(int index) {
        binding.overlayTabSemua.setAlpha((float) 0.5);
        binding.overlayTabMasuk.setAlpha((float) 0.5);
        binding.overlayTabKeluar.setAlpha((float) 0.5);

        switch (index) {
            case 0:
                binding.overlayTabSemua.setAlpha((float) 0);
                break;
            case 1:
                binding.overlayTabMasuk.setAlpha((float) 0);
                break;
            case 2:
                binding.overlayTabKeluar.setAlpha((float) 0);
                break;
            default:
                break;
        }
    }

    private void setupList() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);

        listAdapter = new RekeningDetailListAdapter(this ,viewModel);
        listAdapter.setListener(listener);
        binding.list.setAdapter(listAdapter);
    }

    private void configureIntent() {
        Intent intent = getIntent();
        int bank_id = intent.getIntExtra(SELECTED_REKENING_ID, 0);
        String bank_name = intent.getStringExtra(SELECTED_REKENING_NAME);
        binding.rekeningType.setText(bank_name);

        viewModel.setBank_id(bank_id);
        viewModel.setBank_name(bank_name);
        viewModel.fetchByType(RekeningDetailViewModel.RekeningType.SEMUA);
    }
}