package com.tatabuku.app.ui.saldoPiutang;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.penjualan.CustomerResult;

import java.util.List;

public class ActivitySaldoPiutangViewModel extends ViewModel {
    private MutableLiveData<Boolean> isSort;
    private MutableLiveData<List<CustomerResult>> listCustomer;


    public MutableLiveData<Boolean> getIsSort() {
        return isSort;
    }
    public MutableLiveData<List<CustomerResult>> getListCustomer() {
        return listCustomer;
    }
}
