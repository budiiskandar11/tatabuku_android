package com.tatabuku.app.model.pembelian;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
{
    "count": 2,
    "prev": "",
    "current": 1,
    "next": "",
    "total_pages": 1,
    "result": [
        {
            "total_hutang_tahun": 0.0,
            "total_hutang_bulan": 0.0,
            "total_hutang_hari": 0.0,
            "total_purchase_tahun": 10000.0,
            "total_purchase_bulan": 0.0,
            "total_purchase_hari": 0.0,
            "total_dp_tahun": 10000.0,
            "total_dp_bulan": 0.0,
            "total_dp_hari": 0.0,
            "total_payment_tahun": 0.0,
            "total_payment_bulan": 0.0,
            "total_payment_hari": 0.0
        }
    ]
}
 */

public class DashboardSupplierResponse {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("prev")
    @Expose
    private Integer prev;
    @SerializedName("current")
    @Expose
    private Integer current;
    @SerializedName("next")
    @Expose
    private Integer next;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("result")
    @Expose
    private List<DashboardSupplierResult> result = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPrev() {
        return prev;
    }

    public void setPrev(Integer prev) {
        this.prev = prev;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<DashboardSupplierResult> getResult() {
        return result;
    }

    public void setResult(List<DashboardSupplierResult> result) {
        this.result = result;
    }

}