package com.tatabuku.app.ui.pembelian.pembayaran;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.pembelian.InvoiceLineDetail;
import com.tatabuku.app.model.pembelian.PartnerData;
import com.tatabuku.app.model.pembelian.PaymentDataModel;
import com.tatabuku.app.model.pembelian.PaymentDataParams;
import com.tatabuku.app.model.pembelian.PaymentDataResponse;
import com.tatabuku.app.model.pembelian.PaymentDataResult;
import com.tatabuku.app.model.pembelian.PaymentDetailDataResponse;
import com.tatabuku.app.model.pembelian.PaymentDetailDataResult;
import com.tatabuku.app.model.pembelian.PaymentEditResponse;
import com.tatabuku.app.model.pembelian.PaymentIdModel;
import com.tatabuku.app.model.pembelian.PaymentIdParams;
import com.tatabuku.app.model.pembelian.RekeningBank;
import com.tatabuku.app.model.pembelian.RepostPaymentModel;
import com.tatabuku.app.model.pembelian.RepostPaymentParams;
import com.tatabuku.app.model.pembelian.SubmitOrderResponse;
import com.tatabuku.app.model.pembelian.SubmitPaymentBank;
import com.tatabuku.app.model.pembelian.SubmitPaymentModel;
import com.tatabuku.app.model.pembelian.SubmitPaymentParams;
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.util.StringHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PembayaranViewModel extends ViewModel {
    private MutableLiveData<String> onError;
    private MutableLiveData<String> onSuccess;
    private MutableLiveData<PaymentDataResult> paymentData;
    private MutableLiveData<PaymentDetailDataResult> paymentDetailData;
    private MutableLiveData<List<RekeningBank>> bankList;
    private MutableLiveData<List<InvoiceLineDetail>> itemList;
    private MutableLiveData<PartnerData> supplierResult;
    private MutableLiveData<Boolean> isEdit;
    private MutableLiveData<Boolean> isView;
    private MutableLiveData<Boolean> isFromPayment;

    public MutableLiveData<String> getOnError() {
        return onError;
    }

    public MutableLiveData<String> getOnSuccess() {
        return onSuccess;
    }

    public MutableLiveData<PaymentDataResult> getPaymentData() {
        return paymentData;
    }

    public MutableLiveData<PartnerData> getSupplierResult() {
        return supplierResult;
    }

    public MutableLiveData<List<RekeningBank>> getBankList() {
        return bankList;
    }

    public MutableLiveData<Boolean> getIsEdit() {
        return isEdit;
    }

    public MutableLiveData<Boolean> getIsView() {
        return isView;
    }

    public MutableLiveData<PaymentDetailDataResult> getPaymentDetailData() {
        return paymentDetailData;
    }

    public MutableLiveData<Boolean> getIsFromPayment() {
        return isFromPayment;
    }

    public MutableLiveData<List<InvoiceLineDetail>> getItemList() {
        return itemList;
    }

    public PembayaranViewModel() {
        onError = new MutableLiveData<>();
        onSuccess = new MutableLiveData<>();
        paymentData = new MutableLiveData<>();
        paymentDetailData = new MutableLiveData<>();
        supplierResult = new MutableLiveData<>();
        isEdit = new MutableLiveData<>(false);
        isView = new MutableLiveData<>(false);
        isFromPayment = new MutableLiveData<>(false);
        bankList = new MutableLiveData<>(new ArrayList<>());
        itemList = new MutableLiveData<>(new ArrayList<>());
    }

    public void bayar(int id) {
        if (isFromPayment.getValue()) {
            if (paymentDetailData.getValue() == null) {
                onError.setValue("Terjadi Kesalahan");
            } else {
                repostPembayaran(id);
            }
        } else {
            if (paymentData.getValue() == null) {
                onError.setValue("Terjadi Kesalahan");
            } else {
                submitPembayaran(id);
            }
        }
    }
    public void edit(int id){
        if (isFromPayment.getValue()) {
            cancelPayment(id);
        } else {
            cancelPaymentData(id);
        }
    }

    private void cancelPaymentData(int id) {
        Call<SubmitOrderResponse> call = ConnectionManager.getInstance().getService().cancelPaymentData(new PaymentDataModel(new PaymentDataParams(id)));
        call.enqueue(new Callback<SubmitOrderResponse>() {
            @Override
            public void onResponse(Call<SubmitOrderResponse> call, Response<SubmitOrderResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        isEdit.setValue(false);
                        return;
                    }
                }
                onError.setValue("Gagal membatalkan pembayaran");
            }

            @Override
            public void onFailure(Call<SubmitOrderResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    private void submitPembayaran(int id) {
        RekeningBank bank = null;
        RekeningBank dp = null;
        RekeningBank potongan = null;
        for (RekeningBank b :
                bankList.getValue()) {
            if (b.getId() == 0 && b.getName().equalsIgnoreCase("Saldo DP")) {
                dp = b;
            } else if (b.getId() == 0 && b.getName().equalsIgnoreCase("Potongan Pembayaran")) {
                potongan = b;
            } else if (b.getChecked()) {
                bank = b;
            }
        }

        SubmitPaymentBank submitPaymentBank = null;
        if (bank != null) {
            submitPaymentBank = new SubmitPaymentBank(bank.getId(), bank.getValue());
        }

        if (dp == null && potongan == null) {
            onError.setValue("Pilih pembayaran");
            return;
        }

        SubmitPaymentParams paymentParams = new SubmitPaymentParams(id, StringHelper.getTodayDate("yyyy-MM-dd"), dp != null ? dp.getValue() : 0.0, potongan != null ? potongan.getValue() : 0.0, submitPaymentBank);
        SubmitPaymentModel model = new SubmitPaymentModel(paymentParams);

        Call<SubmitOrderResponse> call = ConnectionManager.getInstance().getService().submitPaymentData(model);
        call.enqueue(new Callback<SubmitOrderResponse>() {
            @Override
            public void onResponse(Call<SubmitOrderResponse> call, Response<SubmitOrderResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null && response.body().getResult().getMessage() != null) {
                        if (response.body().getResult().getMessage().equalsIgnoreCase("Success")) {
                            onSuccess.setValue("");
                            return;
                        } else {
                            onError.setValue(response.body().getResult().getMessage());
                            return;
                        }
                    }
                }
                onError.setValue("Gagal menyimpan pembayaran");
            }

            @Override
            public void onFailure(Call<SubmitOrderResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    public void fetchData(int id) {
        if (isFromPayment.getValue()) {
            fetchDetailPayment(id);
        } else if (isEdit.getValue() || isView.getValue()) {
            fetchPaidInvoice(id);
        } else {
            fetchOpenInvoice(id);
        }
    }

    private void fetchPaidInvoice(int id) {
        Call<PaymentDataResponse> call = ConnectionManager.getInstance().getService().getPaidPaymentData(new PaymentDataModel(new PaymentDataParams(id)));
        call.enqueue(new Callback<PaymentDataResponse>() {
            @Override
            public void onResponse(Call<PaymentDataResponse> call, Response<PaymentDataResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null
                            && response.body().getResult().getPartnerData()  != null) {

                        supplierResult.setValue(response.body().getResult().getPartnerData());

                        for (int i = 0; i < response.body().getResult().getPaymentList().size(); i++) {
                            RekeningBank bank = response.body().getResult().getPaymentList().get(i);
                            bank.setChecked(true);
                            bank.setValue(bank.getAmount());
                        }


                        List<InvoiceLineDetail> list = response.body().getResult().getInvoiceData().getInvoiceLineDetails();
                        List<InvoiceLineDetail> compactList = new ArrayList<>();
                        for (InvoiceLineDetail item : list) {
                            if (item.getQty() != 0) {
                                compactList.add(item);
                            }
                        }

                        itemList.setValue(compactList);

                        bankList.setValue(response.body().getResult().getPaymentList());
                        paymentData.setValue(response.body().getResult());
                        return;
                    }
                }
                onError.setValue("Gagal mengambil data pembayaran");
            }

            @Override
            public void onFailure(Call<PaymentDataResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    private void fetchOpenInvoice(int id) {
        Call<PaymentDataResponse> call = ConnectionManager.getInstance().getService().getPaymentData(new PaymentDataModel(new PaymentDataParams(id)));
        call.enqueue(new Callback<PaymentDataResponse>() {
            @Override
            public void onResponse(Call<PaymentDataResponse> call, Response<PaymentDataResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        supplierResult.setValue(response.body().getResult().getPartnerData());
                        bankList.setValue(response.body().getResult().getBankSelection());

                        List<InvoiceLineDetail> list = response.body().getResult().getInvoiceData().getInvoiceLineDetails();
                        List<InvoiceLineDetail> compactList = new ArrayList<>();
                        for (InvoiceLineDetail item : list) {
                            if (item.getQty() != 0) {
                                compactList.add(item);
                            }
                        }

                        itemList.setValue(compactList);
                        paymentData.setValue(response.body().getResult());
                        return;
                    }
                }
                onError.setValue("Gagal mengambil data pembayaran");
            }

            @Override
            public void onFailure(Call<PaymentDataResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    private void cancelPayment(int id) {
        Call<PaymentEditResponse> call = ConnectionManager.getInstance().getService().cancelPayment(new PaymentIdModel(new PaymentIdParams(id)));
        call.enqueue(new Callback<PaymentEditResponse>() {
            @Override
            public void onResponse(Call<PaymentEditResponse> call, Response<PaymentEditResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        isEdit.setValue(false);
                        return;
                    }
                }
                onError.setValue("Gagal membatalkan pembayaran");
            }

            @Override
            public void onFailure(Call<PaymentEditResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    private void fetchDetailPayment(int id) {
        Call<PaymentDetailDataResponse> call = ConnectionManager.getInstance().getService().getDetailPayment(new PaymentIdModel(new PaymentIdParams(id)));
        call.enqueue(new Callback<PaymentDetailDataResponse>() {
            @Override
            public void onResponse(Call<PaymentDetailDataResponse> call, Response<PaymentDetailDataResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null && response.body().getResult().getInvoiceData().size() > 0) {
                        supplierResult.setValue(response.body().getResult().getPartnerData());
                        for (int i = 0; i < response.body().getResult().getBankList().size(); i++) {
                            RekeningBank bank = response.body().getResult().getBankList().get(i);
                            bank.setChecked(false);
                            for (int j = 0; j < response.body().getResult().getPaymentList().size(); j++) {
                                RekeningBank payment = response.body().getResult().getPaymentList().get(j);
                                if (payment.getName().trim().equalsIgnoreCase(bank.getName().trim())) {
                                    bank.setChecked(true);
                                    bank.setValue(payment.getAmount());
                                    bank.setAmount(payment.getAmount());
                                    break;
                                }
                            }
                        }
                        bankList.setValue(response.body().getResult().getBankList());

                        List<InvoiceLineDetail> list = response.body().getResult().getInvoiceData().get(0).getInvoiceLineDetails();
                        List<InvoiceLineDetail> compactList = new ArrayList<>();
                        for (InvoiceLineDetail item : list) {
                            if (item.getQty() != 0) {
                                compactList.add(item);
                            }
                        }

                        itemList.setValue(compactList);
                        paymentDetailData.setValue(response.body().getResult());
                        return;
                    }
                }
                onError.setValue("Gagal mengambil data pembayaran");
            }

            @Override
            public void onFailure(Call<PaymentDetailDataResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    private void repostPembayaran(int id) {
        RekeningBank bank = null;
        RekeningBank dp = null;
        RekeningBank potongan = null;
        for (RekeningBank b :
                bankList.getValue()) {
            if (b.getId() == 0 && b.getName().equalsIgnoreCase("Saldo DP")) {
                dp = b;
            } else if (b.getId() == 0 && b.getName().equalsIgnoreCase("Potongan Pembayaran")) {
                potongan = b;
            } else if (b.getChecked()) {
                bank = b;
            }
        }

        SubmitPaymentBank submitPaymentBank = null;
        if (bank != null) {
            submitPaymentBank = new SubmitPaymentBank(bank.getId(), bank.getValue());
        }

        if (dp == null && potongan == null) {
            onError.setValue("Pilih pembayaran");
            return;
        }

        RepostPaymentParams paymentParams = new RepostPaymentParams(id, StringHelper.getTodayDate("yyyy-MM-dd"), dp != null ? dp.getValue() : 0.0, potongan != null ? potongan.getValue() : 0.0, submitPaymentBank);
        RepostPaymentModel model = new RepostPaymentModel(paymentParams);

        Call<PaymentEditResponse> call = ConnectionManager.getInstance().getService().repostPaymentData(model);
        call.enqueue(new Callback<PaymentEditResponse>() {
            @Override
            public void onResponse(Call<PaymentEditResponse> call, Response<PaymentEditResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null && response.body().getResult().getMessage() != null) {
                        if (response.body().getResult().getMessage().equalsIgnoreCase("Success")) {
                            onSuccess.setValue("");
                            return;
                        } else {
                            onError.setValue(response.body().getResult().getMessage());
                            return;
                        }
                    }
                }
                onError.setValue("Gagal menyimpan pembayaran");
            }

            @Override
            public void onFailure(Call<PaymentEditResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    public Double getTotal() {
        Double total = 0.0;
        for (RekeningBank bank :
                getBankList().getValue()) {
            if (bank.getChecked()) {
                total += bank.getValue();
            }
        }
        return total;
    }

}
