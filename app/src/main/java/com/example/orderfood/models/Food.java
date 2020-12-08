package com.example.orderfood.models;

public class Food {
    public String _id ;
    public String nameDish ;
    public String price ;
    public int time ;
    public int calories ;
    public int weight ;
    public String ingredient ;
    public String imageDish ;
    public String category ;

    public Food(String _id, String nameDish, String price, int time, int calories, int weight, String ingredient, String imageDish, String category) {
        this._id = _id;
        this.nameDish = nameDish;
        this.price = price;
        this.time = time;
        this.calories = calories;
        this.weight = weight;
        this.ingredient = ingredient;
        this.imageDish = imageDish;
        this.category = category;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNameDish() {
        return nameDish;
    }

    public void setNameDish(String nameDish) {
        this.nameDish = nameDish;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getImageDish() {
        return imageDish;
    }

    public void setImageDish(String imageDish) {
        this.imageDish = imageDish;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
