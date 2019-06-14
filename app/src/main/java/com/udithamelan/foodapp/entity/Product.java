package com.udithamelan.foodapp.entity;

public class Product {
    int id;
    String name;
    String source_url;
    String image_url;

    public Product(int id, String name, String source_url, String image_url) {
        this.id = id;
        this.name = name;
        this.source_url = source_url;
        this.image_url = image_url;
    }

    public Product(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
