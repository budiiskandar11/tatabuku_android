package com.tatabuku.app.ui.rekening.detail;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.rekening.RekeningDetailHeader;
import com.tatabuku.app.model.rekening.RekeningDetailParams;
import com.tatabuku.app.model.rekening.RekeningDetailRequest;
import com.tatabuku.app.model.rekening.RekeningDetailResponse;
import com.tatabuku.app.model.rekening.RekeningDetailTransaction;
import com.tatabuku.app.service.ConnectionManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RekeningDetailViewModel extends ViewModel {

    public enum RekeningType {
        SEMUA, UANGMASUK, UANGKELUAR
    }

    private MutableLiveData<RekeningDetailHeader> header;
    private MutableLiveData<List<RekeningDetailTransaction>> transactions;
    private ArrayList<String> headerDates;
    private MutableLiveData<String> onError;

    private int bank_id;
    private String bank_name;
    private MutableLiveData<RekeningType> selectedTab;

    public MutableLiveData<RekeningDetailHeader> getHeader() {
        return header;
    }

    public MutableLiveData<List<RekeningDetailTransaction>> getTransactions() {
        return transactions;
    }

    public MutableLiveData<String> getOnError() {
        return onError;
    }

    public ArrayList<String> getHeaderDates() {
        return headerDates;
    }

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public MutableLiveData<RekeningType> getSelectedTab() {
        return selectedTab;
    }



    public void setSelectedTab(RekeningType selectedTab) {
        this.selectedTab.setValue(selectedTab);
        setEmptyList();
        fetchByType(selectedTab);
    }

    public void refreshTab() {
        fetchByType(selectedTab.getValue());
    }

    public RekeningDetailViewModel() {
        header = new MutableLiveData<>();
        selectedTab = new MutableLiveData<>();
        transactions = new MutableLiveData<>();
        onError = new MutableLiveData<>();
        transactions.setValue(Collections.emptyList());
        headerDates = new ArrayList<>();
        selectedTab.setValue(RekeningType.SEMUA);
    }

    private void fetchData(String filter, String name){
        RekeningDetailParams params = new RekeningDetailParams(this.bank_id, filter, name);
        RekeningDetailRequest request = new RekeningDetailRequest(params);
        Call<RekeningDetailResponse> call = ConnectionManager.getInstance().getService().getRekeningDetail(request);
        call.enqueue(new Callback<RekeningDetailResponse>() {
            @Override
            public void onResponse(Call<RekeningDetailResponse> call, Response<RekeningDetailResponse> response) {
                if (response.body() != null) {
                    header.setValue(response.body().getResult().getHeaderData());
                    transactions.setValue(response.body().getResult().getListTransaksi());
                    updateHeaderDate();
                }
            }

            @Override
            public void onFailure(Call<RekeningDetailResponse> call, Throwable t) {
                Log.d("Debug", t.getLocalizedMessage());
            }
        });
    }

    public void fetchByType(RekeningType type) {
        fetchData(getStringTabType(type), "");
    }

    public void fetchByName(String name) {
        fetchData(getStringTabType(selectedTab.getValue()), name);
    }

    public void setEmptyList() {
        transactions.setValue(Collections.emptyList());
        headerDates.clear();
    }

    private void updateHeaderDate() {
        headerDates.clear();
        List<RekeningDetailTransaction> transactionList = transactions.getValue();

        for (RekeningDetailTransaction transaction : transactionList) {
            if (!headerDates.contains(transaction.getDate())) {
                headerDates.add(transaction.getDate());
                Log.d("Header Size", headerDates.size() + "added : " + transaction.getDate());
            }
        }
    }

    private String getStringTabType(@NotNull RekeningType type) {
        String filter = "semua";
        switch (type) {
            case SEMUA:
                filter = "semua";
                break;
            case UANGMASUK:
                filter = "masuk";
                break;
            case UANGKELUAR:
                filter = "keluar";
                break;
        }

        return filter;
    }

    public RekeningDetailTransaction getTransaction(int position) {
//        Log.d("Get Transaction", "Masuk sini");
        ArrayList<String> headerDatesCopy = (ArrayList<String>) headerDates.clone();
        String selectedDates = "-";
        int i = 0;

        do {
            RekeningDetailTransaction transaction = transactions.getValue().get(i);
            if (transaction.getDate().equals(selectedDates)) {
                position--;
                i++;
            } else {
                selectedDates = headerDatesCopy.remove(0);
                position--;
            }
        } while (position > 0);
        return transactions.getValue().get(i);
    }

    public String getHeaderDate(int position) {
//        Log.d("Get Header Date", "Masuk sini");
        ArrayList<String> headerDatesCopy = (ArrayList<String>) headerDates.clone();
        String selectedDate = "-";
        int i = 0;

        do {
            RekeningDetailTransaction transaction = transactions.getValue().get(i);
            if (transaction.getDate().equals(selectedDate)) {
                position--;
                i++;
            } else {
//                Log.d("Get Header", "SelectedDate : " + selectedDate + "Position : " + position + " Get : " + headerDatesCopy.get(0));
                selectedDate = headerDatesCopy.remove(0);
                position--;
            }
        } while (position >= 0);
        return selectedDate;
    }

    public Boolean checkTransactionCell(int position) {
//        Log.d("Check Transaction Cell", "Masuk sini");
        ArrayList<String> headerDatesCopy = (ArrayList<String>) headerDates.clone();
        String selectedDates = "-";
        String cellType = "Header";
        int i = 0;

        do {
            RekeningDetailTransaction transaction = transactions.getValue().get(i);
            if (transaction.getDate().equals(selectedDates)) {
                cellType = "Transaction";
                position--;
                i++;
            } else {
                selectedDates = headerDatesCopy.remove(0);
                cellType = "Header";
                position--;
            }
        } while (position >= 0);
//        Log.d("Check Cell Result", cellType);
        return cellType == "Transaction";
    }

    public int getTotalListSize() {
        return transactions.getValue().size() + headerDates.size();
    }
}
