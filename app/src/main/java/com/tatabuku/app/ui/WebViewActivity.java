package com.tatabuku.app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tatabuku.app.BuildConfig;
import com.tatabuku.app.R;
import com.tatabuku.app.databinding.ActivityLoginBinding;
import com.tatabuku.app.databinding.ActivityWebViewBinding;
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.ui.login.LoginActivity;
import com.tatabuku.app.ui.pembelian.dashboard.DashboardSupplierActivity;
import com.tatabuku.app.ui.register.RegisterWebActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.Collections;

public class WebViewActivity<SetCurrentTab> extends AppCompatActivity {

    public enum TabType {
        PEMBELIAN, PENJUALAN, HUTANG, PIUTANG, NERACA, PNL, STOCK
    }

    public static final String ARG_URL = "ARG_URL";

    private ActivityWebViewBinding binding;
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

        setCurrentTab(TabType.PEMBELIAN);

    }

    private void setupView() {
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

//        testurl url = BuildConfig.BASE_URL;

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.webview.setWebViewClient(client);
        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.loadUrl("http://103.152.118.69:8069/web?debug#action=270&model=purchase.report&view_type=pivot&menu_id=148");

//        tambahan budi
        binding.reportPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.title.setText("Report Pembelian");
                binding.webview.setWebViewClient(client);
                binding.webview.getSettings().setJavaScriptEnabled(true);
                binding.webview.loadUrl("http://103.152.118.69:8069/web?debug#action=270&model=purchase.report&view_type=pivot&menu_id=148");
                setCurrentTab(TabType.PEMBELIAN);
                binding.reportPembelian.setSelected(true);

            }
        });

        binding.reportHutang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.title.setText("Report Hutang");
                binding.webview.setWebViewClient(client);
                binding.webview.getSettings().setJavaScriptEnabled(true);
                binding.webview.loadUrl("http://103.152.118.69:8069/web#action=321&menu_id=85");
                setCurrentTab(TabType.HUTANG);
                binding.reportHutang.setSelected(true);
            }
        });

        binding.reportPiutang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.title.setText("Report Piutang");
                binding.webview.setWebViewClient(client);
                binding.webview.getSettings().setJavaScriptEnabled(true);
                binding.webview.loadUrl("http://103.152.118.69:8069/web#action=320&menu_id=85");
                setCurrentTab(TabType.PIUTANG);
                binding.reportPiutang.setSelected(true);
            }
        });

        binding.reportPenjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.title.setText("Report Hutang");
                binding.webview.setWebViewClient(client);
                binding.webview.getSettings().setJavaScriptEnabled(true);
                binding.webview.loadUrl("http://103.152.118.69:8069/web#action=338&model=sale.report&view_type=pivot&menu_id=221");
                setCurrentTab(TabType.PENJUALAN);
                binding.reportPenjualan.setSelected(true);
            }
        });

        binding.reportNeraca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.title.setText("Report Neraca");
                binding.webview.setWebViewClient(client);
                binding.webview.getSettings().setJavaScriptEnabled(true);
                binding.webview.loadUrl("http://103.152.118.69:8069/web#action=309&menu_id=85");
                setCurrentTab(TabType.NERACA);
                binding.reportNeraca.setSelected(true);
            }
        });

        binding.reportPnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.title.setText("Report Laba Rugi");
                binding.webview.setWebViewClient(client);
                binding.webview.getSettings().setJavaScriptEnabled(true);
                binding.webview.loadUrl("http://103.152.118.69:8069/web#action=308&menu_id=85");
                setCurrentTab(TabType.PNL);
                binding.reportPnl.setSelected(true);
            }
        });

        binding.reportStok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.title.setText("Report Stok");
                binding.webview.setWebViewClient(client);
                binding.webview.getSettings().setJavaScriptEnabled(true);
                binding.webview.loadUrl("http://103.152.118.69:8069/web#action=422&active_id=4&model=product.product&view_type=list&menu_id=247");
                setCurrentTab(TabType.STOCK);
                binding.reportStok.setSelected(true);
            }
        });




    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (binding.webview.canGoBack()) {
                        binding.webview.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    private void setCurrentTab(WebViewActivity.TabType type) {
        binding.reportPembelian.setSelected(false);
        binding.reportPenjualan.setSelected(false);
        binding.reportHutang.setSelected(false);
        binding.reportPiutang.setSelected(false);
        binding.reportNeraca.setSelected(false);
        binding.reportPnl.setSelected(false);
        binding.reportStok.setSelected(false);
        }
//
//        viewModel.fetchCurrentType(currentType);
    }
