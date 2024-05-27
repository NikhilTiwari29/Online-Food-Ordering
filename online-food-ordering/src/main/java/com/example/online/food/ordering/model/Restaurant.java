package com.example.online.food.ordering.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private User owner;
    private String name;
    private String description;
<<<<<<< HEAD
    private String cuisine;
=======
    private String cuisineType;
>>>>>>> master
    @OneToOne
    private Address address;
    @Embedded
    private ContactInformation contactInformation;
    private String openingHours;
    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Orders> orders = new ArrayList<>();
    @Column(length = 1000)
    @ElementCollection
    private List<String> images;
    private LocalDateTime registrationDate;
    private boolean open;
    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Foods> foods = new ArrayList<>();
}
