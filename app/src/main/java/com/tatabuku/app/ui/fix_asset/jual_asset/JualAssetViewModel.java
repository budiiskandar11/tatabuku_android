package com.tatabuku.app.ui.fix_asset.jual_asset;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.fixed_asset.DetailAssetHeader;
import com.tatabuku.app.model.fixed_asset.GetCustomerListRequest;
import com.tatabuku.app.model.fixed_asset.GetCustomerListResponse;
import com.tatabuku.app.model.fixed_asset.GetCustomerModel;
import com.tatabuku.app.model.fixed_asset.JualAssetParams;
import com.tatabuku.app.model.fixed_asset.JualAssetRequest;
import com.tatabuku.app.model.fixed_asset.JualAssetResponse;
import com.tatabuku.app.service.ConnectionManager;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JualAssetViewModel extends ViewModel {

    private MutableLiveData<List<GetCustomerModel>> customers;
    private MutableLiveData<GetCustomerModel> selectedCustomer;

    private MutableLiveData<String> onSuccess;
    private MutableLiveData<String> onError;

    private MutableLiveData<DetailAssetHeader> headerInfo;

    public MutableLiveData<List<GetCustomerModel>> getCustomers() {
        return customers;
    }

    public MutableLiveData<String> getOnSuccess() {
        return onSuccess;
    }

    public MutableLiveData<String> getOnError() {
        return onError;
    }

    public MutableLiveData<GetCustomerModel> getSelectedCustomer() {
        return selectedCustomer;
    }

    public MutableLiveData<DetailAssetHeader> getHeaderInfo() {
        return headerInfo;
    }

    public JualAssetViewModel() {
        customers = new MutableLiveData<>();
        selectedCustomer = new MutableLiveData<>();
        headerInfo = new MutableLiveData<>();
        onError = new MutableLiveData<>();
        onSuccess = new MutableLiveData<>();
        customers.setValue(Collections.emptyList());
    }

    public void fetchCustomer() {
        GetCustomerListRequest request = new GetCustomerListRequest();
        Call<GetCustomerListResponse> call = ConnectionManager.getInstance().getService().getCustomerList(request);
        call.enqueue(new Callback<GetCustomerListResponse>() {
            @Override
            public void onResponse(Call<GetCustomerListResponse> call, Response<GetCustomerListResponse> response) {
                if (response.body() != null) {
                    customers.setValue(response.body().getResult().getResult());
                }
            }

            @Override
            public void onFailure(Call<GetCustomerListResponse> call, Throwable t) {

            }
        });
    }

    public void sellAsset(String date, String customer_name, String price) {
        if (date.equals("") || customer_name.equals("") || price.equals("")) {
            onError.setValue("Data tidak lengkap");
            return;
        }

        JualAssetParams params = new JualAssetParams(headerInfo.getValue().getAssetId(), customer_name, date, price);
        JualAssetRequest request = new JualAssetRequest(params);
        Call<JualAssetResponse> call = ConnectionManager.getInstance().getService().sellAsset(request);
        call.enqueue(new Callback<JualAssetResponse>() {
            @Override
            public void onResponse(Call<JualAssetResponse> call, Response<JualAssetResponse> response) {
                if (response.body() != null) {
                    onSuccess.setValue("Sukses");
                }
            }

            @Override
            public void onFailure(Call<JualAssetResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

}
