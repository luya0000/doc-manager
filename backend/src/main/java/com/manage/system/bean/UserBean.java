package com.manage.system.bean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luya on 2018/6/5.
 */
public class UserBean {
    /*id*/
    private Integer id;
    /*账号名称*/
    private String account;
    /*密码*/
    private String password;
    /*用户名*/
    private String name;
    /*性别*/
    private String sex;
    /*年龄*/
    private Integer age;
    /*电话*/
    private String phone;
    /*邮箱*/
    private String email;
    /*备注*/
    private String note;
    /*用户状态，0有效；1无效*/
    private Integer status = 0;
    /*用户组*/
    private Integer group;
    /*用户角色*/
    private List<String> roles = new ArrayList<>();
    /*创建人*/
    private String createUser;
    /*创建时间*/
    private Timestamp createTime;
    /*修改人*/
    private String updateUser;
    /*修改时间*/
    private Timestamp updateTime;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }


    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
