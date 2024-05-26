package com.example.online.food.ordering.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ContactInformation {
    private String email;
    private String mobile;
    private String twitter;
    private String instagram;
}
