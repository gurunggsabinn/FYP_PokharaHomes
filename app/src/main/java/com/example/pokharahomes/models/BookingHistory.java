package com.example.pokharahomes.models;

import java.io.Serializable;

public class BookingHistory implements Serializable {

    private String bookedDateTime, roomNumber, checkedInDate, checkedOutDate, packageName, paymentMethod, totalAmount;

    public BookingHistory(String bookedDateTime, String roomNumber, String checkedInDate, String checkedOutDate, String packageName, String paymentMethod, String totalAmount) {
        this.bookedDateTime = bookedDateTime;
        this.roomNumber = roomNumber;
        this.checkedInDate = checkedInDate;
        this.checkedOutDate = checkedOutDate;
        this.packageName = packageName;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
    }

    public String getBookedDateTime() {
        return bookedDateTime;
    }

    public void setBookedDateTime(String bookedDateTime) {
        this.bookedDateTime = bookedDateTime;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getCheckedInDate() {
        return checkedInDate;
    }

    public void setCheckedInDate(String checkedInDate) {
        this.checkedInDate = checkedInDate;
    }

    public String getCheckedOutDate() {
        return checkedOutDate;
    }

    public void setCheckedOutDate(String checkedOutDate) {
        this.checkedOutDate = checkedOutDate;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
