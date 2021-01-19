package com.tatabuku.app.ui.penjualan.checkout;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.pembelian.CancelOrderResponse;
import com.tatabuku.app.model.pembelian.CreateOrderModel;
import com.tatabuku.app.model.pembelian.CreateOrderParams;
import com.tatabuku.app.model.pembelian.OrderModel;
import com.tatabuku.app.model.pembelian.SaveOrderResponse;
import com.tatabuku.app.model.pembelian.SubmitOrderModel;
import com.tatabuku.app.model.pembelian.SubmitOrderParams;
import com.tatabuku.app.model.pembelian.SubmitOrderResponse;
import com.tatabuku.app.model.pembelian.UpdateOrderModel;
import com.tatabuku.app.model.pembelian.UpdateOrderParams;
import com.tatabuku.app.model.penjualan.CustomerDetailTotalData;
import com.tatabuku.app.model.penjualan.OrderDetailPenjualanResponse;
import com.tatabuku.app.model.penjualan.OrderDetailPenjualanResult;
import com.tatabuku.app.model.penjualan.PenjualanOrderLine;
import com.tatabuku.app.model.penjualan.SaleIdPostModel;
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.util.StringHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutPenjualanViewModel extends ViewModel {
    private MutableLiveData<String> onError;
    private MutableLiveData<Integer> onSuccess;
    private MutableLiveData<Boolean> isView;
    private MutableLiveData<ArrayList<OrderModel>> orders;
    private MutableLiveData<CustomerDetailTotalData> customerData;
    private MutableLiveData<OrderDetailPenjualanResult> orderDetailResult;
//    private MutableLiveData<WarehouseAddress> warehouseAddress;

    public MutableLiveData<OrderDetailPenjualanResult> getOrderDetailResult() {
        return orderDetailResult;
    }

    public MutableLiveData<CustomerDetailTotalData> getCustomerData() {
        return customerData;
    }

    public MutableLiveData<String> getOnError() {
        return onError;
    }

    public MutableLiveData<Integer> getOnSuccess() {
        return onSuccess;
    }

    public MutableLiveData<ArrayList<OrderModel>> getOrders() {
        return orders;
    }

    public MutableLiveData<Boolean> getIsView() {
        return isView;
    }
    //    public MutableLiveData<WarehouseAddress> getWarehouseAddress() {
//        return warehouseAddress;
//    }

    public CheckoutPenjualanViewModel() {
        onError = new MutableLiveData<>();
        onSuccess = new MutableLiveData<>();
        orders = new MutableLiveData<>(new ArrayList<>());
        customerData = new MutableLiveData<>();
        orderDetailResult = new MutableLiveData<>();
        isView = new MutableLiveData<>(false);
//        warehouseAddress = new MutableLiveData<>();
    }

    public void saveOrder(int orderId, Boolean finish) {
        if (getItemCount() == 0) {
            onError.setValue("Order Kosong");
        } else if (orderId == 0) {
            saveOrder(finish);
        } else {
            updateOrder(orderId, finish);
        }
    }

    private void updateOrder(int id, Boolean finish) {
        UpdateOrderParams orderParams = new UpdateOrderParams(orders.getValue().toArray(new OrderModel[0]), id);
        UpdateOrderModel updateOrderModel = new UpdateOrderModel(orderParams);
        Call<SaveOrderResponse> call = ConnectionManager.getInstance().getService().updateOrderPenjualan(updateOrderModel);
        call.enqueue(new Callback<SaveOrderResponse>() {
            @Override
            public void onResponse(Call<SaveOrderResponse> call, Response<SaveOrderResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        if (finish) {
                            onSuccess.setValue(0);
                        } else {
                            submitOrder(response.body().getResult().getID());
                        }
                        return;
                    }
                }
                onError.setValue("Gagal menyimpan Order");
            }

            @Override
            public void onFailure(Call<SaveOrderResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    public void cancelOrder(int id) {
        SaleIdPostModel model = new SaleIdPostModel(id);
        Call<CancelOrderResponse> call = ConnectionManager.getInstance().getService().cancelOrderPenjualan(model);
        call.enqueue(new Callback<CancelOrderResponse>() {
            @Override
            public void onResponse(Call<CancelOrderResponse> call, Response<CancelOrderResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        onSuccess.setValue(0);
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

    private void saveOrder(Boolean finish) {
        CreateOrderParams orderParams = new CreateOrderParams(orders.getValue().toArray(new OrderModel[0]), customerData.getValue().getId());
        CreateOrderModel createOrderModel = new CreateOrderModel(orderParams);
        Call<SaveOrderResponse> call = ConnectionManager.getInstance().getService().saveOrderPenjualan(createOrderModel);
        call.enqueue(new Callback<SaveOrderResponse>() {
            @Override
            public void onResponse(Call<SaveOrderResponse> call, Response<SaveOrderResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        if (finish) {
                            onSuccess.setValue(0);
                        } else {
                            submitOrder(response.body().getResult().getID());
                        }
                        return;
                    }
                }
                onError.setValue("Gagal menyimpan Order");
            }

            @Override
            public void onFailure(Call<SaveOrderResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    private void submitOrder(int id) {
        SubmitOrderParams submitOrderParams = new SubmitOrderParams(id);
        SubmitOrderModel submitOrderModel = new SubmitOrderModel(submitOrderParams);

        Call<SubmitOrderResponse> call = ConnectionManager.getInstance().getService().submitOrderPenjualan(submitOrderModel);
        call.enqueue(new Callback<SubmitOrderResponse>() {
            @Override
            public void onResponse(Call<SubmitOrderResponse> call, Response<SubmitOrderResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        onSuccess.setValue(response.body().getResult().getID());
                        return;
                    }
                }
                onError.setValue("Gagal menyimpan Order");
            }

            @Override
            public void onFailure(Call<SubmitOrderResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    public void fetchOrderData(int id) {
        SaleIdPostModel model = new SaleIdPostModel(id);
        Call<OrderDetailPenjualanResponse> call = ConnectionManager.getInstance().getService().getOrderDetailPenjualan(model);
        call.enqueue(new Callback<OrderDetailPenjualanResponse>() {
            @Override
            public void onResponse(Call<OrderDetailPenjualanResponse> call, Response<OrderDetailPenjualanResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        ArrayList<OrderModel> list = new ArrayList<>();
                        for (PenjualanOrderLine item :
                                response.body().getResult().getOrderLine()) {
                            list.add(new OrderModel(item));
                        }
                        orders.setValue(list);

                        orderDetailResult.setValue(response.body().getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDetailPenjualanResponse> call, Throwable t) {

            }
        });
    }

//    public void fetchWarehouseAddress() {
//        Call<GetWarehouseResponse> call = ConnectionManager.getInstance().getService().getWarehouseAddress();
//        call.enqueue(new Callback<GetWarehouseResponse>() {
//            @Override
//            public void onResponse(Call<GetWarehouseResponse> call, Response<GetWarehouseResponse> response) {
//                if (response.body() != null) {
//                    if (response.body().getResult() != null && response.body().getResult().size() > 0) {
//                        warehouseAddress.setValue(response.body().getResult().get(0));
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetWarehouseResponse> call, Throwable t) {
//
//            }
//        });
//    }

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

    private Double calculatePPN() {
        if(customerData.getValue()!=null && customerData.getValue().getStatusPajak().equalsIgnoreCase("pkp")) {
            return calculateSubtotal() / 10;
        } else {
            return 0.0;
        }
    }

    public String getSubtotal() {
        return StringHelper.numberFormat(calculateSubtotal());
    }

    public String getPPN() {
        return StringHelper.numberFormat(calculatePPN());
    }

    public String getTotal() {
        return StringHelper.numberFormat(calculateSubtotal() + calculatePPN());
    }
}
