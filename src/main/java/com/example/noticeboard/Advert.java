package com.example.noticeboard;

public class Advert {

    protected int id;
    protected String header;
    protected String description;
    protected double price;
    protected String url;
    protected int categoryId;
    protected int locationId;

    public Advert(int id, String header, String description, double price, String url, int categoryId, int locationId) {
        this.id = id;
        this.header = header;
        this.description = description;
        this.price = price;
        this.url = url;
        this.categoryId = categoryId;
        this.locationId = locationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
}
