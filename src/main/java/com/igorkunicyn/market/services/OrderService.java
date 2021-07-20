package com.igorkunicyn.market.services;

import com.igorkunicyn.market.entities.Order;
import com.igorkunicyn.market.entities.Product;
import com.igorkunicyn.market.entities.User;
import com.igorkunicyn.market.repositories.OrderRepository;
import com.igorkunicyn.market.entities.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OrderService {
    private CartService cartService;
    private OrderRepository orderRepository;


    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    public List<Product> getListProducts(HttpSession httpSession){
        Cart cart = cartService.getCurrentCart(httpSession);
        Order order = new Order();
        Date date = new Date();
        order.setDate(date);
        List<Product> productsList = new ArrayList<>(cart.getProductList());

        User user = (User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.addOrders(order);
        order.setNumber(numberOrder(user.getId()) + 1);
        for (Product product : productsList) {
            order.addProducts(product);
        }
//        order.setProducts(productsList);
        orderRepository.save(order);
        return productsList;
    }

    public long numberOrder(Long id){
        List<Order> orderList = orderRepository.findAllByUserIdOrderByUserId(id);
        if (orderList.isEmpty()){
            return 0;
        }
        return orderList.get(orderList.size()-1).getNumber();
    }

    public double totalPriceOrder(List<Product> productList){
        double price = 0.0;
        for (Product product: productList) {
            price += product.getTotalPrice();
        }
        return price;
    }

}
