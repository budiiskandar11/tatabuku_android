package com.tatabuku.app.ui.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.home.HomepageDataResponse;
import com.tatabuku.app.model.home.HomepageDataResult;
import com.tatabuku.app.model.home.HomepageModel;
import com.tatabuku.app.service.ConnectionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<HomepageDataResult> homeData;

    public MutableLiveData<HomepageDataResult> getHomeData() {
        return homeData;
    }

    public HomeViewModel() {
        homeData = new MutableLiveData<>();
    }

    public void getData() {
        Call<HomepageDataResponse> call = ConnectionManager.getInstance().getService().getHomepageData(new HomepageModel());
        call.enqueue(new Callback<HomepageDataResponse>() {
            @Override
            public void onResponse(Call<HomepageDataResponse> call, Response<HomepageDataResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        homeData.setValue(response.body().getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<HomepageDataResponse> call, Throwable t) {
            }
        });
    }

}
