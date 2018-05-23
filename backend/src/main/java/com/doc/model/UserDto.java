package com.doc.model;

import java.sql.Date;
import java.util.List;

public class UserDto {
    /*id*/
    private Integer id;
    /*账号名称*/
    private String account;
    /*用户名id*/
    private String name;
    /*密码*/
    private String pwd;
    /*邮箱*/
    private String email;
    /*电话*/
    private String phone;
    /*用户状态，0有效；1无效*/
    private Integer status = 0;
    /*备注*/
    private String note;
    /*创建时间*/
    private Date ctrateTime;
    /*修改时间*/
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCtrateTime() {
        return ctrateTime;
    }

    public void setCtrateTime(Date ctrateTime) {
        this.ctrateTime = ctrateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
