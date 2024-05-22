package com.qfwebsite.rsx.dao;

import com.qfwebsite.rsx.bean.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zhuangyuehui
 * @Date 2024.05.22
 */
@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByProductIdAndState(String productId, Integer state);
}
