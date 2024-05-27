package com.example.online.food.ordering.service;

import com.example.online.food.ordering.dto.RestaurantDto;
import com.example.online.food.ordering.model.Address;
import com.example.online.food.ordering.model.Restaurant;
import com.example.online.food.ordering.model.User;
import com.example.online.food.ordering.repository.AddressRepository;
import com.example.online.food.ordering.repository.RestaurantRepository;
import com.example.online.food.ordering.repository.UserRepository;
import com.example.online.food.ordering.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest createRestaurantRequest, User user) throws Exception {
        Address address = addressRepository.save(createRestaurantRequest.getAddress());
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(createRestaurantRequest.getAddress());
        restaurant.setContactInformation(createRestaurantRequest.getContactInformation());
        restaurant.setCuisineType(createRestaurantRequest.getCuisineType());
        restaurant.setDescription(createRestaurantRequest.getDescription());
        restaurant.setImages(createRestaurantRequest.getImages());
        restaurant.setName(createRestaurantRequest.getName());
        restaurant.setOpeningHours(createRestaurantRequest.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurantRequest) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        if (restaurant.getCuisineType() != null){
            restaurant.setCuisineType(updateRestaurantRequest.getCuisineType());
        }
        if (restaurant.getDescription() != null){
            restaurant.setDescription(updateRestaurantRequest.getDescription());
        }
        if (restaurant.getName() != null){
            restaurant.setName(updateRestaurantRequest.getName());
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant()  {
        List<Restaurant> allRestaurant = restaurantRepository.findAll();
        return allRestaurant;
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        List<Restaurant> restaurants = restaurantRepository.findBySearchQuery(keyword);
        return restaurants;
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) throws Exception {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isEmpty()){
            throw new Exception("Restaurant not found with restaurantId: " + restaurantId);
        }
        return restaurant.get();
    }

    @Override
    public Restaurant getRestaurantById(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if (restaurant == null){
            throw new Exception("Restaurant not found with userId: " + userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addFavouriteRestaurant(Long restaurantId, Long userId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            throw new Exception("User not found with id: " + userId);
        }

        User user = userOptional.get();
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setId(restaurantId);
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setTitle(restaurant.getName());

        boolean isFavourite = false;
        List<RestaurantDto> favouriteRestaurants = user.getFavouriteRestaurant();
        for (RestaurantDto favourite : favouriteRestaurants){
            if (favourite.getId().equals(restaurantId)){
                isFavourite = true;
                break;
            }
        }
        if (isFavourite){
            favouriteRestaurants.removeIf(favourite -> favourite.getId().equals(restaurantId));
        }
        else {
            favouriteRestaurants.add(restaurantDto);
        }

        userRepository.save(user);
        return restaurantDto;
    }


    @Override
    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
