package com.tatabuku.app.ui.pembelian.tambah.dp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.pembelian.AddDPModel;
import com.tatabuku.app.model.pembelian.RekeningBank;
import com.tatabuku.app.model.pembelian.RekeningDataModel;
import com.tatabuku.app.model.pembelian.RekeningDataResponse;
import com.tatabuku.app.model.pembelian.RekeningDataResult;
import com.tatabuku.app.model.pembelian.SubmitOrderResponse;
import com.tatabuku.app.model.pembelian.SupplierResult;
import com.tatabuku.app.model.penjualan.CustomerDPResponse;
import com.tatabuku.app.model.penjualan.CustomerDPResult;
import com.tatabuku.app.model.penjualan.DPPostModel;
import com.tatabuku.app.model.penjualan.IdPostModel;
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.util.StringHelper;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahDpPembelianViewModel extends ViewModel {
    private MutableLiveData<String> onError;
    private MutableLiveData<String> onSuccess;
    private MutableLiveData<SupplierResult> supplierResult;
    private MutableLiveData<List<RekeningBank>> rekeningList;
    private MutableLiveData<RekeningDataResult> rekeningData;
    private MutableLiveData<RekeningBank> selectedBank;
    private MutableLiveData<CustomerDPResult> dpData;
    private MutableLiveData<Boolean> isEdit;

    public MutableLiveData<SupplierResult> getSupplierResult() {
        return supplierResult;
    }

    public MutableLiveData<List<RekeningBank>> getRekeningList() {
        return rekeningList;
    }

    public MutableLiveData<RekeningDataResult> getRekeningData() {
        return rekeningData;
    }

    public MutableLiveData<RekeningBank> getSelectedBank() {
        return selectedBank;
    }

    public MutableLiveData<String> getOnError() {
        return onError;
    }

    public MutableLiveData<String> getOnSuccess() {
        return onSuccess;
    }

    public MutableLiveData<Boolean> getIsEdit() {
        return isEdit;
    }

    public MutableLiveData<CustomerDPResult> getDpData() {
        return dpData;
    }

    public TambahDpPembelianViewModel() {

        supplierResult = new MutableLiveData<>();
        rekeningList = new MutableLiveData<>(Collections.emptyList());
        rekeningData = new MutableLiveData<>();
        selectedBank = new MutableLiveData<>();
        onError = new MutableLiveData<>();
        onSuccess = new MutableLiveData<>();
        dpData = new MutableLiveData<>();
        isEdit = new MutableLiveData<>(false);

    }

    public void addDP(Integer value, Boolean isRefund, Date date, int dpId) {
        if (value == 0) {
            onError.setValue("Besar DP harus diisi");
        } else if (selectedBank.getValue() == null) {
            onError.setValue("Rekening harus dipilih");
        } else {
            String dateString = StringHelper.formatDate(date, "yyyy-MM-dd");
            if (dpId != 0) {
                updateDP(new DPPostModel(dpId, supplierResult.getValue().getId(), value, dateString, selectedBank.getValue().getId()));
            } else if (isRefund) {
                submitRefundDP(new AddDPModel(supplierResult.getValue().getId(), value, dateString, selectedBank.getValue().getId()));
            } else {
                submitAddDP(new AddDPModel(supplierResult.getValue().getId(), value, dateString, selectedBank.getValue().getId()));
            }
        }
    }

    private void updateDP(DPPostModel model) {
        Call<SubmitOrderResponse> call = ConnectionManager.getInstance().getService().updateDPCustomer(model);
        call.enqueue(new Callback<SubmitOrderResponse>() {
            @Override
            public void onResponse(Call<SubmitOrderResponse> call, Response<SubmitOrderResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        onSuccess.setValue("");
                        return;
                    }
                }
                onError.setValue("Gagal menyimpan DP");
            }

            @Override
            public void onFailure(Call<SubmitOrderResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    private void submitAddDP(AddDPModel model) {
        Call<SubmitOrderResponse> call = ConnectionManager.getInstance().getService().addDP(model);
        call.enqueue(new Callback<SubmitOrderResponse>() {
            @Override
            public void onResponse(Call<SubmitOrderResponse> call, Response<SubmitOrderResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        onSuccess.setValue("");
                        return;
                    }
                }
                onError.setValue("Gagal menyimpan DP");
            }

            @Override
            public void onFailure(Call<SubmitOrderResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    private void submitRefundDP(AddDPModel model) {
        Call<SubmitOrderResponse> call = ConnectionManager.getInstance().getService().refundDP(model);
        call.enqueue(new Callback<SubmitOrderResponse>() {
            @Override
            public void onResponse(Call<SubmitOrderResponse> call, Response<SubmitOrderResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null && response.body().getResult().getMessage() != null) {
                        if(response.body().getResult().getMessage().equalsIgnoreCase("Success")) {
                            onSuccess.setValue("");
                            return;
                        }else {
                            onError.setValue(response.body().getResult().getMessage());
                            return;
                        }
                    }
                }
                onError.setValue("Gagal merefund DP");
            }

            @Override
            public void onFailure(Call<SubmitOrderResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    public void fetchRekeningData(int dpId) {
        int id = supplierResult.getValue().getId();
        RekeningDataModel model = new RekeningDataModel(id);
        Call<RekeningDataResponse> call = ConnectionManager.getInstance().getService().getRekeningData(model);

        call.enqueue(new Callback<RekeningDataResponse>() {
            @Override
            public void onResponse(Call<RekeningDataResponse> call, Response<RekeningDataResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        rekeningData.setValue(response.body().getResult());
                        rekeningList.setValue(response.body().getResult().getBankIds());
                        if (dpId != 0) {
                            fetchDPData(dpId);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RekeningDataResponse> call, Throwable t) {

            }
        });
    }

    public void fetchDPData(int dpId) {
        IdPostModel model = new IdPostModel(dpId);
        Call<CustomerDPResponse> call = ConnectionManager.getInstance().getService().getDataDPCustomer(model);

        call.enqueue(new Callback<CustomerDPResponse>() {
            @Override
            public void onResponse(Call<CustomerDPResponse> call, Response<CustomerDPResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        dpData.setValue(response.body().getResult());

                        for (int i = 0; i < rekeningList.getValue().size(); i++) {
                            if (rekeningList.getValue().get(i).getName().equalsIgnoreCase(response.body().getResult().getRekening())) {
                                selectedBank.setValue(rekeningList.getValue().get(i));
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerDPResponse> call, Throwable t) {

            }
        });
    }
}
