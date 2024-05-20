package com.example.online.food.ordering.repository;

import com.example.online.food.ordering.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
