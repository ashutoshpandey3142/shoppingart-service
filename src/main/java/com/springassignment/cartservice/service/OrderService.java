package com.springassignment.cartservice.service;

import com.springassignment.cartservice.dto.OrderRequestDto;
import com.springassignment.cartservice.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
   void addOrder(OrderRequestDto orderRequestDto);
    Optional<Order> getOrder(Long id);

   List<Order> getAllOrders();
   public void deleteOrder(Long id);
}
