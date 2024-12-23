package com.qfwebsite.rsx.dao;

import com.qfwebsite.rsx.bean.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zhuangyuehui
 * @Date 2024.12.5
 */
@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {
    /*@Transactional
    @Modifying
    @Query(value = "update order_info set state = ?1 where paypal_id = ?2", nativeQuery = true)
    void updateState(Integer state, String paypalId);*/

    Account findByAccount(String account);

    Account findByNameId(Integer nameId);
}
