package com.igorkunicyn.market.services;

import com.igorkunicyn.market.entities.Product;
import com.igorkunicyn.market.entities.Cart;
//import com.igorkunicyn.market.utils.CartElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class CartService {

    private Cart cart;

    public Cart getCart() {
        return cart;
    }

    @Autowired
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Cart getCurrentCart(HttpSession session) {
        cart = ( Cart ) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    public void addCart(Product product, Cart cart) {
        for (Product productFromList : cart.getProductList()) {
            if (productFromList.getId() == product.getId()) {
                productFromList.setQuantity(productFromList.getQuantity() + 1);
                productFromList.setTotalPrice(productFromList.getTotalPrice() + product.getPrice());
                cart.setTotalProducts(cart.getTotalProducts() + 1);
                return;
            }
        }
        product.setQuantity(1);
        product.setTotalPrice(product.getPrice());
        cart.getProductList().add(product);
        cart.setTotalProducts(cart.getTotalProducts() + 1);

    }

    public void deleteFromCart(Cart cart, Product product) {
        for (Product productFromList : cart.getProductList()) {
            if (productFromList.getId() == product.getId()) {
                if (productFromList.getQuantity() == 1) {
                    cart.getProductList().remove(productFromList);
                    cart.setTotalProducts(cart.getTotalProducts() - 1);
                    return;
                }
                productFromList.setQuantity(productFromList.getQuantity() - 1);
                productFromList.setTotalPrice(productFromList.getTotalPrice() - productFromList.getPrice());
                cart.setTotalProducts(cart.getTotalProducts() - 1);
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

    public double totalPriceCart(Cart cart) {
        double totalPriceCart = 0.0;
        for (Product product : cart.getProductList()) {
            totalPriceCart += product.getTotalPrice();
        }
        return totalPriceCart;
    }
}
