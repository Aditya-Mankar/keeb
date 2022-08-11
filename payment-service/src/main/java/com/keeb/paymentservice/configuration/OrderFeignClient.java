package com.keeb.paymentservice.configuration;

import com.keeb.paymentservice.model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("order-service")
public interface OrderFeignClient {

    @GetMapping("v1/order/fetch/{orderId}")
    public ResponseEntity<Order> fetchOrderById(@PathVariable String orderId);

    @PutMapping("v1/order/update")
    public ResponseEntity<String> updateOrder(@RequestBody Order order);

}
