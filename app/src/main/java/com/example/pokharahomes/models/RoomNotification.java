package com.example.pokharahomes.models;

import java.io.Serializable;

public class RoomNotification implements Serializable {

    private String roomName, roomNumber, image, status, bookedDate;

    public RoomNotification(String roomName, String roomNumber, String image, String status, String bookedDate) {
        this.roomName = roomName;
        this.roomNumber = roomNumber;
        this.image = image;
        this.status = status;
        this.bookedDate = bookedDate;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(String bookedDate) {
        this.bookedDate = bookedDate;
    }
}
