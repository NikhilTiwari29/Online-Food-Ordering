package com.example.online.food.ordering.service;

import com.example.online.food.ordering.model.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(String name,Long userId) throws Exception;

    List<Category> findCategoryByRestaurantId(Long userId) throws Exception;

    Category findCategoryById(Long categoryId) throws Exception;

}
