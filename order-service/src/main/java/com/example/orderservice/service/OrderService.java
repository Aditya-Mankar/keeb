package com.example.orderservice.service;

import com.example.orderservice.configuration.InventoryFeignClient;
import com.example.orderservice.configuration.UserFeignClient;
import com.example.orderservice.exception.BadRequestException;
import com.example.orderservice.exception.CustomException;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.ProductInventory;
import com.example.orderservice.model.User;
import com.example.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryFeignClient inventoryFeignClient;
    private final UserFeignClient userFeignClient;
    private final KafkaTemplate<String, Order> kafkaTemplate;

    public ResponseEntity<Object> fetchAllOrders() {
        List<Order> orders = orderRepository.findAll();

        log.info("Fetching all orders");

        return ResponseEntity.ok(orders);
    }

    public ResponseEntity<Order> fetchOrderById(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);

        log.info("Fetching order by id: " + orderId);

        return ResponseEntity.ok(order.orElse(null));
    }

    public ResponseEntity<Object> createOrder(Order order) {
        try {
            List<Long> productIds = order.getProducts().stream()
                    .map(ProductInventory::getProductId)
                    .collect(Collectors.toList());

            ResponseEntity<List<ProductInventory>> response = inventoryFeignClient.fetchInventory(productIds);
            List<ProductInventory> inventoryList = response.getBody();

            order.getProducts().forEach(product -> {
                if (product.getQuantity() < 1)
                    throw new BadRequestException("Invalid quantity for product with id: " + product.getProductId());

                Optional<ProductInventory> inventory = inventoryList.stream()
                        .filter(inv -> inv.getProductId().equals(product.getProductId()))
                        .findAny();

                inventory.ifPresent(inv -> {
                    if (inv.getQuantity() == 0)
                        throw new CustomException("No stock for product with id: " + product.getProductId());

                    if (inv.getQuantity() < product.getQuantity())
                        throw new CustomException("Not enough stock for product with id: " + product.getProductId());

                    inv.setQuantity(inv.getQuantity() - product.getQuantity());
                });
            });

            inventoryFeignClient.updateInventory(inventoryList);

            order.setPaymentStatus("In progress");

            Order savedOrder = orderRepository.save(order);

            ResponseEntity<User> userResponse = userFeignClient.fetchUser(order.getOrderedBy());
            User user = userResponse.getBody();

            if (user != null) {
                List<Order> orders = user.getOrders();
                orders.add(savedOrder);

                user.setOrders(orders);
                user.setAddress(savedOrder.getAddress());

                userFeignClient.updateUser(user);
            }

            kafkaTemplate.send("orderCreatedTopic", savedOrder);

            log.info("Created order having id: " + savedOrder.getId());

            return ResponseEntity.ok(savedOrder);
        } catch (BadRequestException bre) {
            return ResponseEntity.badRequest().body(bre.getErrorMessage());
        } catch (CustomException ce) {
            return ResponseEntity.badRequest().body(ce.getErrorMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    public ResponseEntity<String> updateOrder(Order order) {
        orderRepository.save(order);

        log.info("Updated order having id: " + order.getId());

        return ResponseEntity.ok("Order updated");
    }

    public ResponseEntity<String> deleteOrder(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);

        orderRepository.deleteById(orderId);

        order.ifPresent(deletedOrder -> {
            ResponseEntity<User> userResponse = userFeignClient.fetchUser(deletedOrder.getOrderedBy());
            User user = userResponse.getBody();

            List<Order> updatedOrders = user.getOrders().stream()
                    .filter(ord -> !ord.getId().equals(deletedOrder.getId()))
                    .collect(Collectors.toList());

            user.setOrders(updatedOrders);

            userFeignClient.updateUser(user);

            kafkaTemplate.send("orderDeletedTopic", deletedOrder);
        });

        log.info("Deleted order having id: " + orderId);

        return ResponseEntity.ok("Order deleted");
    }

}
