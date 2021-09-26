package com.tatabuku.app.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tatabuku.app.databinding.ActivityRegisterBinding;
import com.tatabuku.app.databinding.ActivityRegisterWebBinding;
import com.tatabuku.app.ui.login.LoginActivity;

public class RegisterWebActivity extends AppCompatActivity {
    private ActivityRegisterWebBinding binding;

    private String successURL = "/my";

    private WebViewClient client = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (url.contains(successURL)) {
                Intent intent = new Intent(RegisterWebActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            super.onPageStarted(view, url, favicon);

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
    }

    private void setupView() {
        binding = ActivityRegisterWebBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.webview.setWebViewClient(client);
        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.loadUrl("http://103.152.118.69:8069/web/signup");
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