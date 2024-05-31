package com.example.online.food.ordering.service;

import com.example.online.food.ordering.model.IngredientCategory;
import com.example.online.food.ordering.model.IngredientsItem;

import java.util.List;

public interface IngredientService{
    IngredientCategory createIngredientsCategory(String name,Long restaurantId) throws Exception;

    IngredientCategory findIngredientCategoryById(Long id) throws Exception;

    List<IngredientCategory> findIngredientCategoryByRestaurantId(Long restaurantId) throws Exception;

    List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) throws Exception;

    IngredientsItem createIngredientItem(Long restaurantId,String ingredientName,Long categoryId) throws Exception;

    IngredientsItem updateStock(Long id) throws Exception;
}
