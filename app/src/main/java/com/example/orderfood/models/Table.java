package com.example.orderfood.models;

import java.util.ArrayList;
import java.util.List;

public class Table {
    ArrayList<Time> time ;
    String _id ;
    String nameTable ;
    int amount ;
    int status ;

    public Table(ArrayList<Time> time, String _id, String nameTable, int amount, int status) {
        this.time = time;
        this._id = _id;
        this.nameTable = nameTable;
        this.amount = amount;
        this.status = status;
    }

    public ArrayList<Time> getTime() {
        return time;
    }

    public void setTime(ArrayList<Time> time) {
        this.time = time;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNameTable() {
        return nameTable;
    }

    public void setNameTable(String nameTable) {
        this.nameTable = nameTable;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
