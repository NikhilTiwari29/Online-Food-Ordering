package com.example.online.food.ordering.controller;

import com.example.online.food.ordering.dto.RestaurantDto;
import com.example.online.food.ordering.model.Restaurant;
import com.example.online.food.ordering.model.User;
import com.example.online.food.ordering.service.RestaurantService;
import com.example.online.food.ordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(
            @RequestHeader("Authorization") String jwtToken,
            @RequestParam String keyword
    ) throws Exception {
        List<Restaurant> restaurant = restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Restaurant>> getAllRestaurant(
    ) throws Exception {
        List<Restaurant> allRestaurant = restaurantService.getAllRestaurant();
        return new ResponseEntity<>(allRestaurant, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(
            @PathVariable Long id
    ) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-to-favourites")
    public ResponseEntity<RestaurantDto> addToFavorites(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwtToken
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwtToken);
        RestaurantDto restaurant = restaurantService.addFavouriteRestaurant(id,user.getId());

        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }
}
