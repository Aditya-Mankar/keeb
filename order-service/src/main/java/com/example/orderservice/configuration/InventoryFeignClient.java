package com.example.orderservice.configuration;

import com.example.orderservice.model.ProductInventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "inventory-feign-client", url = "http://localhost:8081/v1/inventory")
public interface InventoryFeignClient {

    @PostMapping("/fetch")
    public ResponseEntity<List<ProductInventory>> fetchInventory(@RequestBody List<Long> productIds);

    @PutMapping("/update-quantity")
    public ResponseEntity<String> updateInventory(@RequestBody List<ProductInventory> request);

}
