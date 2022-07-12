package com.example.orderservice.service;

import com.example.orderservice.configuration.InventoryFeignClient;
import com.example.orderservice.exception.BadRequestException;
import com.example.orderservice.exception.CustomException;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.ProductInventory;
import com.example.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryFeignClient inventoryFeignClient;

    public ResponseEntity<Object> fetchAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return ResponseEntity.ok(orders);
    }

    public ResponseEntity<String> createOrder(Order order) {
        try {
            List<Long> productIds = order.getProducts().stream()
                    .map(ProductInventory::getProductId)
                    .collect(Collectors.toList());

            ResponseEntity<List<ProductInventory>> response = inventoryFeignClient.fetchInventory(productIds);
            List<ProductInventory> inventoryList = response.getBody();

            order.getProducts().forEach(product -> {
                if(product.getQuantity() < 1)
                    throw new BadRequestException("Invalid quantity for product with id: " + product.getProductId());

                Optional<ProductInventory> inventory = inventoryList.stream()
                        .filter(inv -> inv.getProductId().equals(product.getProductId()))
                        .findAny();

                inventory.ifPresent(inv -> {
                    if(inv.getQuantity() == 0)
                        throw new CustomException("No stock for product with id: " + product.getProductId());

                    if(inv.getQuantity() < product.getQuantity())
                        throw new CustomException("Not enough stock for product with id: " + product.getProductId());

                    inv.setQuantity(inv.getQuantity() - product.getQuantity());
                });
            });

            inventoryFeignClient.updateInventory(inventoryList);

            // TODO: Add order for user in user service
            // TODO: Send notification to user as order placed using notification service

            orderRepository.save(order);

            return ResponseEntity.ok("Order created");
        } catch (BadRequestException bre) {
            return ResponseEntity.badRequest().body(bre.getErrorMessage());
        } catch (CustomException ce) {
            return ResponseEntity.badRequest().body(ce.getErrorMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    public ResponseEntity<String> deleteOrder(String orderId) {
        orderRepository.deleteById(orderId);

        return ResponseEntity.ok("Order deleted");
    }
}
