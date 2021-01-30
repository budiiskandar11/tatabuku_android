package com.tatabuku.app.ui.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.pembelian.GetURLResponse;
import com.tatabuku.app.model.pembelian.GetURLResult;
import com.tatabuku.app.model.pembelian.LoginModel;
import com.tatabuku.app.model.pembelian.LoginResponse;
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.service.LoginConnectionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<String> onSuccess;
    private MutableLiveData<String> onError;

    public MutableLiveData<String> getOnSuccess() {
        return onSuccess;
    }

    public MutableLiveData<String> getOnError() {
        return onError;
    }

    public LoginViewModel() {
        onSuccess = new MutableLiveData<>();
        onError = new MutableLiveData<>();
    }

    void login(String username, String password) {
        submitLogin(username, password);
    }

    private void submitLogin(String username, String password) {
        LoginModel loginModel = new LoginModel(username, password);
        Call<LoginResponse> call = LoginConnectionManager.getInstance().getService().login(loginModel);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        getUrl(response.body().getResult().getPartnerId());
                    } else {
                        onError.setValue("Username atau password salah");
                    }
                } else {
                    onError.setValue("Terjadi kesalahan pada server");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                onError.setValue("Tidak dapat terhubung ke server");
            }
        });
    }

    private void getUrl(Integer partnerId) {
        String filter = "[[\"is_tatabuku_client\",\"=\",\"true\"],[\"id\",\"=\"," + partnerId + "]]";
        Call<GetURLResponse> call = LoginConnectionManager.getInstance().getService().getUrl(filter);
        call.enqueue(new Callback<GetURLResponse>() {
            @Override
            public void onResponse(Call<GetURLResponse> call, Response<GetURLResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult().size() > 0) {
                        GetURLResult result = response.body().getResult().get(0);
                        submitLoginAndroid(result.getTatabukuUrl(), result.getTatabukuDb(), result.getTatabukuUser(), result.getTatabukuPassword());
                    } else {
                        onError.setValue("Terjadi kesalahan pada server");
                    }
                } else {
                    onError.setValue("Terjadi kesalahan pada server");
                }
            }

            @Override
            public void onFailure(Call<GetURLResponse> call, Throwable t) {
                onError.setValue("Tidak dapat terhubung ke server");
            }
        });
    }

    private void submitLoginAndroid(String url, String dbName, String username, String password) {
        ConnectionManager.newInstance(url);
        LoginModel loginModel = new LoginModel(dbName, username, password);
        Call<LoginResponse> call = ConnectionManager.getInstance().getService().login(loginModel);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        onSuccess.setValue("");
                    } else {
                        onError.setValue("Username atau password salah");
                    }
                } else {
                    onError.setValue("Terjadi kesalahan pada server");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                onError.setValue("Tidak dapat terhubung ke server");
            }
        });
    }
}
