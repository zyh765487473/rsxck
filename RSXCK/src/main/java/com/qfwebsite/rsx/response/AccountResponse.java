package com.qfwebsite.rsx.response;

/**
 * 优惠卷返回对象
 *
 * @author Zhuangyuehui
 * @Date 2024.12.5
 */
public class AccountResponse {

    /**
     * 账号
     */
    private String account;

    /**
     * token
     */
    private String token;

    /**
     * 组名
     */
    private String name;

    /**
     * 权限ID（各自组名的ID编号，用于查询和修改的数据检索）
     */
    private Integer nameId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNameId() {
        return nameId;
    }

    public void setNameId(Integer nameId) {
        this.nameId = nameId;
    }
}
