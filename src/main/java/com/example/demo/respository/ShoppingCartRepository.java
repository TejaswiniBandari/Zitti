package com.example.demo.respository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.Entity.ShoppingCart;

import ch.qos.logback.core.boolex.Matcher;

public interface ShoppingCartRepository extends MongoRepository<ShoppingCart, Integer>{

	Optional<ShoppingCart> findByUserName(String userName);

	Optional<ShoppingCart> findTopByOrderByIdDesc();

	Object save(Matcher any);

}
