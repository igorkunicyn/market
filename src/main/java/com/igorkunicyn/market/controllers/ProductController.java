package com.igorkunicyn.market.controllers;

import com.igorkunicyn.market.entities.Product;
import com.igorkunicyn.market.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(path = "/showProductById", method = RequestMethod.GET)
    @ResponseBody
    public String showProductById(@RequestParam int id) {
        Product product = productService.getProductRepo().findProductById(id);
        if (product != null) {
            return String.format("id: %s, title: %s, cost: %s", product.getId(), product.getTitle(), product.getPrice());
        }
        return "Product with id = " + id + " not exists";
    }

    @GetMapping("/listProducts")
    public String listProducts(Model uiModel) {
        return pageProducts(1,uiModel);
    }

    @GetMapping(value = "/page/{pageNum}")
    public String pageProducts(@PathVariable(name = "pageNum") int pageNum, Model uiModel) {
        Page<Product> productPage = productService.findPaginated(pageNum);
        List<Product> productList = productPage.getContent();
        uiModel.addAttribute("currentPage", pageNum);
        uiModel.addAttribute("totalPages", productPage.getTotalPages());
        uiModel.addAttribute("totalItems", productPage.getTotalElements());
        uiModel.addAttribute("listProducts", productList);
        return "list-product-user";

    }


    @GetMapping("/new")
    public String createNewBook(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "new-product";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveBook(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/product/listProducts";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditBook(@PathVariable(name = "id") int id) {
        ModelAndView modelAndView = new ModelAndView("edit-product");
        Product product = productService.getProductById(id);
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @RequestMapping("/delete/{id}")
    public String deleteBook(@PathVariable(name = "id") int id) {
        productService.deleteProduct(id);
        return "redirect:/product/listProducts";

    }

}
