package com.tatabuku.app.ui.pembelian.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.pembelian.DashboardSupplierResponse;
import com.tatabuku.app.model.pembelian.DashboardSupplierResult;
import com.tatabuku.app.model.pembelian.SupplierListResponse;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.util.StringHelper;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardSupplierViewModel extends ViewModel {

    private final String[][] totalParams = {
            {"total_purchase_hari", "total_purchase_bulan", "total_purchase_tahun"},
            {"total_hutang_hari", "total_hutang_bulan", "total_hutang_tahun"},
            {"total_hutang_hari", "total_hutang_bulan", "total_hutang_tahun"},
            {"total_payment_hari", "total_payment_bulan", "total_payment_tahun"},
            {"total_dp_hari", "total_dp_tahun", "total_dp_bulan"}
    };

    private final String[][] listParams = {
            {"purchase_order_hari", "purchase_order_bulan", "purchase_order_total"},
            {"hutang_hari", "hutang_bulan", "hutang_total"},
            {"hutang_hari", "hutang_bulan", "hutang_total"},
            {"payment_hari", "payment_bulan", "payment_tahun"},
            {"dp_total_hari", "dp_total_bulan", "dp_total"}
    };

    private MutableLiveData<String[]> totalDashboardData;
    private MutableLiveData<List<SupplierResult>> supplierList;
    private MutableLiveData<List<SupplierResult>> faveSupplierList;
    private MutableLiveData<Boolean> isSort;

    public MutableLiveData<List<SupplierResult>> getSupplierList() {
        return supplierList;
    }

    public MutableLiveData<String[]> getTotalDashboardData() {
        return totalDashboardData;
    }

    public MutableLiveData<List<SupplierResult>> getFaveSupplierList() {
        return faveSupplierList;
    }

    public MutableLiveData<Boolean> getIsSort() {
        return isSort;
    }

    public DashboardSupplierViewModel() {
        totalDashboardData = new MutableLiveData<>();
        supplierList = new MutableLiveData<>();
        faveSupplierList = new MutableLiveData<>();
        isSort = new MutableLiveData<>(false);
        supplierList.setValue(Collections.emptyList());
        faveSupplierList.setValue(Collections.emptyList());
    }

    public void fetchCurrentType(DashboardSupplierActivity.DashboardType type) {
        fetchTotalDashboardData(type);
        fetchFaveSupplierList(type);
    }

    private void fetchTotalDashboardData(DashboardSupplierActivity.DashboardType type) {
        String[] params = totalParams[type.ordinal()];
        String stringParams = "{" + StringHelper.convertStringArrayToString(params, ",") + "}";
        Call<DashboardSupplierResponse> call = ConnectionManager.getInstance().getService().getDashboardSupplier(stringParams);
        call.enqueue(new Callback<DashboardSupplierResponse>() {
            @Override
            public void onResponse(Call<DashboardSupplierResponse> call, Response<DashboardSupplierResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult().size() > 0) {
                        String[] resultArray = new String[3];
                        DashboardSupplierResult result = response.body().getResult().get(0);
                        switch (type) {
                            case PEMBELIAN:
                                resultArray[0] = StringHelper.numberFormat(result.getTotalPurchaseHari());
                                resultArray[1] = StringHelper.numberFormat(result.getTotalPurchaseBulan());
                                resultArray[2] = StringHelper.numberFormat(result.getTotalPurchaseTahun());
                                break;
                            case HUTANG:
                            case LUNAS:
                                resultArray[0] = StringHelper.numberFormat(result.getTotalHutangHari());
                                resultArray[1] = StringHelper.numberFormat(result.getTotalHutangBulan());
                                resultArray[2] = StringHelper.numberFormat(result.getTotalHutangTahun());
                                break;
                            case PEMBAYARAN:
                                resultArray[0] = StringHelper.numberFormat(result.getTotalPaymentHari());
                                resultArray[1] = StringHelper.numberFormat(result.getTotalPaymentBulan());
                                resultArray[2] = StringHelper.numberFormat(result.getTotalPaymentTahun());
                                break;
                            case UANGMUKA:
                                resultArray[0] = StringHelper.numberFormat(result.getTotalDpHari());
                                resultArray[1] = StringHelper.numberFormat(result.getTotalDpBulan());
                                resultArray[2] = StringHelper.numberFormat(result.getTotalDpTahun());
                                break;
                        }
                        totalDashboardData.setValue(resultArray);
                    }
                }
            }

            @Override
            public void onFailure(Call<DashboardSupplierResponse> call, Throwable t) {

            }
        });
    }

    public void fetchSupplierList(DashboardSupplierActivity.DashboardType type, String query) {
        String[] params = listParams[type.ordinal()];
        String stringParams = "{id, name, mobile, street, street2, city, image, " + StringHelper.convertStringArrayToString(params, ",") + "}";

        String filter = "[[\"supplier\",\"=\",true], [\"name\",\"ilike\",\"" + query + "\"],[\"partner_rating\",\"not in\",[\"5\",\"4\"]]]";
        String sort = getIsSort().getValue() ? "\"" + params[params.length - 1] + "\"" : null;
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

    private void fetchFaveSupplierList(DashboardSupplierActivity.DashboardType type) {
        String[] params = listParams[type.ordinal()];
        String stringParams = "{id, name, mobile, street, street2, city, image, " + StringHelper.convertStringArrayToString(params, ",") + "}";;

        Call<SupplierListResponse> call = ConnectionManager.getInstance().getService().getFavoriteSupplierList(stringParams);
        call.enqueue(new Callback<SupplierListResponse>() {
            @Override
            public void onResponse(Call<SupplierListResponse> call, Response<SupplierListResponse> response) {
                if (response.body() != null) {
                    faveSupplierList.setValue(response.body().getResult());
                    fetchSupplierList(type, "");
                }
            }

            @Override
            public void onFailure(Call<SupplierListResponse> call, Throwable t) {
            }
        });
    }
}
