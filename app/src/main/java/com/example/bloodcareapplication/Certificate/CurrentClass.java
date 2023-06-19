package com.example.bloodcareapplication.Certificate;

public class CurrentClass {

    private String date, startTime, endTime, status;

    private static final CurrentClass instance = new CurrentClass();

    public static CurrentClass getInstance(){
        return instance;

    }

    public CurrentClass() {
        super();
    }

    public CurrentClass(String date, String startTime, String endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        //this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
}
