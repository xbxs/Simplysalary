package com.example.atry.simplysalary.model.bean;

/**
 * 李维: TZZ on 2019-11-06 18:38
 * 邮箱: 3182430026@qq.com
 */
public class User {
    private String phonenumber;
    private String name;
    private String picture;
    private int flag;
    private int bossnumber;
    private String department;

    public User() {
    }

    public User(String phonenumber, String name, String picture, int flag, int bossnumber, String department) {
        this.phonenumber = phonenumber;
        this.name = name;
        this.picture = picture;
        this.flag = flag;
        this.bossnumber = bossnumber;
        this.department = department;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getBossnumber() {
        return bossnumber;
    }

    public void setBossnumber(int bossnumber) {
        this.bossnumber = bossnumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "User{" +
                "phonenumber=" + phonenumber +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", flag=" + flag +
                ", bossnumber=" + bossnumber +
                ", department='" + department + '\'' +
                '}';
    }
}