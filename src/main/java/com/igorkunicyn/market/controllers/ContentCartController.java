package com.igorkunicyn.market.controllers;

import com.igorkunicyn.market.entities.Cart;
//import com.igorkunicyn.market.entities.Greeting;
//import com.igorkunicyn.market.entities.Message;
import com.igorkunicyn.market.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.server.standard.SpringConfigurator;
import org.thymeleaf.spring5.context.SpringContextUtils;


@Controller
public class ContentCartController {

    private CartService cartService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }


    @MessageMapping("/content")
    @SendTo("/topic/numberProducts")
    public Integer contentCart() throws Exception {
        Thread.sleep(1000);
//        System.out.println( "   метод greeting");
        Cart cart = cartService.getCart();
        int totalProducts;
        if (cart != null){
            totalProducts = cart.getTotalProducts();
        }else {
            totalProducts = 0;
        }
        return totalProducts;

    }


}
