package com.tatabuku.app.ui.titip_journal.tambah;

import com.tatabuku.app.model.titip_jurnal.CreateTitipJurnalParams;
import com.tatabuku.app.model.titip_jurnal.CreateTitipJurnalRequest;
import com.tatabuku.app.model.titip_jurnal.CreateTitipJurnalResponse;
import com.tatabuku.app.service.ConnectionManager;
import com.tatabuku.app.util.ImageHelper;

import java.io.File;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahTitipJurnalViewModel extends ViewModel {
    private MutableLiveData<String> onSuccess;
    private MutableLiveData<String> onError;

    private File selectedFile = null;

    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    public MutableLiveData<String> getOnSuccess() {
        return onSuccess;
    }

    public MutableLiveData<String> getOnError() {
        return onError;
    }

    public TambahTitipJurnalViewModel() {
        onSuccess = new MutableLiveData<>();
        onError = new MutableLiveData<>();

    }

    public void createTitipJurnal(String date, int amount, String description) {
        if (date.equals("") || description.equals("")) {
            onError.setValue("Data tidak lengkap");
            return;
        }

        CreateTitipJurnalParams params = new CreateTitipJurnalParams(date, amount, description);
        if (selectedFile != null) {
            String imageString = ImageHelper.imageToBase64(selectedFile.getAbsolutePath());
            params.setImage(imageString);
        }
        CreateTitipJurnalRequest request = new CreateTitipJurnalRequest(params);
        Call<CreateTitipJurnalResponse> call = ConnectionManager.getInstance().getService().createTitipJurnal(request);
        call.enqueue(new Callback<CreateTitipJurnalResponse>() {
            @Override
            public void onResponse(Call<CreateTitipJurnalResponse> call, Response<CreateTitipJurnalResponse> response) {
                if (response.body() != null) {
                    onSuccess.setValue("Berhasil");
                }
            }

            @Override
            public void onFailure(Call<CreateTitipJurnalResponse> call, Throwable t) {
                    onError.setValue("Terjadi Kesalahan");
            }
        });

    }

}
