package com.tatabuku.app.model.penjualan;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardTotalPenjualanResult {

    @SerializedName("messages")
    @Expose
    private String messages;
    @SerializedName("header")
    @Expose
    private DashboardTotalPenjualanHeader header;
    @SerializedName("sale_channel")
    @Expose
    private List<SaleChannel> saleChannel = null;
    @SerializedName("top_ten_product")
    @Expose
    private List<TopTenProduct> topTenProduct = null;

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public DashboardTotalPenjualanHeader getHeader() {
        return header;
    }

    public void setHeader(DashboardTotalPenjualanHeader header) {
        this.header = header;
    }

    public List<SaleChannel> getSaleChannel() {
        return saleChannel;
    }

    public void setSaleChannel(List<SaleChannel> saleChannel) {
        this.saleChannel = saleChannel;
    }

    public List<TopTenProduct> getTopTenProduct() {
        return topTenProduct;
    }

    public void setTopTenProduct(List<TopTenProduct> topTenProduct) {
        this.topTenProduct = topTenProduct;
    }

}