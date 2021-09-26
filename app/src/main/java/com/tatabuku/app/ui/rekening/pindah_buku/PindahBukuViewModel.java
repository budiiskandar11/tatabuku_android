package com.tatabuku.app.ui.rekening.pindah_buku;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.rekening.PindahBukuHeader;
import com.tatabuku.app.model.rekening.PindahBukuBank;
import com.tatabuku.app.model.rekening.PindahBukuParams;
import com.tatabuku.app.model.rekening.PindahBukuRequest;
import com.tatabuku.app.model.rekening.PindahBukuResponse;
import com.tatabuku.app.model.rekening.SubmitPindahBukuParams;
import com.tatabuku.app.model.rekening.SubmitPindahBukuRequest;
import com.tatabuku.app.model.rekening.SubmitPindahBukuResponse;
import com.tatabuku.app.service.ConnectionManager;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PindahBukuViewModel extends ViewModel {
    private MutableLiveData<PindahBukuHeader> header;
    private MutableLiveData<List<PindahBukuBank>> banks;
    private MutableLiveData<PindahBukuBank> toBank;
    private MutableLiveData<String> onSuccess;
    private MutableLiveData<String> onError;

    private int fromBank;

    public MutableLiveData<PindahBukuHeader> getHeader() {
        return header;
    }

    public MutableLiveData<List<PindahBukuBank>> getBanks() {
        return banks;
    }

    public MutableLiveData<PindahBukuBank> getToBank() {
        return toBank;
    }

    public MutableLiveData<String> getOnSuccess() {
        return onSuccess;
    }

    public MutableLiveData<String> getOnError() {
        return onError;
    }

    public void setToBank(PindahBukuBank bank) {
        this.toBank.setValue(bank);
    }

    public PindahBukuViewModel () {
        header = new MutableLiveData<>();
        banks = new MutableLiveData<>();
        toBank = new MutableLiveData<>();
        onError = new MutableLiveData<>();
        onSuccess = new MutableLiveData<>();
        banks.setValue(Collections.emptyList());
    }

    public void fetchData(int bank_from) {
        this.fromBank = bank_from;
        PindahBukuParams params = new PindahBukuParams(bank_from);
        PindahBukuRequest request = new PindahBukuRequest(params);
        Call<PindahBukuResponse> call = ConnectionManager.getInstance().getService().getPindahBukuList(request);
        call.enqueue(new Callback<PindahBukuResponse>() {
            @Override
            public void onResponse(Call<PindahBukuResponse> call, Response<PindahBukuResponse> response) {
                if (response.body() != null) {
                    header.setValue(response.body().getResult().getHeaderInfo());
                    banks.setValue(response.body().getResult().getBankList());
                }
            }

            @Override
            public void onFailure(Call<PindahBukuResponse> call, Throwable t) {

            }
        });
    }

    public void submitPindahBuku(String amount, String date) {
        if (toBank.getValue() == null || amount.equals("") || date.equals("")) {
            onError.setValue("Data tidak lengkap");
            return;
        }
        int intAmount = Integer.parseInt(amount);
        SubmitPindahBukuParams params = new SubmitPindahBukuParams(fromBank, toBank.getValue().getId(), intAmount, date);
        SubmitPindahBukuRequest request = new SubmitPindahBukuRequest(params);
        Call<SubmitPindahBukuResponse> call = ConnectionManager.getInstance().getService().submitPindahBuku(request);
        call.enqueue(new Callback<SubmitPindahBukuResponse>() {
            @Override
            public void onResponse(Call<SubmitPindahBukuResponse> call, Response<SubmitPindahBukuResponse> response) {
                onSuccess.setValue("Berhasil");
            }

            @Override
            public void onFailure(Call<SubmitPindahBukuResponse> call, Throwable t) {
                onError.setValue("Terjadi Kesalahan");
            }
        });

    }

}
