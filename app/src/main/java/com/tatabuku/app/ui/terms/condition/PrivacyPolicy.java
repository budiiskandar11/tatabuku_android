package com.tatabuku.app.ui.terms.condition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tatabuku.app.databinding.ActivityPrivacyPolicyBinding;
import com.tatabuku.app.model.home.HomepageDataResponse;
import com.tatabuku.app.model.home.HomepageModel;
import com.tatabuku.app.model.pembelian.PrivacyPolicyDataResult;
import com.tatabuku.app.model.pembelian.PrivacyPolicyRespone;
import com.tatabuku.app.model.pembelian.PrivacyPolicyResult;
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.ui.BaseActivity;
import com.tatabuku.app.ui.login.LoginActivity;
import com.tatabuku.app.ui.register.RegisterActivity;
import com.tatabuku.app.ui.register.RegisterViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyPolicy extends BaseActivity {

    private ActivityPrivacyPolicyBinding binding;
    private PrivacyPolicyViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        setupViewModel();
        configureView();
        configureViewModel();
        viewModel.fetchPrivacy();


    }
    private void configureViewModel() {
        viewModel.getPrivacyData().observe(this, new Observer<PrivacyPolicyDataResult>() {
            @Override
            public void onChanged(PrivacyPolicyDataResult dataResult) {
                binding.privacyPolicy.setText(dataResult.getPrivacy());
                Toast.makeText(getApplicationContext(),dataResult.getPrivacy(),Toast.LENGTH_LONG).show();
            }
        });


        viewModel.getOnError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideLoading();
                showAlert("Perhatian", s);
            }
        });

        viewModel.getOnSuccess().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hideLoading();

                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
            }
        });
    }


    private void setupView() {
        binding = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(PrivacyPolicyViewModel.class);
    }

    private void configureView(){
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

}