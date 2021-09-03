package com.tatabuku.app.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.tatabuku.app.databinding.ActivityHomeBinding;
import com.tatabuku.app.databinding.ActivityWebViewBinding;
import com.tatabuku.app.databinding.HomeBankItemBinding;
import com.tatabuku.app.model.home.BankResult;
import com.tatabuku.app.model.home.HomepageDataResult;
import com.tatabuku.app.ui.WebViewActivity;
import com.tatabuku.app.ui.accounting.dashboard.DashboardAccountingActivity;
import com.tatabuku.app.ui.admin.AdminSetting;
import com.tatabuku.app.ui.pembelian.dashboard.DashboardSupplierActivity;
import com.tatabuku.app.ui.fix_asset.dashboard.DashboardFixAssetActivity;
import com.tatabuku.app.ui.penjualan.dashboard.DashboardCustomerActivity;
import com.tatabuku.app.ui.titip_journal.detail.TitipJournalActivity;
import com.tatabuku.app.util.StringHelper;

import com.tatabuku.app.ui.rekening.dashboard.DashboardRekeningActivity;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private HomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        setupObserver();
        setupView();
        configureView();

        viewModel.getData();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    private void setupObserver() {
        viewModel.getHomeData().observe(this, new Observer<HomepageDataResult>() {
            @Override
            public void onChanged(HomepageDataResult homepageDataResult) {
                binding.pembelian.setText(StringHelper.numberFormatWithDecimal(homepageDataResult.getTotalPembelian()));
                binding.penjualan.setText(StringHelper.numberFormatWithDecimal(homepageDataResult.getTotalPenjualan()));
                binding.progressBar.setProgress((int) (homepageDataResult.getTotalPembelian() / (homepageDataResult.getTotalPenjualan() + homepageDataResult.getTotalPembelian()) * 100));

                binding.hutangPelanggan.setText(StringHelper.numberFormatWithDecimal(homepageDataResult.getHutangPelanggan()));
                binding.hutangSaya.setText(StringHelper.numberFormatWithDecimal(homepageDataResult.getHutangSaya()));

                binding.hutangPelanggan.setProgress((int) (homepageDataResult.getHutangPelanggan() / (homepageDataResult.getHutangPelanggan()+homepageDataResult.getHutangSaya()) * 100));
                binding.hutangSaya.setProgress((int) (homepageDataResult.getHutangSaya() / (homepageDataResult.getHutangPelanggan()+homepageDataResult.getHutangSaya()) * 100));


                binding.bankLayout.removeAllViewsInLayout();
                Double total = 0.0;
                for (BankResult bank :
                        homepageDataResult.getBankData()) {
                    total += bank.getBalance();
                }

                for (BankResult bank :
                        homepageDataResult.getBankData()) {
                    HomeBankItemBinding itemBinding = HomeBankItemBinding.inflate(getLayoutInflater());
                    itemBinding.text.setText(bank.getName());
                    itemBinding.progress.setText(StringHelper.numberFormatWithDecimal(bank.getBalance()));
                    itemBinding.progress.setProgress((int) (bank.getBalance() / total * 100));

                    binding.bankLayout.addView(itemBinding.getRoot());
                }
            }
        });
    }

    private void setupView() {
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void configureView() {
        binding.menuPenjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, DashboardCustomerActivity.class);
                startActivity(intent);
            }
        });

        binding.menuPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, DashboardSupplierActivity.class);
                startActivity(intent);
            }
        });

        binding.menuRekening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, DashboardRekeningActivity.class);
                startActivity(intent);
            }
        });

        binding.menuAccounting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, DashboardAccountingActivity.class);
                startActivity(intent);
            }
        });

//        binding.menuFixedAsset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(HomeActivity.this, DashboardFixAssetActivity.class);
//                startActivity(intent);
//            }
//        });

//        binding.menuTataBukuKu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(HomeActivity.this, TitipJournalActivity.class);
//                startActivity(intent);
//            }
//        });

        binding.menuReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, WebViewActivity.class);
                startActivity(intent);
            }
        });

        binding.menuAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AdminSetting.class);
                startActivity(intent);
            }
        });
    }
}