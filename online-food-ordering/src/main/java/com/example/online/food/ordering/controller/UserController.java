package com.example.online.food.ordering.controller;

import com.example.online.food.ordering.jwt.JwtUtils;
import com.example.online.food.ordering.model.User;
import com.example.online.food.ordering.repository.UserRepository;
import com.example.online.food.ordering.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<User> finUserByJwtToken(@RequestHeader("Authorization")  String jwtToken){
        String userNameFromJwtToken = jwtUtils.getUserNameFromJwtToken(jwtToken);
        User userByEmail = userRepository.findUserByEmail(userNameFromJwtToken);
        return new ResponseEntity<>(userByEmail, HttpStatus.OK);
    }
}
