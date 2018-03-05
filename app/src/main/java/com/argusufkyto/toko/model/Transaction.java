package com.argusufkyto.toko.model;

/**
 * Created by ACER on 12/10/2017.
 */

public class Transaction {
    private String transactionID;
    private String uid;
    private String productID;
    private String productName;
    private double price;

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Transaction() {
    }

    public Transaction(String transactionID, String uid, String productID, String productName, double price) {
        this.transactionID = transactionID;
        this.uid = uid;
        this.productID = productID;
        this.productName = productName;
        this.price = price;
    }

    public Transaction(String transactionID, String uid, String productID) {
        this.transactionID = transactionID;
        this.uid = uid;
        this.productID = productID;
    }
}
