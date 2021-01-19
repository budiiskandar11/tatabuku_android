package com.tatabuku.app.model.rekening;

import java.util.List;

          import com.google.gson.annotations.Expose;
          import com.google.gson.annotations.SerializedName;

public class PindahBukuResult {

    @SerializedName("header_info")
    @Expose
    private PindahBukuHeader headerInfo;
    @SerializedName("bank_list")
    @Expose
    private List<PindahBukuBank> bankList = null;
    @SerializedName("Message")
    @Expose
    private String message;

    public PindahBukuHeader getHeaderInfo() {
        return headerInfo;
    }

    public void setHeaderInfo(PindahBukuHeader headerInfo) {
        this.headerInfo = headerInfo;
    }

    public List<PindahBukuBank> getBankList() {
        return bankList;
    }

    public void setBankList(List<PindahBukuBank> bankList) {
        this.bankList = bankList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}