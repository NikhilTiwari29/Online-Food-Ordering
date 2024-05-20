package com.example.online.food.ordering.response;

import com.example.online.food.ordering.model.UserRole;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private UserRole userRole;
}
