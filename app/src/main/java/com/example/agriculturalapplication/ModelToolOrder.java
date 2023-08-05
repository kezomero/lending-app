package com.example.agriculturalapplication;

public class ModelToolOrder {

    //firstly define instance variables

    String itemName;
    String status;
    String lenderContact;
    String hiererName;
    String hiererContact;
    String idImage,days,farm,totalCost,totalOverdueCost,odays;

    //then create an constructor that will be called by MainActivity.java

    public ModelToolOrder(String itemName, String status, String lenderContact, String hiererName, String hiererContact, String idImage, String days, String farm, String totalCost, String totalOverdueCost,String odays) {
        this.itemName = itemName;
        this.status = status;
        this.lenderContact = lenderContact;
        this.hiererName = hiererName;
        this.hiererContact = hiererContact;
        this.idImage = idImage;
        this.days = days;
        this.farm = farm;
        this.totalCost = totalCost;
        this.totalOverdueCost = totalOverdueCost;
        this.odays = odays;
    }

    //then create getter and setter methods


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLenderContact() {
        return lenderContact;
    }

    public void setLenderContact(String lenderContact) {
        this.lenderContact = lenderContact;
    }

    public String getHiererName() {
        return hiererName;
    }

    public void setHiererName(String hiererName) {
        this.hiererName = hiererName;
    }

    public String getHiererContact() {
        return hiererContact;
    }

    public void setHiererContact(String hiererContact) {
        this.hiererContact = hiererContact;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getTotalOverdueCost() {
        return totalOverdueCost;
    }

    public void setTotalOverdueCost(String totalOverdueCost) {
        this.totalOverdueCost = totalOverdueCost;
    }

    public String getOdays() {
        return odays;
    }

    public void setODays(String odays) {
        this.odays = odays;
    }
}

