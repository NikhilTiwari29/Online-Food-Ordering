package com.example.online.food.ordering.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Foods {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private int price;
    @ManyToOne
    private Category foodCategory;
    @Column(length = 1000)
    @ElementCollection
    private List<String> images;
    private boolean available;
    private boolean isVegetarian;
    private boolean isSeasonable;
    @ManyToOne
    private Restaurant restaurant;
    @ManyToMany
    private List<IngredientsItem> ingredientsItems = new ArrayList<>();
    private Date creationDate;
}
