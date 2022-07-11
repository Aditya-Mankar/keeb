package com.keeb.inventoryservice.repository;

import com.keeb.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query(value = "SELECT * FROM Inventory WHERE product_id IN (:productIds)", nativeQuery = true)
    List<Inventory> fetchByProductIds(List<Long> productIds);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Inventory WHERE product_id = :productId", nativeQuery = true)
    void deleteByProductId(Long productId);

}
