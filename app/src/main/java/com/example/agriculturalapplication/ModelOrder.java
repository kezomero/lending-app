package com.example.agriculturalapplication;

public class ModelOrder {

    //firstly define instance variables

    String name;
    String status;
    String days;
    String contact;
    String image;
    String id;

    //then create an constructor that will be called by MainActivity.java

    public ModelOrder(String name,String id, String status, String days, String image, String contact) {
        this.name = name;
        this.id=id;
        this.status = status;
        this.days = days;
        this.image = image;
        this.contact = contact;
    }

    //then create getter and setter methods


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
