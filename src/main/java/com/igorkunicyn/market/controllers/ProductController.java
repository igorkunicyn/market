package com.igorkunicyn.market.controllers;

import com.igorkunicyn.market.entities.Category;
import com.igorkunicyn.market.entities.Product;
import com.igorkunicyn.market.services.CategoryService;
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
    private CategoryService categoryService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public  void setCategoryService(CategoryService categoryService){this.categoryService = categoryService;}

    @RequestMapping(path = "/showProductById", method = RequestMethod.GET)
    @ResponseBody
    public String showProductById(@RequestParam int id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return String.format("id: %s, title: %s, cost: %s", product.getId(), product.getTitleRussian(), product.getPrice());
        }
        return "Product with id = " + id + " not exists";
    }

    @GetMapping("/list/{categoryId}")
    public String createListProductsFromCategory(@PathVariable(name = "categoryId")long categoryId,
                                                 Model model) {
        return pageProducts(1,model,categoryId);
    }

//    @GetMapping(value = "/page/{pageNum}")
//    public String pageProducts(@PathVariable(name = "pageNum") int pageNum, Model uiModel) {
//        Page<Product> productPage = productService.findPaginated(pageNum);
//        List<Product> productList = productPage.getContent();
//        uiModel.addAttribute("currentPage", pageNum);
//        uiModel.addAttribute("totalPages", productPage.getTotalPages());
//        uiModel.addAttribute("totalItems", productPage.getTotalElements());
//        uiModel.addAttribute("listProducts", productList);
//        return "product/list";
//
//    }
    @GetMapping(value = "/list/{categoryId}/page/{pageNum}")
    public String pageProducts(@PathVariable(name = "pageNum") int pageNum, Model model,
                               @PathVariable(name = "categoryId") long categoryId) {
//    @GetMapping(value = "/page/{pageNum}")
//    public String pageProducts(@PathVariable(name = "pageNum") int pageNum, Model model, long id) {
        Page<Product> productPage = productService.findPaginated(pageNum, categoryId);
        List<Product> productList = productPage.getContent();
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("listProducts", productList);
        model.addAttribute("id",categoryId);
        return "product/list";

    }

    @GetMapping("/list/{categoryId}/new")
//    @GetMapping("/new")
    public String createNewProduct(@PathVariable(name = "categoryId")long categoryId, Model model) {
        Product product = new Product();
        Category category = categoryService.getCategoryById(categoryId);
        model.addAttribute("category", category);
        model.addAttribute("product", product);
        return "product/new";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("product") Product product){
        productService.saveProduct(product);
        String path = "/product/list/" + product.getNumberOfCategory();
        return "redirect:" + path;
//        return "redirect:/product/listProducts/";
    }

    @RequestMapping("/list/{categoryId}/edit/{productId}")
    public ModelAndView showEditProduct(@PathVariable(name = "categoryId")long categoryId,
                                        @PathVariable(name = "productId") int productId) {
        ModelAndView modelAndView = new ModelAndView("product/edit");
        Product product = productService.getProductById(productId);
        Category category = categoryService.getCategoryById(categoryId);
        modelAndView.addObject("category", category);
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @RequestMapping("/list/{categoryId}/delete/{productId}")
    public String deleteProduct(@PathVariable(name = "categoryId") long categoryId,
                                @PathVariable(name = "productId") long productId) {
        productService.deleteProduct(categoryId, productId);
        String path = "/product/list/" + categoryId;
        return "redirect:" + path;
//        return "redirect:/product/list";

    }

}
