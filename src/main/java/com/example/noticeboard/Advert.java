package com.example.noticeboard;

/// Advert class holds main info about each advertice
public class Advert {

    protected int id;
    protected String header;
    protected String description;
    protected double price;
    protected String url;
    protected int categoryId;
    protected int locationId;
    protected int userId;
    protected int adTypeId;

    public Advert(int id, String header, String description, double price, String url, int categoryId, int locationId, int userId, int adTypeId) {
        this.id = id;
        this.header = header;
        this.description = description;
        this.price = price;
        this.url = url;
        this.categoryId = categoryId;
        this.locationId = locationId;
        this.userId = userId;
        this.adTypeId = adTypeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAdTypeId() {
        return adTypeId;
    }

    public void setAdTypeId(int adTypeId) {
        this.adTypeId = adTypeId;
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
