package com.tatabuku.app.ui.terms.condition;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.pembelian.PrivacyModel;
import com.tatabuku.app.model.pembelian.PrivacyPolicyDataResult;
import com.tatabuku.app.model.pembelian.PrivacyPolicyRespone;
import com.tatabuku.app.model.pembelian.PrivacyPolicyResult;
import com.tatabuku.app.service.ConnectionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyPolicyViewModel extends ViewModel {
    private MutableLiveData<String> onSuccess;
    private MutableLiveData<String> onError;
    private MutableLiveData<PrivacyPolicyDataResult> privacyData;

    public MutableLiveData<String> getOnSuccess() {
        return onSuccess;
    }

    public MutableLiveData<String> getOnError() {
        return onError;
    }
    public MutableLiveData<PrivacyPolicyDataResult> getPrivacyData() {
        return privacyData;
    }

    public PrivacyPolicyViewModel() {
        onSuccess = new MutableLiveData<>();
        onError = new MutableLiveData<>();
        privacyData = new MutableLiveData<>();

    }

    public void fetchPrivacy () {

        Call<PrivacyPolicyRespone> call = ConnectionManager.getInstance().getService().getTermsCondition(new PrivacyModel());
        call.enqueue(new Callback<PrivacyPolicyRespone>() {
            @Override
            public void onResponse(Call<PrivacyPolicyRespone> call, Response<PrivacyPolicyRespone> response) {
                if (response.body() != null){
                    if (response.body().getResult() != null) {
                        privacyData.setValue(response.body().getResult().getDataResult());

                    } else {
                        onError.setValue("Terjadi Kesalahan");
                    }



                }
                else {
                    onError.setValue("Terjadi Kesalahan");
                }
            }

            @Override
            public void onFailure(Call<PrivacyPolicyRespone> call, Throwable t) {

            }
        });

    }

}
