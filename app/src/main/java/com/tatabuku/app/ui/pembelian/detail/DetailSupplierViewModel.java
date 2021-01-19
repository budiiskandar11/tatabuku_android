package com.tatabuku.app.ui.pembelian.detail;

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
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.ui.pembelian.dashboard.DashboardSupplierActivity;
import com.tatabuku.app.util.StringHelper;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSupplierViewModel extends ViewModel {

    private final String[][] totalParams = {
            {"purchase_order_hari", "purchase_order_bulan", "purchase_order_total"},
            {"hutang_hari", "hutang_bulan", "hutang_total", "hutang_tahun", "debit"},
            {"hutang_hari", "hutang_bulan", "hutang_total", "hutang_tahun", "debit"},
            {"debit"},
            {"dp_total_hari", "dp_total_bulan", "dp_total", "dp_pembelian_masuk", "dp_pembelian_keluar"}
    };

    private MutableLiveData<SupplierResult> supplierResult;
    private MutableLiveData<List<OrderResult>> orderList;
    private MutableLiveData<List<InvoiceResult>> invoiceList;
    private MutableLiveData<List<DownPaymentResult>> dpList;
    private MutableLiveData<List<PembayaranItem>> paymentList;
    private MutableLiveData<Boolean> isSort;

    public MutableLiveData<SupplierResult> getSupplierResult() {
        return supplierResult;
    }

    public MutableLiveData<List<OrderResult>> getOrderList() {
        return orderList;
    }

    public MutableLiveData<List<InvoiceResult>> getInvoiceList() {
        return invoiceList;
    }

    public MutableLiveData<List<DownPaymentResult>> getDpList() {
        return dpList;
    }

    public MutableLiveData<List<PembayaranItem>> getPaymentList() {
        return paymentList;
    }

    public MutableLiveData<Boolean> getIsSort() {
        return isSort;
    }

    public DetailSupplierViewModel() {

        supplierResult = new MutableLiveData<>();
        orderList = new MutableLiveData<>(Collections.emptyList());
        invoiceList = new MutableLiveData<>(Collections.emptyList());
        dpList = new MutableLiveData<>(Collections.emptyList());
        paymentList = new MutableLiveData<>(Collections.emptyList());
        isSort = new MutableLiveData<>(false);
    }

    public void fetchData(Integer id, DashboardSupplierActivity.DashboardType type) {
        fetchSupplier(id, type);
    }

    public void searchData(Integer id, DashboardSupplierActivity.DashboardType type, String query) {
        switch (type) {
            case PEMBELIAN:
                fetchOrderList(id, query);
                break;
            case HUTANG:
                fetchInvoiceList(id, query, false);
                break;
            case LUNAS:
                fetchInvoiceList(id, query, true);
                break;
            case UANGMUKA:
                fetchDPList(id, query);
                break;
            case PEMBAYARAN:
                fetchPaymentList(id, query);
                break;
        }
    }

    private void fetchSupplier(Integer id, DashboardSupplierActivity.DashboardType type) {
        String[] params = totalParams[type.ordinal()];
        String stringParams = "{id, name, mobile, street, street2, city, image, status_pajak, " + StringHelper.convertStringArrayToString(params, ",") + "}";

        String filter = "[[\"id\",\"=\"," + id + "]]";
        String sort = getIsSort().getValue() ? "\"amount_total\"" : null;
        sort = type == DashboardSupplierActivity.DashboardType.UANGMUKA && getIsSort().getValue() ? "\"amount_total_signed\"" : sort;
        Call<SupplierListResponse> call = ConnectionManager.getInstance().getService().getSupplierList(stringParams, filter, sort);
        call.enqueue(new Callback<SupplierListResponse>() {
            @Override
            public void onResponse(Call<SupplierListResponse> call, Response<SupplierListResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult().size() > 0) {
                        supplierResult.setValue(response.body().getResult().get(0));
                        searchData(id, type, "");
                    }
                }
            }

            @Override
            public void onFailure(Call<SupplierListResponse> call, Throwable t) {
            }
        });
    }

    private void fetchInvoiceList(Integer id, String search, Boolean isLunas) {
        String query = "{id, number, date_invoice,partner_id{id,name},invoice_line_ids, amount_total,state,residual}";
        String filter = "[[\"partner_id\",\"=\"," + id + "], [\"number\",\"ilike\",\"" + search + "\"], [\"state\", \"=\", \"" + (isLunas ? "paid" : "open") + "\"]]";
        Call<InvoiceListResponse> call = ConnectionManager.getInstance().getService().getInvoiceList(query, filter);
        call.enqueue(new Callback<InvoiceListResponse>() {
            @Override
            public void onResponse(Call<InvoiceListResponse> call, Response<InvoiceListResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        invoiceList.setValue(response.body().getResult());

                    }
                }
            }

            @Override
            public void onFailure(Call<InvoiceListResponse> call, Throwable t) {
            }
        });
    }

    private void fetchOrderList(Integer id, String search) {
        String query = "{id,name,partner_id{name},date_order,state,amount_total,order_line{product_id,product_uom_qty,price_unit, price_subtotal}}";
        String filter = "[[\"partner_id\",\"=\"," + id + "], [\"name\",\"ilike\",\"" + search + "\"]]";
        Call<OrderListResponse> call = ConnectionManager.getInstance().getService().getOrderList(query, filter);
        call.enqueue(new Callback<OrderListResponse>() {
            @Override
            public void onResponse(Call<OrderListResponse> call, Response<OrderListResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        orderList.setValue(response.body().getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderListResponse> call, Throwable t) {
            }
        });
    }

    private void fetchDPList(Integer id, String search) {
        String query = "{id,name, date, partner_id{name},dp_type,amount_total_signed}";
        String filter = "[[\"partner_id\",\"=\"," + id + "], [\"name\",\"ilike\",\"" + search + "\"]]";
        Call<DownPaymentListResponse> call = ConnectionManager.getInstance().getService().getDPList(query, filter);
        call.enqueue(new Callback<DownPaymentListResponse>() {
            @Override
            public void onResponse(Call<DownPaymentListResponse> call, Response<DownPaymentListResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        dpList.setValue(response.body().getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<DownPaymentListResponse> call, Throwable t) {
            }
        });
    }

    private void fetchPaymentList(Integer id, String search) {
        LoadPaymentModel model = new LoadPaymentModel(id, "supplier", search);
        Call<PembayaranResponse> call = ConnectionManager.getInstance().getService().getPaymentList(model);
        call.enqueue(new Callback<PembayaranResponse>() {
            @Override
            public void onResponse(Call<PembayaranResponse> call, Response<PembayaranResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        SupplierResult supplier = supplierResult.getValue();
                        supplier.setPaymentHari(response.body().getResult().getPartnerData().getPaymentHari());
                        supplier.setPaymentBulan(response.body().getResult().getPartnerData().getPaymentBulan());
                        supplier.setPaymentTahun(response.body().getResult().getPartnerData().getPaymentTotal());

                        paymentList.setValue(response.body().getResult().getPaymentList());
                        supplierResult.setValue(supplier);
                    }
                }
            }

            @Override
            public void onFailure(Call<PembayaranResponse> call, Throwable t) {

            }
        });
    }
}
