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
@RequestMapping("/api/admin")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Orders>> getRestaurantOrderHistory(@PathVariable Long id,
            @RequestParam(required = false) String orderStatus,
            @RequestHeader("Authorization") String jwtToken) throws Exception {
        User userByJwtToken = userService.findUserByJwtToken(jwtToken);
        List<Orders> order = orderService.getUserOrders(userByJwtToken.getId());
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/order/{orderId}/{orderStatus}")
    public ResponseEntity<Orders> updateOrderStatus(@PathVariable Long orderId,
                                                          @PathVariable String orderStatus,
                                                                  @RequestHeader("Authorization") String jwtToken) throws Exception {
        Orders orders = orderService.updateOrder(orderId, orderStatus);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
