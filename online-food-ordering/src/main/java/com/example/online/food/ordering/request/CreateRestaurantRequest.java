package com.example.online.food.ordering.request;

import com.example.online.food.ordering.model.Address;
import com.example.online.food.ordering.model.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class CreateRestaurantRequest {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private List<String> images;
    private String openingHours;
}
