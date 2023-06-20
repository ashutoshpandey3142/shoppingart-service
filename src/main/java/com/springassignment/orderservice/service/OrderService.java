package com.springassignment.orderservice.service;

import com.springassignment.orderservice.dto.OrderRequestDto;
import com.springassignment.orderservice.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
   void addOrder(OrderRequestDto orderRequestDto);
    Optional<Order> getOrder(Long id);

   List<Order> getAllOrders();
   public void deleteOrder(Long id);
}
