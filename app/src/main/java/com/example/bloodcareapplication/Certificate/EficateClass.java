package com.example.bloodcareapplication.Certificate;

public class EficateClass {

    private String name, ic, bloodType, date, status, address;

    private static final EficateClass instance = new EficateClass();

    public static EficateClass getInstance(){
        return instance;

    }

    public EficateClass() {
        super();
    }

    public EficateClass(String date, String bloodType, String address, String name, String ic, String status){

        this.date = date;
        this.bloodType = bloodType;
        this.address = address;
        this.name = name;
        this.ic = ic;
        this.status = status;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
