package com.igorkunicyn.market.utils;

import com.igorkunicyn.market.entities.Product;
import lombok.Data;

@Data
public class CartElement {
    private Product product;
    private Double totalPrice;

    public CartElement(){}


}
