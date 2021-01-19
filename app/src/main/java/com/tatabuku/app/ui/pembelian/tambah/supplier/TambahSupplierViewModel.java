package com.tatabuku.app.ui.pembelian.tambah.supplier;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.pembelian.AddSupplierModel;
import com.tatabuku.app.model.pembelian.CityData;
import com.tatabuku.app.model.pembelian.CityDataResponse;
import com.tatabuku.app.model.pembelian.DefaultResponse;
import com.tatabuku.app.model.pembelian.PaymentTerm;
import com.tatabuku.app.model.pembelian.PaymentTermResponse;
import com.tatabuku.app.model.pembelian.ProvinceData;
import com.tatabuku.app.model.pembelian.ProvinceDataResponse;
import com.tatabuku.app.model.pembelian.SubmitOrderResponse;
import com.tatabuku.app.model.penjualan.EditCustomerModel;
import com.tatabuku.app.model.penjualan.EditCustomerParam;
import com.tatabuku.app.model.penjualan.LoadCustomerData;
import com.tatabuku.app.model.penjualan.LoadCustomerResponse;
import com.tatabuku.app.model.penjualan.PartnerIdPostModel;
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.util.ImageHelper;

import java.io.File;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahSupplierViewModel extends ViewModel {
    private MutableLiveData<PaymentTerm> selectedTerm;
    private MutableLiveData<List<PaymentTerm>> paymentTerms;
    private MutableLiveData<List<ProvinceData>> provinceList;
    private MutableLiveData<ProvinceData> selectedProvince;
    private MutableLiveData<List<CityData>> cityList;
    private MutableLiveData<CityData> selectedCity;
    private MutableLiveData<String> onError;
    private MutableLiveData<String> onSuccess;
    private MutableLiveData<LoadCustomerData> supplierData;

    public MutableLiveData<List<PaymentTerm>> getPaymentTerms() {
        return paymentTerms;
    }

    public MutableLiveData<PaymentTerm> getSelectedTerm() {
        return selectedTerm;
    }

    public MutableLiveData<String> getOnError() {
        return onError;
    }

    public MutableLiveData<String> getOnSuccess() {
        return onSuccess;
    }

    public MutableLiveData<List<ProvinceData>> getProvinceList() {
        return provinceList;
    }

    public MutableLiveData<List<CityData>> getCityList() {
        return cityList;
    }

    public MutableLiveData<ProvinceData> getSelectedProvince() {
        return selectedProvince;
    }

    public MutableLiveData<CityData> getSelectedCity() {
        return selectedCity;
    }

    public MutableLiveData<LoadCustomerData> getSupplierData() {
        return supplierData;
    }

    public TambahSupplierViewModel() {
        paymentTerms = new MutableLiveData<>(Collections.emptyList());
        selectedTerm = new MutableLiveData<>();
        selectedCity = new MutableLiveData<>();
        selectedProvince = new MutableLiveData<>();
        onError = new MutableLiveData<>();
        onSuccess = new MutableLiveData<>();
        supplierData = new MutableLiveData<>();
        provinceList = new MutableLiveData<>(Collections.emptyList());
        cityList = new MutableLiveData<>(Collections.emptyList());
    }

    public void saveSupplier(String name, String address, String email, String phone, String limit, String npwp, String status_pajak, File imageFile) {
        if (name.equals("") || address.equals("") || address.equals("") || email.equals("") || phone.equals("") || limit.equals("") || status_pajak.equals("") || selectedTerm.getValue() == null || selectedCity.getValue() == null || selectedProvince.getValue() == null) {
            onError.setValue("Seluruh data harus diisi");
        } else if (status_pajak.equalsIgnoreCase("pkp") && npwp.equals("")) {
            onError.setValue("Seluruh data harus diisi");
        } else {
            String cleanString = limit.replaceAll("[Rp,.]", "").replaceAll(" ", "").trim();
            Double limitDouble = Double.parseDouble(cleanString);
            AddSupplierModel model = new AddSupplierModel(name, address, selectedCity.getValue().getName(), selectedCity.getValue().getId() + "", selectedProvince.getValue().getId() + "", email, phone, limitDouble, selectedTerm.getValue().getId(), npwp, status_pajak);
            if (imageFile != null) {
                String imageString = ImageHelper.imageToBase64(imageFile.getAbsolutePath());
                model.getParams().getData().setImage(imageString);
            }
            addSupplier(model);
        }
    }

    public void editSupplier(int partnerId, String name, String address, String email, String phone, String limit, String npwp, String status_pajak, File imageFile) {
        if (name.equals("") || address.equals("") || address.equals("") || email.equals("") || phone.equals("") || limit.equals("") || status_pajak.equals("") || selectedTerm.getValue() == null || selectedCity.getValue() == null || selectedProvince.getValue() == null) {
            onError.setValue("Seluruh data harus diisi");
        } else if (status_pajak.equalsIgnoreCase("pkp") && npwp.equals("")) {
            onError.setValue("Seluruh data harus diisi");
        } else {
            String cleanString = limit.replaceAll("[Rp,.]", "").replaceAll(" ", "").trim();
            Double limitDouble = Double.parseDouble(cleanString);
            EditCustomerParam param = new EditCustomerParam(partnerId, name, address, "", email, phone, 0.0, limitDouble, selectedTerm.getValue().getId(), npwp, status_pajak, selectedProvince.getValue().getId() + "", selectedCity.getValue().getId() + "", null, null);
            if (imageFile != null) {
                String imageString = ImageHelper.imageToBase64(imageFile.getAbsolutePath());
                param.setImage(imageString);
            } else {
                param.setImage(supplierData.getValue().getImage());
            }
            editSupplier(new EditCustomerModel(param));
        }
    }

    public void fetchProvince() {
        Call<ProvinceDataResponse> call = ConnectionManager.getInstance().getService().getProvinceList();
        call.enqueue(new Callback<ProvinceDataResponse>() {
            @Override
            public void onResponse(Call<ProvinceDataResponse> call, Response<ProvinceDataResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        provinceList.setValue(response.body().getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<ProvinceDataResponse> call, Throwable t) {

            }
        });
    }

    public void fetchCity(int id) {
        String query = "{id,name,state_id{name}}";
        String filter = "[[\"state_id.id\",\"=\",\"" + id + "\"]]";
        Call<CityDataResponse> call = ConnectionManager.getInstance().getService().getCityList(query, filter);
        call.enqueue(new Callback<CityDataResponse>() {
            @Override
            public void onResponse(Call<CityDataResponse> call, Response<CityDataResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        cityList.setValue(response.body().getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<CityDataResponse> call, Throwable t) {

            }
        });
    }

    public void fetchPaymentTerms() {
        Call<PaymentTermResponse> call = ConnectionManager.getInstance().getService().getPaymentTerm();
        call.enqueue(new Callback<PaymentTermResponse>() {
            @Override
            public void onResponse(Call<PaymentTermResponse> call, Response<PaymentTermResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        paymentTerms.setValue(response.body().getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentTermResponse> call, Throwable t) {

            }
        });
    }

    public void fetchSupplierData(int partnerId) {
        PartnerIdPostModel model = new PartnerIdPostModel(partnerId);
        Call<LoadCustomerResponse> call = ConnectionManager.getInstance().getService().getCustomerData(model);
        call.enqueue(new Callback<LoadCustomerResponse>() {
            @Override
            public void onResponse(Call<LoadCustomerResponse> call, Response<LoadCustomerResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        supplierData.setValue(response.body().getResult().getPartnerDate());

                        if (response.body().getResult().getPartnerDate().getStateId().getId() != null) {
                            ProvinceData provinceData = new ProvinceData();
                            provinceData.setName(response.body().getResult().getPartnerDate().getStateId().getName());
                            provinceData.setId(response.body().getResult().getPartnerDate().getStateId().getId());
                            selectedProvince.setValue(provinceData);
                        }

                        if (response.body().getResult().getPartnerDate().getCityId().getId() != null) {
                            CityData cityData = new CityData();
                            cityData.setName(response.body().getResult().getPartnerDate().getCityId().getName());
                            cityData.setId(response.body().getResult().getPartnerDate().getCityId().getId());
                            selectedCity.setValue(cityData);
                        }

                        if (response.body().getResult().getPartnerDate().getPropertyPaymentTermId().getId() != null) {
                            PaymentTerm paymentTerm = new PaymentTerm();
                            paymentTerm.setName(response.body().getResult().getPartnerDate().getPropertyPaymentTermId().getName());
                            paymentTerm.setId(response.body().getResult().getPartnerDate().getPropertyPaymentTermId().getId());
                            selectedTerm.setValue(paymentTerm);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoadCustomerResponse> call, Throwable t) {

            }
        });
    }

    private void addSupplier(AddSupplierModel model) {
        Call<DefaultResponse> call = ConnectionManager.getInstance().getService().addSupplier(model);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        onSuccess.setValue("");
                        return;
                    }
                }
                onError.setValue("Gagal menyimpan supplier");
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    private void editSupplier(EditCustomerModel model) {
        Call<SubmitOrderResponse> call = ConnectionManager.getInstance().getService().updateCustomerData(model);
        call.enqueue(new Callback<SubmitOrderResponse>() {
            @Override
            public void onResponse(Call<SubmitOrderResponse> call, Response<SubmitOrderResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        onSuccess.setValue("");
                        return;
                    }
                }
                onError.setValue("Gagal menyimpan supplier");
            }

            @Override
            public void onFailure(Call<SubmitOrderResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }
}
