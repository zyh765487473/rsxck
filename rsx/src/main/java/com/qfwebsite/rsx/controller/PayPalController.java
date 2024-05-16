package com.qfwebsite.rsx.controller;

import com.ksyun.ks3.utils.StringUtils;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.qfwebsite.rsx.bean.OrderInfo;
import com.qfwebsite.rsx.register.PaypalConfig;
import com.qfwebsite.rsx.register.PaypalPaymentIntent;
import com.qfwebsite.rsx.register.PaypalPaymentMethod;
import com.qfwebsite.rsx.request.OrderInfoRequest;
import com.qfwebsite.rsx.service.OrderInfoService;
import com.qfwebsite.rsx.utils.*;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(tags = "用户登陆注册")
@RestController
public class PayPalController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private APIContext apiContext;

    @Value("${pay.paypal.PAYPAL_SUCCESS_PAGE}")
    private String PAYPAL_SUCCESS_PAGE;
    @Value("${pay.paypal.PAYPAL_CANCEL_PAGE}")
    private String PAYPAL_CANCEL_PAGE;

    @Value("${pay.paypal.PAYPAL_SUCCESS_URL}")
    private String PAYPAL_SUCCESS_URL;

    @Value("${pay.paypal.PAYPAL_CANCEL_URL}")
    private String PAYPAL_CANCEL_URL;

    @Value("${pay.paypal.md5_str}")
    private String MD5_STR;

    @Autowired
    private OrderInfoService orderInfoService;

    @PostMapping("/paypal/pay")
    public SimpleResponse toPay(@RequestBody @Valid OrderInfoRequest orderInfoRequest) {
        try {
            // 1. 校验请求参数是否正确
            if (!ParameterVerification(orderInfoRequest)) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "param error");
            }

            // 2. 价格转化
            BigDecimal payAmount = new BigDecimal(orderInfoRequest.getOrderPrice());

            // 3. 购买下单
            Map<String, String> otherInfo = orderInfoRequest.getOtherInfo();
            Payment payment = createPayment(
                    payAmount,
                    PaypalConfig.USD,
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    StringUtils.isBlank(otherInfo.get("description")) ? PaypalConfig.PRODUCT_DETAILS : otherInfo.get("description"),
                    PAYPAL_CANCEL_URL,
                    PAYPAL_SUCCESS_URL);
            log.info("=========================================");
            log.info(payment.toString());
            log.info("=========================================");
            log.info("支付金额：" + payAmount);
            log.info("订单号：" + payment.getId());
            List<Links> links = payment.getLinks();
            String url = null;
            for (Links link : links) {
                if (link.getRel().equals("approval_url")) {
                    url = link.getHref();
                }
            }
            if (null == url) {
                return ResponseUtils.createOkResponse(HttpCode.PAY_HTTP_EX, "pay exception");
            }
            Map<String, String> param = UrlUtils.getParam(url);
            if (param.isEmpty()) {
                return ResponseUtils.createOkResponse(HttpCode.PAY_HTTP_EX, "pay exception");
            }

            // 4. 保存paypal预支付信息
            orderInfoService.saveOrderInfo(orderInfoRequest, payment.getId(), param.get("token"));
            return ResponseUtils.createOkResponse(HttpCode.SUCCESS, url);

        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
            return ResponseUtils.createOkResponse(HttpCode.PAY_HTTP_EX, "pay exception");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtils.createOkResponse(HttpCode.INVALID_PRICE, "invalid parameter OrderPrice");
        }
    }


    /**
     * 回调
     * <p>
     * PayPal 支付成功
     *
     * @param response
     * @param paymentId
     * @param payerId
     */
    @GetMapping("/paypal/success")
    public void successPay(HttpServletResponse response, @RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        log.info("================================payPal 回调成功===================================" + paymentId);
        log.info("================================payPal 回调成功===================================" + payerId);
        try {
            Payment payment = executePayment(paymentId, payerId);
            log.info("================================payment===================================");
            log.info(payment.toString());
            log.info("================================payment===================================");
            if (payment.getState().equals("approved")) {
                //修改订单状态
                orderInfoService.updateOrderState(OrderInfo.SUCCESS_PAY, paymentId, null);
                response.sendRedirect(PAYPAL_SUCCESS_PAGE);
            } else {
                response.sendRedirect(PAYPAL_CANCEL_PAGE);
            }

        } catch (PayPalRESTException e) {
            log.info("!!!!!!!!!!!!!!支付回调失败 异常!!!!!!!!!!!!!!");
            log.error(e.getMessage());
            log.info("!!!!!!!!!!!!!!支付回调失败 异常!!!!!!!!!!!!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * paypal 回调 取消支付
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/paypal/cancel")
    public void cancelPay(HttpServletResponse response, @RequestParam("token") String token) throws IOException {
        log.info("================================payPal 取消支付===================================");
        log.info("================================payPal 取消支付===================================");
        response.sendRedirect(PAYPAL_CANCEL_PAGE);
        log.info("================================payment===================================");
        log.info(token);

        //修改订单状态
        orderInfoService.updateOrderState(OrderInfo.CANCEL_PAY, null, token);
    }

    /**
     * @param total
     * @param currency
     * @param method
     * @param intent
     * @param description
     * @param cancelUrl
     * @param successUrl
     * @return
     * @throws PayPalRESTException
     */
    public Payment createPayment(BigDecimal total, String currency, PaypalPaymentMethod method, PaypalPaymentIntent intent,
                                 String description, String cancelUrl, String successUrl) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    /**
     * @param paymentId
     * @param payerId
     * @return
     * @throws PayPalRESTException
     */
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }

    /**
     * 参数校验
     */
    public boolean ParameterVerification(OrderInfoRequest orderInfoRequest) {
        String country = orderInfoRequest.getCountry();
        String orderPrice = orderInfoRequest.getOrderPrice();
        String email = orderInfoRequest.getEmail();
        String phone = orderInfoRequest.getPhone();
        String code = orderInfoRequest.getCode();
        String str = orderPrice + email + code + phone + country + MD5_STR;
        String pa = CryptoUtils.MD5(str);
        log.info("str:{}", str);
        log.info("pa:{}", pa);
        return pa.equals(orderInfoRequest.getMd5());
    }
}
