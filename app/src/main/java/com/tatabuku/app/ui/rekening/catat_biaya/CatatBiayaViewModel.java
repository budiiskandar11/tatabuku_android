package com.tatabuku.app.ui.rekening.catat_biaya;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.rekening.CatatBiayaHeader;
import com.tatabuku.app.model.rekening.CatatBiayaList;
import com.tatabuku.app.model.rekening.CatatBiayaParams;
import com.tatabuku.app.model.rekening.CatatBiayaRequest;
import com.tatabuku.app.model.rekening.CatatBiayaResponse;
import com.tatabuku.app.service.ConnectionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatatBiayaViewModel extends ViewModel {
    private MutableLiveData<CatatBiayaHeader> header;
    private MutableLiveData<List<CatatBiayaList>> biayaList;
    private MutableLiveData<Double> subtotal;
    private MutableLiveData<String> voucher_no;

    private ArrayList<CatatBiayaList> selectedBiayaList;

    private int from_bank;
    private String bank_name;
    private String type;
    private Integer voucher_id;

    public String getBank_name() {
        return bank_name;
    }

    public int getFrom_bank() {
        return from_bank;
    }

    public ArrayList<CatatBiayaList> getSelectedBiayaList() {
        return selectedBiayaList;
    }

    public void setSelectedBiayaList(ArrayList<CatatBiayaList> selectedBiayaList) {
        this.selectedBiayaList = selectedBiayaList;
        countSubtotal();
    }

    public MutableLiveData<CatatBiayaHeader> getHeader() {
        return header;
    }

    public MutableLiveData<List<CatatBiayaList>> getBiayaList() {
        return biayaList;
    }

    public MutableLiveData<Double> getSubtotal() {
        return subtotal;
    }

    public MutableLiveData<String> getVoucher_no() {
        return voucher_no;
    }

    public void setFrom_bank(int from_bank) {
        this.from_bank = from_bank;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public Integer getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(Integer voucher_id) {
        this.voucher_id = voucher_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CatatBiayaViewModel() {
        header = new MutableLiveData<>();
        biayaList = new MutableLiveData<>();
        subtotal = new MutableLiveData<>();
        voucher_no = new MutableLiveData<>();
        selectedBiayaList = new ArrayList<>();
        biayaList.setValue(Collections.emptyList());
    }

    public void fetchData(String query) {
        CatatBiayaParams params = new CatatBiayaParams(from_bank, query, type);
        CatatBiayaRequest request = new CatatBiayaRequest(params);
        Call<CatatBiayaResponse> call = ConnectionManager.getInstance().getService().getListBiaya(request);
        call.enqueue(new Callback<CatatBiayaResponse>() {
            @Override
            public void onResponse(Call<CatatBiayaResponse> call, Response<CatatBiayaResponse> response) {
                if (response.body() != null) {
                    header.setValue(response.body().getResult().getHeaderBankData());
                    biayaList.setValue(response.body().getResult().getListBiaya());
                }
            }

            @Override
            public void onFailure(Call<CatatBiayaResponse> call, Throwable t) {

            }
        });
    }

    public CatatBiayaList getItem(int position) {
        CatatBiayaList biaya = biayaList.getValue().get(position);

        for (CatatBiayaList selectedBiaya : selectedBiayaList) {
            if (biaya.getAccountId().intValue() == selectedBiaya.getAccountId().intValue()) {
                return selectedBiaya;
            }
        }
        return biaya;
    }

    public void updateListedBiaya(CatatBiayaList biaya) {
        for (CatatBiayaList selectedBiaya : selectedBiayaList) {
            if (selectedBiaya.getAccountId() == biaya.getAccountId()) {
                selectedBiayaList.remove(selectedBiaya);
                break;
            }
        }
        selectedBiayaList.add(biaya);
        countSubtotal();
    }

    public void removeListedBiaya(CatatBiayaList biaya) {
        for (CatatBiayaList selectedBiaya : selectedBiayaList) {
            if (selectedBiaya.getAccountId() == biaya.getAccountId()) {
                selectedBiayaList.remove(selectedBiaya);
                break;
            }
        }
        countSubtotal();
    }

    private void countSubtotal() {
        double total = 0;
        for (CatatBiayaList biaya : selectedBiayaList) {
            double amount = biaya.getAmount() == null ? 0 : biaya.getAmount();
            total = total + amount;
        }
        subtotal.setValue(total);
    }
}
