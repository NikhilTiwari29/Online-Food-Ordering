package com.example.online.food.ordering.service;

import com.example.online.food.ordering.model.*;
import com.example.online.food.ordering.repository.AddressRepository;
import com.example.online.food.ordering.repository.OrderItemRepository;
import com.example.online.food.ordering.repository.OrderRepository;
import com.example.online.food.ordering.repository.UserRepository;
import com.example.online.food.ordering.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private CartService cartService;

    @Override
    public Orders createOrder(OrderRequest orderRequest, User user) throws Exception {
        Address deliveryAddress = orderRequest.getDeliveryAddress();
        Address savedAddress = addressRepository.save(deliveryAddress);
        if (!user.getAddresses().contains(savedAddress)){
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }
        Restaurant restaurantByUserId = restaurantService.getRestaurantByUserId(user.getId());
        Orders createdOrders = new Orders();
        createdOrders.setCustomer(user);
        createdOrders.setCreatedAt(new Date());
        createdOrders.setOrderStatus("PENDING");
        createdOrders.setDeliveryAddress(deliveryAddress);
        createdOrders.setRestaurant(restaurantByUserId);

        Cart cartByUserId = cartService.findCartByUserId(user.getId());

        List<OrderedItems> orderedItems = new ArrayList<>();

        for (CartItem cartItem : cartByUserId.getCartItems()){
            OrderedItems orderedItems1 = new OrderedItems();
            orderedItems1.setFoods(cartItem.getFoods());
            orderedItems1.setIngredients(cartItem.getIngredientsItems());
            orderedItems1.setQuantity(cartItem.getQuantity());
            orderedItems1.setTotalPrice(cartItem.getTotalPrice());
            OrderedItems saveOrderedItem = orderItemRepository.save(orderedItems1);
            orderedItems.add(saveOrderedItem);
        }

        createdOrders.setOrderedItems(orderedItems);
        createdOrders.setTotalPrice(cartService.calculateCartTotal(cartByUserId));
        Orders saveOrders = orderRepository.save(createdOrders);
        restaurantByUserId.getOrders().add(saveOrders);

        return createdOrders;
    }

    @Override
    public Orders findOrderById(Long orderId) throws Exception {
        Optional<Orders> order = orderRepository.findById(orderId);
        if (order.isEmpty()) throw new Exception("Order not found with id: " + orderId);
        return order.get();
    }

    @Override
    public Orders updateOrder(Long orderId, String orderStatus) throws Exception {
        Orders order = findOrderById(orderId);
        if (orderStatus.equals("OUT_FOR_DELIVERY") || orderStatus.equals("DELIVERED")
                || orderStatus.equals("COMPLETED") || orderStatus.equals("PENDING"))
        {
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
        }
        throw new Exception("Please select a valid order status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Orders orderById = findOrderById(orderId);
        orderRepository.delete(orderById);
    }

    @Override
    public List<Orders> getUserOrders(Long userId) {
        return orderRepository.findOrderByCustomerId(userId);
    }

    @Override
    public List<Orders> getRestaurantOrders(Long restaurantId, String orderStatus) {
        List<Orders> orderByRestaurantId = orderRepository.findOrderByRestaurantId(restaurantId);
        if (orderStatus != null){
            orderByRestaurantId = orderByRestaurantId.stream().filter(orders -> orders.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }
        return orderByRestaurantId;
    }
}
