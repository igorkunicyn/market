package com.igorkunicyn.market.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class OrderCart {
    private List<CartElement> cartElementList;
    private Double totalPrice;

    public OrderCart() {
        cartElementList = new ArrayList<>();
        totalPrice = 0.0;
    }
}
