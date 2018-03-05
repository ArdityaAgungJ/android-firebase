package com.argusufkyto.toko.model;

public class Sepatu {
    private String id;
    private String name;
    private double price;
    private String imageFile;
    private byte[] image;


    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public Sepatu(String name, double price, String imageFile, String id) {
        this.name = name;
        this.price = price;
        this.imageFile = imageFile;
        this.id = id;
    }

    public Sepatu(){

    }

    public Sepatu(Sepatu sepatu){
        this.name = sepatu.getName();
        this.price = sepatu.getPrice();
        this.imageFile = sepatu.getImageFile();
        this.id = sepatu.getId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public byte[] getImage() {
        return image;
    }


    public void setImage(byte[] image) {
        this.image = image;
    }
}
