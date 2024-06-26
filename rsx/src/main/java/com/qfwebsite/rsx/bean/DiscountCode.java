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
 * @Date 2024.06.19
 */
@Entity
@Table(name = "discount_code", indexes = {
        @Index(name = "u_email", columnList = "email"),
        @Index(name = "u_code", columnList = "code",unique = true),
})
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCode {

    // 状态未使用
    public static final int NOT_USED = 0;

    // 状态已使用
    public static final int YES_USED = 1;

    // 有效天数
    public static final int EFFECTIVE_DAY = 1;

    // 现金
    public static final int DISCOUNT_TYPE_CASH = 0;

    // 折扣
    public static final int DISCOUNT_TYPE_PROPORTION = 1;


    /**
     * 自动递增的id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT(20) NOT NULL COMMENT 'id'")
    private Long id;

    /**
     * 邮箱
     */
    @Column(name = "email", columnDefinition = "VARCHAR(50) NOT NULL COMMENT '邮箱'")
    private String email;

    /**
     * 优惠码
     */
    @Column(name = "code", columnDefinition = "VARCHAR(50) NOT NULL COMMENT '优惠码'")
    private String code;

    /**
     * 优惠类型（0.现金，1.总价折扣）
     */
    @Column(name = "discount_type", columnDefinition = "INT(2) NOT NULL COMMENT '优惠类型（0.现金，1.总价折扣）'")
    private Integer discountType;

    /**
     * 现金
     */
    @Column(name = "cash", columnDefinition = "INT(5) NOT NULL COMMENT '现金'")
    private Integer cash;

    /**
     * 折扣比例
     */
    @Column(name = "proportion", columnDefinition = "INT(5) NOT NULL COMMENT '折扣比例'")
    private Integer proportion;

    /**
     * 有效天数
     */
    @Column(name = "effective_day", columnDefinition = "INT(5) NOT NULL COMMENT '有效天数'")
    private Integer effectiveDay;

    /**
     * code状态（0.未使用，1.已使用）
     */
    @Column(name = "state", columnDefinition = "INT(2) NOT NULL COMMENT 'code状态'")
    private Integer state;

    /**
     * 创建日期
     */
    @Column(name = "create_date", columnDefinition = "VARCHAR(10) NOT NULL COMMENT '创建日期'")
    private String createDate;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Integer discountType) {
        this.discountType = discountType;
    }

    public Integer getCash() {
        return cash;
    }

    public void setCash(Integer cash) {
        this.cash = cash;
    }

    public Integer getProportion() {
        return proportion;
    }

    public void setProportion(Integer proportion) {
        this.proportion = proportion;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getEffectiveDay() {
        return effectiveDay;
    }

    public void setEffectiveDay(Integer effectiveDay) {
        this.effectiveDay = effectiveDay;
    }
}
