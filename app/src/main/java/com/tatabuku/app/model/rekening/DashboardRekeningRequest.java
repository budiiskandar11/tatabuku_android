package com.tatabuku.app.model.rekening;

public class DashboardRekeningRequest {
    private String jsonrpc = "2.0";
    private DashboardRekeningParams params;

    public DashboardRekeningRequest() {
        params = new DashboardRekeningParams();
    }
}
