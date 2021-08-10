package com.igorkunicyn.market.repositories;

import com.igorkunicyn.market.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();

    Page<Product> findAllByCategoryId(Pageable pageable, Long id);

    @Override
    <S extends Product> S save(S s);

    Product findProductById(long id);

}
