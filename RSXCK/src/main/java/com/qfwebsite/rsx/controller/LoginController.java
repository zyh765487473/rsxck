package com.qfwebsite.rsx.controller;

import com.qfwebsite.rsx.error.RequestFailedException;
import com.qfwebsite.rsx.service.AccountService;
import com.qfwebsite.rsx.utils.*;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Api(tags = "登录接口")
@RestController
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Value("${login.md5_str}")
    private String MD5_STR;

    @Autowired
    private AccountService accountService;

    @GetMapping("/account/login")
    public SimpleResponse login(@RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("md5") String md5) {
        try {
            // 1. 校验请求参数是否正确
            if (!parameterVerification(account + password, md5)) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "param error");
            }
            return ResponseUtils.createOkResponse(accountService.login(account, password));
        } catch (RequestFailedException e) {
            return ResponseUtils.createOkResponse(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseUtils.createOkResponse(HttpCode.INNER_ERROR, "inner error");
        }
    }

    @GetMapping("/account/outlogin")
    public SimpleResponse outlogin(@RequestParam("account") String account, @RequestParam("md5") String md5) {
        try {
            // 1. 校验请求参数是否正确
            if (!parameterVerification(account, md5)) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "param error");
            }

            // 2. 退出登录，刷新token
            accountService.outLogin(account);
            return ResponseUtils.createOkResponse();
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
