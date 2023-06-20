package com.springassignment.cartservice.service;

import com.springassignment.cartservice.dto.OrderRequestDto;
import com.springassignment.cartservice.model.Order;
import com.springassignment.cartservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    @Override
    public void addOrder(OrderRequestDto orderRequestDto) {
    Order order = Order.builder()
            .bookId(orderRequestDto.getBookId())
            .orderAmount(orderRequestDto.getOrderAmount())
            .build();
    orderRepository.save(order);
    }

    @Override
    public Optional<Order> getOrder(Long id) {
       return orderRepository.findById(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
