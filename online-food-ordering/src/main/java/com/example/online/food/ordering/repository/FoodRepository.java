package com.example.online.food.ordering.repository;

import com.example.online.food.ordering.model.Foods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Foods,Long> {

    List<Foods> findRestaurantById(Long restaurantId);

    @Query("select f from Foods f where f.name LIKE %:keyword% OR f.foodCategory.name LIKE %:keyword%")
    List<Foods> searchFood(@Param("keyword") String keyword);
}
