package com.igorkunicyn.market.entities;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title_english")
    private String titleEnglish;

    @Column(name = "title_Russian")
    private String titleRussian;

    @Column(name = "price")
    private double price;

    @Transient
    private long quantity;

    @Transient
    private double totalPrice;

//    @Transient
//    @ManyToMany(mappedBy = "products")
//    private List<Order> orders;

    @ManyToMany(mappedBy = "products")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Order> orders = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<ProductImage> images = new ArrayList<>();

    @Transient
    private long numberOfCategory;

    public void addOrders(Order order){
        orders.add(order);
        order.getProducts().add(this);
    }

    public void removeOrders(Order order){
        orders.remove(order);
        order.getProducts().remove(this);
    }

    public void addImages(ProductImage image){
        images.add(image);
        image.setProduct(this);
    }

    public void removeImages(ProductImage image){
        images.remove(image);
        image.setProduct(null);
    }

    public Product() {
    }
}
