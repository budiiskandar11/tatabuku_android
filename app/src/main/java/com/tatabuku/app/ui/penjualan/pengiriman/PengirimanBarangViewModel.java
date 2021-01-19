package com.tatabuku.app.ui.penjualan.pengiriman;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.pembelian.CancelOrderResponse;
import com.tatabuku.app.model.pembelian.ReceivedModel;
import com.tatabuku.app.model.pembelian.SubmitOrderResponse;
import com.tatabuku.app.model.pembelian.SubmitReceivedModel;
import com.tatabuku.app.model.pembelian.SubmitReceivedParams;
import com.tatabuku.app.model.penjualan.CustomerDetailTotalData;
import com.tatabuku.app.model.penjualan.PengirimanBarangItem;
import com.tatabuku.app.model.penjualan.PengirimanBarangResponse;
import com.tatabuku.app.model.penjualan.PengirimanBarangResult;
import com.tatabuku.app.model.penjualan.SaleIdPostModel;
import com.tatabuku.app.model.penjualan.ShippingModel;
import com.tatabuku.app.model.penjualan.ShippingReceivedModel;
import com.tatabuku.app.model.penjualan.ShippingReceivedParams;
import com.tatabuku.app.service.ConnectionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengirimanBarangViewModel extends ViewModel {
    private MutableLiveData<String> onError;
    private MutableLiveData<String> onSuccess;
    private MutableLiveData<CustomerDetailTotalData> customerData;
    private MutableLiveData<List<PengirimanBarangItem>> listItem;
    private MutableLiveData<PengirimanBarangResult> shippingResult;

    public MutableLiveData<List<PengirimanBarangItem>> getListItem() {
        return listItem;
    }

    public MutableLiveData<PengirimanBarangResult> getShippingResult() {
        return shippingResult;
    }

    public MutableLiveData<CustomerDetailTotalData> getCustomerData() {
        return customerData;
    }

    public MutableLiveData<String> getOnError() {
        return onError;
    }

    public MutableLiveData<String> getOnSuccess() {
        return onSuccess;
    }


    public PengirimanBarangViewModel() {
        onError = new MutableLiveData<>();
        onSuccess = new MutableLiveData<>();
        customerData = new MutableLiveData<>();
        shippingResult = new MutableLiveData<>();
        listItem = new MutableLiveData<>(new ArrayList<>());
    }

    public void cancelOrder(int id) {
        SaleIdPostModel model = new SaleIdPostModel(id);
        Call<CancelOrderResponse> call = ConnectionManager.getInstance().getService().cancelOrderPenjualan(model);
        call.enqueue(new Callback<CancelOrderResponse>() {
            @Override
            public void onResponse(Call<CancelOrderResponse> call, Response<CancelOrderResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        onSuccess.setValue("");
                        return;
                    }
                }
                onError.setValue("Gagal membatalkan Order");
            }

            @Override
            public void onFailure(Call<CancelOrderResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    public void submitShipping() {
        int id = getShippingResult().getValue().getPickingId();
        List<ShippingModel> list = new ArrayList<>();
        for (PengirimanBarangItem order :
                listItem.getValue()) {
            list.add(new ShippingModel(order));
        }
        ShippingReceivedParams params = new ShippingReceivedParams(list.toArray(new ShippingModel[0]), id);
        ShippingReceivedModel model = new ShippingReceivedModel(params);

        Call<SubmitOrderResponse> call = ConnectionManager.getInstance().getService().submitPengiriman(model);
        call.enqueue(new Callback<SubmitOrderResponse>() {
            @Override
            public void onResponse(Call<SubmitOrderResponse> call, Response<SubmitOrderResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        onSuccess.setValue("");
                        return;
                    }
                }
                onError.setValue("Gagal mengirim barang");
            }

            @Override
            public void onFailure(Call<SubmitOrderResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    public void fetchData(int id) {
        SaleIdPostModel model = new SaleIdPostModel(id);
        Call<PengirimanBarangResponse> call = ConnectionManager.getInstance().getService().getPengirimanData(model);
        call.enqueue(new Callback<PengirimanBarangResponse>() {
            @Override
            public void onResponse(Call<PengirimanBarangResponse> call, Response<PengirimanBarangResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null && response.body().getResult().getListBarang() != null) {

                        listItem.setValue(response.body().getResult().getListBarang());
                        shippingResult.setValue(response.body().getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<PengirimanBarangResponse> call, Throwable t) {

            }
        });
    }

    public int getItemCount() {
        int count = 0;
        for (PengirimanBarangItem order :
                listItem.getValue()) {
            count += order.getQtyDeliver();
        }
        return count;
    }

    public int getDiffCount() {
        int count = 0;
        for (PengirimanBarangItem order :
                listItem.getValue()) {
            count += order.getDiffQty();
        }
        return count;
    }

    public Double calculateSubtotal() {
        Double subtotal = 0.0;
        for (PengirimanBarangItem order :
                listItem.getValue()) {
            subtotal += order.getQtyDeliver() * order.getPriceUnit();
        }
        return subtotal;
    }

    public Double calculatePPN() {
        if(customerData.getValue() != null && customerData.getValue().getStatusPajak().equalsIgnoreCase("pkp")) {
            return calculateSubtotal() / 10;
        } else {
            return 0.0;
        }
    }

    public Double calculateGrandTotal() {
        return calculateSubtotal() + calculatePPN();
    }
}
