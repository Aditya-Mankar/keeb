package com.keeb.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Order {

    private String id;
    private List<ProductInventory> products;
    private String orderedBy;
    private Double amount;
    private String address;
    private String paymentMethod;

}
