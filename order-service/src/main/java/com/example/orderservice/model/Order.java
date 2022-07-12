package com.example.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Order")
@Getter
@Setter
@AllArgsConstructor
public class Order {

    @Id
    private String id;
    private List<ProductInventory> products;
    private String orderedBy;
    private Double amount;
    private String address;
    private String paymentMethod;

}
