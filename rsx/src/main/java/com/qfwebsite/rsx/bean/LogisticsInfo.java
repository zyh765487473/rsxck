package com.qfwebsite.rsx.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 物流信息表
 *
 * @author Zhuangyuehui
 * @Date 2024.06.25
 */
@Entity
@Table(name = "logistics_info", indexes = {
        @Index(name = "u_order_id", columnList = "order_id"),
        @Index(name = "u_logistics_num", columnList = "logistics_num",unique = true),
})
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsInfo {

    /**
     * 自动递增的id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT(20) NOT NULL COMMENT 'id'")
    private Long id;

    /**
     * 订单编号
     */
    @Column(name = "order_id", columnDefinition = "BIGINT(20) NOT NULL COMMENT '邮箱'")
    private Long orderId;

    /**
     * 物流单号
     */
    @Column(name = "logistics_num", columnDefinition = "VARCHAR(50) NOT NULL COMMENT '物流单号'")
    private String logisticsNum;

    /**
     * 物流承运商
     */
    @Column(name = "carrier", columnDefinition = "VARCHAR(50) NOT NULL COMMENT '物流承运商'")
    private String carrier;

    /**
     * Shipping service(物流服务)
     */
    @Column(name = "shipping_service", columnDefinition = "VARCHAR(50) NOT NULL COMMENT 'Shipping service(物流服务)'")
    private String shippingService;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_time")
    private Date createTime;
}
