package com.tatabuku.app.model.titip_jurnal;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class LoadTitipJurnalModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("due_date")
    @Expose
    private String dueDate;
    @SerializedName("sisa_waktu")
    @Expose
    private Double sisaWaktu;
    @SerializedName("nilai")
    @Expose
    private Double nilai;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("number")
    @Expose
    private Boolean number;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Double getSisaWaktu() {
        return sisaWaktu;
    }

    public void setSisaWaktu(Double sisaWaktu) {
        this.sisaWaktu = sisaWaktu;
    }

    public Double getNilai() {
        return nilai;
    }

    public void setNilai(Double nilai) {
        this.nilai = nilai;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Boolean getNumber() {
        return number;
    }

    public void setNumber(Boolean number) {
        this.number = number;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}