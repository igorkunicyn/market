package com.igorkunicyn.market.services;

import com.igorkunicyn.market.entities.Product;
import com.igorkunicyn.market.enums.AddProduct;
import com.igorkunicyn.market.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@SuppressWarnings("ALL")
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    public ProductRepository getProductRepo() {
        return productRepo;
    }

    public String addProduct(Product product) {
        for (Product products : productRepo.findAll()) {
            if (product.getTitle().equals(products.getTitle()) && product.getPrice() == products.getPrice()) {
                return AddProduct.PRODUCT_EXISTS.getName();
            }
        }
        productRepo.save(product);
        return AddProduct.ADD_PRODUCT.getName();
    }

    public Page<Product> findPaginated(int pageNum) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return productRepo.findAll(pageable);
    }

    public void saveProduct(Product product){
        productRepo.save(product);
    }

    public Product getProductById(long id){
        return productRepo.findProductById(id);
    }

    public void deleteProduct(long id){
        productRepo.deleteById(id);
    }


}
