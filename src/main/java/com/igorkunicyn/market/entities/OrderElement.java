package com.igorkunicyn.market.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "orderElements")
@Data
public class OrderElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private double price;

    @Column(name = "totalPrice")
    private double totalPrice;

    @Column(name = "quantity")
    private int quantity;

    //    @ManyToOne
//    @JoinColumn(name = "order_id")
//    private Order order;
    @Column(name = "order_id")
    private Long orderId;

    public OrderElement(){}
}
