package com.keeb.paymentservice.configuration;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PaypalPaymentConfig {

    @Value("${paypal.mode}")
    private String mode;

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        Map<String, String> configMap = new HashMap<>();
        configMap.put("mode", mode);

        OAuthTokenCredential credential = new OAuthTokenCredential(clientId, clientSecret, configMap);

        APIContext context = new APIContext(credential.getAccessToken());
        context.setConfigurationMap(configMap);

        return context;
    }




}
