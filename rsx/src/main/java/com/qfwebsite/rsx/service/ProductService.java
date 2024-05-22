package com.qfwebsite.rsx.service;

import com.qfwebsite.rsx.bean.Product;
import com.qfwebsite.rsx.dao.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Zhuangyuehui
 * @Date 2022.05.22
 */
@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public Product getProduct(String productId, Integer state) {
        return productRepository.findByProductIdAndState(productId, state);
    }
}
