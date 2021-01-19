package com.tatabuku.app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tatabuku.app.databinding.ActivityLoginBinding;
import com.tatabuku.app.databinding.ActivityWebViewBinding;
import com.tatabuku.app.ui.login.LoginActivity;
import com.tatabuku.app.ui.register.RegisterWebActivity;

public class WebViewActivity extends AppCompatActivity {

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
    }

    private void setupView() {
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.webview.setWebViewClient(client);
        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.loadUrl("http://103.152.118.69:8069/web?debug#action=270&model=purchase.report&view_type=pivot&menu_id=148");
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
}