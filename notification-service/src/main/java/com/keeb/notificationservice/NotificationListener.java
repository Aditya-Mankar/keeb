package com.keeb.notificationservice;

import com.google.gson.Gson;
import com.keeb.notificationservice.model.Order;
import com.keeb.notificationservice.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NotificationListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "orderCreatedTopic", groupId = "groupId")
    public void orderCreatedListener(String data) {
        Gson gson = new Gson();
        Order order = gson.fromJson(data, Order.class);

        System.out.println("Object received on orderCreatedTopic: ");
        System.out.println(order);

        notificationService.sendMail(order, "order-placed-template", "Order placed");
    }

    @KafkaListener(topics = "orderDeletedTopic", groupId = "groupId")
    public void orderDeletedListener(String data) {
        Gson gson = new Gson();
        Order order = gson.fromJson(data, Order.class);

        System.out.println("Object received on orderDeletedTopic: ");
        System.out.println(order);

        order.setIsCOD(order.getPaymentMethod().equals("COD"));

        notificationService.sendMail(order, "order-cancelled-template", "Order cancelled");
    }


}
