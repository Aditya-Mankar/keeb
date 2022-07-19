package com.example.orderservice.controller;

import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/order")
public class OrderController {

    public final OrderService orderService;

    @GetMapping("/fetch-all")
    public ResponseEntity<Object> fetchAllOrders() {
        return orderService.fetchAllOrders();
    }

    @GetMapping("/fetch/{orderId}")
    public ResponseEntity<Order> fetchOrderById(@PathVariable String orderId) {
        return orderService.fetchOrderById(orderId);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateOrder(@RequestBody Order order) {
        return orderService.updateOrder(order);
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable String orderId) {
        return orderService.deleteOrder(orderId);
    }

}
