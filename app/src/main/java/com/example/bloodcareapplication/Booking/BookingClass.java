package com.example.bloodcareapplication.Booking;

public class BookingClass {

    private String startDate, endDate, startTime, endTime, station;

    private static final BookingClass instance = new BookingClass();

    public BookingClass(String startDate, String endDate, String startTime, String endTime) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }



    public BookingClass() {
        super();
    }

    public static BookingClass getInstance() {
        return instance;
    }

    public String getStation(){ return station;}

    public void setStation(String station) { this.station = station;}

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
}
