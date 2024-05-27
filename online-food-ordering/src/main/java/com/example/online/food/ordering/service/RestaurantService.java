package com.example.online.food.ordering.service;

import com.example.online.food.ordering.dto.RestaurantDto;
import com.example.online.food.ordering.model.Restaurant;
import com.example.online.food.ordering.model.User;
import com.example.online.food.ordering.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    Restaurant createRestaurant(CreateRestaurantRequest createRestaurantRequest, User user) throws Exception;

    Restaurant updateRestaurant(Long restaurantId,CreateRestaurantRequest updateRestaurantRequest) throws Exception;

    void deleteRestaurant(Long restaurantId) throws Exception;

    List<Restaurant> getAllRestaurant() throws Exception;

    List<Restaurant> searchRestaurant(String keyword) throws Exception;

    Restaurant findRestaurantById(Long restaurantId) throws Exception;

    Restaurant getRestaurantById(Long userId) throws Exception;

    RestaurantDto addFavouriteRestaurant(Long restaurantId,Long userId) throws Exception;

    Restaurant updateRestaurantStatus(Long restaurantId) throws Exception;

}
