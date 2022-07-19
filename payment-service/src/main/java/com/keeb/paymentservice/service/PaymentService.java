package com.keeb.paymentservice.service;

import com.keeb.paymentservice.model.PaymentObject;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentService {

    private final APIContext apiContext;

    public Payment createPayment(PaymentObject payment) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(payment.getCurrency());
        amount.setTotal(String.valueOf(payment.getPrice()));

        Transaction transaction = new Transaction();
        transaction.setDescription(payment.getDescription());
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(payment.getMethod());

        Payment paymentResponse = new Payment();
        paymentResponse.setIntent(payment.getIntent());
        paymentResponse.setPayer(payer);
        paymentResponse.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setReturnUrl("http://localhost:3000/success");
        redirectUrls.setCancelUrl("http://localhost:3000/failed");
        paymentResponse.setRedirectUrls(redirectUrls);

        return paymentResponse.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecute);
    }

}
