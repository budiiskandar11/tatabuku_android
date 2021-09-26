package com.tatabuku.app.model.pembelian;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProdukListResponse {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("prev")
    @Expose
    private String prev;
    @SerializedName("current")
    @Expose
    private Integer current;
    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("result")
    @Expose
    private List<Produk> result = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Produk> getResult() {
        return result;
    }

    public void setResult(List<Produk> result) {
        this.result = result;
    }

}

