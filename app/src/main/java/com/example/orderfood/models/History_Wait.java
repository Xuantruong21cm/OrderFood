package com.example.orderfood.models;

import java.util.ArrayList;

public class History_Wait {
    public String _id;
    public ArrayList<Dish> dish;
    public String user;
    public int people;
    public String time;
    public int money;
    public int status;
    public int __v;

    public History_Wait(String _id, ArrayList<Dish> dish, String user, int people, String time, int money, int status, int __v) {
        this._id = _id;
        this.dish = dish;
        this.user = user;
        this.people = people;
        this.time = time;
        this.money = money;
        this.status = status;
        this.__v = __v;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public ArrayList<Dish> getDish() {
        return dish;
    }

    public void setDish(ArrayList<Dish> dish) {
        this.dish = dish;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}
