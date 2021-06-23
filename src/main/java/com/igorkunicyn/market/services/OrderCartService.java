package com.igorkunicyn.market.services;

import com.igorkunicyn.market.entities.Product;
import com.igorkunicyn.market.utils.CartElement;
import com.igorkunicyn.market.utils.OrderCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class OrderCartService {

    private OrderCart orderCart;

    @Autowired
    public void setShopCart(OrderCart orderCart) {
        this.orderCart = orderCart;
    }

    public OrderCart getCurrentCart(HttpSession session) {
        OrderCart cart = ( OrderCart ) session.getAttribute("cart");
        if (cart == null) {
            cart = new OrderCart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    public void addCart(Product product, OrderCart orderCart) {
//        int coincidenceTitle = 0;
        for (CartElement cartElement : orderCart.getCartElementList()) {
            if (cartElement.getTitle().equals(product.getTitle())) {
                cartElement.setQuantity(cartElement.getQuantity() + 1);
                cartElement.setTotalPrice(cartElement.getTotalPrice() + product.getPrice());
//                coincidenceTitle++;
//                orderCart.getCartElementList().add(cartElement);
                return;
            }
        }
        CartElement cartElement = new CartElement();
        cartElement.setTitle(product.getTitle());
        cartElement.setQuantity(1);
        cartElement.setPrice(product.getPrice());
        cartElement.setTotalPrice(product.getPrice());
        orderCart.getCartElementList().add(cartElement);
    }

    public void deleteFromCart(OrderCart orderCart, String title) {
        for (CartElement cartElement : orderCart.getCartElementList()) {
            if (cartElement.getTitle().equals(title)) {
                if (cartElement.getQuantity()==1){
                    orderCart.getCartElementList().remove(cartElement);
                    return;
                }
                cartElement.setQuantity(cartElement.getQuantity() - 1);
                cartElement.setTotalPrice(cartElement.getTotalPrice()- cartElement.getPrice());
                return;
            }
        }
    }

    public double totalPriceCart(OrderCart orderCart){
        double totalPriceCart = 0.0;
        for (CartElement cartElement: orderCart.getCartElementList()) {
            totalPriceCart += cartElement.getTotalPrice();
        }
        return totalPriceCart;
    }
}
