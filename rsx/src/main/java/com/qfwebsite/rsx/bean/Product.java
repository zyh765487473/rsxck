package com.qfwebsite.rsx.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 商品表
 *
 * @author Zhuangyuehui
 * @Date 2024.05.22
 */
@Entity
@Table(name = "product", indexes = {
        @Index(name = "u_product_id", columnList = "product_id", unique = true),
})
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    public static final int NOT_LISTED = 0;

    public static final int LISTED = 1;

    /**
     * 自动递增的id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT(20) NOT NULL COMMENT 'id'")
    private Long id;

    /**
     * paypal的订单ID
     */
    @Column(name = "product_id", columnDefinition = "VARCHAR(50) NOT NULL COMMENT '商品ID'")
    private String productId;

    /**
     * 标题
     */
    @Column(name = "title", columnDefinition = "VARCHAR(50) NOT NULL COMMENT '标题'")
    private String title;

    /**
     * 价格
     */
    @Column(name = "price", columnDefinition = "VARCHAR(50) NOT NULL COMMENT '价格'")
    private String price;

    /**
     * 商品状态（0.未上架，1.已上架）
     */
    @Column(name = "state", columnDefinition = "INT(2) NOT NULL COMMENT '订单状态'")
    private Integer state;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_time")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
