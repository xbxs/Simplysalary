package com.example.atry.simplysalary.model.bean;

public class SectionUser {
    private String name;
    private String phonenumber;
    private String allstatics;
    private String flag;
    public SectionUser() {
    }

    public SectionUser(String name, String phonenumber, String allstatics,String flag) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.allstatics = allstatics;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAllstatics() {
        return allstatics;
    }

    public void setAllstatics(String allstatics) {
        this.allstatics = allstatics;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "SectionUser{" +
                "name='" + name + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", allstatics='" + allstatics + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}
