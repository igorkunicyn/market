package com.igorkunicyn.market.repositories;

import com.igorkunicyn.market.entities.Category;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
    Category findById(long id);

}
