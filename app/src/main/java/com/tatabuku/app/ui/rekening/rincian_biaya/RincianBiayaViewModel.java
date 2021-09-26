package com.tatabuku.app.ui.rekening.rincian_biaya;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.rekening.CatatBiayaList;
import com.tatabuku.app.model.rekening.PostedBiayaHeader;
import com.tatabuku.app.model.rekening.PostedBiayaList;
import com.tatabuku.app.model.rekening.PostedBiayaParams;
import com.tatabuku.app.model.rekening.PostedBiayaRequest;
import com.tatabuku.app.model.rekening.PostedBiayaResponse;
import com.tatabuku.app.model.rekening.SubmitCatatBiayaList;
import com.tatabuku.app.model.rekening.SubmitCatatBiayaParams;
import com.tatabuku.app.model.rekening.SubmitCatatBiayaRequest;
import com.tatabuku.app.model.rekening.SubmitCatatBiayaResponse;
import com.tatabuku.app.model.rekening.SubmitEditBiayaList;
import com.tatabuku.app.model.rekening.SubmitEditBiayaParams;
import com.tatabuku.app.model.rekening.SubmitEditBiayaRequest;
import com.tatabuku.app.model.rekening.SubmitEditBiayaResponse;
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.util.StringHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RincianBiayaViewModel extends ViewModel {

    public enum PageType {
        CATAT_BIAYA, RINCIAN_BIAYA, EDIT_BIAYA
    }

    private MutableLiveData<PostedBiayaHeader> header;
    private MutableLiveData<List<PostedBiayaList>> biayaList;
    private MutableLiveData<String> onSuccess;
    private MutableLiveData<String> onError;
    private MutableLiveData<Double> subtotal;
    private MutableLiveData<String> type;

    private String selectedDate;
    private MutableLiveData<ArrayList<CatatBiayaList>> catatBiayaList;
    private int from_bank;
    private String bank_name;
    private PageType pageType;
    private int edit_voucher_id;

    public MutableLiveData<PostedBiayaHeader> getHeader() {
        return header;
    }

    public MutableLiveData<List<PostedBiayaList>> getBiayaList() {
        return biayaList;
    }

    public MutableLiveData<ArrayList<CatatBiayaList>> getCatatBiayaList() {
        return catatBiayaList;
    }

    public MutableLiveData<Double> getSubtotal() {
        return subtotal;
    }

    public MutableLiveData<String> getOnSuccess() {
        return onSuccess;
    }

    public MutableLiveData<String> getOnError() {
        return onError;
    }

    public void setPageType(PageType pageType) {
        this.pageType = pageType;
    }

    public PageType getPageType() {
        return pageType;
    }

    public int getFrom_bank() {
        return from_bank;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setFrom_bank(int from_bank) {
        this.from_bank = from_bank;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public void setEdit_voucher_id(int edit_voucher_id) {
        this.edit_voucher_id = edit_voucher_id;
    }

    public MutableLiveData<String> getType() {
        return type;
    }

    public RincianBiayaViewModel() {
        header = new MutableLiveData<>();
        biayaList = new MutableLiveData<>();
        onSuccess = new MutableLiveData<>();
        onError = new MutableLiveData<>();
        catatBiayaList = new MutableLiveData<>();
        subtotal = new MutableLiveData<>();
        type = new MutableLiveData<>();
        biayaList.setValue(Collections.emptyList());
    }

    public void setCatatBiayaData(String date, ArrayList<CatatBiayaList> catatBiayaList) {
        this.selectedDate = date;
        this.catatBiayaList.setValue(catatBiayaList);
        countSubtotal();
    }

    public void fetchPostedBiaya(int voucher_id) {
        PostedBiayaParams params = new PostedBiayaParams(voucher_id);
        PostedBiayaRequest request = new PostedBiayaRequest(params);
        Call<PostedBiayaResponse> call = ConnectionManager.getInstance().getService().getPostedBiaya(request);
        call.enqueue(new Callback<PostedBiayaResponse>() {
            @Override
            public void onResponse(Call<PostedBiayaResponse> call, Response<PostedBiayaResponse> response) {
                if (response.body() != null) {
                    header.setValue(response.body().getResult().getHeaderData());
                    biayaList.setValue(response.body().getResult().getListBiaya());
                    type.setValue(response.body().getResult().getHeaderData().getVoucher_type());
                    countSubtotal();
                }
            }

            @Override
            public void onFailure(Call<PostedBiayaResponse> call, Throwable t) {

            }
        });
    }

    private void countSubtotal() {
        double total = 0;

        if (pageType == PageType.CATAT_BIAYA || pageType == PageType.EDIT_BIAYA) {
            for (CatatBiayaList biaya : catatBiayaList.getValue()) {
                double amount = biaya.getAmount() == null ? 0 : biaya.getAmount().doubleValue();
                total = total + amount;
            }
        } else {
            for (PostedBiayaList biaya : biayaList.getValue()) {
                double amount = biaya.getAmount() == null ? 0 : biaya.getAmount().doubleValue();
                total = total + amount;
            }
        }
        subtotal.setValue(total);
    }

    private void submitListedBiaya() {
        List<SubmitCatatBiayaList> submitList = convertBiayaToSubmit();
        String formattedDate = StringHelper.formatBackendDate(selectedDate);
        SubmitCatatBiayaParams params = new SubmitCatatBiayaParams(from_bank, formattedDate, type.getValue(), submitList);
        SubmitCatatBiayaRequest request = new SubmitCatatBiayaRequest(params);
        Call<SubmitCatatBiayaResponse> call = ConnectionManager.getInstance().getService().submitCatatBiaya(request);
        call.enqueue(new Callback<SubmitCatatBiayaResponse>() {
            @Override
            public void onResponse(Call<SubmitCatatBiayaResponse> call, Response<SubmitCatatBiayaResponse> response) {
                onSuccess.setValue("Berhasil");
            }

            @Override
            public void onFailure(Call<SubmitCatatBiayaResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    private void editBiaya() {
        ArrayList<SubmitEditBiayaList> submitList = convertBiayaToEdit();
        SubmitEditBiayaParams params = new SubmitEditBiayaParams(edit_voucher_id, submitList);
        SubmitEditBiayaRequest request = new SubmitEditBiayaRequest(params);
        Call<SubmitEditBiayaResponse> call = ConnectionManager.getInstance().getService().editBiaya(request);
        call.enqueue(new Callback<SubmitEditBiayaResponse>() {
            @Override
            public void onResponse(Call<SubmitEditBiayaResponse> call, Response<SubmitEditBiayaResponse> response) {
                onSuccess.setValue("Berhasil");
            }

            @Override
            public void onFailure(Call<SubmitEditBiayaResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    public void saveBiaya() {
        if (pageType == PageType.CATAT_BIAYA) {
            submitListedBiaya();
        } else if (pageType == PageType.EDIT_BIAYA) {
            editBiaya();
        }
    }

    public ArrayList<CatatBiayaList> getCatatBiayaForEdit() {
        ArrayList<CatatBiayaList> catatBiayaLists = new ArrayList<>();
        for (PostedBiayaList biaya : biayaList.getValue()) {
            catatBiayaLists.add(biaya.toCatatBiaya());
        }
        return  catatBiayaLists;
    }

    private ArrayList<CatatBiayaList> filterListedBiaya() {
        ArrayList<CatatBiayaList> filteredList = new ArrayList<>();

        for (CatatBiayaList biaya: catatBiayaList.getValue()) {
            if (biaya.getAmount() > 0) {
                filteredList.add(biaya);
            }
        }

        return filteredList;
    }

    private ArrayList<SubmitCatatBiayaList> convertBiayaToSubmit() {
        ArrayList<CatatBiayaList> filteredList = filterListedBiaya();
        ArrayList<SubmitCatatBiayaList> convertedList = new ArrayList<>();

        for (CatatBiayaList biaya: filteredList) {
            convertedList.add(biaya.toSubmit());
        }

        return convertedList;
    }

    private ArrayList<SubmitEditBiayaList> convertBiayaToEdit() {
        ArrayList<CatatBiayaList> filteredList = filterListedBiaya();
        ArrayList<SubmitEditBiayaList> convertedList = new ArrayList<>();

        for (CatatBiayaList biaya: filteredList) {
            convertedList.add(biaya.toEdit());
        }

        return convertedList;
    }
}
