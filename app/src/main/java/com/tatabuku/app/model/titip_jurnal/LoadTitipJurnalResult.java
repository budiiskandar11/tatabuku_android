package com.tatabuku.app.model.titip_jurnal;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class LoadTitipJurnalResult {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Result")
    @Expose
    private LoadTitipJurnalResult result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoadTitipJurnalResult getResult() {
        return result;
    }

    public void setResult(LoadTitipJurnalResult result) {
        this.result = result;
    }
}
