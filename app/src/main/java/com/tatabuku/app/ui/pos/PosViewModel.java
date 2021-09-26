package com.tatabuku.app.ui.pos;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.pembelian.Category;
import com.tatabuku.app.model.pembelian.CategoryListResponse;
import com.tatabuku.app.model.pembelian.OrderDetailResult;
import com.tatabuku.app.model.pembelian.OrderModel;
import com.tatabuku.app.model.pembelian.Produk;
import com.tatabuku.app.model.pembelian.ProdukListResponse;
import com.tatabuku.app.model.pembelian.SupplierListResponse;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.model.pos.AddOrderBill;
import com.tatabuku.app.model.pos.CreateSalePosModel;
import com.tatabuku.app.model.pos.CreateSalePosParams;
import com.tatabuku.app.model.pos.CreateSalePosResponse;
import com.tatabuku.app.model.pos.CreateSalePosResult;
import com.tatabuku.app.model.pos.OrderBill;
import com.tatabuku.app.model.pos.OrderLineModel;
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.ui.pos.activity.PosActivity;
import com.tatabuku.app.ui.pos.listener.PosListener;
import com.tatabuku.app.util.StringHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PosViewModel extends ViewModel {

    private final MutableLiveData<String> onError;
    private final MutableLiveData<String> onSuccess;
    private final MutableLiveData<List<Produk>> produkList;
    private final MutableLiveData<List<Category>> categories;
    private final MutableLiveData<Category> selectedCategory;
    private MutableLiveData<ArrayList<OrderModel>> orders;
    private final MutableLiveData<SupplierResult> supplierResult;
    private final MutableLiveData<OrderDetailResult> orderDetailResult;
    private final MutableLiveData<CreateSalePosResult> createSalePosResult;
    private MutableLiveData<ArrayList<AddOrderBill>> orderBill;
    private MutableLiveData<Boolean> isSort;
    private MutableLiveData<List<SupplierResult>> supplierList;
    private final SupplierResult result;
    private ArrayList<OrderLineModel> order_line = new ArrayList<>();

    private final ArrayList<AddOrderBill> addOrderBills = new ArrayList<>();

    public SupplierResult getResult(){
        return result;
    }

    private final String[][] listParams = {
            {"purchase_order_hari", "purchase_order_bulan", "purchase_order_total"},
            {"hutang_hari", "hutang_bulan", "hutang_total"},
            {"hutang_hari", "hutang_bulan", "hutang_total"},
            {"payment_hari", "payment_bulan", "payment_tahun"},
            {"dp_total_hari", "dp_total_bulan", "dp_total"}
    };

    public MutableLiveData<List<SupplierResult>> getSupplierList() {
        return supplierList;
    }

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

    public MutableLiveData<ArrayList<AddOrderBill>> getOrderBill() {
        return orderBill;
    }

    public MutableLiveData<ArrayList<OrderModel>> getOrders() {
        return orders;
    }

    public MutableLiveData<Boolean> getIsSort() {
        return isSort;
    }

    public MutableLiveData<CreateSalePosResult> getCreateSalePosResult() {return createSalePosResult;}

    public PosViewModel() {
        onError = new MutableLiveData<>();
        onSuccess = new MutableLiveData<>();
        produkList = new MutableLiveData<>(new ArrayList<>());
        categories = new MutableLiveData<>(new ArrayList<>());
        selectedCategory = new MutableLiveData<>();
        orders = new MutableLiveData<>(new ArrayList<>());
        supplierResult = new MutableLiveData<>();
        orderDetailResult = new MutableLiveData<>();
        supplierList = new MutableLiveData<>();
        orderBill = new MutableLiveData<>();
        orderBill.setValue(new ArrayList<>());
        supplierList.setValue(Collections.emptyList());
        result = new SupplierResult();
        createSalePosResult = new MutableLiveData<>();
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
            } else {
                orders.getValue().add(order);
            }
            orders.setValue(orders.getValue());
        }
    }

    public void setCostumerSelected(SupplierResult supplier) {
        supplierResult.setValue(supplier);
    }

    public void setOrderBill(ArrayList<OrderBill> order, SupplierResult patnerId, Double total) {
        AddOrderBill addOrderBill = new AddOrderBill(patnerId, order, total);
        addOrderBills.add(addOrderBill);
        orderBill.setValue(addOrderBills);
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

    public void fetchSupplierList(PosActivity.DashboardType type, String query) {
        String[] params = listParams[type.ordinal()];
        String stringParams = "{id, name, mobile, street, street2, city, image, " + StringHelper.convertStringArrayToString(params, ",") + "}";

        String filter = "[[\"supplier\",\"=\",true], [\"name\",\"ilike\",\"" + query + "\"],[\"partner_rating\",\"not in\",[\"5\",\"4\"]]]";
        String sort = false ? "\"" + params[params.length - 1] + "\"" : null;
        Call<SupplierListResponse> call = ConnectionManager.getInstance().getService().getSupplierList(stringParams, filter, sort);
        call.enqueue(new Callback<SupplierListResponse>() {
            @Override
            public void onResponse(Call<SupplierListResponse> call, Response<SupplierListResponse> response) {
                if (response.body() != null) {
                    supplierList.setValue(response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<SupplierListResponse> call, Throwable t) {
            }
        });
    }

    public void createSalePos(ArrayList<OrderBill> orderBills, PosListener listener, Boolean isCostumer, AlertDialog dialog){
        for (int i = 0 ; i < orderBills.size() ; i++){
            OrderBill orderBill = orderBills.get(i);
            order_line.add(new OrderLineModel(orderBill.getId(),orderBill.getProduct_qty()));
        }
        CreateSalePosParams createSalePosParams = new CreateSalePosParams(order_line);
        CreateSalePosModel createSalePosModel = new CreateSalePosModel(createSalePosParams);
        Call<CreateSalePosResponse> call = ConnectionManager.getInstance().getService().createSalePos(createSalePosModel);
        call.enqueue(new Callback<CreateSalePosResponse>() {
            @Override
            public void onResponse(Call<CreateSalePosResponse> call, Response<CreateSalePosResponse> response) {
                if (response.body() != null) {
                    createSalePosResult.setValue(response.body().getResult());
                    if (isCostumer){
                        listener.onPaymentResultCostumer("Success");
                    } else {
                        listener.onPaymentResultNotCostumer("Success", Objects.requireNonNull(createSalePosResult.getValue()).getInvoice_id());
                    }
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<CreateSalePosResponse> call, Throwable t) {
                if (isCostumer){
                    listener.onPaymentResultCostumer("Terjadi Kesalahan");
                } else {
                    listener.onPaymentResultNotCostumer("Terjadi Kesalahan", 0);
                }
                dialog.dismiss();
            }
        });
    }

}


