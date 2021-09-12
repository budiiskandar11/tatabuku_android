package com.tatabuku.app.ui.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.tatabuku.app.databinding.ActivityAdminSettingBinding;
import com.tatabuku.app.ui.home.HomeActivity;
import com.tatabuku.app.ui.penjualan.dashboard.DashboardCustomerActivity;
import com.tatabuku.app.ui.saldoPiutang.ActivitySaldoPiutang;


public class AdminSetting extends AppCompatActivity {

    public static final String ARG_URL = "ARG_URL";

    private ActivityAdminSettingBinding binding;
    private String url = "";

    private WebViewClient client = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra(ARG_URL);
        setupView();
        configureView();
    }

    private void setupView() {
        binding = ActivityAdminSettingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void configureView() {
        binding.menuSaldoAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminSetting.this, ActivitySaldoPiutang.class);
                startActivity(intent);
            }
        });

        binding.menuSaldoPiutang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminSetting.this, DashboardCustomerActivity.class);
                startActivity(intent);
            }
        });

        binding.menuSaldoStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminSetting.this, DashboardCustomerActivity.class);
                startActivity(intent);
            }
        });

    }

}