package com.tatabuku.app.ui.rekening.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.rekening.DashboardRekeningHeader;
import com.tatabuku.app.model.rekening.DashboardRekeningModel;
import com.tatabuku.app.model.rekening.DashboardRekeningRequest;
import com.tatabuku.app.model.rekening.DashboardRekeningResponse;
import com.tatabuku.app.service.ConnectionManager;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardRekeningViewModel extends ViewModel {

    private MutableLiveData<DashboardRekeningHeader> header;
    private MutableLiveData<List<DashboardRekeningModel>> rekenings;

    public MutableLiveData<DashboardRekeningHeader> getHeader() {
        return header;
    }

    public MutableLiveData<List<DashboardRekeningModel>> getRekenings() {
        return rekenings;
    }

    public DashboardRekeningViewModel() {
        header = new MutableLiveData<>();
        rekenings = new MutableLiveData<>();
        rekenings.setValue(Collections.emptyList());
    }

    public void fetchDashboardData() {
        DashboardRekeningRequest request = new DashboardRekeningRequest();
        Call<DashboardRekeningResponse> call = ConnectionManager.getInstance().getService().getDashboardRekening(request);
        call.enqueue(new Callback<DashboardRekeningResponse>() {
            @Override
            public void onResponse(Call<DashboardRekeningResponse> call, Response<DashboardRekeningResponse> response) {
                if (response.body() != null) {
                    header.setValue(response.body().getResult().getHeaderInfo());
                    rekenings.setValue(response.body().getResult().getBankList());
                }
            }

            @Override
            public void onFailure(Call<DashboardRekeningResponse> call, Throwable t) {

            }
        });
    }
}
