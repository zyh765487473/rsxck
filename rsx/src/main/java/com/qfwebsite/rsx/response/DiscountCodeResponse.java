package com.qfwebsite.rsx.response;

import javax.persistence.Column;

/**
 * 优惠卷返回对象
 *
 * @author Zhuangyuehui
 * @Date 2024.06.20
 */
public class DiscountCodeResponse {

    /**
     * 现金
     */
    private Integer cash;

    /**
     * 折扣比例
     */
    private Integer proportion;

    /**
     * 优惠类型（0.现金，1.总价折扣）
     */
    private Integer discountType;

    /**
     * 优惠码
     */
    private String code;

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

    public Integer getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Integer discountType) {
        this.discountType = discountType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
