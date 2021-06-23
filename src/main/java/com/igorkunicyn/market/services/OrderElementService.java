package com.igorkunicyn.market.services;

import com.igorkunicyn.market.entities.Order;
import com.igorkunicyn.market.entities.OrderElement;
import com.igorkunicyn.market.repositories.OrderElementRepository;
import com.igorkunicyn.market.repositories.OrderRepository;
import com.igorkunicyn.market.utils.CartElement;
import com.igorkunicyn.market.utils.OrderCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderElementService {
    private OrderCartService orderCartService;
    private OrderElementRepository orderElementRepository;
    private OrderRepository orderRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setOrderElementRepository(OrderElementRepository orderElementRepository) {
        this.orderElementRepository = orderElementRepository;
    }

    @Autowired
    public void setShopCartService(OrderCartService orderCartService) {
        this.orderCartService = orderCartService;
    }

    public List<OrderElement> getListOrderElements(HttpSession httpSession){
        OrderCart orderCart = orderCartService.getCurrentCart(httpSession);
        Order order = new Order();
        Date date = new Date();
        order.setDate(date);
        List<OrderElement> orderElementList = new ArrayList<>();
        for (CartElement cartElement: orderCart.getCartElementList()) {
            OrderElement orderElement = new OrderElement();
//            orderElement.setOrder(order);
            orderElement.setTitle(cartElement.getTitle());
            orderElement.setPrice(cartElement.getPrice());
            orderElement.setTotalPrice(cartElement.getTotalPrice());
            orderElement.setQuantity(cartElement.getQuantity());
            orderElementRepository.save(orderElement);
            orderElementList.add(orderElement);
        }
        order.setOrderElements(orderElementList);
        orderRepository.save(order);
        return orderElementRepository.findAllByOrderId(order.getId());
    }

    public double totalPriceOrder(List<OrderElement> orderElementList){
        double price = 0.0;
        for (OrderElement orderElement: orderElementList) {
            price += orderElement.getTotalPrice();
        }
        return price;
    }
}
