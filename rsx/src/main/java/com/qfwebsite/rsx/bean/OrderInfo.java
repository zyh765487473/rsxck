package com.qfwebsite.rsx.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 订单数据表
 *
 * @author Zhuangyuehui
 * @Date 2024.05.13
 */
@Entity
@Table(name = "order_info", indexes = {
        @Index(name = "u_order_id", columnList = "order_id", unique = true),
        @Index(name = "u_paypal_id", columnList = "paypal_id", unique = true),
        @Index(name = "u_paypal_token", columnList = "paypal_token", unique = true),
        @Index(name = "u_email", columnList = "email")
})
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 46975766493256L;

    // 订单状态（0.未支付，1.已支付  2.已取消）

    public static final int NOT_PAY = 0;

    public static final int SUCCESS_PAY = 1;

    public static final int CANCEL_PAY = 2;

    /**
     * 自动递增的订单id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", columnDefinition = "BIGINT(20) NOT NULL COMMENT 'id'")
    private Long orderId;

    /**
     * paypal的订单ID
     */
    @Column(name = "paypal_id", columnDefinition = "VARCHAR(50) NOT NULL COMMENT 'paypal的订单ID'")
    private String paypalId;

    /**
     * 商品ID
     */
    @Column(name = "product_id", columnDefinition = "VARCHAR(50) NOT NULL COMMENT '商品ID'")
    private String productId;

    /**
     * paypal的订单token
     */
    @Column(name = "paypal_token", columnDefinition = "VARCHAR(50) NOT NULL COMMENT 'paypal的订单token'")
    private String paypalToken;

    /**
     * 用户邮箱
     */
    @Column(name = "email", columnDefinition = "VARCHAR(50) NOT NULL COMMENT '用户邮箱'")
    private String email;

    /**
     * 国家
     */
    @Column(name = "country", columnDefinition = "VARCHAR(50) NOT NULL COMMENT '用户所在国家'")
    private String country;

    /**
     * 用户地址栏1
     */
    @Column(name = "address_one", columnDefinition = "VARCHAR(200) NOT NULL COMMENT '用户地址行1'")
    private String addressOne;

    /**
     * 用户地址栏2
     */
    @Column(name = "address_two", columnDefinition = "VARCHAR(200) COMMENT '用户地址行2'")
    private String addressTwo;

    /**
     * 城市
     */
    @Column(name = "city", columnDefinition = "VARCHAR(50) NOT NULL COMMENT '用户所在城市'")
    private String city;

    /**
     * 州
     */
    @Column(name = "province", columnDefinition = "VARCHAR(50) NOT NULL COMMENT '用户所在州'")
    private String province;

    /**
     * 邮政编码
     */
    @Column(name = "code", columnDefinition = "VARCHAR(50) NOT NULL COMMENT '邮政编码'")
    private String code;

    /**
     * 姓
     */
    @Column(name = "first_name", columnDefinition = "VARCHAR(50) NOT NULL COMMENT '姓'")
    private String firstName;

    /**
     * 名
     */
    @Column(name = "last_name", columnDefinition = "VARCHAR(50) NOT NULL COMMENT '名'")
    private String lastName;

    /**
     * 收件人电话
     */
    @Column(name = "phone", columnDefinition = "VARCHAR(20) NOT NULL COMMENT '收件人电话'")
    private String phone;

    /**
     * 单价
     */
    @Column(name = "price", columnDefinition = "VARCHAR(20) NOT NULL COMMENT '单价'")
    private String price;

    /**
     * 总价
     */
    @Column(name = "order_price", columnDefinition = "VARCHAR(20) NOT NULL COMMENT '总价'")
    private String orderPrice;

    /**
     * 数量
     */
    @Column(name = "num", columnDefinition = "INT(10) NOT NULL COMMENT '数量'")
    private Integer num;

    /**
     * 优惠券code
     */
    @Column(name = "discount_code", columnDefinition = "VARCHAR(50) COMMENT '优惠券code'")
    private String discountCode;

    /**
     * 物流费用
     */
    @Column(name = "postage", columnDefinition = "INT(10) NOT NULL COMMENT '物流费用'")
    private Integer postage;

    /**
     * 其他信息是一个json类型的数据，可以放一些别的信息，订单详细，优惠价格等等
     */
    @Column(name = "other_info", columnDefinition = "VARCHAR(500) COMMENT '其他信息'")
    private String otherInfo;

    /**
     * 订单状态（0.未支付，1. 已支付  2. 已取消）
     */
    @Column(name = "state", columnDefinition = "INT(2) NOT NULL COMMENT '订单状态'")
    private Integer state;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_time")
    private Date createTime;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPaypalId() {
        return paypalId;
    }

    public void setPaypalId(String paypalId) {
        this.paypalId = paypalId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public Integer getPostage() {
        return postage;
    }

    public void setPostage(Integer postage) {
        this.postage = postage;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
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

    public String getPaypalToken() {
        return paypalToken;
    }

    public void setPaypalToken(String paypalToken) {
        this.paypalToken = paypalToken;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
