package com.qfwebsite.rsx.request;

import com.sun.istack.NotNull;

import javax.validation.constraints.NotBlank;

/**
 * 词库更新数据参数
 *
 * @author Zhuangyuehui
 * @Date 2024.12.6
 */
public class LexiconUpdateRequest {

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
     * 词id
     */
    @NotNull
    private Long id;

    /**
     * 上架链接
     */
    @NotBlank
    private String link;

    /**
     * 注册状态
     */
    @NotBlank
    private String registrationStatus;

    /**
     * 备注
     */
    @NotBlank
    private String remarks;

    /**
     * 更新类型
     * 0,上架状态更新
     * 1，注册状态更新
     */
    @NotBlank
    private String type;

    @NotBlank
    private String md5;

    public @NotBlank String getToken() {
        return token;
    }

    public void setToken(@NotBlank String token) {
        this.token = token;
    }

    public Integer getNameId() {
        return nameId;
    }

    public void setNameId(Integer nameId) {
        this.nameId = nameId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank String getLink() {
        return link;
    }

    public void setLink(@NotBlank String link) {
        this.link = link;
    }

    public @NotBlank String getMd5() {
        return md5;
    }

    public void setMd5(@NotBlank String md5) {
        this.md5 = md5;
    }

    public @NotBlank String getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(@NotBlank String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public @NotBlank String getType() {
        return type;
    }

    public void setType(@NotBlank String type) {
        this.type = type;
    }

    public @NotBlank String getRemarks() {
        return remarks;
    }

    public void setRemarks(@NotBlank String remarks) {
        this.remarks = remarks;
    }
}
