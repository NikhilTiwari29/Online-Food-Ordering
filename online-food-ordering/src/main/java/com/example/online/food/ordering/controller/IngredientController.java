package com.example.online.food.ordering.controller;

import com.example.online.food.ordering.model.IngredientCategory;
import com.example.online.food.ordering.model.IngredientsItem;
import com.example.online.food.ordering.request.IngredientCategoryRequest;
import com.example.online.food.ordering.request.IngredientItemRequest;
import com.example.online.food.ordering.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest ingredientCategoryRequest
            ) throws Exception {
        IngredientCategory ingredientsCategory = ingredientService.
                createIngredientsCategory(ingredientCategoryRequest.getName(), ingredientCategoryRequest.getRestaurantId());
        return new ResponseEntity<>(ingredientsCategory, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<IngredientsItem> createIngredientItem(
            @RequestBody IngredientItemRequest ingredientItemRequest
    ) throws Exception {
        IngredientsItem ingredientItem = ingredientService.
                createIngredientItem(ingredientItemRequest.getRestaurantId(), ingredientItemRequest.getName(),
                        ingredientItemRequest.getCategoryId());

        return new ResponseEntity<>(ingredientItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<IngredientsItem> updateIngredientStock(
            @PathVariable Long id
    ) throws Exception {
        IngredientsItem ingredientItem = ingredientService.updateStock(id);
        return new ResponseEntity<>(ingredientItem, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredient(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientsItem> restaurantsIngredients = ingredientService.findRestaurantsIngredients(id);
        return new ResponseEntity<>(restaurantsIngredients, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientCategory> ingredientCategoryByRestaurantId = ingredientService.findIngredientCategoryByRestaurantId(id);
        return new ResponseEntity<>(ingredientCategoryByRestaurantId, HttpStatus.OK);
    }
}
