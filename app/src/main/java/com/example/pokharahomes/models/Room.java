package com.example.pokharahomes.models;

import java.io.Serializable;

public class Room implements Serializable {

    private int roomId, price, maxPersonAllowed;
    private String roomName, included, features, image1, image2;

    public Room(int roomId, int price, int maxPersonAllowed, String roomName, String included, String features, String image1, String image2) {
        this.price = price;
        this.maxPersonAllowed = maxPersonAllowed;
        this.roomId = roomId;
        this.roomName = roomName;
        this.included = included;
        this.features = features;
        this.image1 = image1;
        this.image2 = image2;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMaxPersonAllowed() {
        return maxPersonAllowed;
    }

    public void setMaxPersonAllowed(int maxPersonAllowed) {
        this.maxPersonAllowed = maxPersonAllowed;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getIncluded() {
        return included;
    }

    public void setIncluded(String included) {
        this.included = included;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
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
