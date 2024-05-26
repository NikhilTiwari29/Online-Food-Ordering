package com.example.online.food.ordering.service;

import com.example.online.food.ordering.model.User;

public interface UserService {

    User findUserByJwtToken(String jwt);

    User findUserByEmail(String email);
}
