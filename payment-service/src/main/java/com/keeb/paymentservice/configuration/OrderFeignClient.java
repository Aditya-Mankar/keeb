package com.keeb.paymentservice.configuration;

import com.keeb.paymentservice.model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-feign-client", url = "http://localhost:8082/v1/order")
public interface OrderFeignClient {

    @GetMapping("/fetch/{orderId}")
    public ResponseEntity<Order> fetchOrderById(@PathVariable String orderId);

    @PutMapping("/update")
    public ResponseEntity<String> updateOrder(@RequestBody Order order);

}
