package com.keeb.productservice.repository;

import com.keeb.productservice.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(value = "SELECT * FROM Image WHERE product_id = :productId", nativeQuery = true)
    public Optional<Image> findByProductId(Long productId);

}
