package com.tatabuku.app.model.titip_jurnal;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class CreateTitipJurnalResponse {

        @SerializedName("jsonrpc")
        @Expose
        private String jsonrpc;
        @SerializedName("id")
        @Expose
        private Object id;
        @SerializedName("result")
        @Expose
        private CreateTitipJurnalResult result;

        public String getJsonrpc() {
            return jsonrpc;
        }

        public void setJsonrpc(String jsonrpc) {
            this.jsonrpc = jsonrpc;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public CreateTitipJurnalResult getResult() {
            return result;
        }

        public void setResult(CreateTitipJurnalResult result) {
            this.result = result;
        }

    }
