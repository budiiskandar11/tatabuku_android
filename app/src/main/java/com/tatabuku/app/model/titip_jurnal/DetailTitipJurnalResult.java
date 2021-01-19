        package com.tatabuku.app.model.titip_jurnal;

        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class DetailTitipJurnalResult {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("total_quota")
    @Expose
    private DetailTitipJurnalHeader totalQuota;
    @SerializedName("list_selesai")
    @Expose
    private List<DetailTitipJurnalModel> listSelesai = null;
    @SerializedName("list_belum_selesai")
    @Expose
    private List<DetailTitipJurnalModel> listBelumSelesai = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DetailTitipJurnalHeader getTotalQuota() {
        return totalQuota;
    }

    public void setTotalQuota(DetailTitipJurnalHeader totalQuota) {
        this.totalQuota = totalQuota;
    }

    public List<DetailTitipJurnalModel> getListSelesai() {
        return listSelesai;
    }

    public void setListSelesai(List<DetailTitipJurnalModel> listSelesai) {
        this.listSelesai = listSelesai;
    }

    public List<DetailTitipJurnalModel> getListBelumSelesai() {
        return listBelumSelesai;
    }

    public void setListBelumSelesai(List<DetailTitipJurnalModel> listBelumSelesai) {
        this.listBelumSelesai = listBelumSelesai;
    }
}


