package com.tatabuku.app.ui.terms.condition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.tatabuku.app.databinding.ActivityPrivacyPolicyBinding;
import com.tatabuku.app.databinding.ActivityTermsConditionBinding;
import com.tatabuku.app.model.pembelian.CategoryListResponse;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.model.rekening.RekeningDetailHeader;
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.ui.fix_asset.detail_asset.DetailAssetAdapter;
import com.tatabuku.app.ui.rekening.detail.RekeningDetailViewModel;
import com.tatabuku.app.util.StringHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyPolicy extends AppCompatActivity {
    private MutableLiveData<String> onError;
    private ActivityPrivacyPolicyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        configureView();
    }


    private void setupView() {
        binding = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

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