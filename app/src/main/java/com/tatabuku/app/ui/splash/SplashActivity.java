package com.tatabuku.app.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.tatabuku.app.databinding.ActivitySplashBinding;
import com.tatabuku.app.ui.home.HomeActivity;
import com.tatabuku.app.ui.login.LoginActivity;
import com.tatabuku.app.ui.register.RegisterActivity;
import com.tatabuku.app.ui.register.RegisterWebActivity;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupView();
        configureView();
    }

    private void setupView() {
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void configureView() {

        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}