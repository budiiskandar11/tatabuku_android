package com.tatabuku.app.ui.saldoPiutang;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.penjualan.CustomerResult;
import com.tatabuku.app.model.penjualan.DashboardCustomerResponse;
import com.tatabuku.app.model.penjualan.DashboardHutangCustomerResponse;
import com.tatabuku.app.model.penjualan.FilterPostModel;
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.ui.penjualan.dashboard.DashboardCustomerActivity;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySaldoPiutangViewModel extends ViewModel {
    private MutableLiveData<Boolean> isSort;
    private MutableLiveData<List<CustomerResult>> listCustomer;


    public MutableLiveData<Boolean> getIsSort() {
        return isSort;
    }
    public MutableLiveData<List<CustomerResult>> getListCustomer() {
        return listCustomer;
    }


    public ActivitySaldoPiutangViewModel() {
        isSort = new MutableLiveData<>(false);
        listCustomer = new MutableLiveData<>(Collections.emptyList());

    }

    public void fetchListData( String query) {
        fetchListPiutang(query);
    }

    private void fetchListPiutang(String query) {
        String sort = getIsSort().getValue() ? "hutang_60" : null;
        FilterPostModel model = new FilterPostModel(query, sort);
        Call<DashboardHutangCustomerResponse> call = ConnectionManager.getInstance().getService().getListHutangCustomer(model);
        call.enqueue(new Callback<DashboardHutangCustomerResponse>() {
            @Override
            public void onResponse(Call<DashboardHutangCustomerResponse> call, Response<DashboardHutangCustomerResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        listCustomer.setValue(response.body().getResult().getListCustomerNonPav());
                    }
                }
            }

            @Override
            public void onFailure(Call<DashboardHutangCustomerResponse> call, Throwable t) {

            }
        });
    }
}
