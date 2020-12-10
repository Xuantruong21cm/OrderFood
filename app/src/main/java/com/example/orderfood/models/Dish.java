package com.example.orderfood.models;

public class Dish {
    public String _id ;
    public String imageDish ;
    public String nameDish ;
    public int amount ;

    public Dish(String _id, String imageDish, String nameDish, int amount) {
        this._id = _id;
        this.imageDish = imageDish;
        this.nameDish = nameDish;
        this.amount = amount;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getImageDish() {
        return imageDish;
    }

    public void setImageDish(String imageDish) {
        this.imageDish = imageDish;
    }

    public String getNameDish() {
        return nameDish;
    }

    public void setNameDish(String nameDish) {
        this.nameDish = nameDish;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
