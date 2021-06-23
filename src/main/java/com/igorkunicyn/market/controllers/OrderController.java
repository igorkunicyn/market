package com.igorkunicyn.market.controllers;

import com.igorkunicyn.market.entities.OrderElement;
import com.igorkunicyn.market.services.OrderCartService;
import com.igorkunicyn.market.services.OrderElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {


    private OrderElementService orderElementService;
    private OrderCartService orderCartService;

    @Autowired
    public void setProductService(OrderElementService orderElementService) {
        this.orderElementService = orderElementService;
    }

    @Autowired
    public void setShopCartService(OrderCartService orderCartService) {
        this.orderCartService = orderCartService;
    }

    @GetMapping("")
    public String shopPage(Model model, HttpSession httpSession) {
        List<OrderElement> orderElementList = orderElementService.getListOrderElements(httpSession);
        double price = orderElementService.totalPriceOrder(orderElementList);
        model.addAttribute("price",price);
        model.addAttribute("orderList", orderElementList);
        return "order-page";
    }


}
