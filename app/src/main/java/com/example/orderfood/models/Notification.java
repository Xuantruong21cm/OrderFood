package com.example.orderfood.models;

public class Notification {
    public String _id ;
    public String title ;
    public String content ;
    public String date ;

    public Notification(String _id, String title, String content,String date) {
        this._id = _id;
        this.title = title;
        this.content = content;
        this.date = date ;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
