package com.qfwebsite.rsx.register;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PaypalConfig {

    public static final String USD = "USD";

    public static final String PRODUCT_DETAILS = "I hope every one of our products can satisfy you! I also hope that every choice you make is because you like it!";

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
    public Map<String, String> paypalSdkConfig(){
        Map<String, String> sdkConfig = new HashMap<>();
        sdkConfig.put("mode", mode);
        return sdkConfig;
    }

    @Bean
    public OAuthTokenCredential authTokenCredential(){
        return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
    }

    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        APIContext apiContext = new APIContext(authTokenCredential().getAccessToken());
        apiContext.setConfigurationMap(paypalSdkConfig());
        return apiContext;
    }
}