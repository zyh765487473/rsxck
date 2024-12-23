package com.qfwebsite.rsx.request;

import com.sun.istack.NotNull;

import javax.validation.constraints.NotBlank;

/**
 * 词库数据参数
 *
 * @author Zhuangyuehui
 * @Date 2024.12.6
 */
public class LexiconRequest {

    /**
     * token
     */
    @NotBlank
    private String token;


    /**
     * 用户ID
     */
    @NotNull
    private Integer nameId;

    /**
     * 品牌名
     */
    @NotBlank
    private String brandName;

    /**
     * 完整词
     */
    @NotBlank
    private String completeWord;

    /**
     * 独立站
     */
    @NotBlank
    private String independentStation;

    /**
     * 国家
     */
    @NotNull
    private String country;

    /**
     * 提交时的流量大小
     */
    @NotNull
    private Integer flow;

    @NotBlank
    private String md5;

    public @NotBlank String getBrandName() {
        return brandName;
    }

    public void setBrandName(@NotBlank String brandName) {
        this.brandName = brandName;
    }

    public @NotBlank String getCompleteWord() {
        return completeWord;
    }

    public void setCompleteWord(@NotBlank String completeWord) {
        this.completeWord = completeWord;
    }

    public String getIndependentStation() {
        return independentStation;
    }

    public void setIndependentStation(String independentStation) {
        this.independentStation = independentStation;
    }

    public @NotBlank String getMd5() {
        return md5;
    }

    public void setMd5(@NotBlank String md5) {
        this.md5 = md5;
    }

    public @NotBlank String getToken() {
        return token;
    }

    public void setToken(@NotBlank String token) {
        this.token = token;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getFlow() {
        return flow;
    }

    public void setFlow(Integer flow) {
        this.flow = flow;
    }

    public Integer getNameId() {
        return nameId;
    }

    public void setNameId(Integer nameId) {
        this.nameId = nameId;
    }
}

