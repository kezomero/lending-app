package com.example.agriculturalapplication;

public class ModelTool {

    //firstly define instance variables

    String name;
    String make;
    String category;
    String image;
    String cost;
    String overdueCost,contact,email,county,location,maxDays,status;

    //then create an constructor that will be called by MainActivity.java

    public ModelTool(String name, String make, String category, String image, String cost, String overdueCost, String contact, String email, String county, String location, String maxDays,String status) {
        this.name = name;
        this.make = make;
        this.category = category;
        this.image = image;
        this.overdueCost = overdueCost;
        this.contact = contact;
        this.email = email;
        this.county = county;
        this.location = location;
        this.maxDays = maxDays;
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

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
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

    public String getOverdueCost() {
        return overdueCost;
    }

    public void setOverdueCost(String overdueCost) {
        this.overdueCost = overdueCost;
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

    public String getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(String maxDays) {
        this.maxDays = maxDays;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

