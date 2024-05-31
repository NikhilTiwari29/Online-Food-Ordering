package com.example.online.food.ordering.repository;

import com.example.online.food.ordering.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByRestaurantId(Long id);
}
