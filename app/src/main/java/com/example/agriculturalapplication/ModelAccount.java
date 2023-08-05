package com.example.agriculturalapplication;

public class ModelAccount {

    //firstly define instance variables

    String fName;
    String sName;
    String oName;
    String email;
    String id;
    String gender,phone,county;

    //then create an constructor that will be called by MainActivity.java

    public ModelAccount(String fName, String sName, String oName, String email, String gender, String id, String phone, String county) {
        this.fName = fName;
        this.sName = sName;
        this.oName = oName;
        this.email = email;
        this.gender = gender;
        this.id = id;
        this.phone = phone;
        this.county = county;
    }

    //then create getter and setter methods


    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getoName() {
        return oName;
    }

    public void setoName(String oName) {
        this.oName = oName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }
}

