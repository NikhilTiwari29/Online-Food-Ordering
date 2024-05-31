package com.example.online.food.ordering.controller;

import com.example.online.food.ordering.model.Foods;
import com.example.online.food.ordering.model.Restaurant;
import com.example.online.food.ordering.model.User;
import com.example.online.food.ordering.request.CreateFoodRequest;
import com.example.online.food.ordering.response.MessageResponse;
import com.example.online.food.ordering.service.FoodService;
import com.example.online.food.ordering.service.RestaurantService;
import com.example.online.food.ordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    private ResponseEntity<Foods> createFood(@RequestBody CreateFoodRequest createFoodRequest) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantByRestaurantId(createFoodRequest.getRestaurantId());
        Foods foods = foodService.createFood(createFoodRequest,createFoodRequest.getCategory(),restaurant);
        return new ResponseEntity<>(foods, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id) throws Exception {
        foodService.deleteFoodFromRestaurant(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("food deleted successfully");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Foods> updateFoodAvailabilityStatus(@PathVariable Long id) throws Exception {
        Foods foods = foodService.updateAvailabilityStatus(id);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }


}
