package com.tatabuku.app.ui.pembelian.penerimaan;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.pembelian.CancelOrderResponse;
import com.tatabuku.app.model.pembelian.GetReceivedModel;
import com.tatabuku.app.model.pembelian.GetReceivedParams;
import com.tatabuku.app.model.pembelian.PenerimaanBarangItem;
import com.tatabuku.app.model.pembelian.PenerimaanBarangResponse;
import com.tatabuku.app.model.pembelian.PenerimaanBarangResult;
import com.tatabuku.app.model.pembelian.ReceivedModel;
import com.tatabuku.app.model.pembelian.SubmitOrderResponse;
import com.tatabuku.app.model.pembelian.SubmitReceivedModel;
import com.tatabuku.app.model.pembelian.SubmitReceivedParams;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.model.penjualan.IdPostModel;
import com.tatabuku.app.model.penjualan.PengirimanBarangItem;
import com.tatabuku.app.service.ConnectionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenerimaanBarangViewModel extends ViewModel {
    private MutableLiveData<String> onError;
    private MutableLiveData<String> onSuccess;
    private MutableLiveData<SupplierResult> supplierResult;
    private MutableLiveData<List<PenerimaanBarangItem>> listItem;
    private MutableLiveData<PenerimaanBarangResult> receivedResult;

    public MutableLiveData<List<PenerimaanBarangItem>> getListItem() {
        return listItem;
    }

    public MutableLiveData<PenerimaanBarangResult> getReceivedResult() {
        return receivedResult;
    }

    public MutableLiveData<SupplierResult> getSupplierResult() {
        return supplierResult;
    }

    public MutableLiveData<String> getOnError() {
        return onError;
    }

    public MutableLiveData<String> getOnSuccess() {
        return onSuccess;
    }


    public PenerimaanBarangViewModel() {
        onError = new MutableLiveData<>();
        onSuccess = new MutableLiveData<>();
        supplierResult = new MutableLiveData<>();
        receivedResult = new MutableLiveData<>();
        listItem = new MutableLiveData<>(new ArrayList<>());
    }

    public void submitReceived() {
        int id = getReceivedResult().getValue().getId();
        List<ReceivedModel> list = new ArrayList<>();
        for (PenerimaanBarangItem order :
                listItem.getValue()) {
            list.add(new ReceivedModel(order));
        }
        SubmitReceivedParams params = new SubmitReceivedParams(list.toArray(new ReceivedModel[0]), id);
        SubmitReceivedModel model = new SubmitReceivedModel(params);

        Call<SubmitOrderResponse> call = ConnectionManager.getInstance().getService().submitReceived(model);
        call.enqueue(new Callback<SubmitOrderResponse>() {
            @Override
            public void onResponse(Call<SubmitOrderResponse> call, Response<SubmitOrderResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        onSuccess.setValue("");
                        return;
                    }
                }
                onError.setValue("Gagal menerima barang");
            }

            @Override
            public void onFailure(Call<SubmitOrderResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    public void cancelOrder(int order_id) {
        IdPostModel model = new IdPostModel(order_id);

        Call<CancelOrderResponse> call = ConnectionManager.getInstance().getService().cancelOrder(model);
        call.enqueue(new Callback<CancelOrderResponse>() {
            @Override
            public void onResponse(Call<CancelOrderResponse> call, Response<CancelOrderResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        onSuccess.setValue("");
                        return;
                    }
                }
                onError.setValue("Gagal membatalkan order");
            }

            @Override
            public void onFailure(Call<CancelOrderResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    public void fetchData(int id) {
        GetReceivedModel model = new GetReceivedModel(new GetReceivedParams(id));
        Call<PenerimaanBarangResponse> call = ConnectionManager.getInstance().getService().getStockPicking(model);
        call.enqueue(new Callback<PenerimaanBarangResponse>() {
            @Override
            public void onResponse(Call<PenerimaanBarangResponse> call, Response<PenerimaanBarangResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null && response.body().getResult().getListBarang() != null) {

                        listItem.setValue(response.body().getResult().getListBarang());
                        receivedResult.setValue(response.body().getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<PenerimaanBarangResponse> call, Throwable t) {

            }
        });
    }

    public int getItemCount() {
        int count = 0;
        for (PenerimaanBarangItem order :
                listItem.getValue()) {
            count += order.getQuantityDone();
        }
        return count;
    }

    public int getDiffCount() {
        int count = 0;
        for (PenerimaanBarangItem order :
                listItem.getValue()) {
            count += order.getDiffQty();
        }
        return count;
    }

    public Double calculateSubtotal() {
        Double subtotal = 0.0;
        for (PenerimaanBarangItem order :
                listItem.getValue()) {
            subtotal += order.getQuantityDone() * order.getPriceUnit();
        }
        return subtotal;
    }

    public Double calculatePPN() {
        if(supplierResult.getValue() != null && supplierResult.getValue().getStatusPajak().equalsIgnoreCase("pkp")) {
            return calculateSubtotal() / 10;
        } else {
            return 0.0;
        }
    }

    public Double calculateGrandTotal() {
        return calculateSubtotal() + calculatePPN();
    }
}
