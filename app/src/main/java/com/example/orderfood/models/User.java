package com.example.orderfood.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("msg")
    public String msg ;

    @SerializedName("token")
    public String token ;

    @SerializedName("permission")
    public String permission ;

    @SerializedName("username")
    public String username ;

    public User(String msg, String token, String permission, String username) {
        this.msg = msg;
        this.token = token;
        this.permission = permission;
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
