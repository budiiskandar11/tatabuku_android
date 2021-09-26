package com.tatabuku.app.ui.saldo_hutang;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.pembelian.DashboardSupplierResponse;
import com.tatabuku.app.model.pembelian.DashboardSupplierResult;
import com.tatabuku.app.model.pembelian.SupplierListResponse;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.model.penjualan.CustomerResult;
import com.tatabuku.app.model.penjualan.DashboardCustomerResponse;
import com.tatabuku.app.model.penjualan.DashboardHutangCustomerResponse;
import com.tatabuku.app.model.penjualan.FilterPostModel;
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.ui.pembelian.dashboard.DashboardSupplierActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySaldoHutangViewModel extends ViewModel {

    private final String[][] listParams = {
            {"hutang_hari", "hutang_bulan", "hutang_total"},
    };

    private MutableLiveData<List<SupplierResult>> supplierList;
    private MutableLiveData<Boolean> isSort;


    public MutableLiveData<Boolean> getIsSort() {
        return isSort;
    }
    public MutableLiveData<List<SupplierResult>> getSupplierList() {
        return supplierList;
    }

    public ActivitySaldoHutangViewModel() {
        isSort = new MutableLiveData<>(false);
        supplierList = new MutableLiveData<>(Collections.emptyList());
    }

    public void fetchListData(String query) {
        fetchSupplierList(query);
    }

    public void fetchSupplierList( String query) {
        String[] params = {"hutang_hari", "hutang_bulan", "hutang_total"};
        String stringParams = "{id, name, mobile, street, street2, city, image, " + StringHelper.convertStringArrayToString(params, ",") + "}";
        String filter = "[[\"supplier\",\"=\",true], [\"name\",\"ilike\",\"" + query + "\"],[\"partner_rating\",\"not in\",[\"5\",\"4\"]]]";
        String sort = getIsSort().getValue() ? "hutang_total" : null;
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
}
