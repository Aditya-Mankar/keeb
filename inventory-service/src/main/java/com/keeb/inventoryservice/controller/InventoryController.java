package com.keeb.inventoryservice.controller;

import com.keeb.inventoryservice.model.ProductInventory;
import com.keeb.inventoryservice.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/fetch")
    public ResponseEntity<List<ProductInventory>> fetchInventory(@RequestBody List<Long> productIds) {
        return inventoryService.fetchInventory(productIds);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addInventory(@RequestBody ProductInventory productInventory) {
        return inventoryService.addInventory(productInventory);
    }

    @PutMapping("/update-quantity")
    public ResponseEntity<String> updateInventory(@RequestBody List<ProductInventory> request) {
        return inventoryService.updateInventory(request);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteInventory(@PathVariable("id") Long productId){
        return inventoryService.deleteInventory(productId);
    }

}
