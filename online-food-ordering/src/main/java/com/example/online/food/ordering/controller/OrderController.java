package com.example.online.food.ordering.controller;

import com.example.online.food.ordering.model.Orders;
import com.example.online.food.ordering.model.User;
import com.example.online.food.ordering.request.OrderRequest;
import com.example.online.food.ordering.service.OrderService;
import com.example.online.food.ordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @PostMapping("/order")
    public ResponseEntity<Orders> createOrder(@RequestBody OrderRequest orderRequest,
                                              @RequestHeader("Authorization") String jwtToken) throws Exception {
        User userByJwtToken = userService.findUserByJwtToken(jwtToken);
        Orders order = orderService.createOrder(orderRequest, userByJwtToken);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Orders>> getOrderHistory(@RequestHeader("Authorization") String jwtToken) throws Exception {
        User userByJwtToken = userService.findUserByJwtToken(jwtToken);
        List<Orders> order = orderService.getUserOrders(userByJwtToken.getId());
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
