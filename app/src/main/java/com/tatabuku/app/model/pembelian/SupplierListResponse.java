package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
/*
{
    "count": 2,
    "prev": "",
    "current": 1,
    "next": "",mo
    "total_pages": 1,
    "result": [
        {
            "id": 11,
            "name": "Supplier 1",
            "phone": false,
            "street": false,
            "street2": false,
            "city": false,
            "purchase_order_total": 10000.0,
            "purchase_order_bulan": 0.0,
            "purchase_order_hari": 0.0,
            "dp_total_hari" : 0.0,
            "dp_total_bulan" : 0.0,
            "dp_total" : 0.0,
            "payment_total": 0.0,
            "payment_bulan": 0.0,
            "payment_hari": 0.0,
            "hutang_total": 0.0,
            "hutang_bulan": 0.0,
            "hutang_hari": 0.0,
            "debit_limit": 0.0
        }
    ]
}
 */
public class SupplierListResponse {

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
    private List<SupplierResult> result = null;

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

    public List<SupplierResult> getResult() {
        return result;
    }

    public void setResult(List<SupplierResult> result) {
        this.result = result;
    }

}