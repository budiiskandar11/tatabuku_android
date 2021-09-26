package com.tatabuku.app.ui.pembelian.tambah.produk;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.pembelian.AddProdukModel;
import com.tatabuku.app.model.pembelian.Category;
import com.tatabuku.app.model.pembelian.CategoryListResponse;
import com.tatabuku.app.model.pembelian.CityData;
import com.tatabuku.app.model.pembelian.DefaultResponse;
import com.tatabuku.app.model.pembelian.EditProdukModel;
import com.tatabuku.app.model.pembelian.EditProdukParam;
import com.tatabuku.app.model.pembelian.LoadProductData;
import com.tatabuku.app.model.pembelian.LoadProductResponse;
import com.tatabuku.app.model.pembelian.ProductIdPostModel;
import com.tatabuku.app.model.pembelian.SubmitOrderResponse;
import com.tatabuku.app.model.penjualan.LoadCustomerResponse;
import com.tatabuku.app.model.penjualan.PartnerIdPostModel;
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.util.ImageHelper;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahProdukViewModel extends ViewModel {
    private MutableLiveData<String> onError;
    private MutableLiveData<String> onSuccess;
    private MutableLiveData<List<Category>> categories;
    private MutableLiveData<Category> selectedCategory;
    private MutableLiveData<LoadProductData> productData;

    public MutableLiveData<String> getOnError() {
        return onError;
    }

    public MutableLiveData<String> getOnSuccess() {
        return onSuccess;
    }

    public MutableLiveData<List<Category>> getCategories() {
        return categories;
    }

    public MutableLiveData<Category> getSelectedCategory() {
        return selectedCategory;
    }

    public MutableLiveData<LoadProductData> getProductData() {
        return productData;
    }

    public TambahProdukViewModel() {
        onError = new MutableLiveData<>();
        onSuccess = new MutableLiveData<>();
        categories = new MutableLiveData<>();
        selectedCategory = new MutableLiveData<>();
        productData = new MutableLiveData<>();
    }

    public void fetchCategory() {
        Call<CategoryListResponse> call = ConnectionManager.getInstance().getService().getCategory();
        call.enqueue(new Callback<CategoryListResponse>() {
            @Override
            public void onResponse(Call<CategoryListResponse> call, Response<CategoryListResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        categories.setValue(response.body().getResult());
                        if (response.body().getResult().size() > 0) {
                            selectedCategory.setValue(response.body().getResult().get(0));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryListResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    public void saveProduk(int productId, String name, String sku, String description, String minStock, String maxStock, String hargaBeli, String hargaJual, File imageFile) {
        if (name.equals("") || description.equals("") || minStock.equals("") || maxStock.equals("") || hargaBeli.equals("") || hargaJual.equals("") || selectedCategory.getValue() == null) {
            onError.setValue("Seluruh data harus diisi");
        } else {
            int minStockValue = Integer.parseInt(minStock);
            int maxStockValue = Integer.parseInt(maxStock);
            int buyPrice = Integer.parseInt(hargaBeli);
            int sellPrice = Integer.parseInt(hargaJual);
            if(productId ==0) {
                AddProdukModel model = new AddProdukModel(name, sku, description, sellPrice, buyPrice, minStockValue, maxStockValue, selectedCategory.getValue().getId());
                if (imageFile != null) {
                    String imageString = ImageHelper.imageToBase64(imageFile.getAbsolutePath());
                    model.getParams().getData().setImage(imageString);
                }
                addProduk(model);
            } else {
                EditProdukParam param = new EditProdukParam(productId,name,sku,description,sellPrice,buyPrice,minStockValue,maxStockValue,selectedCategory.getValue().getId());
                EditProdukModel model = new EditProdukModel(param);
                editProduk(model);
            }
        }
    }

    private void addProduk(AddProdukModel model) {
        Call<DefaultResponse> call = ConnectionManager.getInstance().getService().addProduk(model);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        onSuccess.setValue("");
                        return;
                    }
                }
                onError.setValue("Gagal menyimpan produk");
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    private void editProduk(EditProdukModel model) {
        Call<SubmitOrderResponse> call = ConnectionManager.getInstance().getService().updateProductData(model);
        call.enqueue(new Callback<SubmitOrderResponse>() {
            @Override
            public void onResponse(Call<SubmitOrderResponse> call, Response<SubmitOrderResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        onSuccess.setValue("");
                        return;
                    }
                }
                onError.setValue("Gagal menyimpan produk");
            }

            @Override
            public void onFailure(Call<SubmitOrderResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    public void loadProduct(int productId) {
        ProductIdPostModel model = new ProductIdPostModel(productId);
        Call<LoadProductResponse> call = ConnectionManager.getInstance().getService().getProductData(model);
        call.enqueue(new Callback<LoadProductResponse>() {
            @Override
            public void onResponse(Call<LoadProductResponse> call, Response<LoadProductResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        productData.setValue(response.body().getResult().getProductData());

                        if (response.body().getResult().getProductData().getCategId().getId() != null) {
                            Category category = new Category();
                            category.setName(response.body().getResult().getProductData().getCategId().getName());
                            category.setId(response.body().getResult().getProductData().getCategId().getId());
                            selectedCategory.setValue(category);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoadProductResponse> call, Throwable t) {

            }
        });
    }
}
