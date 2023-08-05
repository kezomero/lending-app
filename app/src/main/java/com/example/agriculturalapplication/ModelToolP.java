package com.example.agriculturalapplication;

public class ModelToolP {

    //firstly define instance variables

    String name;
    String quantity;
    String category;
    String image;
    String cost;
    String contact,email,county,location,status;

    //then create an constructor that will be called by MainActivity.java

    public ModelToolP(String name, String quantity, String category, String image, String cost,  String contact, String email, String county, String location, String status) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.image = image;
        this.contact = contact;
        this.email = email;
        this.county = county;
        this.location = location;
        this.cost = cost;
        this.status = status;
    }

    //then create getter and setter methods


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getlocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

