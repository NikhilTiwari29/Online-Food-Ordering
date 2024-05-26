package com.example.online.food.ordering.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderedItems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Foods foods;
    private int quantity;
    private Long totalPrice;
    private List<String> ingredients;
}
