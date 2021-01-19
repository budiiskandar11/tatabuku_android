package com.tatabuku.app.ui.fix_asset.tambah_asset;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.fixed_asset.GetPurchasesModel;
import com.tatabuku.app.model.fixed_asset.GetPurchasesOrderLine;
import com.tatabuku.app.model.fixed_asset.GetPurchasesParams;
import com.tatabuku.app.model.fixed_asset.GetPurchasesRequest;
import com.tatabuku.app.model.fixed_asset.GetPurchasesResponse;
import com.tatabuku.app.model.fixed_asset.GetSupplierModel;
import com.tatabuku.app.model.fixed_asset.GetSupplierParams;
import com.tatabuku.app.model.fixed_asset.GetSupplierRequest;
import com.tatabuku.app.model.fixed_asset.GetSupplierResponse;
import com.tatabuku.app.model.fixed_asset.TambahAssetParams;
import com.tatabuku.app.model.fixed_asset.TambahAssetRequest;
import com.tatabuku.app.model.fixed_asset.TambahAssetResponse;
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.util.ImageHelper;

import java.io.File;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahAssetViewModel extends ViewModel {

    private MutableLiveData<List<GetSupplierModel>> suppliers;
    private MutableLiveData<List<GetPurchasesModel>> purchases;
    private MutableLiveData<List<GetPurchasesOrderLine>> poLines;
    private MutableLiveData<GetSupplierModel> selectedSupplier;
    private MutableLiveData<GetPurchasesModel> selectedPurchase;
    private MutableLiveData<GetPurchasesOrderLine> selectedPOLines;

    private MutableLiveData<String> onSuccess;
    private MutableLiveData<String> onError;
    private MutableLiveData<Boolean> isNewPurchase;

    private int categ_id;
    private File selectedFile = null;

    public void setCateg_id(int categ_id) {
        this.categ_id = categ_id;
    }

    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    public MutableLiveData<List<GetSupplierModel>> getSuppliers() {
        return suppliers;
    }

    public MutableLiveData<List<GetPurchasesModel>> getPurchases() {
        return purchases;
    }

    public MutableLiveData<List<GetPurchasesOrderLine>> getPoLines() {
        return poLines;
    }

    public MutableLiveData<GetSupplierModel> getSelectedSupplier() {
        return selectedSupplier;
    }

    public MutableLiveData<GetPurchasesModel> getSelectedPurchase() {
        return selectedPurchase;
    }

    public MutableLiveData<GetPurchasesOrderLine> getSelectedPOLines() {
        return selectedPOLines;
    }

    public MutableLiveData<String> getOnSuccess() {
        return onSuccess;
    }

    public MutableLiveData<String> getOnError() {
        return onError;
    }

    public MutableLiveData<Boolean> getIsNewPurchase() {
        return isNewPurchase;
    }

    public TambahAssetViewModel () {
        suppliers = new MutableLiveData<>();
        purchases = new MutableLiveData<>();
        poLines = new MutableLiveData<>();
        selectedSupplier = new MutableLiveData<>();
        selectedPOLines = new MutableLiveData<>();
        selectedPurchase = new MutableLiveData<>();
        onSuccess = new MutableLiveData<>();
        onError = new MutableLiveData<>();
        isNewPurchase = new MutableLiveData<>();
        suppliers.setValue(Collections.emptyList());
        purchases.setValue(Collections.emptyList());
        poLines.setValue(Collections.emptyList());
    }

    public void fetchSupplier(String name) {
        GetSupplierParams params = new GetSupplierParams(name);
        GetSupplierRequest request = new GetSupplierRequest(params);
        Call<GetSupplierResponse> call = ConnectionManager.getInstance().getService().getSupplier(request);
        call.enqueue(new Callback<GetSupplierResponse>() {
            @Override
            public void onResponse(Call<GetSupplierResponse> call, Response<GetSupplierResponse> response) {
                if (response.body() != null) {
                    suppliers.setValue(response.body().getResult().getSupplierList());
                    purchases.setValue(Collections.emptyList());
                    poLines.setValue(Collections.emptyList());
                    selectedPOLines.setValue(null);
                    selectedPurchase.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetSupplierResponse> call, Throwable t) {

            }
        });
    }

    public void fetchPurchases(int supplier_id) {
        GetPurchasesParams params = new GetPurchasesParams(supplier_id);
        GetPurchasesRequest request = new GetPurchasesRequest(params);
        Call<GetPurchasesResponse> call = ConnectionManager.getInstance().getService().getPurchases(request);
        call.enqueue(new Callback<GetPurchasesResponse>() {
            @Override
            public void onResponse(Call<GetPurchasesResponse> call, Response<GetPurchasesResponse> response) {
                if (response.body() != null) {
                    purchases.setValue(response.body().getResult().getPurchaseList());
                    poLines.setValue(Collections.emptyList());
                }
            }

            @Override
            public void onFailure(Call<GetPurchasesResponse> call, Throwable t) {

            }
        });
    }

    private void createAsset(TambahAssetParams params) {
        TambahAssetRequest request = new TambahAssetRequest(params);
        Call<TambahAssetResponse> call = ConnectionManager.getInstance().getService().createAsset(request);
        call.enqueue(new Callback<TambahAssetResponse>() {
            @Override
            public void onResponse(Call<TambahAssetResponse> call, Response<TambahAssetResponse> response) {
                if (response.body() != null) {
                    onSuccess.setValue("Berhasil");
                }
            }

            @Override
            public void onFailure(Call<TambahAssetResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    public void createAssetPembelianBaru(String date, String name, Integer value, Integer quantity) {
        if (date.equals("") || name.equals("") || value == null) {
            onError.setValue("Data tidak lengkap");
            return;
        }
        TambahAssetParams params = new TambahAssetParams(categ_id, name, value, date);

        if (selectedFile != null) {
            String imageString = ImageHelper.imageToBase64(selectedFile.getAbsolutePath());
            params.setImage(imageString);
        }

        if (selectedSupplier.getValue() == null || selectedPurchase.getValue() == null || selectedPOLines.getValue() == null || quantity == null) {
            onError.setValue("Data tidak lengkap");
            return;
        }

        params.setSupplier_id(selectedSupplier.getValue().getId());
        params.setPurchase_id(selectedPurchase.getValue().getId());
        params.setPurchase_line_id(selectedPOLines.getValue().getId());
        params.setRef(selectedPurchase.getValue().getName());
        params.setQty(quantity);

        createAsset(params);
    }

    public void createAssetSudahAda(String date, String name, Integer value, Integer usiaDepresiasi) {
        if (date.equals("") || name.equals("") || value == null) {
            onError.setValue("Data tidak lengkap");
            return;
        }
        TambahAssetParams params = new TambahAssetParams(categ_id, name, value, date);

        if (selectedFile != null) {
            String imageString = ImageHelper.imageToBase64(selectedFile.getAbsolutePath());
            params.setImage(imageString);
        }

        if (usiaDepresiasi == null) {
            onError.setValue("Data tidak lengkap");
            return;
        }
        params.setMethod_number(usiaDepresiasi);

        createAsset(params);
    }

    public void clearValue() {
        suppliers.setValue(Collections.emptyList());
        purchases.setValue(Collections.emptyList());
        poLines.setValue(Collections.emptyList());
        selectedSupplier.setValue(null);
    }
}
