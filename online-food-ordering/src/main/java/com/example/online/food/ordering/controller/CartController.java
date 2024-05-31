package com.example.online.food.ordering.controller;

import com.example.online.food.ordering.model.Cart;
import com.example.online.food.ordering.model.CartItem;
import com.example.online.food.ordering.model.User;
import com.example.online.food.ordering.request.AddCartItemRequest;
import com.example.online.food.ordering.request.UpdateCartItemRequest;
import com.example.online.food.ordering.service.CartService;
import com.example.online.food.ordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest addCartItemRequest,
                                                  @RequestHeader("Authorization") String jwtToken) throws Exception {
        CartItem cartItem = cartService.addItemToCart(addCartItemRequest, jwtToken);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartItemRequest updateCartItemRequest) throws Exception {
        CartItem cartItem = cartService.updateCartItemQuantity(updateCartItemRequest.getCartItemId(), updateCartItemRequest.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeCartItem(@PathVariable Long id,
                                                           @RequestHeader("Authorization") String jwtToken) throws Exception {
        Cart cart = cartService.removeItemFromCart(id, jwtToken);
        return new ResponseEntity<>(cart,HttpStatus.OK);
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwtToken) throws Exception {
        User userByJwtToken = userService.findUserByJwtToken(jwtToken);
        Cart cart = cartService.clearCartByUserId(userByJwtToken.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwtToken) throws Exception {
        User userByJwtToken = userService.findUserByJwtToken(jwtToken);
        Cart cart = cartService.findCartByUserId(userByJwtToken.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
