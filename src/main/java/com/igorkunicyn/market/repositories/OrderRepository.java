package com.igorkunicyn.market.repositories;

import com.igorkunicyn.market.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Order save(Order o);

    List<Order> findAllByUserIdOrderByNumber(Long userId);

}
