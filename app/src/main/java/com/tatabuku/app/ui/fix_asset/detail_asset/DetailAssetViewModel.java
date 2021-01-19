package com.tatabuku.app.ui.fix_asset.detail_asset;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.fixed_asset.DetailAssetDepresiasi;
import com.tatabuku.app.model.fixed_asset.DetailAssetHeader;
import com.tatabuku.app.model.fixed_asset.DetailAssetInfo;
import com.tatabuku.app.model.fixed_asset.DetailAssetParams;
import com.tatabuku.app.model.fixed_asset.DetailAssetRequest;
import com.tatabuku.app.model.fixed_asset.DetailAssetResponse;
import com.tatabuku.app.service.ConnectionManager;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailAssetViewModel extends ViewModel {

    private MutableLiveData<DetailAssetHeader> header;
    private MutableLiveData<DetailAssetInfo> info;
    private MutableLiveData<List<DetailAssetDepresiasi>> listDepresiasi;

    private int asset_id;

    public MutableLiveData<DetailAssetHeader> getHeader() {
        return header;
    }

    public MutableLiveData<DetailAssetInfo> getInfo() {
        return info;
    }

    public MutableLiveData<List<DetailAssetDepresiasi>> getListDepresiasi() {
        return listDepresiasi;
    }

    public DetailAssetViewModel () {
        header = new MutableLiveData<>();
        info = new MutableLiveData<>();
        listDepresiasi = new MutableLiveData<>();
        listDepresiasi.setValue(Collections.emptyList());
    }

    public void refreshPage() {
        fetchAssetDetail(asset_id);
    }

    public void fetchAssetDetail(int asset_id) {
        this.asset_id = asset_id;
        DetailAssetParams params = new DetailAssetParams(asset_id);
        DetailAssetRequest request = new DetailAssetRequest(params);
        Call<DetailAssetResponse> call = ConnectionManager.getInstance().getService().getAssetDetail(request);
        call.enqueue(new Callback<DetailAssetResponse>() {
            @Override
            public void onResponse(Call<DetailAssetResponse> call, Response<DetailAssetResponse> response) {
                if (response.body() != null) {
                    header.setValue(response.body().getResult().getAssetHeader());
                    info.setValue(response.body().getResult().getAssetInfo());
                    listDepresiasi.setValue(response.body().getResult().getListDepresiasi());
                }
            }

            @Override
            public void onFailure(Call<DetailAssetResponse> call, Throwable t) {

            }
        });
    }
}
