package com.keeb.paymentservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PaymentObject {

    private double price;
    private String currency;
    private String method;
    private String intent;
    private String description;

}
