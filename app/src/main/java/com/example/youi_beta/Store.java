package com.example.youi_beta;

import android.graphics.Bitmap;

public class Store {
    private int ID;
    private Bitmap pic;
    private String picURL1;

    private String area;
    private String friendly;
    private String style;
    private String name;
    private String phone;
    private String address;
    private String time;
    private String latitude;
    private String longitude;
    private String info;
    private float rating;
    private String price;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    public String getPicURL1() {
        return picURL1;
    }

    public void setPicURL1(String picURL1) {
        this.picURL1 = picURL1;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFriendly() {
        return friendly;
    }

    public void setFriendly(String friendly) {
        this.friendly = friendly;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Store(int ID, Bitmap pic, String picURL1, String area, String friendly, String style, String name, String phone, String address, String time, String latitude, String longitude, String info, float rating, String price) {
        this.ID = ID;
        this.pic = pic;
        this.picURL1 = picURL1;
        this.area = area;
        this.friendly = friendly;
        this.style = style;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.info = info;
        this.rating = rating;
        this.price = price;
    }
}

