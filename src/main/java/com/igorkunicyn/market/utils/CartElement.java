package com.igorkunicyn.market.utils;

import lombok.Data;

@Data
public class CartElement {
    private String title;
    private int quantity;
    private Double price;
    private Double totalPrice;

    public CartElement(){}


}
