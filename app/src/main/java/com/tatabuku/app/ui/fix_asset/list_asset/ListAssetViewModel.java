package com.tatabuku.app.ui.fix_asset.list_asset;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.fixed_asset.ListAssetHeader;
import com.tatabuku.app.model.fixed_asset.ListAssetModel;
import com.tatabuku.app.model.fixed_asset.ListAssetParams;
import com.tatabuku.app.model.fixed_asset.ListAssetRequest;
import com.tatabuku.app.model.fixed_asset.ListAssetResponse;
import com.tatabuku.app.service.ConnectionManager;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAssetViewModel extends ViewModel {

    private MutableLiveData<ListAssetHeader> listAssetHeader;
    private MutableLiveData<List<ListAssetModel>> listAssetModels;

    private int categ_id;

    public int getCateg_id() {
        return categ_id;
    }

    public MutableLiveData<ListAssetHeader> getListAssetHeader() {
        return listAssetHeader;
    }

    public MutableLiveData<List<ListAssetModel>> getListAssetModels() {
        return listAssetModels;
    }

    public ListAssetViewModel() {
        listAssetHeader = new MutableLiveData<>();
        listAssetModels = new MutableLiveData<>();
        listAssetModels.setValue(Collections.emptyList());
    }

    public void refreshList() {
        fetchListAsset(categ_id);
    }

    public void fetchListAsset(int categ_id) {
        this.categ_id = categ_id;
        ListAssetParams params = new ListAssetParams(categ_id);
        ListAssetRequest request = new ListAssetRequest(params);

        Call<ListAssetResponse> call = ConnectionManager.getInstance().getService().getAssetListByCategory(request);
        call.enqueue(new Callback<ListAssetResponse>() {
            @Override
            public void onResponse(Call<ListAssetResponse> call, Response<ListAssetResponse> response) {
                if (response.body() != null) {
                    listAssetHeader.setValue(response.body().getResult().getHeaderData());
                    listAssetModels.setValue(response.body().getResult().getListAssets());
                }
            }

            @Override
            public void onFailure(Call<ListAssetResponse> call, Throwable t) {

            }
        });
    }
}
