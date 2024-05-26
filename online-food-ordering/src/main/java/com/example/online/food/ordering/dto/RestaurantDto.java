package com.example.online.food.ordering.dto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Embeddable
public class RestaurantDto {
    Long id;
    private String title;
    @Column(length = 1000)
    private List<String> images;
    private String description;
}
