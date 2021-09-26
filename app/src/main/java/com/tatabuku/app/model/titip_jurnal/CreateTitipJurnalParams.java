package com.tatabuku.app.model.titip_jurnal;

public class CreateTitipJurnalParams {
    private String date;
    private int amount;
    private String description;
    private String image;

    public CreateTitipJurnalParams(String date, int amount, String description) {
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
