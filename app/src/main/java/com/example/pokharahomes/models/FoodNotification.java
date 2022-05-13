package com.example.pokharahomes.models;

import java.io.Serializable;

public class FoodNotification implements Serializable {

    private String foodName, image, orderedDateTime, quantity, roomNumber;
    private int estimatedTime;

    public FoodNotification(String foodName, String image, String orderedDateTime, String quantity, String roomNumber, int estimatedTime) {
        this.foodName = foodName;
        this.image = image;
        this.orderedDateTime = orderedDateTime;
        this.quantity = quantity;
        this.roomNumber = roomNumber;
        this.estimatedTime = estimatedTime;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOrderedDateTime() {
        return orderedDateTime;
    }

    public void setOrderedDateTime(String orderedDateTime) {
        this.orderedDateTime = orderedDateTime;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
}
