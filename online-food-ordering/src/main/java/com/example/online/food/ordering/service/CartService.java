package com.example.online.food.ordering.service;

import com.example.online.food.ordering.model.Cart;
import com.example.online.food.ordering.model.CartItem;
import com.example.online.food.ordering.request.AddCartItemRequest;

public interface CartService {

    CartItem addItemToCart(AddCartItemRequest addCartItemRequest, String jwtToken) throws Exception;

    CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;

    Cart removeItemFromCart(Long cartItemId,String jwtToken) throws Exception;

    int calculateCartTotal(Cart cart);

    Cart findCartById(Long cartId) throws Exception;

    Cart findCartByUserId(Long userId);

    Cart clearCartByUserId(Long userId);
}
