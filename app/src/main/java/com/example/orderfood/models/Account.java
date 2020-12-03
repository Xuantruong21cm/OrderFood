package com.example.orderfood.models;

public class Account {
    public String phone ;
    public String password ;
    public String fullname ;
    public String permission ;

    public Account(String phone, String password, String fullname, String permission) {
        this.phone = phone;
        this.password = password;
        this.fullname = fullname;
        this.permission = permission;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
