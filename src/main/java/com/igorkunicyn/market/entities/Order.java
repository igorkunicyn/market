package com.igorkunicyn.market.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "data")
    private Date date;

    @OneToMany
    @JoinColumn(name = "order_id")
    private List<OrderElement> orderElements;

    public Order(){}
}
