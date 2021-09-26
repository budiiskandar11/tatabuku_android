package com.tatabuku.app.ui.penjualan.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.DefaultPostModel;
import com.tatabuku.app.model.penjualan.CustomerResult;
import com.tatabuku.app.model.penjualan.DashboardCustomerResponse;
import com.tatabuku.app.model.penjualan.DashboardCustomerTotalResponse;
import com.tatabuku.app.model.penjualan.DashboardCustomerTotalResult;
import com.tatabuku.app.model.penjualan.DashboardHutangCustomerResponse;
import com.tatabuku.app.model.penjualan.DashboardHutangCustomerResult;
import com.tatabuku.app.model.penjualan.DashboardTotalPenjualanResponse;
import com.tatabuku.app.model.penjualan.DashboardTotalPenjualanResult;
import com.tatabuku.app.model.penjualan.FilterPostModel;
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.util.StringHelper;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardCustomerViewModel extends ViewModel {

    private MutableLiveData<DashboardTotalPenjualanResult> totalPenjualanData;
    private MutableLiveData<List<CustomerResult>> listCustomer;
    private MutableLiveData<List<CustomerResult>> listFaveCustomer;
    private MutableLiveData<DashboardCustomerTotalResult> totalCustomerData;
    private MutableLiveData<Boolean> isSort;

    public MutableLiveData<DashboardTotalPenjualanResult> getTotalPenjualanData() {
        return totalPenjualanData;
    }

    public MutableLiveData<List<CustomerResult>> getListCustomer() {
        return listCustomer;
    }

    public MutableLiveData<List<CustomerResult>> getListFaveCustomer() {
        return listFaveCustomer;
    }

    public MutableLiveData<DashboardCustomerTotalResult> getTotalCustomerData() {
        return totalCustomerData;
    }

    public MutableLiveData<Boolean> getIsSort() {
        return isSort;
    }

    public DashboardCustomerViewModel() {

        totalPenjualanData = new MutableLiveData<>();
        totalCustomerData = new MutableLiveData<>();
        isSort = new MutableLiveData<>(false);
        listCustomer = new MutableLiveData<>(Collections.emptyList());
        listFaveCustomer = new MutableLiveData<>(Collections.emptyList());
    }

    public void fetchCurrentType(DashboardCustomerActivity.DashboardType type, String query) {
        switch (type) {
            case PENJUALAN:
                fetchTotalPenjualan();
                break;
            case PIUTANG:
                fetchTotalPiutang();
                break;
            case PEMBAYARAN:
                fetchTotalPayment();
                break;
            case UANGMUKA:
                fetchTotalDP();
                break;
        }

        fetchListData(type, query);
    }

    public void fetchListData(DashboardCustomerActivity.DashboardType type, String query) {
        switch (type) {
            case PENJUALAN:
                fetchListPenjualan(query);
                break;
            case PIUTANG:
                fetchListPiutang(query);
                break;
            case PEMBAYARAN:
                fetchListPembayaran(query);
                break;
            case UANGMUKA:
                fetchListDP(query);
                break;
        }
    }

    private void fetchTotalPenjualan() {
        Call<DashboardTotalPenjualanResponse> call = ConnectionManager.getInstance().getService().getDashboardTotalPenjualan(new DefaultPostModel());
        call.enqueue(new Callback<DashboardTotalPenjualanResponse>() {
            @Override
            public void onResponse(Call<DashboardTotalPenjualanResponse> call, Response<DashboardTotalPenjualanResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        totalPenjualanData.setValue(response.body().getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<DashboardTotalPenjualanResponse> call, Throwable t) {

            }
        });
    }

    private void fetchListPenjualan(String query) {
        String sort = getIsSort().getValue() ? "total_penjualan_tahun" : null;
        FilterPostModel model = new FilterPostModel(query, sort);
        Call<DashboardCustomerResponse> call = ConnectionManager.getInstance().getService().getListPenjualanCustomer(model);
        call.enqueue(new Callback<DashboardCustomerResponse>() {
            @Override
            public void onResponse(Call<DashboardCustomerResponse> call, Response<DashboardCustomerResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        listCustomer.setValue(response.body().getResult().getListCustomerNonPav().getDatalist());
                        listFaveCustomer.setValue(response.body().getResult().getListCustomerPav());
                    }
                }
            }

            @Override
            public void onFailure(Call<DashboardCustomerResponse> call, Throwable t) {

            }
        });
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
                        listFaveCustomer.setValue(response.body().getResult().getListCustomerPav());
                    }
                }
            }

            @Override
            public void onFailure(Call<DashboardHutangCustomerResponse> call, Throwable t) {

            }
        });
    }

    private void fetchListPembayaran(String query) {
        String sort = getIsSort().getValue() ? "payment_tahun" : null;
        FilterPostModel model = new FilterPostModel(query, sort);
        Call<DashboardCustomerResponse> call = ConnectionManager.getInstance().getService().getListPaymentCustomer(model);
        call.enqueue(new Callback<DashboardCustomerResponse>() {
            @Override
            public void onResponse(Call<DashboardCustomerResponse> call, Response<DashboardCustomerResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        listCustomer.setValue(response.body().getResult().getListPaymentNonPav().getDatalist());
                        listFaveCustomer.setValue(response.body().getResult().getListPaymentPav());
                    }
                }
            }

            @Override
            public void onFailure(Call<DashboardCustomerResponse> call, Throwable t) {

            }
        });
    }

    private void fetchListDP(String query) {
        String sort = getIsSort().getValue() ? "saldo_dp" : null;
        FilterPostModel model = new FilterPostModel(query, sort);
        Call<DashboardCustomerResponse> call = ConnectionManager.getInstance().getService().getListDPCustomer(model);
        call.enqueue(new Callback<DashboardCustomerResponse>() {
            @Override
            public void onResponse(Call<DashboardCustomerResponse> call, Response<DashboardCustomerResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        listCustomer.setValue(response.body().getResult().getListDpNonPav().getDatalist());
                        listFaveCustomer.setValue(response.body().getResult().getListDpPav());
                    }
                }
            }

            @Override
            public void onFailure(Call<DashboardCustomerResponse> call, Throwable t) {

            }
        });
    }

    private void fetchTotalPiutang() {
        Call<DashboardCustomerTotalResponse> call = ConnectionManager.getInstance().getService().getTotalHutangCustomer(new DefaultPostModel());
        call.enqueue(new Callback<DashboardCustomerTotalResponse>() {
            @Override
            public void onResponse(Call<DashboardCustomerTotalResponse> call, Response<DashboardCustomerTotalResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        totalCustomerData.setValue(response.body().getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<DashboardCustomerTotalResponse> call, Throwable t) {

            }
        });
    }

    private void fetchTotalPayment() {
        Call<DashboardCustomerTotalResponse> call = ConnectionManager.getInstance().getService().getTotalPaymentCustomer(new DefaultPostModel());
        call.enqueue(new Callback<DashboardCustomerTotalResponse>() {
            @Override
            public void onResponse(Call<DashboardCustomerTotalResponse> call, Response<DashboardCustomerTotalResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        totalCustomerData.setValue(response.body().getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<DashboardCustomerTotalResponse> call, Throwable t) {

            }
        });
    }

    private void fetchTotalDP() {
        Call<DashboardCustomerTotalResponse> call = ConnectionManager.getInstance().getService().getTotalDPCustomer(new DefaultPostModel());
        call.enqueue(new Callback<DashboardCustomerTotalResponse>() {
            @Override
            public void onResponse(Call<DashboardCustomerTotalResponse> call, Response<DashboardCustomerTotalResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        totalCustomerData.setValue(response.body().getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<DashboardCustomerTotalResponse> call, Throwable t) {

            }
        });
    }
}
