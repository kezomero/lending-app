package com.example.agriculturalapplication;

public class ModelStore {

    //firstly define instance variables

    String name;
    String make;
    String category;
    String image;
    String cost;
    String id;

    //then create an constructor that will be called by MainActivity.java

    public ModelStore(String name,String id, String make, String category, String image, String cost) {
        this.name = name;
        this.id=id;
        this.make = make;
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
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
