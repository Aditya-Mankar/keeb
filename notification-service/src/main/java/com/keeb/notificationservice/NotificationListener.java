package com.keeb.notificationservice;

import com.google.gson.Gson;
import com.keeb.notificationservice.model.Order;
import com.keeb.notificationservice.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class NotificationListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "orderCreatedTopic", groupId = "groupId")
    public void orderCreatedListener(String data) {
        Gson gson = new Gson();
        Order order = gson.fromJson(data, Order.class);

        log.info("Object received on orderCreatedTopic: " + order.toString());

        notificationService.sendMail(order, "order-placed-template", "Order placed");
    }

    @KafkaListener(topics = "orderDeletedTopic", groupId = "groupId")
    public void orderDeletedListener(String data) {
        Gson gson = new Gson();
        Order order = gson.fromJson(data, Order.class);

        log.info("Object received on orderDeletedTopic: " + order.toString());

        order.setIsCOD(order.getPaymentMethod().equals("COD"));

        notificationService.sendMail(order, "order-cancelled-template", "Order cancelled");
    }


}
