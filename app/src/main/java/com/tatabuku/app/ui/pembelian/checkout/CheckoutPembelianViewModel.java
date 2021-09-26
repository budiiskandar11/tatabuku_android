package com.tatabuku.app.ui.pembelian.checkout;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.pembelian.CancelOrderResponse;
import com.tatabuku.app.model.pembelian.CreateOrderModel;
import com.tatabuku.app.model.pembelian.CreateOrderParams;
import com.tatabuku.app.model.pembelian.GetWarehouseResponse;
import com.tatabuku.app.model.pembelian.OrderDetailItem;
import com.tatabuku.app.model.pembelian.OrderDetailResponse;
import com.tatabuku.app.model.pembelian.OrderDetailResult;
import com.tatabuku.app.model.pembelian.OrderModel;
import com.tatabuku.app.model.pembelian.SaveOrderResponse;
import com.tatabuku.app.model.pembelian.SubmitOrderModel;
import com.tatabuku.app.model.pembelian.SubmitOrderParams;
import com.tatabuku.app.model.pembelian.SubmitOrderResponse;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.model.pembelian.UpdateOrderModel;
import com.tatabuku.app.model.pembelian.UpdateOrderParams;
import com.tatabuku.app.model.pembelian.WarehouseAddress;
import com.tatabuku.app.model.penjualan.IdPostModel;
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.util.StringHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutPembelianViewModel extends ViewModel {
    private MutableLiveData<String> onError;
    private MutableLiveData<Integer> onSuccess;
    private MutableLiveData<ArrayList<OrderModel>> orders;
    private MutableLiveData<SupplierResult> supplierResult;
    private MutableLiveData<OrderDetailResult> orderDetailResult;
    private MutableLiveData<WarehouseAddress> warehouseAddress;
    private MutableLiveData<Boolean> isView;

    public MutableLiveData<OrderDetailResult> getOrderDetailResult() {
        return orderDetailResult;
    }

    public MutableLiveData<SupplierResult> getSupplierResult() {
        return supplierResult;
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

    public MutableLiveData<WarehouseAddress> getWarehouseAddress() {
        return warehouseAddress;
    }

    public MutableLiveData<Boolean> getIsView() {
        return isView;
    }

    public CheckoutPembelianViewModel() {
        onError = new MutableLiveData<>();
        onSuccess = new MutableLiveData<>();
        orders = new MutableLiveData<>(new ArrayList<>());
        supplierResult = new MutableLiveData<>();
        orderDetailResult = new MutableLiveData<>();
        warehouseAddress = new MutableLiveData<>();
        isView = new MutableLiveData<>(false);
    }

    public void saveOrder(int orderId, Boolean finish) {
        if (getItemCount() == 0){
            onError.setValue("Order kosong");
        } else if (orderId == 0) {
            saveOrder(finish);
        } else {
            updateOrder(orderId, finish);
        }
    }

    private void updateOrder(int id, Boolean finish) {
        UpdateOrderParams orderParams = new UpdateOrderParams(orders.getValue().toArray(new OrderModel[0]), id);
        UpdateOrderModel updateOrderModel = new UpdateOrderModel(orderParams);
        Call<SaveOrderResponse> call = ConnectionManager.getInstance().getService().updateOrder(updateOrderModel);
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

    private void saveOrder(Boolean finish) {
        CreateOrderParams orderParams = new CreateOrderParams(orders.getValue().toArray(new OrderModel[0]), supplierResult.getValue().getId());
        CreateOrderModel createOrderModel = new CreateOrderModel(orderParams);
        Call<SaveOrderResponse> call = ConnectionManager.getInstance().getService().saveOrder(createOrderModel);
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
        IdPostModel model = new IdPostModel(id);

        Call<CancelOrderResponse> call = ConnectionManager.getInstance().getService().cancelOrder(model);
        call.enqueue(new Callback<CancelOrderResponse>() {
            @Override
            public void onResponse(Call<CancelOrderResponse> call, Response<CancelOrderResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        onSuccess.setValue(0);
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

    private void submitOrder(int id) {
        SubmitOrderParams submitOrderParams = new SubmitOrderParams(id);
        SubmitOrderModel submitOrderModel = new SubmitOrderModel(submitOrderParams);

        Call<SubmitOrderResponse> call = ConnectionManager.getInstance().getService().submitOrder(submitOrderModel);
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
        String query = "{id, name, date_order,dest_address_id{street,street2,city,mobile},partner_id{name,street,street2,city,mobile},order_line{product_id{id, image,name, product_tmpl_id},price_unit,product_qty,price_subtotal}}";
        String filter = "[[\"id\",\"=\"," + id + "]]";
        Call<OrderDetailResponse> call = ConnectionManager.getInstance().getService().getOrderDetail(query, filter);
        call.enqueue(new Callback<OrderDetailResponse>() {
            @Override
            public void onResponse(Call<OrderDetailResponse> call, Response<OrderDetailResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        ArrayList<OrderModel> list = new ArrayList<>();
                        for (OrderDetailItem item :
                                response.body().getResult().get(0).getOrderLine()) {
                            list.add(new OrderModel(item));
                        }
                        orders.setValue(list);
                        
                        if (response.body().getResult().size() > 0) {
                            orderDetailResult.setValue(response.body().getResult().get(0));
                        }

                        fetchWarehouseAddress();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDetailResponse> call, Throwable t) {

            }
        });
    }

    public void fetchWarehouseAddress() {
        Call<GetWarehouseResponse> call = ConnectionManager.getInstance().getService().getWarehouseAddress();
        call.enqueue(new Callback<GetWarehouseResponse>() {
            @Override
            public void onResponse(Call<GetWarehouseResponse> call, Response<GetWarehouseResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null && response.body().getResult().size() > 0) {
                        warehouseAddress.setValue(response.body().getResult().get(0));
                    }
                }
            }

            @Override
            public void onFailure(Call<GetWarehouseResponse> call, Throwable t) {

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

    private Double calculatePPN() {
        if(supplierResult.getValue() != null && supplierResult.getValue().getStatusPajak().equalsIgnoreCase("pkp")) {
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
