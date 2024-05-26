package com.example.online.food.ordering.service;

import com.example.online.food.ordering.jwt.JwtUtils;
import com.example.online.food.ordering.model.User;
import com.example.online.food.ordering.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;
    @Override
    public User findUserByJwtToken(String jwt) {
        String userNameFromJwtToken = jwtUtils.getUserNameFromJwtToken(jwt);
        User userByEmail = findUserByEmail(userNameFromJwtToken);
        return userByEmail;
    }

    @Override
    public User findUserByEmail(String email) {
        User userByEmail = userRepository.findUserByEmail(email);
        return userByEmail;
    }
}
