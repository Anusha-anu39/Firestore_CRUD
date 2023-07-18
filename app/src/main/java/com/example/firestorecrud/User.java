package com.example.firestorecrud;

public class User {
    String id, firstName, lastName, bio, phone;

    public User() {
    }

    public String getPhone() {
        return phone;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getId() {
        return id;
    }

    public String getBio() {
        return bio;
    }


    public void setId(String id) {
        this.id = id;
    }

}