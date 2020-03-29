package com.example.atry.simplysalary.model.bean;

public class PickUserInfo {
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private User user;
    private boolean isChecked;

    public PickUserInfo(User user, boolean isChecked) {
        this.user = user;
        this.isChecked = isChecked;
    }

    public PickUserInfo() {
    }
}
