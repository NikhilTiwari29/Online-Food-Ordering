package com.example.online.food.ordering.service;

import com.example.online.food.ordering.model.Cart;
import com.example.online.food.ordering.model.CartItem;
import com.example.online.food.ordering.model.Foods;
import com.example.online.food.ordering.model.User;
import com.example.online.food.ordering.repository.CartItemRepository;
import com.example.online.food.ordering.repository.CartRepository;
import com.example.online.food.ordering.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest addCartItemRequest, String jwtToken) throws Exception {
        User user = userService.findUserByJwtToken(jwtToken);
        Foods foods = foodService.findFoodById(addCartItemRequest.getFoodId());
        Cart cart = cartRepository.findByCustomerId(user.getId());
        for (CartItem cartItem : cart.getCartItems()){
            if (cartItem.getFoods().equals(foods)){
                int newQuantity = cartItem.getQuantity() + addCartItemRequest.getQuantity();
                return updateCartItemQuantity(cartItem.getId(),newQuantity);
            }
        }
        CartItem newCartItem = new CartItem();
        newCartItem.setFoods(foods);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(addCartItemRequest.getQuantity());
        newCartItem.setIngredientsItems(addCartItemRequest.getIngredients());
        newCartItem.setTotalPrice(addCartItemRequest.getQuantity() * foods.getPrice());

        CartItem savedCartItem = cartItemRepository.save(newCartItem);
        cart.getCartItems().add(savedCartItem);
        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem.isEmpty()) throw new Exception("cart item not found with the id: " + cartItemId);
        CartItem cartItem1 = cartItem.get();
        cartItem1.setQuantity(quantity);
        cartItem1.setTotalPrice(cartItem1.getFoods().getPrice() * quantity);
        return cartItemRepository.save(cartItem1);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwtToken) throws Exception {
        User user = userService.findUserByJwtToken(jwtToken);
        Cart cart = cartRepository.findByCustomerId(user.getId());
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem.isEmpty()) throw new Exception("cart item not found with the id: " + cartItemId);
        CartItem cartItem1 = cartItem.get();
        cart.getCartItems().remove(cartItem1);
        return cartRepository.save(cart);
    }

    @Override
    public int calculateCartTotal(Cart cart) {
        int total = 0;
        for (CartItem cartItem : cart.getCartItems()){
            total += cartItem.getFoods().getPrice() * cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long cartId) throws Exception {
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (cart.isEmpty()) throw new Exception("cart not found with the id: " + cartId);
        return cart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) {
        Cart cart = cartRepository.findByCustomerId(userId);
        return cart;
    }

    @Override
    public Cart clearCartByUserId(Long userId) {
        Cart cartByUserId = findCartByUserId(userId);
        cartByUserId.getCartItems().clear();
        return cartRepository.save(cartByUserId);
    }
}
