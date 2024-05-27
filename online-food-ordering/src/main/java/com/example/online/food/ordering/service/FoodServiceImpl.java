package com.example.online.food.ordering.service;

import com.example.online.food.ordering.model.Category;
import com.example.online.food.ordering.model.Foods;
import com.example.online.food.ordering.model.Restaurant;
import com.example.online.food.ordering.repository.FoodRepository;
import com.example.online.food.ordering.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FoodServiceImpl implements FoodService{

    @Autowired
    private FoodRepository foodRepository;
    @Override
    public Foods createFood(CreateFoodRequest createFoodRequest, Category category, Restaurant restaurant) {
        Foods foods = new Foods();
        foods.setFoodCategory(category);
        foods.setRestaurant(restaurant);
        foods.setDescription(createFoodRequest.getDescription());
        foods.setImages(createFoodRequest.getImages());
        foods.setName(createFoodRequest.getName());
        foods.setPrice(createFoodRequest.getPrice());
        foods.setIngredientsItems(createFoodRequest.getIngredients());
        foods.setSeasonable(createFoodRequest.isSeasonal());
        foods.setVegetarian(createFoodRequest.isVegetarian());
        Foods savedFoods = foodRepository.save(foods);
        restaurant.getFoods().add(savedFoods);
        return savedFoods;
    }

    @Override
    public void deleteFoodFromRestaurant(Long foodId) throws Exception {
        Foods foods = findFoodById(foodId);
        foods.setRestaurant(null);
        foodRepository.save(foods);
    }

    @Override
    public List<Foods> getRestaurantFood(Long restaurantId, boolean isVegetarian, boolean isNonVeg, boolean isSeasonal, String foodCategory) {
        List<Foods> foods = foodRepository.findRestaurantById(restaurantId);
        if (isVegetarian){
            filterFoodsByVegetarian(foods);
        }
        if (isNonVeg){
            filterFoodsByNonVeg(foods);
        }
        if (isSeasonal){
            filterFoodsByIsSeasonal(foods);
        }
        if (foodCategory != null && !foodCategory.equals("")){
            filterByCategory(foods,foodCategory);
        }
        return List.of();
    }

    private List<Foods> filterFoodsByVegetarian(List<Foods> foods) {
        return foods.stream().filter(food -> food.isVegetarian() == true).collect(Collectors.toList());
    }

    private List<Foods> filterFoodsByNonVeg(List<Foods> foods) {
        return foods.stream().filter(food -> food.isVegetarian() == false).collect(Collectors.toList());
    }

    private List<Foods> filterFoodsByIsSeasonal(List<Foods> foods) {
        return foods.stream().filter(food -> food.isSeasonable() == true).collect(Collectors.toList());
    }

    private List<Foods> filterByCategory(List<Foods> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if (food.getFoodCategory() != null){
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Foods> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Foods findFoodById(Long foodId) throws Exception {
        Optional<Foods> foods = foodRepository.findById(foodId);
        if (foods.isEmpty()){
            throw new Exception("food doesn't exist with foodId " + foodId);
        }
        return foods.get();
    }

    @Override
    public Foods updateAvailabilityStatus(Long foodId) {
        Foods foods = new Foods();
        foods.setAvailable(!foods.isAvailable());
        return foodRepository.save(foods);
    }
}
