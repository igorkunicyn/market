package com.igorkunicyn.market.controllers;

import com.igorkunicyn.market.entities.Cart;
import com.igorkunicyn.market.entities.Greeting;
import com.igorkunicyn.market.entities.Message;
import com.igorkunicyn.market.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.server.standard.SpringConfigurator;
import org.thymeleaf.spring5.context.SpringContextUtils;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

@Controller
//@RequestMapping("/app")
public class ContentCartController {

//    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
//    public Greeting greeting(Message message) throws Exception {
//        System.out.println("попали в метод greeting");
////        Thread.sleep(1000); // simulated delay
////        return new Greeting(message.getNumber());
//        return new Greeting("12");
//
//    }
    private CartService cartService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(Message message) throws Exception {

        String s = String.valueOf(cartService.getCart().getTotalProducts());
        System.out.println(s);
        return new Greeting(String.valueOf(cartService.getCart().getTotalProducts()));
    }


}
