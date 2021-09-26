package com.tatabuku.app.model.pos;

import java.util.ArrayList;

public class CreateSalePosParams {
    private ArrayList<OrderLineModel> order_line;

    public CreateSalePosParams(ArrayList<OrderLineModel> order_line){
        this.order_line = order_line;
    }

}
