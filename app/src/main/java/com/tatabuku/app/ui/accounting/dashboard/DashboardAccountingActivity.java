package com.tatabuku.app.ui.accounting.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.tatabuku.app.databinding.ActivityDashboardAccountingBinding;

public class DashboardAccountingActivity extends AppCompatActivity {

    private ActivityDashboardAccountingBinding binding;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        setupList();
        configureView();
    }

    private void setupView(){
        binding = ActivityDashboardAccountingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void setupList(){
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.list.setLayoutManager(layoutManager);
    }

    private void configureView() {
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}