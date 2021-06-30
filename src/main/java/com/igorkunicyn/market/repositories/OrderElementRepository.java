package com.igorkunicyn.market.repositories;

import com.igorkunicyn.market.entities.OrderElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderElementRepository extends JpaRepository<OrderElement,Long> {
    OrderElement save(OrderElement o);

    List<OrderElement> findAllByOrderId(Long id);
}
