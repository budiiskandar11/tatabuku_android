package com.tatabuku.app.ui.terms.condition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tatabuku.app.databinding.ActivityPrivacyPolicyBinding;
import com.tatabuku.app.model.home.HomepageDataResponse;
import com.tatabuku.app.model.home.HomepageModel;
import com.tatabuku.app.model.pembelian.PrivacyPolicyRespone;
import com.tatabuku.app.model.pembelian.PrivacyPolicyResult;
import com.tatabuku.app.model.pembelian.PrivacyRequestModel;
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.ui.BaseActivity;
import com.tatabuku.app.ui.register.RegisterViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyPolicy extends BaseActivity {
    private MutableLiveData<String> onError;
    private ActivityPrivacyPolicyBinding binding;
    private PrivacyPolicyViewModel viewModel;
//    private PrivacyRequestModel privacyRequestModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        setupViewModel();
        configureView();

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
        viewModel.fetchPrivacy();
        viewModel.getOnSuccess().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.privacyPolicy.setText(s);
            }
        });




        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

}