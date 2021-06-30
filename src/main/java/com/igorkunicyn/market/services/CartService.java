package com.igorkunicyn.market.services;

import com.igorkunicyn.market.entities.Product;
import com.igorkunicyn.market.utils.Cart;
import com.igorkunicyn.market.utils.CartElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class CartService {

    private Cart cart;

    @Autowired
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Cart getCurrentCart(HttpSession session) {
        Cart cart = ( Cart ) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    public void addCart(Product product, Cart cart) {
//        int coincidenceTitle = 0;
//        for (CartElement cartElement : cart.getCartElementList()) {
         for (Product productFromList : cart.getProductList()){
             if (productFromList.getId() == product.getId()){
//            if (cartElement.getProduct().getId() == product.getId()){
//            if (cartElement.getTitle().equals(product.getTitle())) {
//                cartElement.setQuantity(cartElement.getQuantity() + 1);
                productFromList.setQuantity(productFromList.getQuantity() + 1);
                productFromList.setTotalPrice(productFromList.getTotalPrice() + product.getPrice());
//                coincidenceTitle++;
//                cart.getCartElementList().add(cartElement);
                return;
            }
        }
//        CartElement cartElement = new CartElement();
        product.setQuantity(1);
//        cartElement.setProduct(product);
//        cartElement.setQuantity(1);
        product.setTotalPrice(product.getPrice());
        cart.getProductList().add(product);
    }

    public void deleteFromCart(Cart cart, Product product) {
        for (Product productFromList : cart.getProductList()) {
            if (productFromList.getId() == product.getId()) {
                if (productFromList.getQuantity() == 1) {
                    cart.getProductList().remove(productFromList);
                    return;
                }
                productFromList.setQuantity(productFromList.getQuantity() - 1);
                productFromList.setTotalPrice(productFromList.getTotalPrice() - productFromList.getPrice());
                return;
//            if (cartElement.getProduct().getId() == product.getId()) {
//                if (cartElement.getQuantity()==1){
//                    cart.getCartElementList().remove(cartElement);
//                    return;
//                }
//                cartElement.setQuantity(cartElement.getQuantity() - 1);
//                cartElement.setTotalPrice(cartElement.getTotalPrice()- cartElement.getProduct().getPrice());
//                return;
//            }
            }
        }
    }

    public double totalPriceCart(Cart cart){
        double totalPriceCart = 0.0;
        for (Product product: cart.getProductList()) {
            totalPriceCart += product.getTotalPrice();
        }
        return totalPriceCart;
    }
}
