package com.argusufkyto.toko.helpers;

import android.app.Application;

/**
 * Created by ACER on 12/4/2017.
 */

public class PenampunganGlobal extends Application {
    private String id;
    private String name;
    private double price;
    private String imageFile;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    private double totalPrice;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }


    public String getId(){
        return this.id;
    }

    public void setId(String d){
        this.id =d;
    }
}