package com.keeb.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "User")
@Getter
@Setter
@AllArgsConstructor
@ToString
public class User {

    @Id
    private String id;
    private String emailId;
    private List<Order> orders;
    private List<Product> wishlist;
    private String address;

}
