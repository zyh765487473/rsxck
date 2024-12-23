package com.qfwebsite.rsx.service;

import com.qfwebsite.rsx.bean.Account;
import com.qfwebsite.rsx.dao.AccountRepository;
import com.qfwebsite.rsx.error.RequestFailedException;
import com.qfwebsite.rsx.response.AccountResponse;
import com.qfwebsite.rsx.utils.HttpCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Zhuangyuehui
 * @Date 2022.12.5
 */
@Service
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    public AccountResponse login(String account, String password) {
        logger.info("login account:{}, password:{}", account, password);
        AccountResponse res = new AccountResponse();
        Account ac = accountRepository.findByAccount(account);
        if (null == ac) {
            throw new RequestFailedException(HttpCode.LOGIN_ERROR, "账号或者密码错误");
        }
        if (ac.getPassword().equals(password)) {
            BeanUtils.copyProperties(ac, res);
            return res;
        }
        throw new RequestFailedException(HttpCode.LOGIN_ERROR, "账号或者密码错误");
    }

    public void outLogin(String account) {
        logger.info("outLogin account:{}", account);
        Account ac = accountRepository.findByAccount(account);
        if (null == ac) {
            throw new RequestFailedException(HttpCode.ACCOUNT_NOT_EXISTENT, "账号不存在");
        }
        long time = new Date().getTime();
        ac.setToken(Long.toString(time));
        ac.setUpdateTime(new Date());
        accountRepository.save(ac);
    }

    public boolean loginVerification(String token, Integer nameId) {
        Account ac = accountRepository.findByNameId(nameId);
        if (null == ac) {
            throw new RequestFailedException(HttpCode.ACCOUNT_NOT_EXISTENT, "账号不存在");
        }
        if (!ac.getToken().equals(token)) {
            throw new RequestFailedException(HttpCode.ACCOUNT_NOT_EXPIRE, "账号已过期，请重新登录");
        } else {
            return true;
        }

    }
}
