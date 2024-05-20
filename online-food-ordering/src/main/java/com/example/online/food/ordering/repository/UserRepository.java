package com.example.online.food.ordering.repository;

import com.example.online.food.ordering.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findUserByEmail(String userName);
}
