package com.qfwebsite.rsx.dao;

import com.qfwebsite.rsx.bean.DiscountCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zhuangyuehui
 * @Date 2024.06.19
 */
@Transactional(readOnly = true)
public interface DiscountCodeRepository extends JpaRepository<DiscountCode, Long> {
    DiscountCode findByCode(String code);

    DiscountCode findByEmailAndCreateDate(String email,String createDate);

    @Transactional
    @Modifying
    @Query(value = "update discount_code set state = ?1 where code = ?2", nativeQuery = true)
    void updateState(Integer state, String code);
}
