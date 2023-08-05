package com.example.agriculturalapplication;

public class ModelStoreP {

    //firstly define instance variables

    String name;
    String quantity;
    String category;
    String image;
    String cost;
    String id;

    //then create an constructor that will be called by MainActivity.java

    public ModelStoreP(String name,String id, String quantity, String category, String image, String cost) {
        this.name = name;
        this.id=id;
        this.quantity = quantity;
        this.category = category;
        this.image = image;
        this.cost = cost;
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
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
