package com.tatabuku.app.ui.titip_journal.detail;

import com.tatabuku.app.model.titip_jurnal.DetailTitipJurnalHeader;
import com.tatabuku.app.model.titip_jurnal.DetailTitipJurnalModel;
import com.tatabuku.app.model.titip_jurnal.DetailTitipJurnalParams;
import com.tatabuku.app.model.titip_jurnal.DetailTitipJurnalRequest;
import com.tatabuku.app.model.titip_jurnal.DetailTitipJurnalResponse;
import com.tatabuku.app.service.ConnectionManager;

import java.util.Collections;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TitipJurnalViewModel extends ViewModel {

    private MutableLiveData<List<DetailTitipJurnalModel>> jurnalList;
    private MutableLiveData<DetailTitipJurnalHeader> jurnalHeader;
    private MutableLiveData<List<DetailTitipJurnalModel>> jurnalListbelum;

    public MutableLiveData<List<DetailTitipJurnalModel>> getJurnalListbelum() { return jurnalListbelum; }

    public MutableLiveData<List<DetailTitipJurnalModel>> getJurnalList() { return jurnalList; }

    public MutableLiveData<DetailTitipJurnalHeader> getJurnalHeader() { return jurnalHeader; }

    public TitipJurnalViewModel() {
        jurnalList = new MutableLiveData<>();
        jurnalHeader = new MutableLiveData<>();
        jurnalListbelum = new MutableLiveData<>();
        jurnalListbelum.setValue(Collections.emptyList());
        jurnalList.setValue(Collections.emptyList());
    }

    public void fetchDetailJurnal(String filter){
        DetailTitipJurnalParams params = new DetailTitipJurnalParams(filter);
        DetailTitipJurnalRequest request = new DetailTitipJurnalRequest(params);
        Call<DetailTitipJurnalResponse> call = ConnectionManager.getInstance().getService().getDetailTitipJurnal(request);
        call.enqueue(new Callback<DetailTitipJurnalResponse>() {
            @Override
            public void onResponse(Call<DetailTitipJurnalResponse> call, Response<DetailTitipJurnalResponse> response) {
                if (response.body() != null) {
                    jurnalHeader.setValue(response.body().getResult().getTotalQuota());
                    jurnalList.setValue(response.body().getResult().getListSelesai());
                    jurnalListbelum.setValue(response.body().getResult().getListBelumSelesai());
                }
            }

            @Override
            public void onFailure(Call<DetailTitipJurnalResponse> call, Throwable t) {

            }
        });


    }
}
