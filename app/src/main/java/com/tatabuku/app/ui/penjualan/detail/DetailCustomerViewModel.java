package com.tatabuku.app.ui.penjualan.detail;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.pembelian.DownPaymentListResponse;
import com.tatabuku.app.model.pembelian.DownPaymentResult;
import com.tatabuku.app.model.pembelian.InvoiceListResponse;
import com.tatabuku.app.model.pembelian.InvoiceResult;
import com.tatabuku.app.model.pembelian.LoadPaymentModel;
import com.tatabuku.app.model.pembelian.LunasDataResponse;
import com.tatabuku.app.model.pembelian.LunasDataResult;
import com.tatabuku.app.model.pembelian.OrderListResponse;
import com.tatabuku.app.model.pembelian.OrderResult;
import com.tatabuku.app.model.pembelian.PembayaranItem;
import com.tatabuku.app.model.pembelian.PembayaranResponse;
import com.tatabuku.app.model.pembelian.SupplierListResponse;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.model.penjualan.CustomerDetailDPResponse;
import com.tatabuku.app.model.penjualan.CustomerDetailHutangResponse;
import com.tatabuku.app.model.penjualan.CustomerDetailPenjualanResponse;
import com.tatabuku.app.model.penjualan.CustomerDetailPostModel;
import com.tatabuku.app.model.penjualan.CustomerDetailTotalData;
import com.tatabuku.app.model.penjualan.DPData;
import com.tatabuku.app.model.penjualan.HutangData;
import com.tatabuku.app.model.penjualan.PenjualanData;
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.ui.pembelian.dashboard.DashboardSupplierActivity;
import com.tatabuku.app.ui.penjualan.dashboard.DashboardCustomerActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCustomerViewModel extends ViewModel {
    private MutableLiveData<CustomerDetailTotalData> totalData;
    private MutableLiveData<List<PenjualanData>> penjualanList;
    private MutableLiveData<List<HutangData>> hutangList;
    private MutableLiveData<List<DPData>> dpList;
    private MutableLiveData<List<PembayaranItem>> pembayaranList;
    private MutableLiveData<Boolean> isSort;

    public MutableLiveData<CustomerDetailTotalData> getTotalData() {
        return totalData;
    }

    public MutableLiveData<List<PenjualanData>> getPenjualanList() {
        return penjualanList;
    }

    public MutableLiveData<List<HutangData>> getHutangList() {
        return hutangList;
    }

    public MutableLiveData<List<DPData>> getDpList() {
        return dpList;
    }

    public MutableLiveData<List<PembayaranItem>> getPembayaranList() {
        return pembayaranList;
    }

    public MutableLiveData<Boolean> getIsSort() {
        return isSort;
    }

    public DetailCustomerViewModel() {

        totalData = new MutableLiveData<>();
        penjualanList = new MutableLiveData<>(Collections.emptyList());
        hutangList = new MutableLiveData<>(Collections.emptyList());
        dpList = new MutableLiveData<>(Collections.emptyList());
        pembayaranList = new MutableLiveData<>(Collections.emptyList());
        isSort = new MutableLiveData<>(false);
    }

    public void searchData(Integer id, DashboardCustomerActivity.DashboardType type, String query) {
        switch (type) {
            case PENJUALAN:
                fetchPenjualanData(id, query);
                break;
            case PIUTANG:
                fetchHutangData(id, query, false);
                break;
            case LUNAS:
                fetchHutangData(id, query, true);
                break;
            case UANGMUKA:
                fetchDPData(id, query);
                break;
            case PEMBAYARAN:
                fetchPaymentList(id, query);
                break;
        }
    }

    private void fetchPenjualanData(Integer id, String query) {
        String sort = getIsSort().getValue() ? "amount_total" : null;
        Call<CustomerDetailPenjualanResponse> call = ConnectionManager.getInstance().getService().getDetailPenjualanCustomer(new CustomerDetailPostModel(id, query, sort));
        call.enqueue(new Callback<CustomerDetailPenjualanResponse>() {
            @Override
            public void onResponse(Call<CustomerDetailPenjualanResponse> call, Response<CustomerDetailPenjualanResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        totalData.setValue(response.body().getResult().getTotalPenjualanCustomer());
                        penjualanList.setValue(response.body().getResult().getResult().getDatalist());
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerDetailPenjualanResponse> call, Throwable t) {

            }
        });
    }

    private void fetchHutangData(Integer id, String query, Boolean isLunas) {
        String sort = getIsSort().getValue() ? "total_harus_dibayar" : null;
        Call<CustomerDetailHutangResponse> call = ConnectionManager.getInstance().getService().getDetailHutangCustomer(new CustomerDetailPostModel(id, query, (isLunas ? "paid" : "open"), sort));
        call.enqueue(new Callback<CustomerDetailHutangResponse>() {
            @Override
            public void onResponse(Call<CustomerDetailHutangResponse> call, Response<CustomerDetailHutangResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        totalData.setValue(response.body().getResult().getTotalPiutangCustomer());
                        hutangList.setValue(response.body().getResult().getResult().getDatalist());
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerDetailHutangResponse> call, Throwable t) {
            }
        });
    }

    private void fetchDPData(Integer id, String query) {
        String sort = getIsSort().getValue() ? "amount" : null;
        Call<CustomerDetailDPResponse> call = ConnectionManager.getInstance().getService().getDetailDPCustomer(new CustomerDetailPostModel(id, query, sort));
        call.enqueue(new Callback<CustomerDetailDPResponse>() {
            @Override
            public void onResponse(Call<CustomerDetailDPResponse> call, Response<CustomerDetailDPResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        totalData.setValue(response.body().getResult().getTotalDpCustomer());
                        dpList.setValue(response.body().getResult().getResult().getDatalist());
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerDetailDPResponse> call, Throwable t) {
            }
        });
    }

    private void fetchPaymentList(Integer id, String search) {
        LoadPaymentModel model = new LoadPaymentModel(id, "customer", search);
        Call<PembayaranResponse> call = ConnectionManager.getInstance().getService().getPaymentList(model);
        call.enqueue(new Callback<PembayaranResponse>() {
            @Override
            public void onResponse(Call<PembayaranResponse> call, Response<PembayaranResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        totalData.setValue(response.body().getResult().getPartnerData());
                        pembayaranList.setValue(response.body().getResult().getPaymentList());
                    }
                }
            }

            @Override
            public void onFailure(Call<PembayaranResponse> call, Throwable t) {

            }
        });
    }
}
