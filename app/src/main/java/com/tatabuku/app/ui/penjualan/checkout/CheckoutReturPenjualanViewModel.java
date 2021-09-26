package com.tatabuku.app.ui.penjualan.checkout;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.pembelian.OrderModel;
import com.tatabuku.app.model.pembelian.ReturnModel;
import com.tatabuku.app.model.pembelian.ReturnOrderModel;
import com.tatabuku.app.model.pembelian.ReturnOrderParams;
import com.tatabuku.app.model.pembelian.SubmitOrderResponse;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.model.penjualan.CustomerDetailTotalData;
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.util.StringHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutReturPenjualanViewModel extends ViewModel {
    private MutableLiveData<String> onError;
    private MutableLiveData<String> onSuccess;
    private MutableLiveData<ArrayList<OrderModel>> orders;
    private MutableLiveData<CustomerDetailTotalData> customerData;

    public MutableLiveData<CustomerDetailTotalData> getCustomerData() {
        return customerData;
    }

    public MutableLiveData<String> getOnError() {
        return onError;
    }

    public MutableLiveData<String> getOnSuccess() {
        return onSuccess;
    }

    public MutableLiveData<ArrayList<OrderModel>> getOrders() {
        return orders;
    }


    public CheckoutReturPenjualanViewModel() {
        onError = new MutableLiveData<>();
        onSuccess = new MutableLiveData<>();
        orders = new MutableLiveData<>(new ArrayList<>());
        customerData = new MutableLiveData<>();
    }

    public void saveRetur() {
        ArrayList<ReturnModel> list = new ArrayList<>();
        for (OrderModel order:
             orders.getValue()) {
            list.add(new ReturnModel(order));
        }
        ReturnOrderParams orderParams = new ReturnOrderParams(list.toArray(new ReturnModel[0]), customerData.getValue().getId());
        ReturnOrderModel returnOrderModel = new ReturnOrderModel(orderParams);
        Call<SubmitOrderResponse> call = ConnectionManager.getInstance().getService().submitReturPenjualan(returnOrderModel);
        call.enqueue(new Callback<SubmitOrderResponse>() {
            @Override
            public void onResponse(Call<SubmitOrderResponse> call, Response<SubmitOrderResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        onSuccess.setValue("");
                        return;
                    }
                }
                onError.setValue("Gagal menyimpan Return");
            }

            @Override
            public void onFailure(Call<SubmitOrderResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    public int getItemCount() {
        int count = 0;
        for (OrderModel order :
                orders.getValue()) {
            count += order.getProduct_qty();
        }
        return count;
    }

    private Double calculateSubtotal() {
        Double total = 0.0;
        for (OrderModel order :
                orders.getValue()) {
            total += order.getProduct_qty() * order.getPrice_unit();
        }
        return total;
    }

    public String getSubtotal() {
        return StringHelper.numberFormat(calculateSubtotal());
    }

}
