package com.example.online.food.ordering.repository;

import com.example.online.food.ordering.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,Long> {

    List<Orders> findOrderByCustomerId(Long userId);

    List<Orders> findOrderByRestaurantId(Long restaurantId);
}
