package com.example.online.food.ordering.repository;

import com.example.online.food.ordering.model.Address;
import com.example.online.food.ordering.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
