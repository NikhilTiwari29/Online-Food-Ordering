package com.example.online.food.ordering.controller;

import com.example.online.food.ordering.model.Foods;
import com.example.online.food.ordering.service.FoodService;
import com.example.online.food.ordering.service.RestaurantService;
import com.example.online.food.ordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    private ResponseEntity<List<Foods>> searchFood(@RequestParam String name) throws Exception {
        List<Foods> foods = foodService.searchFood(name);
        return new ResponseEntity<>(foods, HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/restaurantId")
    private ResponseEntity<List<Foods>> getRestaurantFood(@RequestParam boolean isVegetarian,
                                                          @RequestParam boolean isNonVegetarian,
                                                          @RequestParam boolean isSeasonal,
                                                          @RequestParam(required = false) String foodCategory,
                                                          @PathVariable Long restaurantId) throws Exception {
        List<Foods> foods = foodService.getRestaurantFood(restaurantId,isVegetarian,isNonVegetarian,isSeasonal,foodCategory);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
