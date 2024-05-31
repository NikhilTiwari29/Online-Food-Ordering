package com.example.online.food.ordering.repository;

import com.example.online.food.ordering.model.OrderedItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderedItems,Long> {
}
