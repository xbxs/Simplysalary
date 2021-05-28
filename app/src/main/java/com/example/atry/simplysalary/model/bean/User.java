package com.example.atry.simplysalary.model.bean;

/**
 * 李维:
 * 邮箱: 3182430026@qq.com
 */
public class User {
    //账号
    private String phonenumber;
    //昵称
    private String name;
    //图片
    private String picture;
    //标识 0为员工 1为老板
    private int flag;
    //部门
    private String department;
    //底薪
    private int u_bas;
    //时薪
    private int u_wage;
    public User() {
    }

    public User(String phonenumber, String name, String picture, int flag, String department, int u_bas, int u_wage) {
        this.phonenumber = phonenumber;
        this.name = name;
        this.picture = picture;
        this.flag = flag;
        this.department = department;
        this.u_bas = u_bas;
        this.u_wage = u_wage;
    }

    public int getU_bas() {
        return u_bas;
    }

    public void setU_bas(int u_bas) {
        this.u_bas = u_bas;
    }

    public int getU_wage() {
        return u_wage;
    }

    public void setU_wage(int u_wage) {
        this.u_wage = u_wage;
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


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "User{" +
                "phonenumber='" + phonenumber + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", flag=" + flag +
                ", department='" + department + '\'' +
                ", u_bas='" + u_bas + '\'' +
                ", u_wage='" + u_wage + '\'' +
                '}';
    }
}
