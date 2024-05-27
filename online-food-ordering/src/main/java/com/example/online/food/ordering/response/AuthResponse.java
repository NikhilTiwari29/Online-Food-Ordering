package com.example.online.food.ordering.response;

import com.example.online.food.ordering.model.UserRole;
<<<<<<< HEAD
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private UserRole userRole;
=======
import lombok.AllArgsConstructor;
import lombok.Data;
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
>>>>>>> master
}
