package com.example.bloodcareapplication.Booking;

public class BookingClass {

    private String startDate, bloodtype, startTime, endTime, duration, station;

    private static final BookingClass instance = new BookingClass();

    public BookingClass(String startDate, String bloodtype, String startTime, String endTime) {
        super();
        this.startDate = startDate;
        this.bloodtype = bloodtype;
        this.startTime = startTime;
        this.endTime = endTime;
    }



    public BookingClass() {
        super();
    }

    public static BookingClass getInstance() {
        return instance;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getBloodtype() {
        return bloodtype;
    }

    public void setBloodtype(String bloodtype) {
        this.bloodtype = bloodtype;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
