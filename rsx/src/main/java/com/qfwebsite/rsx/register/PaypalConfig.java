package com.qfwebsite.rsx.register;

import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypalConfig {

    public static final String USD = "USD";

    public static final String PRODUCT_DETAILS = "I hope every one of our products can satisfy you! I also hope that every choice you make is because you like it!";

    private APIContext apiContext = null;

    /**
     * paypal配置应用的客户端ID
     */
    @Value("${pay.paypal.clientId}")
    private String clientId;

    /**
     * paypal配置应用的客户端密钥
     */
    @Value("${pay.paypal.clientSecret}")
    private String clientSecret;

    /**
     * paypal连接环境：live表示生产，sandbox表示沙盒
     */
    @Value("${pay.paypal.mode}")
    private String mode;

    @Bean
    public APIContext apiContext() {
        if (apiContext == null) {
            apiContext = new APIContext(clientId, clientSecret, mode);
        }
        return apiContext;
    }
}