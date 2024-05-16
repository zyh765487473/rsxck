package com.qfwebsite.rsx.service;

import com.ksyun.ks3.utils.StringUtils;
import com.qfwebsite.rsx.bean.OrderInfo;
import com.qfwebsite.rsx.dao.OrderInfoRepository;
import com.qfwebsite.rsx.request.OrderInfoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Zhuangyuehui
 * @Date 2022.03.14
 */
@Service
public class OrderInfoService {
    private static final Logger logger = LoggerFactory.getLogger(OrderInfoService.class);

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    public void saveOrderInfo(OrderInfoRequest param, String paypalId, String token) {

        OrderInfo byPaypalId = orderInfoRepository.findByPaypalId(paypalId);
        if (null != byPaypalId) {
            logger.info("save orderInfoRepository not null paypalId:{}", paypalId);
            byPaypalId.setUpdateTime(new Date());
            orderInfoRepository.save(byPaypalId);
            return;
        }

        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(param, orderInfo);
        orderInfo.setOtherInfo(param.getOtherInfo().toString());
        orderInfo.setPaypalId(paypalId);
        Double orderPrice = Double.parseDouble(param.getOrderPrice());
        Integer num = param.getNum();
        double pr = orderPrice / num;
        orderInfo.setPrice(String.valueOf(pr));
        orderInfo.setCreateTime(new Date());
        orderInfo.setState(OrderInfo.NOT_PAY);
        orderInfo.setPaypalToken(token);
        orderInfoRepository.save(orderInfo);
    }

    public void updateOrderState(Integer state, String paypalId, String token) {
        OrderInfo byPaypalId = null;
        if (StringUtils.isBlank(token)) {
            byPaypalId = orderInfoRepository.findByPaypalId(paypalId);
        } else {
            byPaypalId = orderInfoRepository.findByPaypalToken(token);
        }

        if (null == byPaypalId) {
            logger.warn("update orderInfoRepository is null paypalId:{}, token:{}", paypalId, token);
            return;
        }
        orderInfoRepository.updateState(state, byPaypalId.getPaypalId());
    }
}
