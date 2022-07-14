package com.keeb.notificationservice.service;

import com.keeb.notificationservice.configuration.ProductFeignClient;
import com.keeb.notificationservice.model.Mail;
import com.keeb.notificationservice.model.Order;
import com.keeb.notificationservice.model.Product;
import com.keeb.notificationservice.model.ProductInventory;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationService {

    private final ProductFeignClient productFeignClient;
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendMail(Order order, String template, String subject) {
        try {
            List<Long> productIds = order.getProducts().stream()
                    .map(ProductInventory::getProductId)
                    .collect(Collectors.toList());

            ResponseEntity<List<Product>> productsResponse = productFeignClient.fetchProducts(productIds);
            List<Product> products = productsResponse.getBody();

            products.forEach(product -> {
                Optional<ProductInventory> pro = order.getProducts().stream()
                        .filter(p -> p.getProductId().equals(product.getId()))
                        .findAny();

                pro.ifPresent(p -> product.setQuantity(p.getQuantity()));
            });

            Map<String, Object> props = new HashMap<>();
            props.put("order", order);
            props.put("products", products);

            Mail mail = Mail.builder()
                    .mailFrom("birthday.notifier.service@gmail.com")
                    .mailTo(order.getOrderedBy())
                    .subject(subject)
                    .props(props)
                    .build();

            sendHtmlMail(mail, template);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void sendHtmlMail(Mail mail, String template) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariables(mail.getProps());

        String html = templateEngine.process(template, context);

        helper.setFrom(mail.getMailFrom());
        helper.setTo(mail.getMailTo());
        helper.setSubject(mail.getSubject());
        helper.setText(html, true);

        mailSender.send(message);
    }

}
