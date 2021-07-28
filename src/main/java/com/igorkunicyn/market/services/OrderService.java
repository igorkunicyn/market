package com.igorkunicyn.market.services;

import com.igorkunicyn.market.entities.Order;
import com.igorkunicyn.market.entities.Product;
import com.igorkunicyn.market.entities.User;
import com.igorkunicyn.market.repositories.OrderRepository;
import com.igorkunicyn.market.entities.Cart;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OrderService {
    private CartService cartService;
    private OrderRepository orderRepository;
    private Order order;


    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    public Order getOrder() {
        return order;
    }

//    @Autowired
//    public void setOrder(Order order) {
//        this.order = order;
//    }

    public List<Product> getListProducts(HttpSession httpSession){
        Cart cart = cartService.getCurrentCart(httpSession);
//        order = new Order();
//        Date date = new Date();
//        order.setDate(date);
//        List<Product> productsList = new ArrayList<>(cart.getProductList());
//
//        User user = (User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        user.addOrders(order);
//        order.setNumber(numberOrder(user.getId()) + 1);
//        for (Product product : productsList) {
//            order.addProducts(product);
//        }
//        orderRepository.save(order);
        return cart.getProductList();
    }

    public Order createOrder(HttpSession httpSession){
        Cart cart = cartService.getCurrentCart(httpSession);
        order = new Order();
        Date date = new Date();
        order.setDate(date);
        List<Product> productsList = new ArrayList<>(cart.getProductList());

        User user = (User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.addOrders(order);
        order.setNumber(numberOrder(user.getId()) + 1);
        for (Product product : productsList) {
            order.addProducts(product);
        }
        return order;
    }

    public long numberOrder(Long id){
        List<Order> orderList = orderRepository.findAllByUserIdOrderByNumber(id);
        if (orderList.isEmpty()){
            return 0;
        }
        return orderList.get(orderList.size()-1).getNumber();
    }

        public BigDecimal totalPriceOrder(List<Product> productList){
        BigDecimal price = new BigDecimal("0");
        for (Product product: productList) {
            price.add(product.getTotalPrice());
        }
        return price;
    }

}
