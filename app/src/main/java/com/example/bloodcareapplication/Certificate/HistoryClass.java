package com.example.bloodcareapplication.Certificate;

public class HistoryClass {

    private String date, bloodType, startTime, endTime, status, address;

    private static final HistoryClass instance = new HistoryClass();

    public static HistoryClass getInstance(){
        return instance;

    }

    public HistoryClass() {
        super();
    }

    public HistoryClass(String date, String bloodType, String startTime, String endTime, String address, String status){
        this.date = date;
        this.bloodType = bloodType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.address = address;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
