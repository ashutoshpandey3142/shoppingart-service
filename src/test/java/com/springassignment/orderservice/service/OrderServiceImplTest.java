package com.springassignment.orderservice.service;

import com.springassignment.orderservice.dto.OrderRequestDto;
import com.springassignment.orderservice.model.Order;
import com.springassignment.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderServiceImpl(orderRepository);
    }

    @Test
    void testAddOrder() {
        // Arrange
        List<Long> bookIds = List.of(1L, 2L, 3L);
        BigDecimal orderAmount = BigDecimal.valueOf(100.0);
        OrderRequestDto orderRequestDto = new OrderRequestDto(bookIds, orderAmount);
        Order expectedOrder = Order.builder()
                .bookId(bookIds)
                .orderAmount(orderAmount)
                .build();

        // Act
        orderService.addOrder(orderRequestDto);

        // Assert
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testGetOrder() {
        // Arrange
        Long orderId = 1L;
        List<Long> bookIds = List.of(1L, 2L, 3L);
        BigDecimal orderAmount = BigDecimal.valueOf(100.0);
        Order expectedOrder = Order.builder()
                .id(orderId)
                .bookId(bookIds)
                .orderAmount(orderAmount)
                .build();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(expectedOrder));

        // Act
        Optional<Order> result = orderService.getOrder(orderId);

        // Assert
        assertEquals(Optional.of(expectedOrder), result);
    }

    @Test
    void testGetAllOrders() {
        // Arrange
        List<Order> orders = new ArrayList<>();
        List<Long> bookIds1 = List.of(1L, 2L, 3L);
        BigDecimal orderAmount1 = BigDecimal.valueOf(100.0);
        Order order1 = Order.builder()
                .bookId(bookIds1)
                .orderAmount(orderAmount1)
                .build();
        List<Long> bookIds2 = List.of(4L, 5L, 6L);
        BigDecimal orderAmount2 = BigDecimal.valueOf(200.0);
        Order order2 = Order.builder()
                .bookId(bookIds2)
                .orderAmount(orderAmount2)
                .build();
        orders.add(order1);
        orders.add(order2);
        when(orderRepository.findAll()).thenReturn(orders);

        // Act
        List<Order> result = orderService.getAllOrders();

        // Assert
        assertEquals(orders, result);
    }

    @Test
    void testDeleteOrder() {
        // Arrange
        Long orderId = 1L;

        // Act
        orderService.deleteOrder(orderId);

        // Assert
        verify(orderRepository, times(1)).deleteById(orderId);
    }
}
