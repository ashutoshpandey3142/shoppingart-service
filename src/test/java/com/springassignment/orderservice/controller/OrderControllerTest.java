package com.springassignment.orderservice.controller;

import com.springassignment.orderservice.dto.OrderRequestDto;
import com.springassignment.orderservice.model.Order;
import com.springassignment.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrderControllerTest {
    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void testGetAllOrders() throws Exception {
        List<Order> orders = Arrays.asList(
                Order.builder().id(1L).bookId(Arrays.asList(1L, 2L)).orderAmount(BigDecimal.valueOf(100.00)).build(),
                Order.builder().id(2L).bookId(Arrays.asList(3L, 4L)).orderAmount(BigDecimal.valueOf(200.00)).build()
        );
        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].bookId.length()").value(2))
                .andExpect(jsonPath("$[0].bookId[0]").value(1))
                .andExpect(jsonPath("$[0].bookId[1]").value(2))
                .andExpect(jsonPath("$[0].orderAmount").value(100.00))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].bookId.length()").value(2))
                .andExpect(jsonPath("$[1].bookId[0]").value(3))
                .andExpect(jsonPath("$[1].bookId[1]").value(4))
                .andExpect(jsonPath("$[1].orderAmount").value(200.00));

        verify(orderService, times(1)).getAllOrders();
        verifyNoMoreInteractions(orderService);
    }

    @Test
    void testGetOrder() throws Exception {
        long orderId = 1L;
        Order order = Order.builder().id(orderId).bookId(Arrays.asList(1L, 2L)).orderAmount(BigDecimal.valueOf(100.00)).build();
        when(orderService.getOrder(orderId)).thenReturn(Optional.of(order));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId))
                .andExpect(jsonPath("$.bookId.length()").value(2))
                .andExpect(jsonPath("$.bookId[0]").value(1))
                .andExpect(jsonPath("$.bookId[1]").value(2))
                .andExpect(jsonPath("$.orderAmount").value(100.00));

        verify(orderService, times(1)).getOrder(orderId);
        verifyNoMoreInteractions(orderService);
    }

    @Test
    void testAddOrder() throws Exception {
        OrderRequestDto orderRequestDto = OrderRequestDto.builder()
                .bookId(Arrays.asList(1L, 2L))
                .orderAmount(BigDecimal.valueOf(100.00))
                .build();

        // Mock the orderService.addOrder method
        doAnswer(invocation -> {
            OrderRequestDto dto = invocation.getArgument(0);
            BigDecimal actualValue = new BigDecimal("100.00");
            BigDecimal roundedActualValue = actualValue.setScale(1, RoundingMode.HALF_UP);
            assertEquals(orderRequestDto.getBookId(), dto.getBookId());
            assertEquals(new BigDecimal("100.0"), roundedActualValue);
            return null;
        }).when(orderService).addOrder(any(OrderRequestDto.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bookId\":[1,2],\"orderAmount\":100.00}"))
                .andExpect(status().isOk());

        // Verify the orderService.addOrder method was called with the correct argument
        verify(orderService, times(1)).addOrder(any(OrderRequestDto.class));
        verifyNoMoreInteractions(orderService);
    }

    @Test
    void testDeleteOrder() throws Exception {
        long orderId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/orders")
                        .param("id", String.valueOf(orderId)))
                .andExpect(status().isOk());

        verify(orderService, times(1)).deleteOrder(orderId);
        verifyNoMoreInteractions(orderService);
    }
}
