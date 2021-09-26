package com.tatabuku.app.model.rekening;

import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class DashboardRekeningResult {

    @SerializedName("header_info")
    @Expose
    private DashboardRekeningHeader headerInfo;
    @SerializedName("bank_list")
    @Expose
    private List<DashboardRekeningModel> bankList = null;
    @SerializedName("Message")
    @Expose
    private String message;

    public DashboardRekeningHeader getHeaderInfo() {
        return headerInfo;
    }

    public void setHeaderInfo(DashboardRekeningHeader headerInfo) {
        this.headerInfo = headerInfo;
    }

    public List<DashboardRekeningModel> getBankList() {
        return bankList;
    }

    public void setBankList(List<DashboardRekeningModel> bankList) {
        this.bankList = bankList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

