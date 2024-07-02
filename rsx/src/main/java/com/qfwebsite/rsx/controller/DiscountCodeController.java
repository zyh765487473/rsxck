package com.qfwebsite.rsx.controller;

import com.qfwebsite.rsx.error.RequestFailedException;
import com.qfwebsite.rsx.service.DiscountCodeService;
import com.qfwebsite.rsx.utils.*;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Api(tags = "优惠卷接口")
@RestController
public class DiscountCodeController {
    private static final Logger logger = LoggerFactory.getLogger(DiscountCodeController.class);

    @Value("${pay.paypal.md5_str}")
    private String MD5_STR;

    @Autowired
    private DiscountCodeService discountCodeService;

    @GetMapping("/discount/code/get")
    public SimpleResponse getDiscountCode(@RequestParam("email") String email, @RequestParam("md5") String md5) {
        try {
            // 1. 校验请求参数是否正确
            if (!parameterVerification(email, md5)) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "param error");
            }

            // 2. 保存并发送邮件
            discountCodeService.addDiscountCode(email);
            return ResponseUtils.createOkResponse("received successfully, please check your email");
        } catch (RequestFailedException e) {
            return ResponseUtils.createOkResponse(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseUtils.createOkResponse(HttpCode.INNER_ERROR, "inner error");
        }
    }

    @GetMapping("/discount/code/check")
    public SimpleResponse checkDiscountCode(@RequestParam("discountCode") String discountCode, @RequestParam("md5") String md5) {
        try {
            // 1. 校验请求参数是否正确
            if (!parameterVerification(discountCode, md5)) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "param error");
            }

            // 2. 获取
            return ResponseUtils.createOkResponse(discountCodeService.getDiscountCode(discountCode));
        } catch (RequestFailedException e) {
            return ResponseUtils.createOkResponse(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseUtils.createOkResponse(HttpCode.INNER_ERROR, "inner error");
        }
    }

    /**
     * 参数校验
     */
    public boolean parameterVerification(String data, String md5) {
        String str = data + MD5_STR;
        String pa = CryptoUtils.MD5(str);
        logger.info("str:{}", str);
        logger.info("pa:{}", pa);
        return pa.equals(md5);
    }
}
