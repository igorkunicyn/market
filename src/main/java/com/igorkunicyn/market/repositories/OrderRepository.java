package com.igorkunicyn.market.repositories;

import com.igorkunicyn.market.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Order save(Order o);
}
