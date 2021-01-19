
package com.tatabuku.app.model.pembelian;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderListResponse {

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
    private List<OrderResult> result = null;

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

    public List<OrderResult> getResult() {
        return result;
    }

    public void setResult(List<OrderResult> result) {
        this.result = result;
    }

}

