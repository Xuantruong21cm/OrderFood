package com.example.orderfood.models;

public class Notification {
    public String _id ;
    public String title ;
    public String content ;

    public Notification(String _id, String title, String content) {
        this._id = _id;
        this.title = title;
        this.content = content;
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
