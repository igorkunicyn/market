package com.igorkunicyn.market.controllers;

import com.igorkunicyn.market.entities.Product;
import com.igorkunicyn.market.services.OrderCartService;
import com.igorkunicyn.market.services.ProductService;
import com.igorkunicyn.market.utils.CartElement;
import com.igorkunicyn.market.utils.OrderCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private ProductService productService;
    private OrderCartService orderCartService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setShopCartService(OrderCartService orderCartService) {
        this.orderCartService = orderCartService;
    }

    @GetMapping
    public String cartPage(Model model, HttpSession httpSession) {
        OrderCart cart = orderCartService.getCurrentCart(httpSession);
        List<CartElement> cartElementList = cart.getCartElementList();
        double price = orderCartService.totalPriceCart(cart);
        model.addAttribute("price",price);
        model.addAttribute("cartList", cartElementList);
        return "cart-page";
    }

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable("id")Long id, HttpSession httpSession){
        Product product = productService.getProductRepo().findProductById(id);
        OrderCart orderCart = orderCartService.getCurrentCart(httpSession);
        orderCartService.addCart(product, orderCart);
        return "redirect:/cart";
    }

    @GetMapping("/delete/{title}")
    public String addToCart(@PathVariable("title")String title, HttpSession httpSession){
        OrderCart orderCart = orderCartService.getCurrentCart(httpSession);
        orderCartService.deleteFromCart(orderCart, title);
        return "redirect:/cart";
    }

}
