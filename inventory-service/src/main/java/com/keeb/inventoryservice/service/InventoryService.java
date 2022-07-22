package com.keeb.inventoryservice.service;

import com.keeb.inventoryservice.model.Inventory;
import com.keeb.inventoryservice.model.ProductInventory;
import com.keeb.inventoryservice.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public ResponseEntity<List<ProductInventory>> fetchInventory(List<Long> productIds) {
        List<Inventory> inventoryList = inventoryRepository.fetchByProductIds(productIds);
        List<ProductInventory> response = new ArrayList<>();

        inventoryList.forEach(inventory -> {
            ProductInventory productInventory = ProductInventory.builder()
                    .productId(inventory.getProductId())
                    .quantity(inventory.getQuantity())
                    .build();

            response.add(productInventory);
        });

        log.info("Fetching products with ids: " + productIds);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<String> addInventory(ProductInventory productInventory) {
        Inventory inventory = Inventory.builder()
                .productId(productInventory.getProductId())
                .quantity(productInventory.getQuantity())
                .build();

        inventoryRepository.save(inventory);

        log.info("Saving inventory for product with id: " + productInventory.getProductId());

        return ResponseEntity.ok("Inventory added product with id: " + productInventory.getProductId());
    }

    public ResponseEntity<String> updateInventory(List<ProductInventory> request) {
        List<Long> productIds = request.stream()
                .map(ProductInventory::getProductId)
                .collect(Collectors.toList());

        List<Inventory> inventoryList = inventoryRepository.fetchByProductIds(productIds);

        request.forEach(req -> {
            Optional<Inventory> inv = inventoryList.stream()
                    .filter(inventory -> inventory.getProductId().equals(req.getProductId()))
                    .findAny();

            inv.ifPresent(inventory -> inventory.setQuantity(req.getQuantity()));
        });

        inventoryRepository.saveAll(inventoryList);

        log.info("Updating inventory for products: " + request);

        return ResponseEntity.ok("Inventory updated");
    }

    public ResponseEntity<String> deleteInventory(Long productId) {
        inventoryRepository.deleteByProductId(productId);

        log.info("Deleting inventory for product with id: " + productId);

        return ResponseEntity.ok("Inventory deleted for product with id: " + productId);
    }

}