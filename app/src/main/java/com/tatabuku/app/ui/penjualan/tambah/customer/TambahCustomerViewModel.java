package com.tatabuku.app.ui.penjualan.tambah.customer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tatabuku.app.model.pembelian.CityData;
import com.tatabuku.app.model.pembelian.CityDataResponse;
import com.tatabuku.app.model.pembelian.PaymentTerm;
import com.tatabuku.app.model.pembelian.PaymentTermResponse;
import com.tatabuku.app.model.pembelian.ProvinceData;
import com.tatabuku.app.model.pembelian.ProvinceDataResponse;
import com.tatabuku.app.model.pembelian.SubdistrictData;
import com.tatabuku.app.model.pembelian.SubdistrictDataResponse;
import com.tatabuku.app.model.pembelian.SubmitOrderResponse;
import com.tatabuku.app.model.penjualan.AddCustomerModel;
import com.tatabuku.app.model.penjualan.AddCustomerParam;
import com.tatabuku.app.model.penjualan.CreateCustomerResponse;
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

public class TambahCustomerViewModel extends ViewModel {
    private MutableLiveData<PaymentTerm> selectedTerm;
    private MutableLiveData<List<PaymentTerm>> paymentTerms;
    private MutableLiveData<List<ProvinceData>> provinceList;
    private MutableLiveData<ProvinceData> selectedProvince;
    private MutableLiveData<List<CityData>> cityList;
    private MutableLiveData<CityData> selectedCity;
    private MutableLiveData<List<SubdistrictData>> subdistrictList;
    private MutableLiveData<SubdistrictData> selectedSubdistrict;
    private MutableLiveData<LoadCustomerData> customerData;
    private MutableLiveData<String> onError;
    private MutableLiveData<String> onSuccess;

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

    public MutableLiveData<List<SubdistrictData>> getSubdistrictList() {
        return subdistrictList;
    }

    public MutableLiveData<SubdistrictData> getSelectedSubdistrict() {
        return selectedSubdistrict;
    }

    public MutableLiveData<LoadCustomerData> getCustomerData() {
        return customerData;
    }

    public TambahCustomerViewModel() {
        paymentTerms = new MutableLiveData<>(Collections.emptyList());
        selectedTerm = new MutableLiveData<>();
        selectedCity = new MutableLiveData<>();
        selectedProvince = new MutableLiveData<>();
        selectedSubdistrict = new MutableLiveData<>();
        customerData = new MutableLiveData<>();
        onError = new MutableLiveData<>();
        onSuccess = new MutableLiveData<>();
        provinceList = new MutableLiveData<>(Collections.emptyList());
        cityList = new MutableLiveData<>(Collections.emptyList());
        subdistrictList = new MutableLiveData<>(Collections.emptyList());
    }

    public void saveCustomer(String name, String address, String email, String phone, String limit, String npwp, String status_pajak, String postalCode, File imageFile) {
        if (name.equals("") || address.equals("") || address.equals("") || email.equals("") ||
                phone.equals("") || limit.equals("") || status_pajak.equals("") || selectedTerm.getValue() == null ||
                selectedSubdistrict.getValue() == null || selectedCity.getValue() == null || selectedProvince.getValue() == null) {
            onError.setValue("Seluruh data harus diisi");
        } else if (status_pajak.equalsIgnoreCase("pkp") && npwp.equals("")) {
            onError.setValue("Seluruh data harus diisi");
        } else {
            String cleanString = limit.replaceAll("[Rp,.]", "").replaceAll(" ", "").trim();
            Double limitDouble = Double.parseDouble(cleanString);

            AddCustomerParam param = new AddCustomerParam(name, address, "", email, phone, limitDouble,
                    selectedTerm.getValue().getId(), npwp, status_pajak,
                    selectedProvince.getValue().getId() + "", selectedCity.getValue().getId() + "",
                    selectedSubdistrict.getValue().getId() + "", postalCode);
            if (imageFile != null) {
                String imageString = ImageHelper.imageToBase64(imageFile.getAbsolutePath());
                param.setImage(imageString);
            }

            AddCustomerModel model = new AddCustomerModel(param);
            addCustomer(model);
        }
    }

