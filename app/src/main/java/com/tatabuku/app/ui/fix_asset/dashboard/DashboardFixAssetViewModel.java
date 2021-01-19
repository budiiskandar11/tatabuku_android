package com.tatabuku.app.ui.fix_asset.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.fixed_asset.DashboardAssetHeader;
import com.tatabuku.app.model.fixed_asset.DashboardAssetModel;
import com.tatabuku.app.model.fixed_asset.DashboardAssetRequest;
import com.tatabuku.app.model.fixed_asset.DashboardAssetResponse;
import com.tatabuku.app.service.ConnectionManager;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFixAssetViewModel extends ViewModel {

    private MutableLiveData<List<DashboardAssetModel>> assetList;
    private MutableLiveData<DashboardAssetHeader> assetHeader;

    public MutableLiveData<List<DashboardAssetModel>> getAssetList() {
        return assetList;
    }

    public MutableLiveData<DashboardAssetHeader> getAssetHeader() {
        return assetHeader;
    }

    public DashboardFixAssetViewModel() {
        assetList = new MutableLiveData<>();
        assetHeader = new MutableLiveData<>();
        assetList.setValue(Collections.emptyList());
    }

    public void fetchDashboardData() {
        Call<DashboardAssetResponse> call = ConnectionManager.getInstance().getService().getDashboardFixedAsset(new DashboardAssetRequest());
        call.enqueue(new Callback<DashboardAssetResponse>() {
            @Override
            public void onResponse(Call<DashboardAssetResponse> call, Response<DashboardAssetResponse> response) {
                if (response.body() != null) {
                    assetHeader.setValue(response.body().getResult().getAssetDashboardHeader());
                    assetList.setValue(response.body().getResult().getAssetDashboardList());
                }
            }

            @Override
            public void onFailure(Call<DashboardAssetResponse> call, Throwable t) {

            }
        });
    }
}
