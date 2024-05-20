package com.example.online.food.ordering.model;

import com.example.online.food.ordering.dto.RestaurantDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private UserRole userRole = UserRole.ROLE_CUSTOMER;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
    @JsonIgnore
    private List<Orders> orders = new ArrayList<>();
    @ElementCollection
    private List<RestaurantDto> favouriteRestaurant = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();
}
