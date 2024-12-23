package com.qfwebsite.rsx.request;

import com.sun.istack.NotNull;

import javax.validation.constraints.NotBlank;

/**
 * 词库更新数据参数
 *
 * @author Zhuangyuehui
 * @Date 2024.12.17
 */
public class LexiconUpdateAdminRequest {

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

    @NotBlank
    private String md5;

    @NotNull
    private Integer flow;

    /**
     * 用户ID
     */
    @NotNull
    private Integer aNameId;

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

    public @NotBlank String getMd5() {
        return md5;
    }

    public void setMd5(@NotBlank String md5) {
        this.md5 = md5;
    }

    public Integer getaNameId() {
        return aNameId;
    }

    public void setaNameId(Integer aNameId) {
        this.aNameId = aNameId;
    }

    public Integer getFlow() {
        return flow;
    }

    public void setFlow(Integer flow) {
        this.flow = flow;
    }
}
