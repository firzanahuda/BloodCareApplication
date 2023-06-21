package com.example.bloodcareapplication.Certificate;

public class CurrentClass {

    private String date, startTime, endTime, status, ID, QRcode;

    private static final CurrentClass instance = new CurrentClass();

    public static CurrentClass getInstance(){
        return instance;

    }

    public CurrentClass() {
        super();
    }

    public CurrentClass(String ID, String date, String startTime, String endTime, String QRCCode) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.ID =ID;
        this.QRcode = QRCCode;
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getQRcode() {
        return QRcode;
    }

    public void setQRcode(String QRcode) {
        this.QRcode = QRcode;
    }
}
