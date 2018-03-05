package com.argusufkyto.toko.model;

public class User {

    private String id;
    private String name;
    private String email;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private String role;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }

    public User(String id, String email, String name, String role) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
