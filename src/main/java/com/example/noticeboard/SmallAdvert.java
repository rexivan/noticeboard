package com.example.noticeboard;

public class SmallAdvert {
    protected String header;
    protected String description;
    protected double price;

    public SmallAdvert(String header, String description, double price) {
        this.header = header;
        this.description = description;
        this.price = price;
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
}

