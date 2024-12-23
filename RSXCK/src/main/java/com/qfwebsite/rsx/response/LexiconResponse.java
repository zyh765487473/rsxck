package com.qfwebsite.rsx.response;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 优惠卷返回对象
 *
 * @author Zhuangyuehui
 * @Date 2024.12.5
 */
public class LexiconResponse {

    /**
     * 自动递增的id
     */
    private Long id;

    /**
     * 品牌名
     */
    private String brandName;

    /**
     * 完整词
     */
    private String completeWord;

    /**
     * 独立站
     */
    private String independentStation;

    /**
     * 流量
     */
    private Integer flow;

    /**
     * 有效期
     */
    private String validity;

    /**
     * 上架状态（0.未上架，1.已上架）
     */
    private Integer state;

    /**
     * ID（各自组名的ID编号，用于查询和修改的数据检索）
     */
    private Integer nameId;

    /**
     * 组员名称
     */
    private String name;

    /**
     * 上架链接
     */
    private String link;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 公词状态（0.非公，1.公）
     */
    private Integer general;

    /**
     * 创建日期
     */
    private String createTime;

    /**
     * 国家
     */
    private String country;

    /**
     * 注册状态
     */
    private String registrationStatus;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCompleteWord() {
        return completeWord;
    }

    public void setCompleteWord(String completeWord) {
        this.completeWord = completeWord;
    }

    public String getIndependentStation() {
        return independentStation;
    }

    public void setIndependentStation(String independentStation) {
        this.independentStation = independentStation;
    }

    public Integer getFlow() {
        return flow;
    }

    public void setFlow(Integer flow) {
        this.flow = flow;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getNameId() {
        return nameId;
    }

    public void setNameId(Integer nameId) {
        this.nameId = nameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getGeneral() {
        return general;
    }

    public void setGeneral(Integer general) {
        this.general = general;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }
}
