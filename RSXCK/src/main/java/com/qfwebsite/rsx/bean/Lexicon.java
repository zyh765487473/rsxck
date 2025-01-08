package com.qfwebsite.rsx.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 词库表
 *
 * @author Zhuangyuehui
 * @Date 2024.12.5
 */
@Entity
@Table(name = "lexicon", indexes = {
        @Index(name = "u_brand_name", columnList = "brand_name"),
        @Index(name = "u_name_id", columnList = "name_id"),
        @Index(name = "u_general", columnList = "general"),
        @Index(name = "u_state", columnList = "state"),
})
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lexicon {

    // 已完成全部
    public static final Integer TWO_STATE = 2;

    // 已上主图
    public static final Integer ONE_STATE = 1;

    // 未上架
    public static final Integer NOT_STATE = 0;

    // 流拍
    public static final Integer LOSS_GENERAL = 2;

    // 公
    public static final Integer YES_GENERAL = 1;

    // 非公
    public static final Integer NOT_GENERAL = 0;


    /**
     * 自动递增的id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT(20) NOT NULL COMMENT 'id'")
    private Long id;

    /**
     * 品牌名
     */
    @Column(name = "brand_name", columnDefinition = "VARCHAR(255) NOT NULL COMMENT '品牌'")
    private String brandName;

    /**
     * 是否注册
     */
    @Column(name = "registration_status", columnDefinition = "VARCHAR(10) default '未注册' NOT NULL COMMENT '是否注册'")
    private String registrationStatus;

    /**
     * 品牌名（去空格，去除大小写）
     */
    @Column(name = "brand_name_hand", columnDefinition = "VARCHAR(255) NOT NULL COMMENT '品牌名（去空格，去除大小写）'")
    private String brandNameHand;

    /**
     * 完整词
     */
    @Column(name = "complete_word", columnDefinition = "TEXT NOT NULL COMMENT '完整词'")
    private String completeWord;

    /**
     * 独立站
     */
    @Column(name = "independent_station", columnDefinition = "TEXT COMMENT '独立站'")
    private String independentStation;

    /**
     * 流量
     */
    @Column(name = "flow", columnDefinition = "INT(11) NOT NULL COMMENT '流量'")
    private Integer flow;

    /**
     * 有效期
     */
    @Column(name = "validity")
    private Date validity;

    /**
     * 上架状态（0.未上架，1.已上架）
     */
    @Column(name = "state", columnDefinition = "INT(2) NOT NULL COMMENT '上架状态'")
    private Integer state;

    /**
     * 国家（0.美国，1.英国）
     */
    @Column(name = "country", columnDefinition = "VARCHAR(20) default '美国' NOT NULL COMMENT '国家'")
    private String country;

    /**
     * ID（各自组名的ID编号，用于查询和修改的数据检索）
     */
    @Column(name = "name_id", columnDefinition = "INT(2) NOT NULL COMMENT 'ID'")
    private Integer nameId;

    /**
     * 上架链接
     */
    @Column(name = "link", columnDefinition = "TEXT COMMENT '上架链接'")
    private String link;

    /**
     * 备注
     */
    @Column(name = "remarks", columnDefinition = "TEXT COMMENT '备注'")
    private String remarks;

    /**
     * 公词状态（0.非公，1.公）
     */
    @Column(name = "general", columnDefinition = "INT(2) COMMENT '公词状态'")
    private Integer general;

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

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBrandNameHand() {
        return brandNameHand;
    }

    public void setBrandNameHand(String brandNameHand) {
        this.brandNameHand = brandNameHand;
    }

    public String getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }
}
