package com.qfwebsite.rsx.controller;

import com.ksyun.ks3.utils.StringUtils;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.qfwebsite.rsx.bean.OrderInfo;
import com.qfwebsite.rsx.bean.Product;
import com.qfwebsite.rsx.error.RequestFailedException;
import com.qfwebsite.rsx.register.PaypalConfig;
import com.qfwebsite.rsx.register.PaypalPaymentIntent;
import com.qfwebsite.rsx.register.PaypalPaymentMethod;
import com.qfwebsite.rsx.request.OrderInfoRequest;
import com.qfwebsite.rsx.service.DiscountCodeService;
import com.qfwebsite.rsx.service.OrderInfoService;
import com.qfwebsite.rsx.service.ProductService;
import com.qfwebsite.rsx.utils.*;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(tags = "paypal购买接口")
@RestController
public class PayPalController {
    private static final Logger log = LoggerFactory.getLogger(PayPalController.class);

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

    @Autowired
    private ProductService productService;

    @Autowired
    private DiscountCodeService discountCodeService;

    @PostMapping("/paypal/pay")
    public SimpleResponse toPay(@RequestBody @Valid OrderInfoRequest orderInfoRequest) {
        try {
            // 1. 校验请求参数是否正确
            if (!parameterVerification(orderInfoRequest)) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "param error");
            }
            // 价格转化
            BigDecimal payAmount = new BigDecimal(orderInfoRequest.getOrderPrice());
            BigDecimal price = new BigDecimal(orderInfoRequest.getPrice());

            // 2. 校验商品信息
            if (!productVerification(orderInfoRequest.getProductId(), price.doubleValue())) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "product error");
            }

            // 3.校验优惠卷信息
            String discountCode = orderInfoRequest.getDiscountCode();
            if (!StringUtils.isBlank(discountCode)) {
                discountCodeService.getDiscountCode(discountCode);
            }

            // 4. 购买下单
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

            // 5. 保存paypal预支付信息
            orderInfoService.saveOrderInfo(orderInfoRequest, payment.getId(), param.get("token"));

            return ResponseUtils.createOkResponse(url);

        } catch (RequestFailedException e) {
            return ResponseUtils.createOkResponse(e.getCode(), e.getMessage());
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
            return ResponseUtils.createOkResponse(HttpCode.PAY_HTTP_EX, "pay exception");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtils.createOkResponse(HttpCode.INVALID_PRICE, "invalid parameter OrderPrice");
        }
    }

    /**
     * js购买按钮的支付-------暂时不考虑使用
     *
     * @param orderInfoRequest
     * @return
     */
    @PostMapping("/paypal/but/pay/success")
    public SimpleResponse toButPaySuccess(@RequestBody @Valid OrderInfoRequest orderInfoRequest) {
        /*try {
            // 1. 校验请求参数是否正确
            if (!parameterVerification(orderInfoRequest)) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "param error");
            }
            // 价格转化
            BigDecimal payAmount = new BigDecimal(orderInfoRequest.getOrderPrice());
            BigDecimal price = new BigDecimal(orderInfoRequest.getPrice());

            // 4. 保存paypal预支付信息
            orderInfoService.saveOrderInfo(orderInfoRequest, payment.getId(), param.get("token"));
            return ResponseUtils.createOkResponse(url);

        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
            return ResponseUtils.createOkResponse(HttpCode.PAY_HTTP_EX, "pay exception");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseUtils.createOkResponse(HttpCode.INVALID_PRICE, "invalid parameter OrderPrice");
        }*/
        return null;
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
        log.info("================================payPal success===================================" + paymentId);
        log.info("================================payPal success===================================" + payerId);
        OrderInfo orderInfo = null;
        try {
            Payment payment = executePayment(paymentId, payerId);
            log.info("================================payment===================================");
            log.info(payment.toString());
            log.info("================================payment===================================");
            if (payment.getState().equals("approved")) {
                log.info("================================approved===================================");
                //修改订单状态
                log.info("================================updateOrderState start===================================");
                orderInfo = orderInfoService.updateOrderState(OrderInfo.SUCCESS_PAY, paymentId, null);
                log.info("================================updateOrderState over===================================");
                response.sendRedirect(PAYPAL_SUCCESS_PAGE);
            } else {
                response.sendRedirect(PAYPAL_CANCEL_PAGE);
            }

        } catch (PayPalRESTException e) {
            log.info("!!!!!!!!!!!!!!PayPalRESTException!!!!!!!!!!!!!!");
            log.error(e.getMessage());
            log.info("paymentId:{}, payerId:{}", paymentId, payerId);
        } catch (IOException e) {
            log.info("!!!!!!!!!!!!!!IOException!!!!!!!!!!!!!!");
            e.printStackTrace();
            log.info("paymentId:{}, payerId:{}", paymentId, payerId);
        }

        // 不要影响回调，除了必要的订单状态以外
        try {
            if (null != orderInfo) {
                // 发送邮件
                log.info("================================sendBuySuccessMail start===================================");
                MailUtils.sendBuySuccessMail(orderInfo.getEmail());
                log.info("================================sendBuySuccessMail over===================================");
                // 修改优惠卷状态
                if (!StringUtils.isBlank(orderInfo.getDiscountCode())) {
                    log.info("================================updateDiscountCode start===================================");
                    discountCodeService.updateDiscountCode(orderInfo.getDiscountCode());
                    log.info("================================updateDiscountCode over===================================");
                }
            }else {
                log.info("================================orderInfo is null===================================");
                log.info("paymentId:{}, payerId:{}", paymentId, payerId);
            }
        } catch (MessagingException e) {
            log.info("MessagingException e:{}", e.getMessage());
            log.info("paymentId:{}, payerId:{}", paymentId, payerId);
        } catch (Exception e) {
            log.info("Exception e:{}", e.getMessage());
            log.info("paymentId:{}, payerId:{}", paymentId, payerId);
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
    public boolean parameterVerification(OrderInfoRequest orderInfoRequest) {
        String country = orderInfoRequest.getCountry();
        String orderPrice = orderInfoRequest.getOrderPrice();
        String email = orderInfoRequest.getEmail();
        String phone = orderInfoRequest.getPhone();
        String code = orderInfoRequest.getCode();
        String productId = orderInfoRequest.getProductId();
        String str = orderPrice + email + code + phone + country + productId + MD5_STR;
        String pa = CryptoUtils.MD5(str);
        log.info("str:{}", str);
        log.info("pa:{}", pa);
        return pa.equals(orderInfoRequest.getMd5());
    }

    /**
     * 商品校验
     */
    public boolean productVerification(String productId, double price) {
        Product product = productService.getProduct(productId, Product.LISTED);
        if (null == product) {
            return false;
        }
        // 价格转化
        BigDecimal p = new BigDecimal(product.getPrice());
        return p.doubleValue() == price;
    }
}
