package com.igorkunicyn.market.utils;

import com.igorkunicyn.market.entities.Product;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class Cart {
    private List<Product> productList;

    public Cart() {
        productList = new ArrayList<>();
    }
}
