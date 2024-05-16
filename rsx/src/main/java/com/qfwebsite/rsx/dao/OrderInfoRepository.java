package com.qfwebsite.rsx.dao;

import com.qfwebsite.rsx.bean.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zhuangyuehui
 * @Date 2024.05.14
 */
@Transactional(readOnly = true)
public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {
    @Transactional
    @Modifying
    @Query(value = "update order_info set state = ?1 where paypal_id = ?2", nativeQuery = true)
    void updateState(Integer state, String paypalId);

    OrderInfo findByPaypalId(String paypalId);

    OrderInfo findByPaypalToken(String paypalId);
}
