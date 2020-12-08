package com.example.orderfood.models;

public class ListDish {
    public String nameDish ;
    public String dishid ;
    public int count ;
    public int cost ;
    public String price ;
    public String imageDish ;

    public ListDish(String nameDish, String dishid, int count, int cost, String price,String imageDish) {
        this.nameDish = nameDish;
        this.dishid = dishid;
        this.count = count;
        this.cost = cost;
        this.price = price;
        this.imageDish = imageDish ;
    }

    public String getNameDish() {
        return nameDish;
    }

    public void setNameDish(String nameDish) {
        this.nameDish = nameDish;
    }

    public String getDishid() {
        return dishid;
    }

    public void setDishid(String dishid) {
        this.dishid = dishid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageDish() {
        return imageDish;
    }

    public void setImageDish(String imageDish) {
        this.imageDish = imageDish;
    }
}
