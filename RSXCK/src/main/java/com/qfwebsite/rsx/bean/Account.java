package com.qfwebsite.rsx.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户表
 *
 * @author Zhuangyuehui
 * @Date 2024.12.5
 */
@Entity
@Table(name = "account", indexes = {
        @Index(name = "i_account", columnList = "account", unique = true),
        @Index(name = "i_name_id", columnList = "name_id", unique = true),
})
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    /**
     * 自动递增的id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT(20) NOT NULL COMMENT 'id'")
    private Long id;

    /**
     * 账号
     */
    @Column(name = "account", columnDefinition = "VARCHAR(50) NOT NULL COMMENT '账号'")
    private String account;

    /**
     * 密码
     */
    @Column(name = "password", columnDefinition = "VARCHAR(50) NOT NULL COMMENT '密码'")
    private String password;

    /**
     * token
     */
    @Column(name = "token", columnDefinition = "VARCHAR(50) NOT NULL COMMENT 'token'")
    private String token;

    /**
     * 组名
     */
    @Column(name = "name", columnDefinition = "VARCHAR(50) NOT NULL COMMENT '组名'")
    private String name;

    /**
     * 权限ID（各自组名的ID编号，用于查询和修改的数据检索）
     */
    @Column(name = "name_id", columnDefinition = "INT(2) NOT NULL COMMENT '组名ID'")
    private Integer nameId;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
