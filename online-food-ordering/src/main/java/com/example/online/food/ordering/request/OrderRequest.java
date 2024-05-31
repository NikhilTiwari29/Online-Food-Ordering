package com.example.online.food.ordering.request;

import com.example.online.food.ordering.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    Long restaurantId;
    Address deliveryAddress;
}
