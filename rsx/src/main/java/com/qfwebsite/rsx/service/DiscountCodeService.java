package com.qfwebsite.rsx.service;

import com.qfwebsite.rsx.bean.DiscountCode;
import com.qfwebsite.rsx.dao.DiscountCodeRepository;
import com.qfwebsite.rsx.error.RequestFailedException;
import com.qfwebsite.rsx.response.DiscountCodeResponse;
import com.qfwebsite.rsx.utils.CryptoUtils;
import com.qfwebsite.rsx.utils.HttpCode;
import com.qfwebsite.rsx.utils.MailUtils;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.Date;

/**
 * @author Zhuangyuehui
 * @Date 2022.06.19
 */
@Service
public class DiscountCodeService {
    private static final Logger logger = LoggerFactory.getLogger(DiscountCodeService.class);

    public static final Integer CASH = 15;

    public static final Integer PROPORTION = 20;

    private static final String MSG_SUCCESS_BUY = "Your order will be shipped within 3 working days. Please pay attention to your email, and we will send you the logistics information via email.";

    @Autowired
    private DiscountCodeRepository discountCodeRepository;

    @Transactional
    public String addDiscountCode(String email) throws MessagingException {
        // 1. 查询该账号今天有没有领取过优惠卷
        String da = DateUtils.formatDate(new Date(), "yyyyMMdd");
        DiscountCode discountCode = discountCodeRepository.findByEmailAndCreateDate(email, da);
        if (null != discountCode) {
            throw new RequestFailedException(HttpCode.DISCOUNT_CODE_EXISTENCE, "I have already received the discount coupon today. Please check your email");
        }
        // 2. 新增优惠卷
        String code = CryptoUtils.MD5(String.valueOf(new Date().getTime()));
        discountCode = new DiscountCode();
        discountCode.setCash(CASH);
        discountCode.setCode(code);
        discountCode.setCreateDate(da);
        discountCode.setCreateTime(new Date());
        discountCode.setEmail(email);
        discountCode.setState(DiscountCode.NOT_USED);
        discountCode.setEffectiveDay(DiscountCode.EFFECTIVE_DAY);
        discountCode.setProportion(PROPORTION);
        discountCode.setDiscountType(DiscountCode.DISCOUNT_TYPE_CASH);
        discountCodeRepository.save(discountCode);
        // 3. 发送邮件
        MailUtils.sendGetCodeSuccessMail(email, code);
        return code;
    }

    public DiscountCodeResponse getDiscountCode(String code) {
        DiscountCode dis = discountCodeRepository.findByCode(code);
        if (null == dis) {
            // 优惠卷不存在
            throw new RequestFailedException(HttpCode.DISCOUNT_CODE_NOT_EXISTENCE, "discount code not existence");
        }
        Date createTime = dis.getCreateTime();
        long outTime = createTime.getTime() + 24 * 60 * 60 * 1000 * dis.getEffectiveDay();
        long nowTime = new Date().getTime();
        if (nowTime > outTime || dis.getState() == DiscountCode.YES_USED) {
            // 优惠卷已失效
            throw new RequestFailedException(HttpCode.DISCOUNT_CODE_INVALID, "discount code invalid");
        }


        DiscountCodeResponse res = new DiscountCodeResponse();
        res.setCash(dis.getCash());
        res.setDiscountType(dis.getDiscountType());
        res.setProportion(dis.getProportion());
        res.setCode(dis.getCode());
        return res;
    }

    public void updateDiscountCode(String code) {
        discountCodeRepository.updateState(DiscountCode.YES_USED, code);
    }
}