    public void editCustomer(int partnerId, String name, String address, String email, String phone, String limit, String npwp, String status_pajak, String postalCode, File imageFile) {
        if (name.equals("") || address.equals("") || address.equals("") || email.equals("") ||
                phone.equals("") || limit.equals("") || status_pajak.equals("") || selectedTerm.getValue() == null ||
                selectedSubdistrict.getValue() == null || selectedCity.getValue() == null || selectedProvince.getValue() == null) {
            onError.setValue("Seluruh data harus diisi");
        } else if (status_pajak.equalsIgnoreCase("pkp") && npwp.equals("")) {
            onError.setValue("Seluruh data harus diisi");
        } else {
            String cleanString = limit.replaceAll("[Rp,.]", "").replaceAll(" ", "").trim();
            Double limitDouble = Double.parseDouble(cleanString);

            EditCustomerParam param = new EditCustomerParam(partnerId, name, address, "", email, phone, limitDouble, 0.0, selectedTerm.getValue().getId(), npwp, status_pajak, selectedProvince.getValue().getId() + "", selectedCity.getValue().getId() + "", selectedSubdistrict.getValue().getId() + "", postalCode);
            if (imageFile != null) {
                String imageString = ImageHelper.imageToBase64(imageFile.getAbsolutePath());
                param.setImage(imageString);
            } else {
                param.setImage(customerData.getValue().getImage());
            }
            editCustomer(new EditCustomerModel(param));
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

    public void fetchCity(String code) {
        String query = "{id,name}";
        String filter = "[[\"state_id.code\",\"=\",\"" + code + "\"]]";
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

    public void fetchSubdisctrict(String id) {
        String query = "{id,name}";
        String filter = "[[\"city_id.id\",\"=\",\"" + id + "\"]]";
        Call<SubdistrictDataResponse> call = ConnectionManager.getInstance().getService().getKecamatanList(query, filter);
        call.enqueue(new Callback<SubdistrictDataResponse>() {
            @Override
            public void onResponse(Call<SubdistrictDataResponse> call, Response<SubdistrictDataResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        subdistrictList.setValue(response.body().getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<SubdistrictDataResponse> call, Throwable t) {

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

    public void fetchCustomerData(int partnerId) {
        PartnerIdPostModel model = new PartnerIdPostModel(partnerId);
        Call<LoadCustomerResponse> call = ConnectionManager.getInstance().getService().getCustomerData(model);
        call.enqueue(new Callback<LoadCustomerResponse>() {
            @Override
            public void onResponse(Call<LoadCustomerResponse> call, Response<LoadCustomerResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null) {
                        customerData.setValue(response.body().getResult().getPartnerDate());

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

                        if (response.body().getResult().getPartnerDate().getSubdistrictId().getId() != null) {
                            SubdistrictData subdistrictData = new SubdistrictData();
                            subdistrictData.setName(response.body().getResult().getPartnerDate().getSubdistrictId().getName());
                            subdistrictData.setId(response.body().getResult().getPartnerDate().getSubdistrictId().getId());
                            selectedSubdistrict.setValue(subdistrictData);
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

    private void addCustomer(AddCustomerModel model) {
        Call<CreateCustomerResponse> call = ConnectionManager.getInstance().getService().addCustomer(model);
        call.enqueue(new Callback<CreateCustomerResponse>() {
            @Override
            public void onResponse(Call<CreateCustomerResponse> call, Response<CreateCustomerResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() != null && response.body().getResult().getResult() != null) {
                        onSuccess.setValue("");
                        return;
                    }
                }
                onError.setValue("Gagal menyimpan customer");
            }

            @Override
            public void onFailure(Call<CreateCustomerResponse> call, Throwable t) {
                onError.setValue("Terjadi kesalahan");
            }
        });
    }

    private void editCustomer(EditCustomerModel model) {
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
