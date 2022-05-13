package com.example.pokharahomes.models;

import java.io.Serializable;

public class OrderHistory implements Serializable {

    private String orderedDateTime, foodName, quantity, roomNumber, totalAmount;

    public OrderHistory(String orderedDateTime, String foodName, String quantity, String roomNumber, String totalAmount) {
        this.orderedDateTime = orderedDateTime;
        this.foodName = foodName;
        this.quantity = quantity;
        this.roomNumber = roomNumber;
        this.totalAmount = totalAmount;
    }

    public String getOrderedDateTime() {
        return orderedDateTime;
    }

    public void setOrderedDateTime(String orderedDateTime) {
        this.orderedDateTime = orderedDateTime;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
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

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
