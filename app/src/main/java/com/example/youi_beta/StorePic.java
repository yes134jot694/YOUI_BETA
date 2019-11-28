package com.example.youi_beta;

public class StorePic {

    private int ID;
    String PicUrl;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public StorePic(int ID, String picUrl) {
        this.ID = ID;
        PicUrl = picUrl;
    }

}

