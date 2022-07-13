package com.example.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class User {

    private String id;
    private String emailId;
    private List<Order> orders;
    private List<Product> wishlist;
    private String address;

}
