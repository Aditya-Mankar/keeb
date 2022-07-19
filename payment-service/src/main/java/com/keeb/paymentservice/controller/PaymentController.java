package com.keeb.paymentservice.controller;

import com.keeb.paymentservice.configuration.OrderFeignClient;
import com.keeb.paymentservice.configuration.UserFeignClient;
import com.keeb.paymentservice.model.Order;
import com.keeb.paymentservice.model.PaymentObject;
import com.keeb.paymentservice.model.User;
import com.keeb.paymentservice.service.PaymentService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderFeignClient orderFeignClient;
    private final UserFeignClient userFeignClient;

    @PostMapping("/pay")
    public String payment(@RequestBody PaymentObject payment) {
        String url = "";

        try {
            Payment pay = paymentService.createPayment(payment);

            for(Links link: pay.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    url = link.getHref();
                    break;
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }

        return url;
    }

    @GetMapping("/success/{paymentId}/{payerId}")
    public String successPay(@PathVariable String paymentId, @PathVariable String payerId) {
        try {
            Payment payment = paymentService.executePayment(paymentId, payerId);

            if (payment.getState().equals("approved")) {
                String description = payment.getTransactions().get(0).getDescription();
                String[] strings = description.split(",");

                String emailId = strings[0];
                String orderId = strings[1];

                ResponseEntity<Order> orderResponse = orderFeignClient.fetchOrderById(orderId);
                Order order = orderResponse.getBody();

                if (order != null) {
                    order.setPaymentStatus("Completed");

                    orderFeignClient.updateOrder(order);
                }

                ResponseEntity<User> userResponse = userFeignClient.fetchUser(emailId);
                User user = userResponse.getBody();

                if (user != null) {
                    user.getOrders().stream()
                            .filter(ord -> ord.getId().equals(orderId))
                            .forEach(ord -> ord.setPaymentStatus("Completed"));

                    userFeignClient.updateUser(user);
                }

                return "success";
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }

        return "failed";
    }

}
