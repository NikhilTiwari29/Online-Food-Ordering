package com.example.online.food.ordering.service;

import com.example.online.food.ordering.model.Orders;
import com.example.online.food.ordering.model.User;
import com.example.online.food.ordering.request.OrderRequest;

import java.util.List;

public interface OrderService {

    Orders createOrder(OrderRequest orderRequest, User user) throws Exception;

    Orders findOrderById(Long orderId) throws Exception;

    Orders updateOrder(Long orderId, String orderStatus) throws Exception;

    void cancelOrder(Long orderId) throws Exception;

    List<Orders> getUserOrders(Long userId);

    List<Orders> getRestaurantOrders(Long restaurantId,String orderStatus);
}
