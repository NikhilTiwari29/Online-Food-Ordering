package com.example.online.food.ordering.service;

import com.example.online.food.ordering.model.IngredientCategory;
import com.example.online.food.ordering.model.IngredientsItem;
import com.example.online.food.ordering.model.Restaurant;
import com.example.online.food.ordering.repository.IngredientCategoryRepository;
import com.example.online.food.ordering.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService{

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientsCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantByRestaurantId(restaurantId);
        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setRestaurant(restaurant);
        ingredientCategory.setName(name);
        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> ingredient = ingredientCategoryRepository.findById(id);
        if (ingredient.isEmpty()){
            throw new Exception("ingredient category not found with id " + id);
        }
        return ingredient.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long restaurantId) throws Exception {
        restaurantService.findRestaurantByRestaurantId(restaurantId);
        return ingredientCategoryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantByRestaurantId(restaurantId);

        IngredientCategory ingredientCategoryById = findIngredientCategoryById(categoryId);

        IngredientsItem ingredientsItem = new IngredientsItem();
        ingredientsItem.setName(ingredientName);
        ingredientsItem.setRestaurant(restaurant);
        ingredientsItem.setIngredientCategory(ingredientCategoryById);

        IngredientsItem ingredientsItem1 = ingredientItemRepository.save(ingredientsItem);
        ingredientCategoryById.getIngredientsItems().add(ingredientsItem1);
        return ingredientsItem1;
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> ingredientItem = ingredientItemRepository.findById(id);
        if (ingredientItem.isEmpty()) throw new Exception("ingredient not found with id " + id);
        IngredientsItem ingredientsItem = ingredientItem.get();
        ingredientsItem.setStocked(!ingredientsItem.isStocked());
        return ingredientItemRepository.save(ingredientsItem);
    }
}
