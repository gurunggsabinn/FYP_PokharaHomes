package com.example.pokharahomes.models;

import java.io.Serializable;

public class Food implements Serializable {

    private int foodId, price, estimatedTime;
    private String foodName, ingredients, image1, image2;

    public Food(int foodId, int price, int estimatedTime, String foodName, String ingredients, String image1, String image2) {
        this.foodId = foodId;
        this.price = price;
        this.estimatedTime = estimatedTime;
        this.foodName = foodName;
        this.ingredients = ingredients;
        this.image1 = image1;
        this.image2 = image2;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }
}
