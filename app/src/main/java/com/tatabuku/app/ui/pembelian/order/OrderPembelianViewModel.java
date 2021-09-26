package com.tatabuku.app.ui.pembelian.order;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.pembelian.CancelOrderResponse;
import com.tatabuku.app.model.pembelian.Category;
import com.tatabuku.app.model.pembelian.CategoryListResponse;
import com.tatabuku.app.model.pembelian.OrderDetailResult;
import com.tatabuku.app.model.pembelian.OrderModel;
import com.tatabuku.app.model.pembelian.Produk;
import com.tatabuku.app.model.pembelian.ProdukListResponse;
import com.tatabuku.app.model.pembelian.SubmitOrderResponse;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.model.penjualan.IdPostModel;
import com.tatabuku.app.service.ConnectionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderPembelianViewModel extends ViewModel {
    private MutableLiveData<String> onError;
    private MutableLiveData<String> onSuccess;
    private MutableLiveData<List<Produk>> produkList;
    private MutableLiveData<List<Category>> categories;
    private MutableLiveData<Category> selectedCategory;
    private MutableLiveData<ArrayList<OrderModel>> orders;
    private MutableLiveData<SupplierResult> supplierResult;
    private MutableLiveData<OrderDetailResult> orderDetailResult;

    public MutableLiveData<OrderDetailResult> getOrderDetailResult() {
        return orderDetailResult;
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

    public MutableLiveData<List<Category>> getCategories() {
        return categories;
    }

    public MutableLiveData<Category> getSelectedCategory() {
        return selectedCategory;
    }

    public MutableLiveData<List<Produk>> getProdukList() {
        return produkList;
    }

    public MutableLiveData<ArrayList<OrderModel>> getOrders() {
        return orders;
    }

    public OrderPembelianViewModel() {
        onError = new MutableLiveData<>();
        onSuccess = new MutableLiveData<>();
        produkList = new MutableLiveData<>(new ArrayList<>());
        categories = new MutableLiveData<>(new ArrayList<>());
        selectedCategory = new MutableLiveData<>();
        orders = new MutableLiveData<>(new ArrayList<>());
        supplierResult = new MutableLiveData<>();
        orderDetailResult = new MutableLiveData<>();
    }

    public OrderModel getOrder(Integer produkId) {
        OrderModel order = new OrderModel(produkId);
        if (orders.getValue() != null) {
            int index = orders.getValue().indexOf(order);
            if (index != -1) {
                order = orders.getValue().get(index);
            }
        }
        return order;
    }

    public void updateOrder(OrderModel order) {
        if (orders.getValue() != null) {
            int index = orders.getValue().indexOf(order);
            if (index != -1) {
                orders.getValue().get(index).setPrice_unit(order.getPrice_unit());
                orders.getValue().get(index).setProduct_qty(order.getProduct_qty());
                orders.setValue(orders.getValue());
            } else {
                orders.getValue().add(order);
                orders.setValue(orders.getValue());
            }
        }
    }

    public void fetchCategory() {
        Call<CategoryListResponse> call = ConnectionManager.getInstance().getService().getCategory();
        call.enqueue(new Callback<CategoryListResponse>() {
            @Override
            public void onResponse(Call<CategoryListResponse> call, Response<CategoryListResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        categories.setValue(response.body().getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryListResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    public void fetchProduk(Category category, String query) {
        String filter = "[[\"name\",\"ilike\",\"" + query + "\"]]";
        if (category != null) {
            filter = "[[\"categ_id\",\"=\"," + category.getId() + "],[\"name\",\"ilike\",\"" + query + "\"]]";
        }
        Call<ProdukListResponse> call = ConnectionManager.getInstance().getService().getProduk(filter);
        call.enqueue(new Callback<ProdukListResponse>() {
            @Override
            public void onResponse(Call<ProdukListResponse> call, Response<ProdukListResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        produkList.setValue(response.body().getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<ProdukListResponse> call, Throwable t) {

            }
        });
    }
}
