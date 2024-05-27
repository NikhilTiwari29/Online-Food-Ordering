package com.example.online.food.ordering.service;

import com.example.online.food.ordering.model.Category;
import com.example.online.food.ordering.model.Foods;
import com.example.online.food.ordering.model.Restaurant;
import com.example.online.food.ordering.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    Foods createFood(CreateFoodRequest createFoodRequest, Category category, Restaurant restaurant);

    void deleteFood(Long foodId);

    List<Foods> getRestaurantFood(Long restaurantId,
                                         boolean isVegetarian,boolean isNonVeg,boolean isSeasonal,String foodCategory);

    List<Foods> searchFood(String keyword);

    Foods findFoodById(Long foodId);

    Foods updateAvailabilityStatus(Long foodId);

}
