package com.tatabuku.app.ui.terms.condition;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.tatabuku.app.databinding.ActivityTermsConditionBinding;


public class TermsCondition extends AppCompatActivity {

    private ActivityTermsConditionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        configureView();
    }
    private void setupView() {
        binding = ActivityTermsConditionBinding.inflate(getLayoutInflater());
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
    }
}