package com.tatabuku.app.ui.saldoPiutang;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.penjualan.CustomerResult;
import com.tatabuku.app.model.penjualan.DashboardCustomerResponse;
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
        fetchListPenjualan(query);
    }

    public void fetchListPenjualan(String query) {
        String sort = getIsSort().getValue() ? "total_penjualan_tahun" : null;
        FilterPostModel model = new FilterPostModel(query, sort);
        Call<DashboardCustomerResponse> call = ConnectionManager.getInstance().getService().getListPenjualanCustomer(model);
        call.enqueue(new Callback<DashboardCustomerResponse>() {
            @Override
            public void onResponse(Call<DashboardCustomerResponse> call, Response<DashboardCustomerResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        listCustomer.setValue(response.body().getResult().getListCustomerNonPav().getDatalist());
                    }
                }
            }

            @Override
            public void onFailure(Call<DashboardCustomerResponse> call, Throwable t) {

            }
        });
    }
}
