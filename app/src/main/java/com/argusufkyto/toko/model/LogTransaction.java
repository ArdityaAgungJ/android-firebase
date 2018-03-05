package com.argusufkyto.toko.model;

/**
 * Created by ACER on 12/12/2017.
 */

public class LogTransaction {
    private String uid,pid,id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public LogTransaction() {
    }

    public LogTransaction(String uid, String pid,String id) {
        this.uid = uid;
        this.pid = pid;
        this.id = id;
    }
}
