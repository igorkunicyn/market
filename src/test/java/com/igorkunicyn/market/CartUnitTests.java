package com.igorkunicyn.market;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.igorkunicyn.market.entities.Cart;
import com.igorkunicyn.market.entities.Product;
import com.igorkunicyn.market.services.CartService;
import com.igorkunicyn.market.services.ProductService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartUnitTests {

    private CartService cartService;
    private Cart cart;
    private List<Product> productList;
    private ProductService productService;

    @Before
    public void setUp(){
        cartService = new CartService();
        cart = new Cart();
        productList = new ArrayList<>(
                Arrays.asList(new Product("meat", BigDecimal.valueOf(250)),
                new Product("milk", BigDecimal.valueOf(55))));
        cart.setProductList(productList);
        productService = new ProductService();
    }

    @Test
    public void currentCart(){
        Assert.assertNotNull(cartService.getCurrentCart(new MockHttpSession()));
    }

    @Test
    public void productList(){
        Assert.assertEquals(productList, cart.getProductList());
    }

    @Test
    public void totalPrice(){
        Assert.assertEquals(BigDecimal.valueOf(305),
                new BigDecimal(String.valueOf(cart.getProductList().get(0).getPrice().add(cart.getProductList()
                .get(1).getPrice()))));
        Assert.assertNotNull(cartService.totalPriceCart(productList));
    }

    @Test
    public void addToCart(){
        Assert.assertEquals(3, cartService.addCart(new Product("fish", BigDecimal.valueOf(360))
                ,cart).getProductList().size());
    }

}
