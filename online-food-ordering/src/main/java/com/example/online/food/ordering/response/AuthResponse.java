package com.example.online.food.ordering.response;

import com.example.online.food.ordering.model.UserRole;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String username;
    private List<UserRole> roles;
    private String jwtToken;
    private String message;
}
