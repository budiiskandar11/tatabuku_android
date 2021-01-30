package com.tatabuku.app.ui.rekening.create_bank_account;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.rekening.CreateBankAccountParams;
import com.tatabuku.app.model.rekening.CreateBankAccountRequest;
import com.tatabuku.app.model.rekening.CreateBankAccountResponse;
import com.tatabuku.app.service.ConnectionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBankAccountViewModel extends ViewModel {

    private MutableLiveData<String> onSuccess;
    private MutableLiveData<String> onError;

    public MutableLiveData<String> getOnSuccess() {
        return onSuccess;
    }

    public MutableLiveData<String> getOnError() {
        return onError;
    }

    public CreateBankAccountViewModel(){
        onSuccess = new MutableLiveData<>();
        onError = new MutableLiveData<>();
    }

    public void createBankAccount(String name, String rekening) {
        if (name == null || name.isEmpty() || rekening == null || rekening.isEmpty()) {
            onError.setValue("Data tidak lengkap");
            return;
        }

        CreateBankAccountParams params = new CreateBankAccountParams(name, rekening);
        CreateBankAccountRequest request = new CreateBankAccountRequest(params);
        Call<CreateBankAccountResponse> call = ConnectionManager.getInstance().getService().createBankAccount(request);
        call.enqueue(new Callback<CreateBankAccountResponse>() {
            @Override
            public void onResponse(Call<CreateBankAccountResponse> call, Response<CreateBankAccountResponse> response) {
                if (response.body().getResult() != null) {
                    onSuccess.setValue("Ok");
                } else {
                    onError.setValue("Terjadi Kesalahan");
                }
            }

            @Override
            public void onFailure(Call<CreateBankAccountResponse> call, Throwable t) {
                onError.setValue("Terjadi Kesalahan");
            }
        });
    }
}
