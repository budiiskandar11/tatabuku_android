package com.tatabuku.app.model.pos;

import java.util.ArrayList;

public class CreateSalePosModel {
    String jsonrpc = "2.0";
    CreateSalePosParams params;

    public CreateSalePosModel(CreateSalePosParams order_line){
        params = order_line;
    }

}
