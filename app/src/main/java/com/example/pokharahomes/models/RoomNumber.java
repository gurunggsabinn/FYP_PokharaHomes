package com.example.pokharahomes.models;

import java.io.Serializable;

public class RoomNumber implements Serializable {

    private int id, roomId;
    private String roomNumber;

    public RoomNumber(int id, int roomId, String roomNumber) {
        this.id = id;
        this.roomId = roomId;
        this.roomNumber = roomNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
