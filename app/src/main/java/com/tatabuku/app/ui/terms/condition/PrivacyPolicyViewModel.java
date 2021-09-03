package com.tatabuku.app.ui.terms.condition;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.databinding.ActivityPrivacyPolicyBinding;
import com.tatabuku.app.model.pembelian.PrivacyPolicyRespone;
import com.tatabuku.app.model.pembelian.PrivacyRequestModel;
import com.tatabuku.app.service.ConnectionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyPolicyViewModel extends ViewModel {
    private MutableLiveData<String> onSuccess;
    private MutableLiveData<String> onError;

    public MutableLiveData<String> getOnSuccess() {
        return onSuccess;
    }

    public MutableLiveData<String> getOnError() {
        return onError;
    }

    public PrivacyPolicyViewModel() {
        onSuccess = new MutableLiveData<>();
        onError = new MutableLiveData<>();
    }

    public void fetchPrivacy () {
        PrivacyRequestModel provicyRequestModel = new PrivacyRequestModel();
        Call<PrivacyPolicyRespone> call = ConnectionManager.getInstance().getService().getTermsCondition(provicyRequestModel);
        call.enqueue(new Callback<PrivacyPolicyRespone>() {
            @Override
            public void onResponse(Call<PrivacyPolicyRespone> call, Response<PrivacyPolicyRespone> response) {
                if (response.body() != null){
                    if (response.body().getResult() != null) {
                        onSuccess.setValue(response.body().getResult().getPrivacy());

                    } else {
                        onError.setValue("Terjadi Kesalahan");
                    }

                }
            }

            @Override
            public void onFailure(Call<PrivacyPolicyRespone> call, Throwable t) {

            }
        });

    }

}
