package com.tatabuku.app.ui.rekening.create_bank_account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.tatabuku.app.databinding.ActivityCreateBankAccountBinding;
import com.tatabuku.app.databinding.ActivityDashboardRekeningBinding;
import com.tatabuku.app.ui.BaseActivity;

public class CreateBankAccountActivity extends BaseActivity {

    private ActivityCreateBankAccountBinding binding;
    private CreateBankAccountViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        configureViewModel();
        setupView();
        configureView();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(CreateBankAccountViewModel.class);
    }

    private void configureViewModel() {
        viewModel.getOnSuccess().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideLoading();
                finish();
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
        binding = ActivityCreateBankAccountBinding.inflate(getLayoutInflater());
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

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                String nama = binding.namaBank.toString();
                String rekening = binding.nomorRekening.toString();
                viewModel.createBankAccount(nama, rekening);
            }
        });
    }
}