package com.qfwebsite.rsx.request;

import com.sun.istack.NotNull;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 订单数据参数
 *
 * @author Zhuangyuehui
 * @Date 2024.05.13
 */
public class OrderInfoRequest {

    /**
     * 用户邮箱
     */
    @NotBlank
    private String email;

    /**
     * 国家
     */
    @NotBlank
    private String country;

    /**
     * 用户地址栏1
     */
    @NotBlank
    private String addressOne;

    /**
     * 用户地址栏2
     */
    private String addressTwo;

    /**
     * 城市
     */
    @NotBlank
    private String city;

    /**
     * 州
     */
    @NotBlank
    private String province;

    /**
     * 邮政编码
     */
    @NotBlank
    private String code;

    /**
     * 姓
     */
    @NotBlank
    private String firstName;

    /**
     * 名
     */
    private String lastName;

    /**
     * 收件人电话
     */
    @NotBlank
    private String phone;

    /**
     * 单价(折前)
     */
    @NotBlank
    private String price;

    /**
     * 总价
     */
    @NotBlank
    private String orderPrice;

    /**
     * 数量
     */
    @NotNull
    private Integer num;

    /**
     * 优惠券code
     */
    private String discountCode;

    /**
     * 物流费用
     */
    @NotNull
    private Integer postage;

    /**
     * 其他信息是一个json类型的数据，可以放一些别的信息，订单详细，优惠价格等等
     */
    private Map<String,String> otherInfo;

    @NotBlank
    private String md5;

    public @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank String email) {
        this.email = email;
    }

    public @NotBlank String getCountry() {
        return country;
    }

    public void setCountry(@NotBlank String country) {
        this.country = country;
    }

    public @NotBlank String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(@NotBlank String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public @NotBlank String getCity() {
        return city;
    }

    public void setCity(@NotBlank String city) {
        this.city = city;
    }

    public @NotBlank String getProvince() {
        return province;
    }

    public void setProvince(@NotBlank String province) {
        this.province = province;
    }

    public @NotBlank String getCode() {
        return code;
    }

    public void setCode(@NotBlank String code) {
        this.code = code;
    }

    public @NotBlank String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank String phone) {
        this.phone = phone;
    }

    public @NotBlank String getPrice() {
        return price;
    }

    public void setPrice(@NotBlank String price) {
        this.price = price;
    }

    public @NotBlank String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(@NotBlank String orderPrice) {
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

    public Map<String, String> getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(Map<String, String> otherInfo) {
        this.otherInfo = otherInfo;
    }

    public @NotBlank String getMd5() {
        return md5;
    }

    public void setMd5(@NotBlank String md5) {
        this.md5 = md5;
    }
}
