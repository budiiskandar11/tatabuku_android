package com.tatabuku.app.model.titip_jurnal;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class DetailTitipJurnalHeader {

    @SerializedName("quota_journal_titipan")
    @Expose
    private Double quotaJournalTitipan;
    @SerializedName("quota_terpakai")
    @Expose
    private Double quotaTerpakai;
    @SerializedName("quota_sisa")
    @Expose
    private Double quotaSisa;

    public Double getQuotaJournalTitipan() {
        return quotaJournalTitipan;
    }

    public void setQuotaJournalTitipan(Double quotaJournalTitipan) {
        this.quotaJournalTitipan = quotaJournalTitipan;
    }

    public Double getQuotaTerpakai() {
        return quotaTerpakai;
    }

    public void setQuotaTerpakai(Double quotaTerpakai) {
        this.quotaTerpakai = quotaTerpakai;
    }

    public Double getQuotaSisa() {
        return quotaSisa;
    }

    public void setQuotaSisa(Double quotaSisa) {
        this.quotaSisa = quotaSisa;
    }

}



