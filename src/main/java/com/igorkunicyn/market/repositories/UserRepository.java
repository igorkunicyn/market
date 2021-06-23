package com.igorkunicyn.market.repositories;

import com.igorkunicyn.market.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findAll();

    User findByUsername(String username);

    User findById(Long id);

    void deleteById(Long id);


}
