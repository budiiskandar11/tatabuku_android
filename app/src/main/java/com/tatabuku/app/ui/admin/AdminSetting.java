package com.tatabuku.app.ui.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.tatabuku.app.databinding.ActivityAdminSettingBinding;


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
    }

    private void setupView() {
        binding = ActivityAdminSettingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.adminWeb.setWebViewClient(client);
        binding.adminWeb.getSettings().setJavaScriptEnabled(true);
        binding.adminWeb.loadUrl("http://103.152.118.69:8069/web?debug#action=308");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (binding.adminWeb.canGoBack()) {
                        binding.adminWeb.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}