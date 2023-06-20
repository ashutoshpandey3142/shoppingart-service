package com.springassignment.orderservice.controller;

import com.springassignment.orderservice.dto.OrderRequestDto;
import com.springassignment.orderservice.model.Order;
import com.springassignment.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }
    @GetMapping("/{id}")
    public Optional<Order> getOrder(@PathVariable("id") long id){
        return orderService.getOrder(id);
    }

    @PostMapping
    public void  addOrder(@RequestBody OrderRequestDto orderRequestDto){
        orderService.addOrder(orderRequestDto);
    }
    @DeleteMapping
    public void deleteOrder(@RequestParam("id") Long id){
        orderService.deleteOrder(id);
    }
}
