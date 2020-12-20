package com.example.orderfood.models;

public class Hour {
    String _id ;
    String startingTime ;
    String endTime ;
    int slot  ;

    public Hour(String _id, String startingTime, String endTime,int slot) {
        this._id = _id;
        this.startingTime = startingTime;
        this.endTime = endTime;
        this.slot = slot ;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
