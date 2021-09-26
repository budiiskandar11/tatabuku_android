package com.tatabuku.app.ui.register;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.pembelian.RegisterModel;
import com.tatabuku.app.model.pembelian.RegisterResponse;
import com.tatabuku.app.service.ConnectionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {
    private MutableLiveData<String> onSuccess;
    private MutableLiveData<String> onError;

    public MutableLiveData<String> getOnSuccess() {
        return onSuccess;
    }

    public MutableLiveData<String> getOnError() {
        return onError;
    }

    public RegisterViewModel() {
        onSuccess = new MutableLiveData<>();
        onError = new MutableLiveData<>();
    }

    void register(String storeName, String name, String phoneNum, String email, String password){
        submitRegister(storeName, name,phoneNum,email,password);
    }

    private void submitRegister(String storeName, String name, String phoneNum, String email, String password) {
        RegisterModel registerModel = new RegisterModel(storeName,name,phoneNum,email,password);
        Call<RegisterResponse> call = ConnectionManager.getInstance().getService().register(registerModel);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.body() != null){
                    if (response.body().getResult() != null) {
                        onSuccess.setValue("");

                    } else {
                        onError.setValue("Terjadi Kesalahan");
                    }
                }
                else {
                    onError.setValue("Terjadi kesalahan pada server");
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });
    }
}
