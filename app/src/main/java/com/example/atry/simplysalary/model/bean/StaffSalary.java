package com.example.atry.simplysalary.model.bean;

public class StaffSalary {
    private String s_rtime;
    private String s_term;
    private String s_shift;

    public StaffSalary() {
    }

    public StaffSalary(String s_rtime, String s_term, String s_shift) {
        this.s_rtime = s_rtime;
        this.s_term = s_term;
        this.s_shift = s_shift;
    }

    public String getS_rtime() {
        return s_rtime;
    }

    public void setS_rtime(String s_rtime) {
        this.s_rtime = s_rtime;
    }

    public String getS_term() {
        return s_term;
    }

    public void setS_term(String s_term) {
        this.s_term = s_term;
    }

    public String getS_shift() {
        return s_shift;
    }

    public void setS_shift(String s_shift) {
        this.s_shift = s_shift;
    }

    @Override
    public String toString() {
        return "StaffSalary{" +
                "s_rtime='" + s_rtime + '\'' +
                ", s_term='" + s_term + '\'' +
                ", s_shift='" + s_shift + '\'' +
                '}';
    }
}
